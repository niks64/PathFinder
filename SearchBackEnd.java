import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

interface SearchBackEndInterface {
  public void addAirport(String airportName);
  public boolean removeAirport(String airportName);
  public void addFlight(String source, String destination, int weight);
  public void removeFlight(String source, String destination);
  public List<String> shortestPath(String source, String destination);
  public List<String> getAllAirports();
  public List<String> getAllDestinations(String source); 
}

public class SearchBackEnd implements SearchBackEndInterface{

    CS400Graph<String> graph;
    
    public SearchBackEnd() {
      graph = new CS400Graph<String>();
    }

    public CS400Graph<String> getGraph() {
      return graph;
    }

    @Override
    public void addAirport(String airportName) {
      graph.insertVertex(airportName);
    }
    
    @Override
    public boolean removeAirport(String airportName) {
      return graph.removeVertex(airportName);
    }
    
    @Override
    public void addFlight(String source, String destination, int weight) {
      graph.insertEdge(source, destination, weight);
    }
    
    @Override
    public void removeFlight(String source, String destination) {
      graph.removeEdge(source, destination);
    }    

    @Override
    public List<String> shortestPath(String source, String destination) {
      return graph.shortestPath(source, destination);
    }
    
    @Override
    public List<String> getAllAirports() {
      LinkedList<String> returnList = new LinkedList<String>();
      
      for (CS400Graph.Vertex v: graph.vertices.values()) {
        if (v != null) {
          returnList.add((String) v.data);
        }
      }
      
      return returnList; 
    }
    
    @Override
    public List<String> getAllDestinations(String source) {
      LinkedList<String> returnList = new LinkedList<String>();
      
      for (int i = 0; i < graph.vertices.get(source).edgesLeaving.size(); i++) {
        returnList.add(graph.vertices.get(source).edgesLeaving.get(i).target.data);
      }
      
      return returnList;
    } 
    
}

