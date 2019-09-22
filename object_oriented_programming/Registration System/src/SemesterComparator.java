import java.util.Comparator;

/**
 * The <code>SemesterComparator</code> class implements the <code>Comparator
 * </code> class so that it may be used to sort java data structures. This
 * class overrides <code>Comparator<code>'s <code>compare</code> method by
 * checking to see if the two input courses are equal in year. If the year is
 * the same, then their seasons are compared.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #6
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class SemesterComparator implements Comparator
{

    /**
     * The <code>compare</code> method overrides the compare method in
     * <code>Comparator</code> and casts two objects into courses and compares
     * their semesters.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>Object</code> parameters are assumed to be
     *    <code>Course</code> objects.
     *    
     * <dt>Postcondition:
     *    <dd><code>o1</code> and <code>o2</code>'s semesters are substringed
     *    into two parts, consisting of their course season (F/S) and course
     *    year (2010 - 2025). If the year of the two courses are the same, then
     *    the seasons are compared and returned. If the years of the two
     *    courses are different, then they are compared and returned.
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        Course left = (Course)o1;
        Course right = (Course)o2;
        
        String leftSemester = left.getSemester();
        String rightSemester = right.getSemester();
        
        // Parses the years into ints so that they can be compared as numbers
        // The year is found in the last 4 spaces of the length 5 string
        // Example semester string: F2017
        int lYear = Integer.parseInt(leftSemester.substring(1, 5));
        int rYear = Integer.parseInt(rightSemester.substring(1, 5));
        
        if (lYear < rYear)
            return -1;
        else if (lYear > rYear)
            return 1;
        else
        {
            // The season can either be F (Fall) or S (Spring). S is larger
            // than F with the compareTo method. Since Fall is after Spring,
            // we will consider Fall to be bigger than Spring and multiply
            // the result of compareTo by -1 to flip its effects.
            String lSeason = leftSemester.substring(0, 1);
            String rSeason = rightSemester.substring(0, 1);
            return lSeason.compareTo(rSeason) * -1;
        }
    }

}
