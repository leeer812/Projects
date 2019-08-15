/**
 * The <code>FullQueueException</code> extends the <code>Exception</code>
 * class and is thrown when a <code>Person</code> is enqueued into a full
 * <code>HoldingQueue</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #4
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class FullQueueException extends Exception
{
    // The message stored within the exception
    private String message;
    
    /**
     * Instantiates a new <code>FullQueueException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>FullQueueException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    
    public FullQueueException(String s)
    {
        message = s;
    }
 

}
