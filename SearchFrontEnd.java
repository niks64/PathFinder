import java.util.List;
import java.util.Scanner;

interface SearchFrontEndInterface {
    public void run(SearchBackEndInterface searchEngine);
    
    // This front end will support:
    
    // 
    // 1. Insert new airport
    // 2. Insert new flight
    // 3. Remove airport
    // 4. Remove flight
    // 5. Get destination airports from a particular airport
    // 6. Find flight path between two airports
    // 7. Quit
}
public class SearchFrontEnd implements SearchFrontEndInterface {

    @Override
    public void run(SearchBackEndInterface searchEngine) {
	Scanner scnr = new Scanner(System.in);
	int cmd = 0;
	
	while(cmd != 7) {
	    System.out.println("Please select from the following menu:");
	    System.out.println("1. Insert new airport");
	    System.out.println("2. Insert new flight");
	    System.out.println("3. Remove airport");
	    System.out.println("4. Remove flight");
	    System.out.println("5. Get destination airports from a particular airport");
	    System.out.println("6. Find flight path between two airports");
	    System.out.println("7. Quit");
	    System.out.println();
	    
	    cmd = Integer.parseInt(scnr.nextLine());
	    
	    if (cmd == 1) {
		// Prompt for airport information
		System.out.println("Enter the 3 letter ID of the airport: ");
		String airportID = scnr.nextLine();
		
		searchEngine.addAirport(airportID);
		System.out.println("Airport " + airportID + " has been added to the database.");
	    }
	    
	    else if (cmd == 2) {
		// Prompt for flight information
		System.out.println("Enter the flight number: ");
		int flightNum = Integer.parseInt(scnr.nextLine());
		
		System.out.println("Enter the 3 letter origin airport ID: ");
		String origin = scnr.nextLine();
		
		System.out.println("Enter the 3 letter destination airport ID: ");
		String destination = scnr.nextLine();
		
		System.out.println("Enter the flight distance: ");
		int distance = Integer.parseInt(scnr.nextLine());
		
		searchEngine.addFlight(origin, destination, distance);
		
		System.out.println("Flight " + flightNum + " from " + origin + " to " + destination
			+ " has been added to the database.");
	    }
	    
	    else if (cmd == 3) {
		// Prompt for airport information
		System.out.println("Enter the 3 letter ID of the airport to be removed: ");
		String airportToRemove = scnr.nextLine();
		
		searchEngine.removeAirport(airportToRemove);
		System.out.println("Airport " + airportToRemove + " has been removed from the database.");
	    }
	    
	    else if (cmd == 4) {
		// Prompt for flight information
		System.out.println("Enter the flight origin of the flight to be removed: ");
		String origin = scnr.nextLine();
		//List<String> database = searchEngine.getGraph();
		System.out.println("Enter the flight destination of the flight to be removed: ");
		String destination = scnr.nextLine();
		
		searchEngine.removeFlight(origin, destination);
	    }
	    
	    else if (cmd == 5) {
		// Prompt for airport ID
		System.out.println("Enter the desired airport's 3 letter ID: ");
		String airportID = scnr.nextLine();
		
		if (searchEngine.getAllAirports().contains(airportID) == false) {
		    System.out.println("Airport does not exist in the database.");
		    System.out.println();
		} else {
		    System.out.println(airportID + " has outbound flights to the following airports: ");
		    List<String> destinations = searchEngine.getAllDestinations(airportID);
		    for (int i = 0; i < (destinations.size() - 1); i++) {
			System.out.print(destinations.get(i) + ", ");
		    }
		    System.out.println(destinations.get(destinations.size() - 1));
		    
		}
	    }
	    
	    else if (cmd == 6) {
		// Prompt for two airports
		System.out.println("Enter the origin airport's 3 letter ID: ");
		String origin = scnr.nextLine();
		System.out.println("Enter the destination airport's 3 letter ID: ");
		String destination = scnr.nextLine();
		
		if (searchEngine.shortestPath(origin, destination) == null) {
		    System.out.println("No path could be found.");
		} else {
		    for (int i = 0; i < (searchEngine.shortestPath(origin, destination).size() - 1); i++) {
			System.out.print(searchEngine.shortestPath(origin, destination).get(i) + " -> ");
		    }
		    System.out.println(searchEngine.shortestPath(origin, destination).get(searchEngine.shortestPath(origin, destination).size() - 1));
		}
	    }
	}
	scnr.close();
    }
}
