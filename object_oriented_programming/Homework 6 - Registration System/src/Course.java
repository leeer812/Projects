import java.io.Serializable;

/**
 * The <code>Course</code> class is a course within the <code>Lunar System
 * </code> and implements <code>Serializable</code> so that it may be stored
 * in a hash table. The course contains a <code>department</code>, a course
 * <code>number</code>, and the <code>semester</code> that it belongs to.
 * Methods are given to get, set, and manipulate these fields.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #6
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class Course implements Serializable
{
    private String department;
    private int number;
    // The semester the course is being taken (F/FALL, S/SPRING) and year
    // between 2010 and 2025
    private String semester;
    
    /**
     * Returns an instance of the <code>Course</code> object given a <code>
     * String</code>, <code>int</code>, and <code>String</code> as parameters.
     * 
     * @param d
     * The department that <code>department</code> will be set to
     * 
     * @param n
     * The course number that <code>number</code> will be set to
     * 
     * @param s
     * The semester that <ode>semester</code> will be set to
     * 
     * <dt>Postcondition:
     *    <dd><code>department</code>, <code>number</code>, and <code>semester
     *    </code> are initialized and set to equal their respective inputs.
     */
    public Course(String d, int n, String s)
    {
        department = d;
        number = n;
        semester = s;
    }
    
    /**
     * Returns this student's <code>department</code>
     * 
     * @return
     * Returns <code>department</code>
     */
    public String getDepartment()
    {
        return department;
    }
    
    /**
     * Sets <code>department</code> equal to the input <code>String</code>
     * parameter.
     * 
     * @param department
     * The <code>String</code> that <code>department</code> will be set to
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }
    
    /**
     * Returns this course's course number
     * 
     * @return
     * Returns <code>number</code>
     */
    public int getNumber()
    {
        return number;
    }
    
    /** Sets <code>course</code> to the input <code>int</code>
     * 
     * @param number
     * The int parameter that <code>number</code> will be set to
     */
    public void setNumber(int number)
    {
        this.number = number;
    }
    
    /**
     * Returns this course's <code>semester</code>
     * 
     * @return
     * Returns <code>semester</code>
     */
    public String getSemester()
    {
        return semester;
    }
    
    /**
     * Sets semester to the input <code>String <code> parameter
     * 
     * @param semester
     * The input <code>String</code> parameter that <code>semester</code> will
     * be set to
     */
    public void setSemester(String semester)
    {
        this.semester = semester;
    }
    
    /**
     * Returns a <code>String</code> containing <code>department</code> and
     * <code>semester</code>.
     */
    public String toString()
    {
        return department + " " + semester;
    }
    
    /**
     * Returns a <code>String</code> containing <code>department</code>, 
     * <code>number</code> and <code>semester</code>.
     * 
     * @return
     * Returns a <code>String</code> with <code>department</code>, <code>
     * number</code>, and <code>semester<code>.
     */
    public String studentMenuToString()
    {
        String d = department.toUpperCase();
        String s = semester.toUpperCase();
        return String.format("%-6s%-7d%s", d, number, s);
    }
}
