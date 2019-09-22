/**
 * The <code>InvalidIndexException</code> extends the <code>Exception</code>
 * class and is thrown when an input index is outside the valid range.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class InvalidIndexException extends Exception
{
    // The message stored within the exception
    private String message;
    
    /**
     * Instantiates a new <code>InvalidIndexException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>message</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>InvalidIndexException</code> is instantiated and has
     *    its <code>message</code> field set to the input <code>s</code>.
     */
    
    public InvalidIndexException(String s)
    {
        message = s;
    }
}
