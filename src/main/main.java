package main;

import animator.SwingAnimator;
import animator.SwingAnimatorBuilder;
import animator.TextAnimatorBuilder;
import light.LightControllerObj;
import tServer.TimeServerLinked;
import ui.TextUI;
import data.ObjFactory;


public class main {

	static public TimeServerLinked TS = ObjFactory.makeTimeServer();
	static public SwingAnimatorBuilder builder = new SwingAnimatorBuilder();
	static public TextAnimatorBuilder textBuilder;

	
	static public LightControllerObj[][] rotateArrays(LightControllerObj[][] matrix)
	{
	  
	    int w = matrix.length;
	    int h = matrix[0].length;
	    LightControllerObj[][] ret = new LightControllerObj[h][w];
	    for (int i = 0; i < h; ++i) {
	        for (int j = 0; j < w; ++j) {
	            ret[i][j] = matrix[w - j - 1][i];
	        }
	    }
	    return ret;
	}
	
	static public LightControllerObj[][] makeArrays(int x, int y){
		LightControllerObj [][] LCArray = new LightControllerObj[x][y];
		
		for (int i = 0; i < x; i++){
			for (int z = 0; z < y; z++){
				LCArray[i][z] = ObjFactory.makeLightController(TS);
			}
		}
		
		return LCArray;
	}

	public static void main(String[] args){
		SimParams sm = new SimParams();
		TextUI ui = new TextUI();
		
		Control myControl = new Control(TS, ui, sm);		
		myControl.run();

	}
	
}
