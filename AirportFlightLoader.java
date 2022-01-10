import java.io.BufferedReader;
import java.util.zip.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

interface AirportFlightLoaderInterface {
	public List<FlightInterface> loadFile(String csvFilePath) throws FileNotFoundException;

}

public class AirportFlightLoader implements AirportFlightLoaderInterface {
	int column = 0;
	int flightID; // Flight number
	String origin;
	String destination;
	int distance;

	@Override
	public List<FlightInterface> loadFile(String csvFilePath) throws FileNotFoundException {
		if(csvFilePath == null || csvFilePath == "") {
        	throw new FileNotFoundException();
        }
		
		List<FlightInterface> output = new LinkedList<>();
		BooleanSupplier relevantColumn = () -> {
			if (column == 5 || column == 7 || column == 8 || column == 17) // These columns contain the relevant fields for our flight object
				return true;
			else return false;};
        
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
			String substring = "";        	
        	
        	//fields is an array of headers
        	//each index of the array corresponds to a header, which when done with a line
        	//we will add entries based as a flight
        	String[] fields = new String[4];
        	
        	//keeps track of index in field
        	int count = 0;
        	
        	//reading the line of headers
            //first line is irrelevant, so skip it
        	br.readLine();
        	String line = br.readLine();
        	
        	while (line != null) {
    			for (char c : line.toCharArray()) {
    				if (c == ',') {
    					if (relevantColumn.getAsBoolean()) { // If we were just in a relevant column
    						fields[count] = substring;
    						substring = "";
    						count++;
    					}
    					column++;
    					if (count >= 4) // All fields have been completed
    						break;
    				} else if (relevantColumn.getAsBoolean()) {
    					substring = substring + c;
    				}
    			}
  
    			// converts any strings into correct types
    			flightID = Integer.valueOf(fields[0]);
    			origin = fields[1];
    			destination = fields[2];
    			distance = Integer.valueOf(fields[3]);
    			
    			// Check to see if we have a duplicate
    			boolean isDuplicate = false;
    			for (FlightInterface f : output) {
    				if (f.getFlightNum() == flightID) {
    					isDuplicate = true;
    					break;
    				}
    			}
    			if (!isDuplicate) // Only add the flight if one with a duplicate ID doesn't exist in the output data already
    				output.add(new FlightData(flightID, origin, destination, distance));
    			
    			// Prepare for reading next line
        		fields = new String[4];
        		column = 0;
        		count = 0;
        		line = br.readLine();
        	}
		} catch (FileNotFoundException fnfe) {
			System.out.println("FileNotFoundException caught atempting to open " + csvFilePath);
			fnfe.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException caught reading file " + csvFilePath);
			e.printStackTrace();
		}
		return output;
		
	}
}

/* The below class is now irrelevant/obsolete. The interface changed in the application phase. ------------------------------------------------------------------------------------------------------------ */
class AirportFlightLoaderPlaceholder implements AirportFlightLoaderInterface {
	List<String> airports = new LinkedList<String>(); // List of airport abbreviations
	Hashtable<Integer, List<Object>> flightContainer = new Hashtable<Integer, List<Object>>(); /** Hashtable for storing the flights. The key is the flight number,
																								 * and the value is a list where:
																								 * index 0  is the origin airport
																								 * index 1 is the destination airport
																								 * index 2 is the distance.
																								 */
	List<Object> flightDataChain = new LinkedList<Object>(); // The value chaining list for the previous Hashtable
	List<Object> finalList = new LinkedList<Object>(); /** The Object list which is to be returned and contains all pertinent data.
														 * Index 0 contains the airport list.
														 * Index 1 contains the flight Hashtable.
												         */
	public List<FlightInterface> loadFile(String csvFilePath) throws FileNotFoundException {
		airports.add("MSN");
		airports.add("LAX");
		flightDataChain.add(0, "MSN");
		flightDataChain.add(1, "LAX");
		flightDataChain.add(2, 1991);
		flightContainer.put(0, flightDataChain);
        finalList.add(0, airports);
        finalList.add(1, flightContainer);
        return null; // set to null to avoid warnings (interface construction has changed in application phase)
	}
}
