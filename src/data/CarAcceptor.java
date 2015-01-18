package data;
import main.ACCEPTORTYPE;
import main.DIRECTION;


public interface CarAcceptor {
	public double distanceToObstacle(double fromPosition);
	
	public void addCar(Car newCar);
	public void removeCar(Car car);
	
	public CarAcceptor nextAcceptor();
	public CarAcceptor prevAcceptor();
	
	public double getEndPosition();
	public void setEndPosition(CarAcceptor prev);
	
	public void setPrevious(CarAcceptor prev);
	public ACCEPTORTYPE getType();
	public DIRECTION getDirection();
	
	public double getLength();
}
