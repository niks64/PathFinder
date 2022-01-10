import java.lang.invoke.MethodHandles;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;

import java.io.FileNotFoundException;
import java.util.List;



public class ShortestFlightPathTests {
	SearchBackEnd backend_instance;

	/* --------------------------------------------------- Data Wrangler Tests ---------------------------------------------------- */
	
	/**
	 * Instantiate a data loader instance for data wrangler testing purposes.
	 * @author Nic Leighty - Data Wrangler
	 */
	@BeforeEach
	public void createInstance() {
		loader = new AirportFlightLoader();
	}
	
	/**
	 * Tests that the CSV file retrieved without issues, i.e.,
	 * no FileNotFoundException is thrown.
	 * @author Nic Leighty - Data Wrangler
	 */
	@Test
	public void dataWrangler_TestFileRetrieval() {
		String filePath = "./flights.csv"; 
		try {
			loader.loadFile(filePath);
		} catch (FileNotFoundException e) {
			fail("Failed to load data file at path " + filePath);
		}
	}
	
	/**
	 * Tests for valid flight data in the output of AirportFlightLoader.loadFile
	 * (using the first 5 flights).
	 * @throws FileNotFoundException
	 * @author Nic Leighty - Data Wrangler
	 */
	@Test
	public void dataWrangler_TestFlightData() throws FileNotFoundException {
		List<FlightInterface> flights = loader.loadFile("./flights.csv");
		SearchBackEnd engine = new SearchBackEnd();
		
		if (!(flights.get(0).getOrigin().equals("ANC") && flights.get(0).getDestination().equals("SEA") && flights.get(0).getDistance() == 1448 && flights.get(0).getFlightNum() == 98))
			fail("Incorrect flight data retrieval detected");
		else if (!(flights.get(1).getOrigin().equals("LAX") && flights.get(1).getDestination().equals("PBI") && flights.get(1).getDistance() == 2330 && flights.get(1).getFlightNum() == 2336))
			fail("Incorrect flight data retrieval detected");
		else if (!(flights.get(2).getOrigin().equals("SFO") && flights.get(2).getDestination().equals("CLT") && flights.get(2).getDistance() == 2296 && flights.get(2).getFlightNum() == 840))
			fail("Incorrect flight data retrieval detected");
		else if (!(flights.get(3).getOrigin().equals("LAX") && flights.get(3).getDestination().equals("MIA") && flights.get(3).getDistance() == 2342 && flights.get(3).getFlightNum() == 258))
			fail("Incorrect flight data retrieval detected");
		else if (!(flights.get(4).getOrigin().equals("SEA") && flights.get(4).getDestination().equals("ANC") && flights.get(4).getDistance() == 1448 && flights.get(4).getFlightNum() == 135))
			fail("Incorrect flight data retrieval detected");		
		}
	
	/**
	 * Tests that the loader is not adding duplicate flights (flights with the same flight number) to its output.
	 * It is naive out of necessity due to limitations in externally parsing the large CSV data set
	 * to determine the exact amount of flights in the entire CSV, less duplicates.
	 * Thus, it makes sure that the size of the loader output list is less than the total number of flight entries
	 * in the CSV file.
	 * @throws FileNotFoundException
	 * @author Nic Leighty - Data Wrangler
	 */
	@Test
	public void dataWrangler_TestDuplicateRemovalNaive() throws FileNotFoundException {
		List<FlightInterface> flights = loader.loadFile("./flights.csv");
		SearchBackEnd engine = new SearchBackEnd();
		int csvLines = 5819079; // Number of flight entries in the CSV file
		
		if (!(flights.size() < csvLines))
			fail("No duplicates were removed in the data collection, even though the CSV contains duplicate flight numbers.");
	}
	
	/* ---------------------------------------------------   Back End Tests    ---------------------------------------------------- */
		
