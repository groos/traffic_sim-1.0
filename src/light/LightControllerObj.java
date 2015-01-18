package light;
import main.DIRECTION;
import data.CarAcceptor;
import data.ObjFactory;
import data.Road;
import tServer.TimeServerLinked;

public class LightControllerObj {
	Light NSLight;
	Light EWLight;
	Road NSRoad;
	Road EWRoad;
	TimeServerLinked TS;
	private final LightState stateGroup;
	
	public LightControllerObj(TimeServerLinked TS){
		this.TS = TS;
		
		this.stateGroup = new LightState();
	}
	
	public void addRoad(Road next){
		if (next.getDirection() == DIRECTION.NS){
			//System.out.println("adding NS road to LC");
			this.NSRoad = next;
		}
		else if (next.getDirection() == DIRECTION.EW){
			//System.out.println("adding EW road to LC");
			this.EWRoad = next;
		}
		else{
			System.out.println("LightControllerObj was unable to get direction for Road");
			throw new IllegalArgumentException();
		}
	}
	
	public Light createLight(DIRECTION dir){
		if (dir == DIRECTION.NS){
			assert(this.NSRoad != null);
			assert(this.stateGroup != null);
			assert(this.TS != null);
			NSLight = ObjFactory.makeLight(NSRoad, stateGroup.getGreen(), TS, DIRECTION.NS);
			return NSLight;
		}
		if (dir == DIRECTION.EW){
			assert(this.EWRoad != null);
			assert(this.stateGroup != null);
			assert(this.TS != null);
			EWLight = ObjFactory.makeLight(EWRoad, stateGroup.getRed(), TS, DIRECTION.EW);
			return EWLight;
		}
		throw new IllegalArgumentException();
	}
	
	public Light getLight(DIRECTION dir){
		if (dir == DIRECTION.NS) return NSLight;
		if (dir == DIRECTION.EW) return EWLight;
		
		throw new IllegalArgumentException();
	}
	
	public void setPartners(){
		this.NSLight.setPartner(EWLight);
		this.EWLight.setPartner(NSLight);
	}
	
	public Light getNSLight(){
		return this.NSLight;
	}
	
	public Light getEWLight(){
		return this.EWLight;
	}
	
}
