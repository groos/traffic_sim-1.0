package data;

import tServer.TimeServerLinked;
import light.Light;
import light.LightControllerObj;
import light.LightState;
import main.ACCEPTORTYPE;
import main.DIRECTION;
import junit.framework.TestCase;

public class CarAcceptorTEST extends TestCase{
	public CarAcceptorTEST(String name){
		super(name);
	}
	
	Sink sink = ObjFactory.makeSink();
	TimeServerLinked TS = ObjFactory.makeTimeServer();
	Road r1 = ObjFactory.makeRoad(sink, DIRECTION.EW);
	Road r2 = ObjFactory.makeRoad(r1, DIRECTION.EW);
	
	public void testNextandEndPositionROAD(){
		
		assert(r1.nextAcceptor().equals(sink));
		assert(r1.prevAcceptor() == null);
		assert(r1.getEndPosition() == r1.getLength());
		
		
		assert(r2.nextAcceptor().equals(r1));
		assert(r2.getLength() == r2.getEndPosition());
		
		assert(r1.getEndPosition() == r2.getLength() + r1.getLength());
	}
	
	public void testDirectionAndType(){
		Road r3 = ObjFactory.makeRoad(sink, DIRECTION.EW);
		assert(r3.getDirection() == DIRECTION.EW);
		assert(r3.getType() == ACCEPTORTYPE.ROAD);
	}
	
	public void testCars(){
		Car newCar = ObjFactory.makeCar(r1, TS);
		assert(r1.cars.contains(newCar));
		assert(newCar.getCurrentAcceptor().equals(r1));
		
		r1.removeCar(newCar);
		assertFalse(r1.cars.contains(newCar));
	}
	
	public void testLights(){
		LightControllerObj LC = ObjFactory.makeLightController(TS);
		LightState lightState = ObjFactory.makeLightState();
		
		Light light = ObjFactory.makeLight(r2, lightState.getGreen(), TS, DIRECTION.EW);
		Light newLight = ObjFactory.makeLight(r2, lightState.getRed(), TS, DIRECTION.EW);
		Light otherLight = light;
	
		assertFalse(newLight.equals(light));
		assert(light.equals(otherLight));
		assert(light.nextAcceptor().equals(r2));
		assert(r1.prevAcceptor().equals(light));
	
		Road r3 = ObjFactory.makeRoad(light, DIRECTION.EW);
		
		assert(r3.nextAcceptor().equals(light));
		assert(light.prevAcceptor().equals(r3));
	
	}
}

