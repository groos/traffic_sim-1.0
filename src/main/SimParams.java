package main;

import java.util.Random;

public class SimParams {
	
	public SimParams(){
		
	}
	
	public static double SIM_STEP = .1;
	public static double SIM_DURATION = 1000.0;
	public static Integer GRID_ROWS = 4;
	public static Integer GRID_COLUMNS = 6;
	public static boolean TRAFFIC_PATTERN = true; // later use state pattern
	
	public static double GEN_DELAY_MIN = 2.0;
	public static double GEN_DELAY_MAX = 25.0;
	
	public static double SEGMENT_LENGTH_MIN = 200.0;
	public static double SEGMENT_LENGTH_MAX = 500.0;
	
	public static double INTERSECTION_LENGTH_MIN = 10.0;
	public static double INTERSECTION_LENGTH_MAX = 15.0;
	
	public static double CAR_LENGTH_MIN = 5.0;
	public static double CAR_LENGTH_MAX = 10.0;
	
	public static double MAX_VEL_MIN = 10.0;
	public static double MAX_VEL_MAX = 30.0;
	
	public static double STOP_DISTANCE_MIN = 0.5;
	public static double STOP_DISTANCE_MAX = 5.0;
	
	public static double BRAKE_DISTANCE_MIN = 9.0;
	public static double BRAKE_DISTANCE_MAX = 10.0;
	
	public static double GREEN_LIGHT_DUR_MIN = 30.0;
	public static double GREEN_LIGHT_DUR_MAX = 180.0;
	
	public static double YELLOW_LIGHT_DUR_MIN = 4.0;
	public static double YELLOW_LIGHT_DUR_MAX = 5.0;
	
	public static String spacesLeft(String s){
		int i = 38 - s.length();
		char c = ' ';
		String answer = "";
		for (int x = 0; x < i; x++){
			answer += c;
		}
		return answer;
	}
	
	public static String stepLabel(){

		String label = "";
		
		String name = "Simulation time step (seconds)";
		String spaces = spacesLeft(name);
		String values = '[' + String.valueOf(SIM_STEP) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String durationLabel(){
		String label = "";
		
		String name = "Simulation run time (seconds)";
		String spaces = spacesLeft(name);
		String values = '[' + String.valueOf(SIM_DURATION) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String gridLabel(){
		String label = "";
		
		String name = "Grid size (number of roads)";
		String spaces = spacesLeft(name);
		String values = "[row=" + String.valueOf(GRID_ROWS) + ",column=" + String.valueOf(GRID_COLUMNS) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	
	public static String patternLabel(){
		String label = "";
		
		String name = "Traffic pattern";
		String spaces = spacesLeft(name);
		String pattern = (TRAFFIC_PATTERN == true) ? "simple" : "alternating";
		String values = '[' + pattern + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String genLabel(){
		String label = "";
		
		String name = "Car entry rate (seconds/car)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(GEN_DELAY_MIN) + ",max=" + String.valueOf(GEN_DELAY_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	
	public static String segmentLabel(){
		String label = "";
		
		String name = "Road segment length (meters)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(SEGMENT_LENGTH_MIN) + ",max=" + String.valueOf(SEGMENT_LENGTH_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String intersectionLabel(){
		String label = "";
		
		String name = "Intersection length (meters)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(INTERSECTION_LENGTH_MIN) + ",max=" + String.valueOf(INTERSECTION_LENGTH_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String carLengthLabel(){
		String label = "";
		
		String name = "Car length (meters)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(CAR_LENGTH_MIN) + ",max=" + String.valueOf(CAR_LENGTH_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String maxVelLabel(){
		String label = "";
		
		String name = "Car maximum velocity (meters/second)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(MAX_VEL_MIN) + ",max=" + String.valueOf(MAX_VEL_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String stopDistanceLabel(){
		String label = "";
		
		String name = "Car stop distance (meters)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(STOP_DISTANCE_MIN) + ",max=" + String.valueOf(STOP_DISTANCE_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String brakeDistanceLabel(){
		String label = "";
		
		String name = "Car brake distance (meters)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(BRAKE_DISTANCE_MIN) + ",max=" + String.valueOf(BRAKE_DISTANCE_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;	
	}
	
	public static String greenDurationLabel(){
		String label = "";
		
		String name = "Traffic light green time (seconds)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(GREEN_LIGHT_DUR_MIN) + ",max=" + String.valueOf(GREEN_LIGHT_DUR_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String yellowDurationLabel(){
		String label = "";
		
		String name = "Traffic light yellow time (seconds)";
		String spaces = spacesLeft(name);
		String values = "[min=" + String.valueOf(YELLOW_LIGHT_DUR_MIN) + ",max=" + String.valueOf(YELLOW_LIGHT_DUR_MAX) + ']';
		
		label += name + spaces + values + '\n';
		
		return label;
	}
	
	public static String printParams(){
		String list = "";
		list += stepLabel() + durationLabel() + gridLabel() + patternLabel() + genLabel() + segmentLabel() + intersectionLabel() + 
				carLengthLabel() + maxVelLabel() + stopDistanceLabel() + brakeDistanceLabel() + greenDurationLabel() + yellowDurationLabel();
		
		return list;
	}
	
	
	public static double getRangedParam(double min, double max){
		double answer;
		Random r = new Random();
		answer = min + (max - min)*r.nextDouble();
		return answer;
	}

}
