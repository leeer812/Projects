import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import big.data.DataSource;

/**
 * The <code>RoadCalculator</code> class loads a <code>HashMap</code> of cities
 * represented in nodes. The calculator calculates the Minimum Spanning Tree of
 * the graph and can find the cheapest route from one city to another using
 * Djikstra's algorithm.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #7
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class RoadCalculator
{
    private static HashMap<String, Node> graph;
    private static LinkedList<Edge> mst;
    
    /**
     * The main method of <code>RoadCalculator</code> loads a graph of cities
     * based on the input URL provided by the user. A MST is then generated and
     * displayed for the user. The user is then prompted for a source city and
     * destination city to find the cheapest possible path from the source city
     * to the destination using Djikstra's algorithm. Once the user is done,
     * the user can quit.
     */
    public static void main(String[] args)
    {
        Boolean on = true;
        Scanner scanner = new Scanner(System.in);
        
        // While there is no graph loaded, prompt the user for a URL to load
        // the graph from
        while (graph == null)
        {
            System.out.println("Please enter graph URL: ");
            // build the graph using the input
            graph = buildGraph(scanner.nextLine());
        }
        
        // Creates the Minimum Spanning Tree sequence using the graph
        mst = buildMST(graph);
        System.out.format("%n%s%n%n", "Minimum Spanning Tree: ");
        
        // Prints all of the elements in the MST
        for (int i = 0; i < mst.size(); i++)
        {
            System.out.println(mst.get(i));
        }
        
        // Prompts the user for a source and destination city to calculate the
        // cheapest route linking the two cities until the user decides to
        // input Q to quit.
        while (on = true)
        {
            System.out.format("%n%s", "Please enter a starting point or Q for quit: ");
            String choice = scanner.nextLine();
            if (choice.equals("q") || choice.equals("Q"))
            {
                System.out.println("Bye!");
                System.exit(0);
            }
            else
            {
                String source = choice;
                System.out.println("Enter a destination: ");
                String dest = scanner.nextLine();
                
                System.out.println("Distance: " + Dhikstra(graph, source, dest));
            }
        }
    }
    
    /**
     * Builds a graph of nodes using an input URL
     * 
     * @param location
     * The input URL that will be used to create a graph of nodes
     * 
     * <dt>Precondition:
     *    <dd><code>location</code> is a valid URL linking to a xml file in the
     *    correct format as specified by the prompt.
     *    
     * <dt>Postcondition:
     *    <dd>A graph with all the cities and roads is created and returned if
     *    the location was valid. If location was invalid, then null is
     *    returned and the user is notified as such.
     * 
     * @return
     * Returns a <code>HashMap</code> of nodes, addressed by the city name.
     */
    public static HashMap<String, Node> buildGraph(String location)
    {
        try
        {
            // Initializes a new HashMap of nodes, addressed by strings
            HashMap<String, Node> cities = new HashMap<String, Node>();
            // Sets source to the input location
            DataSource ds = DataSource.connectXML(location);
            ds.load();
            // Loads everything in the 'cities' tag
            String cityNamesStr = ds.fetchString("cities");
            // Replaces \" with blank, and splits the input by commas so that
            // each city inhabits a different cell
            String[] cityNames = cityNamesStr.
                substring(1, cityNamesStr.length() - 1).
                replace("\"", "").split(",");
            // Loads everything in the 'roads' tag
            String roadNamesStr = ds.fetchString("roads");
            // Splits the input by ", so that each road inhabits a different
            // cell. ", instead of , because each road is input with quotation
            // marks in the input xml
            String[] roadNames = roadNamesStr.
                substring(2 , roadNamesStr.length() - 2).split("\",\"");
            // Puts every city into the graph and prints the name of the city
            // after it has been placed into the graph
            System.out.format("%n%s%n%n", "Cities: ");
            for (int i = 0; i < cityNames.length; i++)
            {
                cities.put(cityNames[i], new Node(cityNames[i]));
                System.out.println(cities.get(cityNames[i]).getName());
            }
            // For each road in the xml, creates the road and adds it into the
            // edge HashSet of the towns that the road is connected to
            System.out.format("%n%s%n%n", "Roads: ");
            for (int i = 0; i < roadNames.length; i++)
            {
                // Splits the road name by commas so that the town names and
                // cost each inhabit a different cell
                String roadName[] = roadNames[i].split(",");
                Node a = cities.get(roadName[0]);
                Node b = cities.get(roadName[1]);
                Edge e = new Edge(a, b, Integer.parseInt(roadName[2]));
                Edge eb = new Edge(b, a, Integer.parseInt(roadName[2]));
                System.out.println(e.toString());
                a.getEdges().add(e);
                b.getEdges().add(eb);
            }
            return cities;
        }
        catch(Exception e)
        {
            System.out.println("There was a problem with the URL provided");
        }
        return null;
    }

    /**
     * Builds a Minimum Spanning Tree for the input <code>graph</code>.
     * 
     * @param graph
     * The graph that a minimum spanning tree will be built for
     * 
     * <dt>Precondition:
     *    <dd>The input <code>graph</code> must be a fully built graph of nodes
     *    and edges.
     * 
     * @return
     * Returns a <code>LinkedList</code> of edges for the MST of the input
     * graph.
     */
    public static LinkedList<Edge> buildMST(HashMap<String, Node> graph)
    {
        LinkedList<Edge> MST = new LinkedList<Edge>();
        ArrayList<Edge> edges = new ArrayList<Edge>();
        Edge currentEdge;
        int cityCount = graph.size();
        
        // adds all the edges in the graph into an array list and sorts all
        // the edges from lowest cost to greatest
        edges = addSortEdges(edges);
        currentEdge = edges.get(0);
        // adds the first, and lowest cost edge, to the MST
        MST.add(currentEdge);
        // sets both the nodes that are linked by this edge to visited
        currentEdge.getA().setVisited(true);
        currentEdge.getB().setVisited(true);
        // decrease total cityCount by 2 since these cities have been visited
        cityCount -= 2;
        
        // While there are still cities that we have not visited
        while (cityCount > 0)
        {
            // For all of the edges that exist in the graph
            for (int i = 0; i < edges.size(); i++)
            {
                currentEdge = edges.get(i);
                // If the current edge's town A has been visited and town B
                // has not been visited 
                if (currentEdge.getA().isVisited() && !currentEdge.getB()
                    .isVisited())
                {
                    // adds the current edge to the MST
                    MST.add(currentEdge);
                    // sets town B of the current edge to visited so that we
                    // will not process this edge again
                    currentEdge.getB().setVisited(true);
                    // decreases the total amount of cities less since this
                    // city has been visited
                    cityCount--;
                }
            }
        }
        // Resets the properties of the nodes so that they do not obstruct
        // future method calls
        resetNodes(graph);
        return MST;
    }
    
    /**
     * Resets the properties of the nodes in the input graph
     * 
     * @param graph
     * The graph whose nodes will be reset to their default properties
     * 
     * @return
     * Returns the input <code>graph</code> with all of its nodes default
     * properties reset to the default properties that they had when they were
     * put into the graph
     */
    private static HashMap<String, Node> resetNodes(HashMap<String, Node> graph)
    {
        // Goes through each node in the graph
        for (Node n : graph.values())
        {
            // Sets visited to false and resets its path
            n.visited = false;
            n.setPath(new LinkedList<String>());
            // Sets all of the edge's visited to false
            for (Edge e : n.getEdges())
            {
                e.getA().setVisited(false);
                e.getB().setVisited(false);
            }
        }
        return graph;
    }

    /**
     * Adds all of the edges in the graph to the input array list and sorts
     * the edges from lowest cost to highest cost.
     * 
     * @param edges
     * The array list that will be filled with the sorted edges in graph
     * 
     * @return
     * Returns an <code>ArrayList</code> of all the edges in graph with their
     * costs sorted in increasing order.
     */
    private static ArrayList<Edge> addSortEdges(ArrayList<Edge> edges)
    {
        EdgeComparator comparator = new EdgeComparator();
        for (Node n: graph.values())
        {
            edges.addAll(n.getEdges());
        }
        Collections.sort(edges, comparator);
        return edges;
    }
    
    /**
     * Calculates the shortest path (in cost) between the source and
     * destination city in the graph.
     * 
     * @param graph
     * The <code>HashMap</code> of the nodes where the source and destination
     * city are found.
     * 
     * @param source
     * The beginning city
     * 
     * @param dest
     * The ending city
     * 
     * <dt>Precondition:
     *    <dd>The input source and destination cities must be in the input
     *    <code>graph</code>.
     *    
     * <dt>Postcondition:
     *    <dd>The path for the smallest total cost accumulated from traveling
     *    from the source to destination city is printed and the cost is
     *    returned if both the source and destination city were found in the
     *    graph. If either of the cities were not in the graph, then the user
     *    is notified as such and -1 is returned.
     * 
     * @return
     * Returns the smallest total cost needed to go from the source city to
     * the destination city. If either cities were not in the graph, then -1
     * is returned.
     */
    public static int Dhikstra(HashMap<String, Node> graph, String source,
        String dest)
    {
        ArrayList<Node> unsortedCities = new ArrayList<Node>();
        
        // Adds all the cities in the graph into unsortedCities and initializes
        // their distances as infinity
        for (Node n : graph.values())
        {
            unsortedCities.add(n);
            n.setDistance(Integer.MAX_VALUE);
        }
        
        try
        {
           // Start at the source city
           Node current = graph.get(source);
           // the distance it takes to travel to the source city from the
           // source city is 0
           current.setDistance(0);

           // while there are still cities that we have not tried going to
           while (!unsortedCities.isEmpty())
           {
               // Go through all of the adjacent cities
               for (Edge e : current.getEdges())
               {
                   // if the distance of going to the adjacent city from the
                   // current city is greater than the previous distance of
                   // going to the city
                   if(e.getA().getDistance() + e.getCost()
                        < e.getB().getDistance())
                   {
                       // set the adjacent city's distance to the cost it takes
                       // to get there from the current city
                       e.getB().setDistance(e.getA().getDistance()
                           + e.getCost());
                       // Reset its path and set it to the path it took to get
                       // there from the current city
                       e.getB().setPath(new LinkedList<String>());
                       e.getB().getPath().add(current.printPath() 
                           + current.getName());
                   }
               }
               // remove this city from unsortedCities since we have visited it
               unsortedCities.remove(current);
               // Go through all of the unsortedCities
               for (int i = 0; i < unsortedCities.size(); i++)
               {
                   // if the distance of a city is less than max value, then
                   // we must have visited it as an adjacent city and therefore
                   // we need to visit its adjacent cities so we set current
                   // to it
                   if (unsortedCities.get(i).getDistance() < Integer.MAX_VALUE)
                   {
                       current = unsortedCities.get(i);
                       break;
                   }
               }
           }
           // Prints the path taken from source to destination
           System.out.println("Path: " + graph.get(dest).printPath());
           // Resets the properties of the graph
           graph = resetNodes(graph);
           // Returns the distance taken from source to destination
           return graph.get(dest).getDistance();
        }
        catch(Exception x)
        {
            System.out.println("There was an error inputting the city names");
        }
        // Resets the properties of the graph and returns -1 because this is
        // reached only in the case that an exception was thrown due to an
        // error in input and no shortest route was calculated.
        graph = resetNodes(graph);
        return -1;
    }
}
