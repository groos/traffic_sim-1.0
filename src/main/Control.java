package main;

import animator.Animator;
import animator.SwingAnimator;
import animator.SwingAnimatorBuilder;
import animator.TextAnimatorBuilder;
import tServer.TimeServerLinked;
import ui.TextUI;
import ui.UI;
import ui.UIForm;
import ui.UIFormBuilder;
import ui.UIFormTest;
import ui.UIMenu;
import ui.UIMenuAction;
import ui.UIMenuBuilder;

public class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int MODIFY = 3;
	private static final int numberOfMenus = 5; // but only for first menu?
	private int _state;
	
	private TimeServerLinked TS;
	private UI ui;
	private SimParams params;
	private TextUI textUI;
	
	private UIMenu[] menus;
	public static SwingAnimator SA;
	private TextAnimatorBuilder TB;
	public static Animator A;

	
	Control(TimeServerLinked TS, UI ui, SimParams sm){
		this.TS = TS;
		this.ui = ui;
		this.textUI = (TextUI) ui;
		this.params = sm;
		
		menus = new UIMenu[numberOfMenus];
		_state = START;
		addSTART(START);
		addEXIT(EXIT);
		addMODIFY(MODIFY);
		
	}
	
	void run(){
		try{
			while (_state != EXITED){

				ui.processMenu(menus[_state]);
				
			}
		} catch (IllegalArgumentException e){
			ui.displayError("UI closed");
		}
	}
	
	private void addSTART(int stateNum){
		UIMenuBuilder m = new UIMenuBuilder();
		
		m.add("Default", 
			new UIMenuAction(){
				public void run(){
					ui.displayError("doh!");
				}
		});
		m.add("Run Simulation",
			new UIMenuAction(){
				public void run(){
					main.builder = new SwingAnimatorBuilder();
					main.textBuilder = new TextAnimatorBuilder();
					TB = main.textBuilder;
					SA = new SwingAnimator(main.builder._painter, "Traffic_Sim", 768, 550, 1);
					
					System.out.flush();
					CmdBuildGrid grid = new CmdBuildGrid(TS, params);
					grid.run();
					
					A = TB.getAnimator();
					TS.run(params.SIM_DURATION);
				}
		});
		m.add("Change simulation parameters", 
			new UIMenuAction(){
				public void run(){
					System.out.flush();
					_state = MODIFY;
				}
		});
		m.add("Exit", 
			new UIMenuAction(){
				public void run(){
					System.out.flush();
					A.dispose();
					SA.dispose();
					_state = EXIT;
				}
		});
		
		menus[stateNum] = m.toUIMenu("Traffic Simulation");
	}
	
	private void addMODIFY(int stateNum){
		UIMenuBuilder m = new UIMenuBuilder();
		
		m.add("Default", 
			new UIMenuAction(){
				public void run(){
					ui.displayError("doh!");
				}
		});
		
		m.add("Show current values", 
			new UIMenuAction(){
				public void run(){
					System.out.flush();
					String message = SimParams.printParams();
					ui.displayMessage(message);
				}
		});
		m.add("Simulation time step", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter time step:");
						double step = Double.parseDouble(textUI.getResponse());
						
						params.SIM_STEP = step;
					}
		});
		m.add("Simulation run time", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter run time:");
						double runtime = Double.parseDouble(textUI.getResponse());
						
						params.SIM_DURATION = runtime;
					}
		});
		m.add("Grid size", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter grid rows:");
						int rows = Integer.parseInt(textUI.getResponse());
						if (rows <= 0){
							ui.displayMessage("Must have at least 1 row. Keeping old parameter setting.");
							rows = params.GRID_ROWS;
						}
						ui.displayMessage("enter grid columns");
						int columns = Integer.parseInt(textUI.getResponse());
						if (columns <= 0){
								ui.displayMessage("Must have at least 1 row. Keeping old parameter setting.");
								columns = params.GRID_COLUMNS;	
							}
						params.GRID_ROWS = rows;
						params.GRID_COLUMNS = columns;
					}
		});
		m.add("Traffic pattern", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter traffic pattern: simple or alternating");
						String answer = textUI.getResponse();
						boolean p;
						p = (answer == "alternating") ? true : false;
						
						params.TRAFFIC_PATTERN = p;
					}
		});
		m.add("Car entry rate", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum car generation rate:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum car generation rate:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.GEN_DELAY_MIN = min;
						params.GEN_DELAY_MAX = max;
					}
		});
		m.add("Road segment length", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum road segment length:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum road segment length:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.SEGMENT_LENGTH_MIN = min;
						params.SEGMENT_LENGTH_MAX = max;
					}
		});
		m.add("Intersection length", 
				new UIMenuAction(){
					public void run(){
						ui.displayMessage("enter minimum intersection length:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum intersection length:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.INTERSECTION_LENGTH_MIN = min;
						params.INTERSECTION_LENGTH_MAX = max;
					}
		});
		m.add("Car length", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum car length:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum car length:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.CAR_LENGTH_MIN = min;
						params.CAR_LENGTH_MAX = max;
					}
		});
		m.add("Car maximum velocity", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum max-car velocity:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum max-car velocity:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.MAX_VEL_MIN = min;
						params.MAX_VEL_MAX = max;
					}
		});
		m.add("Car stop distance", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum car stopping distance:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum car stopping distance:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.STOP_DISTANCE_MIN = min;
						params.STOP_DISTANCE_MAX = max;
					}
		});
		m.add("Car brake distance", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum car braking distance:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum car braking distance:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.BRAKE_DISTANCE_MIN = min;
						params.BRAKE_DISTANCE_MAX = max;
					}
		});
		m.add("Traffic light green time", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum green light duration:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum green light generation:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.GREEN_LIGHT_DUR_MIN = min;
						params.GREEN_LIGHT_DUR_MAX = max;
					}
		});
		m.add("Traffic light yellow time", 
				new UIMenuAction(){
					public void run(){
						System.out.flush();
						ui.displayMessage("enter minimum yellow light duration:");
						double min = Double.parseDouble(textUI.getResponse());
						ui.displayMessage("enter maximum yellow light duration:");
						double max = Double.parseDouble(textUI.getResponse());
						
						params.YELLOW_LIGHT_DUR_MIN = min;
						params.YELLOW_LIGHT_DUR_MAX = max;
					}
		});
		m.add("Reset simulation and return to the main menu", 
				new UIMenuAction(){
					public void run(){
						_state = START;
					}
		});
		
		menus[stateNum] = m.toUIMenu("Modify Simulation Parameters");
	}
	
	
	

	private void addEXIT(int stateNum){
	    UIMenuBuilder m = new UIMenuBuilder();
	    
	    m.add("Default", new UIMenuAction() { public void run() {} });
	    m.add("YES", 
	    	new UIMenuAction(){
	    		public void run(){
	    		_state = EXITED;
	    	}
	    });
	    m.add("NO",
	    	new UIMenuAction(){
	    		public void run(){
	    			_state = START;
	    		}
	    });
	    
	    menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	}
	
}
