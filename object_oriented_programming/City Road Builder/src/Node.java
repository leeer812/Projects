import java.util.HashSet;

/**
 * The <code>Node</code> class of <code>City Road Builder</code> acts as a city
 * and has a name, a set of <code>edges</code> (roads) that it is connected to,
 * a path that is used for Djikstra's algorithm, a field to denote whether or
 * not it has been visited for Djikstra's algorithm, and an int to denote how
 * far it is from the source for Djikstra's algorithm
 */
import java.util.LinkedList;

public class Node
{
    String name; // the name of the city
    HashSet<Edge> edges; // all the roads that are connected to this city
    LinkedList<String> path; // the path to get to this city in Djikstra's
    boolean visited; // whether this city has been visited yet in Djikstra's
    int distance; // total distance it takes to get to this city in Djikstra's
    
    /**
     * The default no-args constructor for <code>Node</code> that initializes
     * all the default fields.
     */
    public Node()
    {
        name = "";
        edges = new HashSet<Edge>();
        path = new LinkedList<String>();
        visited = false;
        distance = 0;
    }
    
    /**
     * Constructor that takes in a <code>String</code> parameter that will be
     * used to instantiate this city's name. All of the node's other fields are
     * initialized to their default values.
     * 
     * @param n
     * The <code>String</code> that this city's name will be set to
     */
    public Node(String n)
    {
        name = n;
        edges = new HashSet<Edge>();
        path = new LinkedList<String>();
        visited = false;
        distance = 0;
    }
    
    /**
     * Prints the path taken to arrive to this city from the source city. Used
     * for Djikstra's algorithm.
     * 
     * @return
     * Returns a <code>String</code> representation of path
     */
    public String printPath()
    {
        // If there is nothing in path, return blank
        if (path.isEmpty())
           return "";
        
        String result="";
        
        // Adds every element in path to the string
        for (int i = 0; i < path.size(); i++)
        {
            if (!path.isEmpty())
               result+=path.get(i)+" ";
        }
        return result;
    }
    
    /**
     * Returns a <code>String</code> representation of this city
     * 
     * @return
     * Returns this city's <code>name</code>, <code>visited</code> value, and
     * <code>distance</code>.
     */
    public String toString()
    {
        return String.format("%s %s %d", name, "Visited: " + visited, distance);
    }
    
    /**
     * Returns the name of this city
     * 
     * @return
     * Returns <code>name</code>
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the name of this city to the input <code>String</code>
     * 
     * @param name
     * The input <code>String</code> that this city's <code>name</code> will
     * be set to
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Returns a list of roads that are connected to this city
     * 
     * @return
     * Returns <code>edges</code>
     */
    public HashSet<Edge> getEdges()
    {
        return edges;
    }
    
    /**
     * Sets <code>edges</code> to the input hashset of edges
     * 
     * @param edges
     * The input <code>HashSet</code> of edges that <code>edges</code> will be
     * set to
     */
    public void setEdges(HashSet<Edge> edges)
    {
        this.edges = edges;
    }
    
    /**
     * Returns the path taken to arrive at this city from the source city. Used
     * for Djikstra's algorithm.
     * 
     * @return
     * Returns <code>path</code>
     */
    public LinkedList<String> getPath()
    {
        return path;
    }
    
    /**
     * Sets <code>path</code> to the input <code>LinkedList</code> of <code>
     * Strings</code>.
     * 
     * @param path
     * The input <code>LinkedList</code> of <code>String</code>s that <code>
     * path</code> will be set to.
     */
    public void setPath(LinkedList<String> path)
    {
        this.path = path;
    }
    
    /**
     * Returns whether or not this city has been visited yet
     * 
     * @return
     * Returns <code>visited</code>
     */
    public boolean isVisited()
    {
        return visited;
    }
    
    /**
     * Sets the <code>visited</code> value to the input value
     * 
     * @param visited
     * The value that this city's <code>visited</code> will be set to
     */
    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }
    
    /**
     * Returns the total distance needed to get to this city from the source
     * city
     * 
     * @return
     * Returns <code>distance</code>
     */
    public int getDistance()
    {
        return distance;
    }
    
    /**
     * Sets this city's <code>distance</code> to the input distance
     * 
     * @param distance
     * The input that this city's <code>distance</code> will be set to
     */
    public void setDistance(int distance)
    {
        this.distance = distance;
    }
    
    
}
