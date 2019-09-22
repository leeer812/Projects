/**
 * <code>EmptyListException</code> class extends <code>Exception</code>
 * and is thrown when there are no <code>CarListNode</code> objects in the
 * list.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class EmptyListException extends Exception
{
    // The message stored within the exception.
    private String message = "";

    /**
     * Instantiates a new <code>EmptyListException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>EmptyListException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */

    public EmptyListException(String s)
    {
        message = s;
    }

    /** 
     * Prints a <code>String</code> containing the message stored within this
     * Exception.
     * 
     * @return
     *    Returns this object's <code>message</code>
     */
    
    public void printMessage()
    {
        System.out.format("%s", message);
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
