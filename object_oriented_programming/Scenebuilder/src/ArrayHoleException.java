/**
 * The <code>ArrayHoleException</code> extends the <code>Exception</code>
 * class and is thrown when a <code>FXTreeNode</code> is added into a children
 * array such that a hole is created in the array.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class ArrayHoleException extends Exception
{
    // The message stored within the exception
    private String message;
    
    /**
     * Instantiates a new <code>ArrayHoleException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>ArrayHoleException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    
    public ArrayHoleException(String s)
    {
        message = s;
    }
}
