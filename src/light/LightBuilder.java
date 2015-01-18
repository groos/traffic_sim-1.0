package light;

import main.DIRECTION;
import data.CarAcceptor;
import data.Road;
import tServer.TimeServerLinked;

public class LightBuilder {
	private Double startPosition;
	private CarAcceptor next;
	private ChangingState state;
	private TimeServerLinked TS;
	private DIRECTION dir;
	
	public LightBuilder LightBuilder(){
		return new LightBuilder();
	}
	
	public void setDirection(DIRECTION dir){
		this.dir = dir;
	}
	
	public void setPosition(Double pos){
		this.startPosition = pos;
	}
	
	public void setNext(CarAcceptor next){
		this.next = next;
	}
	
	public void setState(ChangingState state){
		this.state = state;
	}
	
	public void setTimeServer(TimeServerLinked TS){
		this.TS = TS;
	}
	
	public Light createLight(){
		return new Light(next, state, TS, dir);
	}
}
