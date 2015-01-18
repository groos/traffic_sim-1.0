package data;

import java.util.HashSet;
import java.util.Set;

import main.ACCEPTORTYPE;
import main.DIRECTION;


public class Sink implements CarAcceptor, Agent{
	private Set <Car> cars;
	private ACCEPTORTYPE SINK;
	private CarAcceptor prev;
	private double endPosition;
	
	public Sink(){
		this.cars = new HashSet<Car>(); // probs dont need this
	}

	public void removeCar(Car c){
		cars.remove(c);
		this.prev.removeCar(c);
	}
	
	@Override
	public double distanceToObstacle(double fromPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCar(Car newCar) {
		cars.add(newCar);
	}

	@Override
	public CarAcceptor nextAcceptor() {
		// no next acceptor
		return null;
	}

	@Override
	public double getEndPosition() {
		// is infinity right?
		return prev.getEndPosition();
	}

	@Override
	public ACCEPTORTYPE getType() {
		return ACCEPTORTYPE.SINK;
	}

	@Override
	public void setEndPosition(CarAcceptor prev) {
		this.endPosition = prev.getEndPosition();
	}

	@Override
	public void setPrevious(CarAcceptor prev) {
		this.prev = prev;
	}

	@Override
	public DIRECTION getDirection() {
		return null;
	}

	@Override
	public void run() {
		// I actually don't think i need to add the sink to the TS.
		// If the car is 
		cars.clear();
	}

	@Override
	public CarAcceptor prevAcceptor() {
		return this.prev;
	}

	@Override
	public double getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
