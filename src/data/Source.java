package data;

import tServer.TimeServerLinked;
import main.SimParams;

public class Source implements Agent{
	private double waketime;
	private double GEN_DELAY;
	
	private CarAcceptor thisAcceptor;
	private TimeServerLinked TS;
	
	public Source(CarAcceptor acceptor, TimeServerLinked thisTS){
		if (acceptor == null || thisTS == null){
			System.out.println("acceptor or TS were null in Source");
			throw new IllegalArgumentException();
		}
		
		this.TS = thisTS;
		this.waketime = this.TS.currentTime();
		this.GEN_DELAY = SimParams.getRangedParam(SimParams.GEN_DELAY_MIN, SimParams.GEN_DELAY_MAX);
		this.thisAcceptor = acceptor;
		thisAcceptor.setPrevious(null);
		TS.enqueue(this.waketime, this);
	}

	@Override
	public void run() {
		assert(this.waketime == TS.currentTime());
		
		assert(this.thisAcceptor instanceof Road);
		
		Car newCar = ObjFactory.makeCar(this.thisAcceptor, this.TS);
		assert(newCar.getWakeTime() == TS.currentTime());
		newCar.run();
		
		this.waketime += this.GEN_DELAY;
		TS.enqueue(this.waketime, this);
	}

	public double getWakeTime(){
		return this.waketime;
	}
	
	public CarAcceptor nextAcceptor(){
		return this.thisAcceptor;
	}

}
