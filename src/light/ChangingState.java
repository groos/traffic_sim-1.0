package light;

import main.ACCEPTORTYPE;

public interface ChangingState {
	public ChangingState getNextState();
	public void setNextState(ChangingState next);
	public double duration();
	public String toString();
	public ACCEPTORTYPE getType();
	public boolean getLightState();
}