	/**
	 * Instantiate a backend for testing purposes
	 * 
	 * @author Nikunj Harlalka
	 * @role Backend Developer
	 */
	@BeforeEach
	public void backEndDeveloper_createInstance() {
	  backend_instance = new SearchBackEnd();
	  
	  //Adding airports to the graphs
	  backend_instance.addAirport("Airport A");
	  backend_instance.addAirport("Airport B");
	  backend_instance.addAirport("Airport C");
	  backend_instance.addAirport("Airport D");
	  backend_instance.addAirport("Airport E");
	  backend_instance.addAirport("Airport F");
	  
	  //Adding flights
	  backend_instance.addFlight("Airport A", "Airport B", 6);
	  backend_instance.addFlight("Airport A", "Airport C", 2);
	  backend_instance.addFlight("Airport A", "Airport D", 5);
	  backend_instance.addFlight("Airport B", "Airport E", 1);
	  backend_instance.addFlight("Airport B", "Airport C", 2);
	  backend_instance.addFlight("Airport C", "Airport B", 3);
	  backend_instance.addFlight("Airport C", "Airport F", 1);
	  backend_instance.addFlight("Airport D", "Airport E", 3);
	  backend_instance.addFlight("Airport E", "Airport A", 4);
	  backend_instance.addFlight("Airport F", "Airport A", 1);
	  backend_instance.addFlight("Airport F", "Airport D", 1);
	}
	
	/**
    	 * Checks the shortest path from airport A to airport F. Returns true if the
    	 * correct path is produced.
    	 * 
     	* @author Nikunj Harlalka
     	* @role Backend Developer
     	*/
	@Test
	public void backEndDeveloper_testShortestPath1() {
	  assertTrue(backend_instance.shortestPath("Airport A", "Airport F").toString().equals("[Airport A, Airport C, Airport F]"));
	}
	
	
	/**
     	* Checks the if the implementation adds airports correctly
     	*	 
    	 * @author Nikunj Harlalka
    	 * @role Backend Developer
     	*/
   	 @Test
    	public void backEndDeveloper_testAdd() {
      		backend_instance.addAirport("Airport G");
      		backend_instance.addFlight("Airport G", "Airport F", 9);
      
     		 assertTrue(backend_instance.getAllDestinations("Airport G").contains("Airport F"));
    	}
    
    	/**
     	* Checks if the getAllAirports method is implemented correctly. Returns true
     	* if the airports added in the beforeEach method are returned correctly.
     	* 
     	* @author Nikunj Harlalka
     	* @role Backend Developer
     	*/
    	@Test
    	public void backEndDeveloper_testGetAllAirports() {
      		assertTrue(backend_instance.getAllAirports().contains("Airport A"));
      		assertTrue(backend_instance.getAllAirports().contains("Airport B"));
      		assertTrue(backend_instance.getAllAirports().contains("Airport C"));
      		assertTrue(backend_instance.getAllAirports().contains("Airport D"));
      		assertTrue(backend_instance.getAllAirports().contains("Airport E"));
      		assertTrue(backend_instance.getAllAirports().contains("Airport F"));
    }
	
	/* ---------------------------------------------------   Front End Tests    ---------------------------------------------------- */
	
    	/**
	 * Test to check that command 1 adds an airport to the database.
	 * 
	 * @author Abby Ruffridge
	 * @role Front End Developer
	 */
    	@Test
	public void frontEnd_TestAddAirport() {
	    TextUITester test1 = new TextUITester("1\nSEA");
	    SearchFrontEndInterface frontEnd = new SearchFrontEnd();
	    SearchBackEndInterface backEnd = new SearchBackEnd();
	    frontEnd.run(backEnd);
	    String check = test1.checkOutput();
	    boolean ifTrue = check.contains("Airport SEA has been added to the database.");
	    assertEquals(true, ifTrue);
	}
	
