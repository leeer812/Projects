/**
 * The <code>RootFullException</code> extends the <code>Exception</code>
 * class and is thrown when a <code>FXTreeNode</code> is added to the root
 * when the root already has a maximum of 1 child.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class RootFullException extends Exception
{
    // The message stored within the exception
    private String message;
    
    /**
     * Instantiates a new <code>RootFullException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>RootFullException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    
    public RootFullException(String s)
    {
        message = s;
    }
}
