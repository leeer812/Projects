/** 
 * The <code>Student</code> class initiates the Student object with
 * a name and money amount which are stored within the created student
 * object. You can also compare the student to another object in order
 * to see if they are the same based on whether their names and money
 * are the same. The name and money can be set with methods. A student
 * can be cloned and converted to string to display its name and money
 * to an external class.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 **/

public class Student implements Cloneable
{
    private String name; // student name
    private double money; // amount of money student has

    // Invariants:
    // name always represents the name of the student which is set when Student
    // is first initialized.

    /**
     * Returns an instance of <code>Student</code>
     * 
     * @param name
     * 	  The name that the student will take
     * 
     * @param money
     *    The amount of money the student will have
     *    
     * <dt>Postcondition:
     *    <dd><code>Student</code> has been initiated with the specified name
     *    and money.
     **/

    public Student(String name, double money) 
    {
        this.name = name;
        this.money = money;
    }


    /**
     * Returns a cloned <code>object</code> based off this student
     * 
     * @return
     *    Returns a <code>object</code> with the same properties as this
     *    student
     *    
     * <dt>Postcondition:
     *    <dd><code>clonedStudent</code> is returned as an <code>object</code>
     *    with the same <code>name</code> and <code>money</code> as this student.
     **/

    public Object clone()
    {
        Student clonedStudent = new Student(name,money);
        return clonedStudent;
    }

    /**
     * Sets the name of this student to the specified input
     * 
     * @param n
     *    The name the student will take
     *    
     * <dt>Postcondition:
     *    <dd><code>name</code> of this student is set to <code>n</code>
     */

    public void setName(String n) 
    {
        name = n;
    }

    /**
     * Sets the name of this student to the specified input
     * 
     * @param m
     *    The amount of money the student will have
     *    
     * <dt>Postcondition:
     *    <dd><code>money</code> of this student is set to <code>m</code>
     */

    public void setMoney(double m)
    {
        money = m;
    }

    /**
     * Returns the name of this student.
     * 
     * @return
     *    Returns <code>name</code> of this student
     */

    public String getName() 
    {
        return name;
    }

    /**
     * Returns the money this student has
     * 
     * @return
     *    Returns <code>money</code> of this student
     */

    public double getMoney() 
    {
        return money;
    }

    /**
     * Returns a string representation of this student
     * 
     * @return
     *    Returns a string detailing this student's name and money using
     *    the <code>getName</code> and <code>getMoney</code> method.
     *    
     * <dt>Postcondition:
     *    <dd>A string representation of this <code>student</code> is returned
     *    as this <code>student<code> object's <code>name</code> and its
     *    <code>money</code>.
     */

    public String toString()
    {
        //System.out.format("%32s%10d%16s", string1, int1, string2);
        String temp = String.format("%-20s %s%.2f", this.getName(), "$", this.getMoney());
        return temp;
        /*String temp = "";
        temp += "Name: " + this.getName() + " Money: " + this.getMoney();
        return temp;
        */
    }

    /**
     * Checks if the input object is equivalent of this student
     * 
     * @param obj
     *    The object that will be compared to this student for equivalence
     * 
     * @return
     *    Returns a <code>boolean</code> as <code>true</code> if the input
     *    object is a <code>Student</code> element and has the same
     *    <code>name</code> and <code>money</code> by using the
     *    <code>getName</code> and <code>getMoney</code> methods.
     *    
     * <dt>Postcondition:
     *    <dd>A true/false <code>boolean</code> is returned based on whether
     *    the input object, <code>obj</code> has the same <code>name</code>
     *    and <code>moeny</code> as this <code>student</code>. If not, then
     *    <code>false</code> is returned.
     */

    public boolean equals(Object obj)
    {
        if (obj instanceof Student)
        {
            Student s = (Student)obj;
            return (getName().equals(s.getName()) & getMoney()==s.getMoney());
        }
        return false;
    }
}




