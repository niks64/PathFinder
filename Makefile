run: compile
	echo "Add command to run the program here"
	javac ShortestFlightPathApp.java
	java ShortestFlightPathApp

compile: compileDataWrangler SearchBackEnd.class compileFrontEnd
	echo "Use this rule to compile all necessary java source files"


SearchBackEnd.class: SearchBackEnd.java
	javac SearchBackEnd.java

compileDataWrangler: FlightData.java
	javac FlightData.java
	javac AirportFlightLoader.java

compileFrontEnd: SearchFrontEnd.java
	javac SearchFrontEnd.java


test: compileTest
	javac ShortestFlightPathTests.java
	
compileTest:
	java ShortestFlightPathTests.java

clean:
	rm *.class
