package data;
import tServer.TimeServerLinked;
import light.ChangingState;
import light.Light;
import light.LightBuilder;
import light.LightControllerObj;
import light.LightState;
import main.DIRECTION;
import data.Road;




/*
 * arguments still need to be added for these
 * 
 */

public class ObjFactory {
	
	
	static public Sink makeSink(){
		return new Sink();
	}
	
	static public TimeServerLinked makeTimeServer(){
		return new TimeServerLinked();
	}
	
	static public Car makeCar(CarAcceptor acceptor, TimeServerLinked TS){
		return new Car(acceptor, TS);
	}
	
	static public LightState makeLightState(){
		return new LightState();
	}
	
	/*
	static public LightState makeLightState(LightControllerObj LC){
		return new LightState(LC);
	}
	*/
	
	static public Road makeRoad(CarAcceptor next, DIRECTION dir){
		return new Road(next, dir);
	}
	
	/*
	 * changed Light to take in a Double, because prevroad was 
	 * only need for its end position
	 */
	static public Light makeLight(Road nextRoad, ChangingState state, TimeServerLinked TS, DIRECTION dir){

		LightBuilder LB = new LightBuilder();
		LB.setNext(nextRoad);
		LB.setState(state);
		LB.setTimeServer(TS);
		LB.setDirection(dir);
		return LB.createLight();
	}
	
	static public LightControllerObj makeLightController(TimeServerLinked TS){
		return new LightControllerObj(TS);
	}
	
	static public Source makeSource(CarAcceptor acceptor, TimeServerLinked TS){
		return new Source(acceptor, TS);
	}
	
}
