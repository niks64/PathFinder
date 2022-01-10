// import java.util.LinkedList;
// import java.util.List;
/*
interface FlightInterface {
	public int getFlightNum();
}

class FlightPlaceholderA implements FlightInterface { 

	public int getFlightNum() {
		return 1;
	}
}

class FlightPlaceholderB implements FlightInterface { 

	public int getFlightNum() {
		return 4;
	}
}

class FlightPlaceholderC implements FlightInterface { 

	public int getFlightNum() {
		return 16;
	}
}

interface AirportInterface {
	public String getID();
}

class AirportPlaceholder implements AirportInterface {
	public String getID() {
		return "MSN";
	}
}
*/

interface FlightInterface {

	public int getFlightNum();
	public String getOrigin();
	public String getDestination();
	public int getDistance();

}

public class FlightData implements FlightInterface {
	int flightNum;
	String origin;
	String destination;
	int distance;
	
	public FlightData() {
		flightNum = -1;
		origin = null;
		destination = null;
		distance = -1;
	}
	
	public FlightData(int num, String orig, String dest, int dist) {
		flightNum = num;
		origin = orig;
		destination = dest;
		distance = dist;
	}
	
	public int getFlightNum() {
		return flightNum;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	public int getDistance() {
		return distance;
	}
}

class FlightPlaceholderA implements FlightInterface { 

	public int getFlightNum() {
		return 1;
	}
	public String getOrigin() {
		return "MSN";
	}

	public String getDestination() {
		return "LAX";
	}

	public int getDistance() {
		return 2;
	}

}

class FlightPlaceholderB implements FlightInterface { 

	public int getFlightNum() {
		return 4;
	}

	public String getOrigin() {
		return "LAX";
	}

	public String getDestination() {
		return "MSN";
	}

	public int getDistance() {
		return 8;
	}

}

class FlightPlaceholderC implements FlightInterface { 

	public int getFlightNum() {
		return 16;
	}

	public String getOrigin() {
		return "ORD";
	}

	public String getDestination() {
		return "LAX";
	}

	public int getDistance() {
		return 32;
	}

}

