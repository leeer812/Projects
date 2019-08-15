import java.io.Serializable;
import java.util.ArrayList;

/**
 * The <code>Student</code> class is a student within the <code>Lunar System
 * </code> and implements <code>Serializable</code> in order to be stored in a
 * hash table. The student contains a <code>webID</code>, an <code>ArrayList
 * </code> of <code>Course</code> objects, and <code>courseCount</code> to
 * count how many courses the student is enrolled in. Methods are provided to
 * get, set, and manipulate the various fields.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #6
 * CSE214-R02
 * TA: David S. Li
 *
 */


public class Student implements Serializable
{
    private String webID;
    private ArrayList<Course> courses;
    private int courseCount;
    
    /**
     * Returns an instance of the <code>Student</code> object given a <code>
     * String</code> parameter.
     * 
     * @param id
     * The <code>String</code> that this student's <code>webID</code> will be
     * set to.
     * 
     * <dt>Postcondition:
     *    <dd>This student object's <code>webID</code> is set to the input
     *    <code>String</code> parameter. <code>courses</code> is initialized
     *    as a new <code>ArrayList</code> of courses, and <code>courseCount
     *    </code> is initialized as 0.
     */
    public Student(String id)
    {
        webID = id;
        courses = new ArrayList<Course>();
        courseCount = 0;
    }
    
    /**
     * Increments <code>courseCount</code> by 1
     * 
     * <dt>Postcondition:
     *    <dd><code>courseCount</code> is incremented by 1
     */
    public void incrementCourseCount()
    {
        courseCount++;
    }

    /**
     * Decrements <code>courseCount</code> by 1
     * 
     * <dt>Postcondition:
     *    <dd><code>courseCount</code> is decremented by 1
     */
    public void decrementCourseCount()
    {
        courseCount--;
    }
    
    /**
     * Returns this student's <code>webID</code>
     * 
     * @return
     * Returns this student's <code>webID</code>
     */
    public String getWebID()
    {
        return webID;
    }
    
    /**
     * Sets this student's <code>webID</code> to the input <code>String</code>
     * parameter.
     * 
     * @param webID
     * The <code>String</code> parameter that <code>webID</code> will be set
     * to.
     */
    public void setWebID(String webID)
    {
        this.webID = webID;
    }
    
    /**
     * Returns this student's <code>ArrayList</code> of <code>courses</code>
     * 
     * @return
     * Returns <code>courses</code>
     */
    public ArrayList<Course> getCourses()
    {
        return courses;
    }
    
    /**
     * Returns this student's <code>courseCount</code>
     * 
     * @return
     * Returns <code>courseCount</code>
     */
    public int getCourseCount()
    {
        return courseCount;
    }

    /**
     * Sets this student's <code>courseCount</code> to the input <code>int
     * </code>
     * 
     * @param courseCount
     * The input <code>int</code> that <code>courseCount</code> will be set to
     */
    public void setCourseCount(int courseCount)
    {
        this.courseCount = courseCount;
    }

    /**
     * Sorts the <code>Course</code> objects in the <code>Course</code> <code>
     * ArrayList</code> courses by their <code>department</code> and course
     * <code>number</code>.
     * 
     * <dt>Postcondition:
     *    <dd>The courses in <code>courses</code> are sorted by their
     *    departments and numbers, descending in decreasing alphabetical order.
     */
    public void sortCoursesCND()
    {
        CourseNameComparator c = new CourseNameComparator();
        courses.sort(c);
    }
    
    /**
     * Sorts the <code>Course</code> objects in the <code>Course</code> <code>
     * ArrayList</code> courses by their <code>semester</code>.
     * 
     * <dt>Postcondition:
     *    <dd>The courses in <code>courses</code> are sorted by their
     *    seasons and years, descending in season and year.
     */
    public void sortCoursesS()
    {
        SemesterComparator c = new SemesterComparator();
        courses.sort(c);
    }
    
    /**
     * Sets <code>courses</code> to the input <code>ArrayList<Course></code>
     * 
     * @param courses
     * The input <code>ArrayList<Course></code> that <code>courses</code> will
     * be set to.
     */
    public void setCourses(ArrayList<Course> courses)
    {
        this.courses = courses;
    }
    
    /**
     * Checks to see if this student is enrolled in the input course. Does not
     * check to see if the course semesters match.
     * 
     * @param c
     * The input parameter <code>Course</code> that will be searched for in
     * <code>courses</code> and returned if it is found.
     * 
     * <dt>Postcondition:
     *    <dd>If the class is in this student's <code>courses</code> 
     *    <code>ArrayList<Course></code>, then the course is returned. If it is
     *    not found in <code>courses</code>, then null is returned.
     */
    public Course inCourse(Course c)
    {
        CourseNameComparator comparator = new CourseNameComparator();
        // Checks every course in courses to see if it is equal to the input
        for (int i = 0; i < courses.size(); i++)
        {
            // if it is equal, then return the course
            if (comparator.compare(courses.get(i), c) == 0)
                return courses.get(i);
        }
        // returns null if the course was not found
        return null;
    }
    
    /**
     * Checks to see if this student is enrolled in the input course. Checks to
     * see if the course semesters match.
     * 
     * @param c
     * The input parameter <code>Course</code> that will be searched for in
     * <code>courses</code> and returned if it is found.
     * 
     * <dt>Postcondition:
     *    <dd>If the class is in this student's <code>courses</code> 
     *    <code>ArrayList<Course></code>, then the course is returned. If it is
     *    not found in <code>courses</code>, then null is returned.
     */
    public Course inCourseSemester(Course c)
    {
        CourseNameComparator comparator = new CourseNameComparator();
        SemesterComparator sComparator = new SemesterComparator();
        // checks every course to see if it is equal in department, number, and
        // semester
        for (int i = 0; i < courses.size(); i++)
        {
            if (comparator.compare(courses.get(i), c) == 0 &&
                sComparator.compare(courses.get(i), c) == 0)
                // if it is equal, return it
                    return courses.get(i);
        }
        // returns null if the course is not found
        return null;
    }
    
    /**
     * Searches for the input <code>Course</code> and if found, returns a string
     * containing this student's <code>webID</code> and the <code>semester
     * </code> that this student is taking the course in.
     * 
     * @param c
     * The input <code>Course</code> that will be searched for in <code>courses
     * </code>.
     * 
     * <dt>Postcondition:
     *    <dd>If the input <code>Course</code> is found in <code>courses</code>
     *    then a <code>String</code> is returned with this student's
     *    <code>webID</code> and the course's <code>semester</code>.
     * 
     * @return
     * Returns a <Code>String</code> containing this student's <code>webID
     * </code> and the semester of the input <code>Course</code>
     */
    public String printNameSemester(Course c)
    {
        CourseNameComparator compare = new CourseNameComparator();
        String semester = "";
        String name = webID.substring(0, 1).toUpperCase() +
            webID.substring(1, webID.length());
        for (int i = 0; i < courseCount; i++)
        {
            if (compare.compare(c, courses.get(i)) == 0)
                semester = courses.get(i).getSemester();
        }
        semester = semester.toUpperCase();
       return String.format("%-11s%s", name, semester);
    }
}
