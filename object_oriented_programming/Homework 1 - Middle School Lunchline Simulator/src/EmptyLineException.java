/**
 * <code>EmptyLineException</code> class extends <code>Exception</code>
 * and is thrown when there are no <code>student</code> objects in the
 * <code>students</code> array 
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */
public class EmptyLineException extends Exception 
{
    
    // The message stored within the exception.
    String message="";

    /**
     * Instantiates a new <code>EmptyLineException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>EmptyLineException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    public EmptyLineException(String s)
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
