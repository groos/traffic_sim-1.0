package main;

import animator.SwingAnimator;
import animator.SwingAnimatorBuilder;
import animator.TextAnimatorBuilder;
import light.LightControllerObj;
import tServer.TimeServerLinked;

public class CmdBuildGrid {
	private LightControllerObj[][] LCArray;
	private TimeServerLinked TS;
	private boolean pattern;
	private SimParams sm;
	
	public CmdBuildGrid(TimeServerLinked TS, SimParams sm){
		this.TS = TS;
		this.sm = sm;
		this.pattern = sm.TRAFFIC_PATTERN;
		this.LCArray = new LightControllerObj[sm.GRID_ROWS][sm.GRID_COLUMNS];
	}
	
	public boolean run(){
		CmdMakeSegments make;
		boolean pattern;
		
		LCArray = main.makeArrays(sm.GRID_ROWS, sm.GRID_COLUMNS);
		
		
		if (sm.TRAFFIC_PATTERN == false) {
			pattern = true;
			for (int i = 0; i < sm.GRID_ROWS; i++){
				
				make = new CmdMakeSegments(LCArray[i], DIRECTION.EW, TS, i, 0, pattern);
				make.run();
			}
			LCArray = main.rotateArrays(LCArray);
			for (int i = 0; i < sm.GRID_COLUMNS; i++){
				
				make = new CmdMakeSegments(LCArray[i], DIRECTION.NS, TS, -1, i, pattern);
				make.run();
			}
		}
		if (sm.TRAFFIC_PATTERN == true) {
			pattern = true;
			for (int i = 0; i < sm.GRID_ROWS; i++){
				
				make = new CmdMakeSegments(LCArray[i], DIRECTION.EW, TS, i, 0, pattern);
				make.run();
				pattern = !pattern;
			}
			LCArray = main.rotateArrays(LCArray);
			pattern = true;
			for (int i = 0; i < sm.GRID_COLUMNS; i++){
			
				make = new CmdMakeSegments(LCArray[i], DIRECTION.NS, TS, -1, i, pattern);
				make.run();
				pattern = !pattern;
			}
		}
		
		for (int i = 0; i < sm.GRID_COLUMNS; i++){
			for (int c = 0; c < sm.GRID_ROWS; c++){
				LCArray[i][c].setPartners();
			}
		}
		
		return true;
	}
	
}