	/**
	 * Test to check that command 2 adds a flight to the database.
	 * 
	 * @author Abby Ruffridge
	 * @role Front End Developer
	 */
	@Test
	public void frontEnd_TestAddFlight() {
	    TextUITester test2 = new TextUITester("2\n976\nORD\nSEA\n1721");
	    SearchFrontEndInterface frontEnd = new SearchFrontEnd();
	    SearchBackEndInterface backEnd = new SearchBackEnd();
	    frontEnd.run(backEnd);
	    String check = test2.checkOutput();
	    boolean ifTrue = check.contains("Flight 976 from ORD to SEA + has been "
	    	+ "added to the database.");
	    assertEquals(true, ifTrue);
	}
	
	/**
	 * Test to check that command 3 removes a given airport from the database.
	 * 
	 * @author Abby Ruffridge
	 * @role Front End Developer
	 */
	@Test
	public void frontEnd_TestRemoveAirport() {
	    TextUITester test3 = new TextUITester("3\nMSN");
	    SearchFrontEndInterface frontEnd = new SearchFrontEnd();
	    SearchBackEndInterface backEnd = new SearchBackEnd();
	    frontEnd.run(backEnd);
	    String check = test3.checkOutput();
	    boolean ifTrue = check.contains("Airport MSN has been removed from the database.");
	    assertEquals(true, ifTrue);
	}
	
	/**
	 * Test to check that command 4 removes a given flight from the database.
	 * 
	 * @author Abby Ruffridge
	 * @role Front End Developer
	 */
	@Test
	public void frontEnd_TestRemoveFlight() {
	    TextUITester test4 = new TextUITester("4\nORD\nSEA");
	    SearchFrontEndInterface frontEnd = new SearchFrontEnd();
	    SearchBackEndInterface backEnd = new SearchBackEnd();
	    frontEnd.run(backEnd);
	    String check = test4.checkOutput();
	    boolean ifTrue = check.contains("Flight from ORD to SEA has been removed from the database.");
	    assertEquals(true, ifTrue);
	}
	
	/**
	 * Test to check that command 5 returns all destination airports from a given
	 * origin airport. Referenced https://www.msnairport.com/flight_travel/where
	 * to obtain the list of all destination airports from MSN to cross check
	 * the program output with.
	 * 
	 * @author Abby Ruffridge
	 * @role Front End Developer
	 */
	@Test
	public void frontEnd_TestAirportDestinations() {
	    TextUITester test5 = new TextUITester("5\nMSN");
	    SearchFrontEndInterface frontEnd = new SearchFrontEnd();
	    SearchBackEndInterface backEnd = new SearchBackEnd();
	    frontEnd.run(backEnd);
	    String check = test5.checkOutput();
	    boolean ifTrue = check.contains("ATL, CLT, ORD, DFW, DEN, DTW, RSW, LAS, MIA, "
	    	+ "MSP, LGA, EWR, MCO, PHL, PHX, SEA, TPA, DCA");
	    assertEquals(true, ifTrue);
	}
	
	/**
	 * Test to check that command 6 returns the correct shortest path from given
	 * origin and destination airports. Referenced https://www.flightconnections.com
	 * /flights-from-madison-msn to cross check output.
	 * 
	 * @author Abby Ruffridge
	 * @role Front End Developer
	 * 
	 */
	@Test
	public void frontEnd_TestShortestPath() {
	    TextUITester test6 = new TextUITester("6\nMSN\nOSL");
	    SearchFrontEndInterface frontEnd = new SearchFrontEnd();
	    SearchBackEndInterface backEnd = new SearchBackEnd();
	    frontEnd.run(backEnd);
	    String check = test6.checkOutput();
	    boolean ifTrue = check.contains("MSN -> EWR -> OSL");
	    assertEquals(true, ifTrue);
	}
	/* ------------------------------------------------- Integration Manager Tests -------------------------------------------------- */


	AirportFlightLoaderInterface loader;
	public static void main(String[] args) {
		String className = MethodHandles.lookup().lookupClass().getName();
		String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
		String[] arguments = new String[] {
			"-cp",
			classPath,
			"--include-classname=.*",
			"--select-class=" + className };
			ConsoleLauncher.main(arguments);
		}

}
