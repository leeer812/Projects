import java.util.Comparator;

/**
 * The <code>EdgeComparator</code> class implements the <code>Comparator
 * </code> class so that it may be used to sort java data structures. This
 * class overrides <code>Comparator<code>'s <code>compare</code> method by
 * checking to see which of the two edges' costs are less.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #7
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class EdgeComparator implements Comparator
{
    /**
     * The <code>compare</code> method overrides the compare method in
     * <code>Comparator</code> and casts two objects into edges and compares
     * their costs
     * 
     * <dt>Precondition:
     *    <dd>The input <code>Object</code> parameters are assumed to be
     *    <code>Edge</code> objects.
     *    
     * <dt>Postcondition:
     *    <dd><code>o1</code> and <code>o2</code>'s costs are compared and the
     *    respective int (-1 for o1 is less than o2, 0 for equal, 1 for o1 is
     *    more than o2) is returned.
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        Edge edge1 = (Edge)o1;
        Edge edge2 = (Edge)o2;
        
        if (edge1.getCost() < edge2.getCost())
            return -1;
        else if (edge1.getCost() == edge2.getCost())
            return 0;
        else
            return 1;
    }
}
