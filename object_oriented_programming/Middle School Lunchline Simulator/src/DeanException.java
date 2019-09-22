/**
 * <code>DeanException</code> class extends <code>Exception</code>
 * and is thrown when there are already 20 <code>student</code> objects
 * in the <code>students</code> array in the <code>StudentLine</code> object
 * to alert the user that the last student on the line has been removed to make
 * space for the newly added student.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */
public class DeanException extends Exception 
{
    // The message stored within the exception.
    String message="";
    
    // The name of the last student on the line who is being removed to make
    // space for the new student.
    String name = "";
    
    /**
     * Instantiates a new <code>DeanException</code>.
     *
     * @param s
     *    The input <code>String</code> the <code>name</code> will be set to.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>DeanException</code> is instantiated and has
     *    its <code>message</code> field set to the alert message with the
     *    <code>name</code> of the last student on the line to indicate the
     *    last student was removed to make space for the newly added student.
     */
    public DeanException(Student s)
    {
        name = s.getName();
        message = "The line is full and with the addition of our new student, " + name + " has been removed from the line by the dean.";
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
