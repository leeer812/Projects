import java.util.ArrayList;

/**
 * The <code>HoldingQueue</code> class extends, and inherits all of the methods
 * in, <code>VirtualLine</code>. The <code>HoldingQueue</code> can only hold a
 * certain amount of people which is specified by <code>maxSize</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #4
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class HoldingQueue extends VirtualLine
{
    // The maximum amount of people allowed in the queue
    private int maxSize;
    
    /**
     * Returns an instance of <code>HoldingQueue</code> with its name set to
     * the input <code>n</code>
     * 
     * @param n
     * The input <code>n</code> that <code>name</code> will be set to
     */
    public HoldingQueue(String n)
    {
        super(n);
    }
    
    /**
     * Adds the input <code>Person</code> to the end of the queue if its
     * current <code>size</code> is less than its <code>maxSize</code>. If a
     * person is successfully added, then <code>size</code> is incremented by 1
     * 
     * <dt>Precondition:
     *    <dd><code>size</code> must be less than <code>maxSize</code>
     * 
     * @param p
     * The input <code>Person</code> that will be added into the queue
     * 
     * <dt>Postcondition:
     *    <dd>The input <code>Person</code> is added to the end of the queue
     *    if <code>size</code> was less than <code>maxSize</code>. If a person
     *    was added to the queue, then <code>size</code> is incremented by 1.
     */
    @Override
    public void enqueue(Person p)
    {
        if (this.size < maxSize)
            super.enqueue(p);
    }

    /**
     * Returns the maximum amount of people allowed on the queue
     * 
     * @return
     * Returns <code>maxSize</code>
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the <code>maxSize</code> to the input <code>maxSize</code>
     * 
     * @param maxSize
     * The input <code>maxSize</code> that <code>maxSize</code> will be set to
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
