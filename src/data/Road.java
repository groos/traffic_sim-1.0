package data;

import java.util.HashSet;
import java.util.Set;

import main.ACCEPTORTYPE;
import main.DIRECTION;
import main.SimParams;

public class Road implements CarAcceptor{
	public Set <Car> cars;
	private CarAcceptor prevAcceptor;
	private CarAcceptor nextAcceptor;
	private double endPosition;
	private double length;
	private DIRECTION dir;
	private ACCEPTORTYPE ROAD;
	
	public Road(CarAcceptor next, DIRECTION dir){
		this.nextAcceptor = next;
		this.length = SimParams.getRangedParam(SimParams.SEGMENT_LENGTH_MIN, SimParams.SEGMENT_LENGTH_MAX);
		cars = new HashSet<Car>();
		this.dir = dir;
		
		next.setPrevious(this);
	}
	
	private double distanceToCarBack(double fromPosition){
	if (cars.isEmpty()){
		//System.out.println("no cars on road. returning pos infinity");
		return Double.POSITIVE_INFINITY;
		}
		
		double carBackPosition = Double.POSITIVE_INFINITY;
		
		for (Car c : cars)
			if (c.backPosition() >= fromPosition &&
				c.backPosition() < carBackPosition){
				//System.out.println("bumping into cars");
				carBackPosition = c.backPosition();
			}
		
		return carBackPosition;
	}

	@Override
	public double distanceToObstacle(double fromPosition) {
		double obstaclePosition = this.distanceToCarBack(fromPosition);
		
		 // if satisfied, there are no obstacles on this road segment
		// obstacle will be on some next segment
		if (obstaclePosition == Double.POSITIVE_INFINITY && this.nextAcceptor.getType() != ACCEPTORTYPE.SINK){
			obstaclePosition = nextAcceptor.distanceToObstacle(this.endPosition) + (this.endPosition - fromPosition);
			//System.out.println("obstacle position: " + obstaclePosition);
			}
		else if (obstaclePosition == Double.POSITIVE_INFINITY && this.nextAcceptor.getType() == ACCEPTORTYPE.SINK) {
			return Double.POSITIVE_INFINITY;
		}



		if (nextAcceptor != null && (nextAcceptor.getType() == ACCEPTORTYPE.RED )){
			if (this.endPosition < obstaclePosition){
				double dist;

				obstaclePosition = this.endPosition;
				}
		}

		
		double answer = obstaclePosition - fromPosition;
		return obstaclePosition - fromPosition;
	}
	
	public boolean equals(Object o){
		if (o == null){return false;}
		
		if (!(o instanceof Road)){return false;}
		
		Road t = (Road) o;
		
		if (t.cars != this.cars || t.dir != this.dir 
			|| t.endPosition != this.endPosition 
			|| t.length != this.length
			|| t.getType() != this.getType()){
			return false;
		}
		
		if (this.prevAcceptor != null){
			if (!t.prevAcceptor.equals(this.prevAcceptor)) return false;
		} else {
			if (this.prevAcceptor != t.prevAcceptor) return false;
		}
		
		if (this.nextAcceptor != null){
			if (!t.nextAcceptor.equals(this.nextAcceptor)) return false;
		} else {
			if (this.nextAcceptor != t.nextAcceptor) return false;
		}

		return true;
	}

	
	@Override
	public void addCar(Car newCar) {
		this.cars.add(newCar);
	}
	
	public void removeCar(Car car){
		this.cars.remove(car);
	}

	@Override
	public CarAcceptor nextAcceptor() {
		return this.nextAcceptor;
	}


	@Override
	public double getEndPosition() {
		return this.endPosition;
	}
	
	public Set <Car> getCars(){
		return cars;
	}


	@Override
	public ACCEPTORTYPE getType() {
		return ACCEPTORTYPE.ROAD;
	}

	@Override
	public void setEndPosition(CarAcceptor prev) {
		this.endPosition = (prev != null) ? this.prevAcceptor.getEndPosition() + this.length : this.length;
		this.nextAcceptor.setEndPosition(this);
		System.out.println(this.toString() + " length is: " + this.length + " end position is: " + this.getEndPosition());
	}


	@Override
	public DIRECTION getDirection() {
		return this.dir;
	}

	@Override
	public void setPrevious(CarAcceptor prev) {
		this.prevAcceptor = prev;
	}

	@Override
	public CarAcceptor prevAcceptor() {
		return this.prevAcceptor;
	}

	@Override
	public double getLength() {
		return this.length;
	}

}
