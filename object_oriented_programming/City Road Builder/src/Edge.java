/**
 * The <code>Edge</code> class of the <code>City Road Builder</code> connects
 * two city <code>Node</code> objects with a road for a certain cost. Whether
 * the road exists yet (if it connects two cities) is kept track of with a
 * <code>connected</code> boolean.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #7
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class Edge
{
    Node A; // City A that this edge connects to
    Node B; // City B that this edge connects to
    int cost; // The cost of this road
    boolean connected; // Specifies whether this edge exists on the graph yet
    
    /** 
     * Constructor that takes two nodes and an int as parameters. Initializes
     * this edge, assigning its nodes and cost. Its default connected status is
     * false.
     * 
     * @param a
     * The node that will be set as town A
     * 
     * @param b
     * The node that will be set as town B
     * 
     * @param i
     * The cost of this edge
     */
    public Edge(Node a, Node b, int i)
    {
        A = a;
        B = b;
        cost = i;
        connected = false;
    }
    
    /**
     * Default no-args constructor that initializes Edge with null town nodes,
     * a cost of -1, and connected as false.
     */
    public Edge()
    {
        A = null;
        B = null;
        cost = -1;
        connected = false;
    }

    /**
     * Compares the cost of this edge to the input edge
     * 
     * @param otherEdge
     * The edge whose cost is being compared to this edge's cost
     * 
     * @return
     * Returns 1 if this edge's cost is greater than other edge's cost, 0 if
     * the costs are equal, and -1 if this edge's cost is less than the other
     * edge's cost.
     */
    int compareTo(Object otherEdge)
    {
        Edge oEdge = (Edge)otherEdge;
        int oCost = oEdge.getCost();
        
        if (cost > oCost)
            return 1;
        else if (cost == oCost)
            return 0;
        else
            return -1;
    }
    
    /**
     * Returns whether this road exists in the graph yet, therefore connecting
     * two cities.
     * 
     * @return
     * Returns <code>connected</code>
     */
    public boolean getConnection()
    {
        return connected;
    }
    
    /**
     * Sets connected to true
     */
    public void connected()
    {
        connected = true;
    }
    
    /**
     * Sets connected to false
     */
    public void unconnected()
    {
        connected = false;
    }

    /**
     * Returns town A of this edge
     * 
     * @return
     * Returns <code>a</code>
     */
    public Node getA()
    {
        return A;
    }

    /**
     * Sets town A of this edge to the specified node
     * 
     * @param a
     * The node that <code>A</code> will be set to
     */
    public void setA(Node a)
    {
        A = a;
    }

    /**
     * Returns town B of this edge
     * 
     * @return
     * Returns <code>b</code>
     */
    public Node getB()
    {
        return B;
    }

    /**
     * Sets town B of this edge to the specified node
     * 
     * @param b
     * The node that <code>B</code> will be set to
     */
    public void setB(Node b)
    {
        B = b;
    }

    /**
     * Returns the cost of this road
     * 
     * @return
     * Returns <code>cost</code>
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * Sets the cost of this road to the input cost
     * 
     * @param cost
     * The cost that <code>cost</code> will be set to
     */
    public void setCost(int cost)
    {
        this.cost = cost;
    }
    
    /**
     * Returns a <code>String</code> representation of this edge
     * 
     * @return
     * Returns town A's name, town B's name, and the cost of this edge in a
     * <code>String</code> representation.
     */
    public String toString()
    {
        return A.getName() + " to " + B.getName() + " " + cost;
    }
}
