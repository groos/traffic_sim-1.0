package data;
import tServer.TimeServerLinked;
import main.DIRECTION;
import light.ChangingState;
import light.Light;
import light.LightControllerObj;
import light.LightState;
import main.SimParams;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ObjectsTEST extends TestCase{
	public ObjectsTEST(String name){
		super(name);
	}
	
	/*
	 * make some components to be used in the tests
	 */
	TimeServerLinked TS = ObjFactory.makeTimeServer();
	Sink sink = ObjFactory.makeSink();

	Road r1 = ObjFactory.makeRoad(sink, DIRECTION.EW);
	Road r2 = ObjFactory.makeRoad(sink, DIRECTION.NS);
	
	public void testLightController(){
		
		LightControllerObj LCObj  = ObjFactory.makeLightController(TS);
		LCObj.addRoad(r1);
		LCObj.addRoad(r2);
		//LCObj.createControlledLights();
		
	/// check roads data is correct
		assert(r1.nextAcceptor() == (CarAcceptor) sink);
		assert(r1.getDirection() == DIRECTION.EW);
		assert(r2.getDirection() == DIRECTION.NS);
		
	/// check LCObj data is set up correctly
		
		assert(LCObj.getEWLight().getDirection() == r1.getDirection());
		assert(LCObj.getNSLight().getDirection() == r2.getDirection());
		
	}
	
	public void testTimeSyncing(){
		r1.setPrevious(null);
		r1.setEndPosition(null);
		Car newCar = ObjFactory.makeCar(r1, TS);
		Source source = ObjFactory.makeSource(r1, TS);
		
		assert(newCar.getWakeTime() == TS.currentTime());
		assert(source.getWakeTime() == TS.currentTime());
		
		source.run();
		
		assert(source.getWakeTime() == TS.currentTime());
	}
	
	public void testEqualsWorksCorrectly(){
		Road r3 = r1;
		
		assert(r3.equals(r1));
		assert(r1.equals(r3));
		
		assertFalse(r3.equals(r2));
		assertFalse(r2.equals(r3));
		
	}
	
}
