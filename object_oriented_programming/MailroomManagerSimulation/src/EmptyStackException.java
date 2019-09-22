/**
 * The <code>EmptyStackException</code> extends the <code>Exception</code>
 * class and is thrown when a <code>Package</code> is popped from an empty
 * <code>PackageStack</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #3
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class EmptyStackException extends Exception
{
    // The message stored within the exception
    private String message;
    
    /**
     * Instantiates a new <code>EmptyStackException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>EmptyStackException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    public EmptyStackException(String s)
    {
        message = s;
    }
    
    /** 
     * Returns a <code>String</code> containing the message stored within this
     * Exception.
     * 
     * @return
     *    Returns this object's <code>message</code>
     */
    
    public String toString()
    {
        return message;
    }
}
