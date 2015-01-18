package data;

import java.awt.Color;

import main.ACCEPTORTYPE;
import main.SimParams;
import tServer.TimeServerLinked;

public class Car implements Agent{
	private double length;
	private double maxVelocity;
	private double currentVelocity;
	private double brakeDistance;
	private double stopDistance;
	private double frontPosition;
	private double waketime;
	private TimeServerLinked TS;
	private CarAcceptor currentAcceptor;
	private java.awt.Color color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
	
	public Car(CarAcceptor acceptor, TimeServerLinked TS){
		if (acceptor == null || TS == null){
			throw new IllegalArgumentException();
		}
		
		//// set up data members and make sure they qualify params
		this.length = SimParams.getRangedParam(SimParams.CAR_LENGTH_MIN, SimParams.CAR_LENGTH_MAX);
		this.maxVelocity = SimParams.getRangedParam(SimParams.MAX_VEL_MIN, SimParams.MAX_VEL_MAX);
		this.brakeDistance = SimParams.getRangedParam(SimParams.BRAKE_DISTANCE_MIN, SimParams.BRAKE_DISTANCE_MAX);
		this.stopDistance = SimParams.getRangedParam(SimParams.STOP_DISTANCE_MIN, SimParams.STOP_DISTANCE_MAX);

		assert(this.length >= SimParams.CAR_LENGTH_MIN && this.length <= SimParams.CAR_LENGTH_MAX);
		assert(this.maxVelocity >= SimParams.MAX_VEL_MIN && this.maxVelocity <= SimParams.MAX_VEL_MAX);
		assert(this.brakeDistance >= SimParams.BRAKE_DISTANCE_MIN && this.brakeDistance <= SimParams.BRAKE_DISTANCE_MAX);
		assert(this.stopDistance >= SimParams.STOP_DISTANCE_MIN && this.stopDistance <= SimParams.STOP_DISTANCE_MAX);
		
		this.frontPosition = 0;
		this.currentAcceptor = acceptor;
		this.waketime = TS.currentTime();
		
		this.TS = TS;
		currentAcceptor.addCar(this);
		TS.enqueue(this.getWakeTime(), this);
	}
	
	/*
	 * checks the current acceptor the car is driving on, and returns it.
	 */
	CarAcceptor getCurrentAcceptor(){
		CarAcceptor c = this.currentAcceptor;
		
		while (this.frontPosition > c.getEndPosition()){
			c = c.nextAcceptor();
			
			if (c.getType() == ACCEPTORTYPE.SINK){
				return c;
			}
		}
		return c;
	}
	

	@Override
	public void run() {
		if (this.currentAcceptor.getType() == ACCEPTORTYPE.SINK){
			Sink sink = (Sink) this.currentAcceptor;
			sink.removeCar(this);
			return;
		}
		
		CarAcceptor acceptorTemp;
		double nextPosition;
		double obstacle = this.currentAcceptor.distanceToObstacle(frontPosition);
		
		double velocity = (maxVelocity / (brakeDistance - stopDistance))
                * (obstacle - stopDistance);
		velocity = Math.max(0.0, velocity);
		velocity = Math.min(maxVelocity, velocity);
		
		
		if ((this.currentAcceptor.getEndPosition() - this.frontPosition) <= this.brakeDistance 
			&& this.currentAcceptor.nextAcceptor().getType() == ACCEPTORTYPE.YELLOW){
			velocity = 0;
		}


		nextPosition = frontPosition + (velocity * SimParams.SIM_STEP);
		this.frontPosition = nextPosition;
		assert(this.frontPosition <= this.currentAcceptor.getEndPosition());
		
		/*
		 * if the car has moved on to a new acceptor, remove it from the old one.
		 */
		acceptorTemp = getCurrentAcceptor();
		
		if (acceptorTemp != null && !acceptorTemp.equals(this.currentAcceptor)){
			this.currentAcceptor.removeCar(this);
		}
		
		this.currentAcceptor = acceptorTemp;
		this.currentAcceptor.addCar(this);
		
		
		assert(this.currentAcceptor != null);
		assert(this.frontPosition <= this.currentAcceptor.getEndPosition());
		assert(this.currentVelocity <= this.maxVelocity);
		
		/*
		 * update waketime and requeue
		 */
		this.waketime += SimParams.SIM_STEP;
		
		if (this.waketime >= TS.currentTime()){
			this.TS.enqueue(this.getWakeTime(), this);
		}
		else{
			return;
		}
	}
	
	void printPosition(){
		System.out.println("car is at position: " + this.frontPosition);
	}
	
	public double getWakeTime(){
		return this.waketime;
	}

	public double backPosition() {
		return this.frontPosition - this.length;
	}
	
	public Color getColor(){
		return color;
	}
	
	public double getPosition(){
		return this.frontPosition;
	}
	
	public double getBrakeDistance(){
		return this.brakeDistance;
	}
	
	public double getLength(){
		return this.length;
	}
	
}
