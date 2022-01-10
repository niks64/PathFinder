import java.util.List;

public class ShortestFlightPathApp {
	
	public static void main(String[] args) throws Exception{
		System.out.println("Welcome to ShortestFlightPath");
		List<FlightInterface> flights = new AirportFlightLoader().loadFile("./flights.csv");
		
		SearchBackEnd engine = new SearchBackEnd();
		
		for(FlightInterface f: flights) {
			engine.addAirport(f.getOrigin());
			if(!engine.getGraph().containsVertex(f.getOrigin())) {
				engine.addAirport(f.getOrigin());
			}

			if(!engine.getGraph().containsVertex(f.getDestination())) {
				engine.addAirport(f.getDestination());
			}
			
			engine.addFlight(f.getOrigin(), f.getDestination(), f.getDistance());	

		}
		
		// System.out.println(engine.getAllAirports().get(0));

		SearchFrontEndInterface ui = new SearchFrontEnd();
		ui.run(engine);
	}
	
}
