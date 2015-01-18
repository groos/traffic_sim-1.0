package light;
import main.ACCEPTORTYPE;
import main.DIRECTION;
import main.SimParams;
import light.LightControllerObj;

import java.util.Set;
import java.util.HashSet;

import data.Agent;
import data.Car;
import data.CarAcceptor;
import tServer.TimeServerLinked;
import data.Road;

public class Light implements CarAcceptor, Agent{
	private Set <Car> cars;
	private Double startPosition;
	private double endPosition;
	
	private CarAcceptor next;
	private CarAcceptor prev;
	private ChangingState state;
	private TimeServerLinked TS;
	
	double waketime;
	double length;
	
	private DIRECTION dir;
	private ACCEPTORTYPE k;
	private Light partner;
	private boolean crossTraffic;
	
	
	public Light(CarAcceptor next, ChangingState state, TimeServerLinked TS, DIRECTION dir){
		if (next == null ||state == null || TS == null){
			throw new IllegalArgumentException();
		}
		if (!(next instanceof Road)) {throw new IllegalArgumentException();}
		
		cars = new HashSet();
		this.dir = dir;
		this.state = state;
		this.TS = TS;
		this.waketime = this.TS.currentTime() + this.state.duration();
		
		final double thisLength = SimParams.getRangedParam(SimParams.INTERSECTION_LENGTH_MIN, SimParams.INTERSECTION_LENGTH_MAX);
		this.length = thisLength;
		this.next = next;
		
		assert(this.next != null);
		
		next.setPrevious(this);
		this.TS.enqueue(this.waketime, this);
		this.crossTraffic = false;
	}

	private void setDIRECTION(DIRECTION dir){
		this.dir = dir;
	}

	private double distanceToCarBack(double fromPosition) {
		
		if (cars.isEmpty()){
			//System.out.println("no cars in intersection. returning pos infinity");
			return Double.POSITIVE_INFINITY;
			}
		
		double carBackPosition = Double.POSITIVE_INFINITY;
		
		for (Car c : cars)
			if (c.backPosition() >= fromPosition &&
				c.backPosition() < carBackPosition)
				carBackPosition = c.backPosition();
		
		return carBackPosition;
	}
	
	public double distanceToObstacle(double fromPosition) {
		checkCrossTraffic();
		
		double obstaclePosition = this.distanceToCarBack(fromPosition);
		
		 // if satisfied, there are no obstacles on this road segment
		// obstacle will be on some next segment
		if (obstaclePosition == Double.POSITIVE_INFINITY){
			obstaclePosition = next.distanceToObstacle(this.endPosition) + (this.endPosition - fromPosition);
			}
		
		//System.out.println("distance to obstacle from Light: " + obstaclePosition);
		
		if (crossTraffic){
			System.out.println("crosstraffic");
			obstaclePosition = this.startPosition;
		}
		
		return obstaclePosition - fromPosition;
	}

	@Override
	public void addCar(Car newCar) {
		cars.add(newCar);
	}
	
	@Override
	public void removeCar(Car car) {
		cars.remove(car);
	}
	
	public void checkCrossTraffic(){
		double half = partner.length * .25;
		double count = 0;
		
		for (Car c : partner.cars){
			count += c.getLength();
			crossTraffic = (count >= half) ? true : false;
		}
	}

	
	@Override
	public void run() {
		this.state = this.state.getNextState();

		this.waketime = TS.currentTime() + this.state.duration();
		
		TS.enqueue(this.waketime, this);
	}

	@Override
	public CarAcceptor nextAcceptor() {
		CarAcceptor temp = (CarAcceptor) this.next;
		return (CarAcceptor) this.next;
	}	

	@Override
	public double getEndPosition() {
		return this.endPosition;
	}

	@Override
	public ACCEPTORTYPE getType() {
		return this.state.getType();
	}

	@Override
	public void setEndPosition(CarAcceptor prev) {
		this.endPosition = (prev != null) ? this.prev.getEndPosition() + this.length : this.length;
		this.next.setEndPosition(this);
		this.startPosition = this.endPosition - this.length;
	}

	@Override
	public void setPrevious(CarAcceptor prev) {
		this.prev = prev;
	}

	@Override
	public DIRECTION getDirection() {
		return this.dir;
	}

	public double getWakeTime(){
		return this.waketime;
	}

	@Override
	public CarAcceptor prevAcceptor() {
		return this.prev;
	}

	@Override
	public double getLength() {
		return this.length;
	}

	public boolean getLightState(){
		return this.state.getLightState();
	}
	
	public void setPartner(Light l){
		this.partner = l;
	}

}
