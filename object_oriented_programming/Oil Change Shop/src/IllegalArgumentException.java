/**
 * <code>IllegalArgumentException</code> class extends <code>Exception</code>
 * and is thrown when an input <code>Car</code> points to a non-existing (null)
 * <code>Car</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */
public class IllegalArgumentException extends Exception 
{
    
    // The message stored within the exception.
    String message="";

    /**
     * Instantiates a new <code>IllegalArgumentException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>IllegalArgumentException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    public IllegalArgumentException(String s)
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
    public String getMessage()
    {
        return message;
    }
}
