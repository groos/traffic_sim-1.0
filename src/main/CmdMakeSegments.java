package main;

import tServer.TimeServerLinked;
import data.CarAcceptor;
import data.ObjFactory;
import data.Road;
import light.Light;
import light.LightControllerObj;


public class CmdMakeSegments implements Command{
	private LightControllerObj[] LCArray;
	private DIRECTION dir;
	private TimeServerLinked TS;
	private int row;
	private int column;
	private boolean pattern;
	
	public CmdMakeSegments(LightControllerObj[] LCArray, DIRECTION dir, TimeServerLinked TS, int row, int column, boolean direction){
		this.LCArray = LCArray;
		this.dir = dir;
		this.TS = TS;
		this.row = row;
		this.column = column;
	}

	@Override
	public boolean run() {
		boolean direction = pattern;
			
			if (row != -1){
				int r = row; // using 1 based indexing for rows and columns
				int j = SimParams.GRID_COLUMNS;// + 1;
				
				CarAcceptor next = ObjFactory.makeSink();
				Road lastRoad = ObjFactory.makeRoad(next, dir);
				main.builder.addHorizontalRoad(lastRoad, r, j, pattern);
				main.textBuilder.addHorizontalRoad(lastRoad, r, j, pattern);
				
				for (int i = LCArray.length - 1; i >= 0; i--){
					j--;
					LCArray[i].addRoad(lastRoad);
					Light l = LCArray[i].createLight(dir);
					main.builder.addLight(l, r, j);
					main.textBuilder.addLight(l, r, j);
					
					lastRoad = ObjFactory.makeRoad(l, dir);
					main.builder.addHorizontalRoad(lastRoad, r, j, pattern);
					main.textBuilder.addHorizontalRoad(lastRoad, r, j, pattern);
				}
				ObjFactory.makeSource(lastRoad, TS);
				lastRoad.setEndPosition(null);
			}
			else { // vertical road
				int r = SimParams.GRID_ROWS;// + 1;
				int j = column;
				
				CarAcceptor next = ObjFactory.makeSink();
				Road lastRoad = ObjFactory.makeRoad(next, dir);
				main.builder.addVerticalRoad(lastRoad, r, j, pattern);
				main.textBuilder.addVerticalRoad(lastRoad, r, j, pattern);
				
				
				for (int i = LCArray.length - 1; i >= 0; i--){
					r--;
					LCArray[i].addRoad(lastRoad);
					Light l = LCArray[i].createLight(dir);
					main.builder.addLight(l, r, j);
					main.textBuilder.addLight(l, r, j);
					
					lastRoad = ObjFactory.makeRoad(l, dir);
					main.builder.addVerticalRoad(lastRoad, r, j, pattern);
					main.textBuilder.addVerticalRoad(lastRoad, r, j, pattern);
				}
				ObjFactory.makeSource(lastRoad, TS);
				lastRoad.setEndPosition(null);
			}
		
		return true;
	}

}
