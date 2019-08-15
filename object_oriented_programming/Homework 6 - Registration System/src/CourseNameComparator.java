import java.util.Comparator;

/**
 * The <code>CourseNameComparator</code> class implements the <code>Comparator
 * </code> class so that it may be used to sort java data structures. This
 * class overrides <code>Comparator<code>'s <code>compare</code> method by
 * checking to see if the two input courses are equal in departments. If they
 * are, then it checks to see if their course numbers are equal.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #6
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class CourseNameComparator implements Comparator
{
    /**
     * The <code>compare</code> method overrides the compare method in
     * <code>Comparator</code> and casts two objects into courses and compares
     * their departments and course number.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>Object</code> parameters are assumed to be
     *    <code>Course</code> objects.
     *    
     * <dt>Postcondition:
     *    <dd>If <code>o1</code> and <code>o2</code> have equal
     *    <code>department</code>s, then their <code>number</code>s are
     *    compared and returned. If their departments are not equal, then
     *    their departments are compared and returned.
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        // Casts o1 into a course named left
        Course left = (Course)o1;
        // Casts o2 into a course named right
        Course right = (Course)o2;
        
        String lDepartment = left.getDepartment();
        String rDepartment = right.getDepartment();
        
        int lNumber = left.getNumber();
        int rNumber = right.getNumber();
        
        // If the departments are equal, then compare their numbers
        if (lDepartment.compareTo(rDepartment) == 0)
        {
            if (lNumber < rNumber)
                return -1;
            else if (lNumber > rNumber)
                return 1;
            else
                return 0;
        }
        else
            // If the departments are not equal, just compare the departments
            // because the departments are priority and numbers are irrelevant
            return lDepartment.compareTo(rDepartment);
    }

}
