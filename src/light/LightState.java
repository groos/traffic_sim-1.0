package light;
import main.ACCEPTORTYPE;
import main.SimParams;

public class LightState {
	private double GREEN_DURATION;
	private double YELLOW_DURATION;
	private double RED_DURATION;
	private Green GREEN_STATE;
	private Yellow YELLOW_STATE;
	private Red RED_STATE;
	private LightControllerObj LC;
	private ACCEPTORTYPE T;

	/*
	public LightState(LightControllerObj LC){
		this.LC = LC;
		this.GREEN_DURATION = SimParams.getRangedParam(SimParams.GREEN_LIGHT_DUR_MIN, SimParams.GREEN_LIGHT_DUR_MAX);
		//this.YELLOW_DURATION = SimParams.getRangedParam(SimParams.YELLOW_LIGHT_DUR_MIN, SimParams.YELLOW_LIGHT_DUR_MAX);
		this.YELLOW_DURATION = this.GREEN_DURATION + 2;
		this.RED_DURATION = this.GREEN_DURATION + this.YELLOW_DURATION;
	}
	*/
	
	/*
	 * overload to instantiate without LC
	 */
	public LightState(){
		this.GREEN_DURATION = SimParams.getRangedParam(SimParams.GREEN_LIGHT_DUR_MIN, SimParams.GREEN_LIGHT_DUR_MAX);
		this.YELLOW_DURATION = SimParams.getRangedParam(SimParams.YELLOW_LIGHT_DUR_MIN, SimParams.YELLOW_LIGHT_DUR_MAX);
		this.RED_DURATION = this.GREEN_DURATION + this.YELLOW_DURATION;
		
		this.GREEN_STATE = new Green();
		this.YELLOW_STATE = new Yellow();
		this.RED_STATE = new Red();
		
		GREEN_STATE.setNextState(YELLOW_STATE);
		YELLOW_STATE.setNextState(RED_STATE);
		RED_STATE.setNextState(GREEN_STATE);
	}
	
	public ChangingState getGreen(){
		return GREEN_STATE;
	}
	public ChangingState getYellow(){
		return YELLOW_STATE;
	}
	public ChangingState getRed(){
		return RED_STATE;
	}
	
	public class Green implements ChangingState{
		ChangingState nextState;
		private double duration;
		//private double waketime;
		private ACCEPTORTYPE GREEN;
		
		public Green(){
			this.duration = GREEN_DURATION;
		}
		
		@Override
		public ChangingState getNextState() {
			return this.nextState;
		}

		@Override
		public double duration() {
			return this.duration;
		}
		
		public ACCEPTORTYPE getType(){
			return ACCEPTORTYPE.GREEN;
		}

		@Override
		public void setNextState(ChangingState next) {
			this.nextState = next;
		}
		
		public boolean getLightState(){
			return true;
		}
	}
	
	public class Yellow implements ChangingState{
		ChangingState nextState;
		private double duration;
		//private double waketime;
		private ACCEPTORTYPE YELLOW;

		public Yellow(){
			this.duration = YELLOW_DURATION;
		}
		
		@Override
		public ChangingState getNextState() {
			return this.nextState;
		}

		@Override
		public double duration() {
			//System.out.println("yellow duration is: " + this.duration);
			return this.duration;
		}
		
		public ACCEPTORTYPE getType(){
			return ACCEPTORTYPE.YELLOW;
		}

		@Override
		public void setNextState(ChangingState next) {
			this.nextState = next;
		}
		
		public boolean getLightState(){
			return false;
		}
	}
	
	public class Red implements ChangingState{
		ChangingState nextState;
		private double duration;
		//private double waketime;
		private ACCEPTORTYPE RED;
		
		public Red(){
			this.duration = RED_DURATION;
		}

		@Override
		public ChangingState getNextState() {
			return this.nextState;
		}

		@Override
		public double duration() {
			return this.duration;
		}
		
		public ACCEPTORTYPE getType(){
			return ACCEPTORTYPE.RED;
		}

		@Override
		public void setNextState(ChangingState next) {
			this.nextState = next;
		}
		
		public boolean getLightState(){
			return false;
		}
	}
	
}
