/**
 * The <code>StudentLine</code> class initiates the StudentLine object with
 * an array of <code>Student</code> elements. The amount of students are
 * recorded with the <code>studentCount</code> field and is restricted by
 * the <code>CAPACITY</code> field. A <code>scanner</code> is used for
 * user input. 
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 **/

import java.util.Scanner; // imports the Scanner class

public class StudentLine implements Cloneable
{
    
    
    private Student[] students; // Array of students within the StudentLine
    
    
    private int studentCount; // The amount of students within the StudentLine
    
    
    final int CAPACITY = 20; // The maximum amount of students allowed in line
    
   
    Scanner inputScanner = new Scanner(System.in); // Initializing the scanner
    
    // Invariants:
    // CAPACITY will always be 20 as this is the specified maximum amount of
    // students for this assignment

    /**
     * Returns an instance of StudentLine.
     *
     * @param s
     *    The input array of <code>student</code> objects used for
     *    initializing the <code>StudentLine</code>.
     *    
     * <dt>Postcondition:
     *    <dd>StudentLine is initialized with the specified
     *    students array and has had <code>studentCount</code> set to 0.
     */
    
    public StudentLine(Student[] s)
    {
        students = s;
        studentCount = 0;
    }
    
    /**
     * Returns the amount of students in the StudentLine.
     *
     * @return    
     *    Returns <code>studentCount</code> of this StudentLine
     */

    public int numStudents() 
    {
        return studentCount;
    }

    /**
     * Sets the studentCount of this StudentLine to the specified input
     * 
     * <dt>Preconditions:
     *    <dd>studentCount is more than or equal to 0 and less than or equal to 20.
     *
     * @param studentCount    
     *    The input <code>int</code> that <code>this.studentCount</code> will be set to
     *    
     * <dt>Postcondition:
     *    <dd><code>this.studentCount</code> is set to the input <code>studentCount</code>
     */
    
    public void setStudentCount(int studentCount)
    {
        this.studentCount = studentCount;
    }
    
    /**
     * Returns the student in the array at the specified index if the index
     * is within bounds. If the index is out of bounds, the program throws
     * <code>ArrayIndexOutOfBoundsException</code>.
     * 
     * <dt>Precondition:
     *    <dd><code>index</code> is within bounds (0 to 19) and
     *    <code>index</code> in <code>students</code> must not be null
     * 
     * @param index
     *    The <code>index</code> that will be accessed in <code>students</code>
     *    
     * @return
     *    Returns the <code>student</code> at <code>index</code> of the array
     *    if the index is below the capacity of the array and above or equal
     *    to 0.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates that the input <code>index</code> is out of bounds.
     *    
     * <dt>Postcondition:
     *    <dd><code>Student</code> at <code>index</code> of
     *    <code>students</code> is returned.
     **/

    public Student getStudent(int index) throws ArrayIndexOutOfBoundsException
    {
        if (index < CAPACITY && index >= 0)
            return students[index];
        else
            throw new ArrayIndexOutOfBoundsException("This index is out of bounds (0-19)");
    }

    /**
     * Returns and removes the <code>student</code> at the specified
     * <code>index</code> and decrements <code>studentCount</code>.
     * 
     * <dt>Precondition:
     *    <dd><code>index</code> is more than or equal to 0 and less than or
     *    equal to 19. A student must be at the specified index.
     * 
     * @param index
     *     The <code>index</code> that will be accessed in <code>students</code>
     *     
     * @return
     *    Returns the removed <code>student</code> if <code>studentCount</code>
     *    is more than 0 and the <code>index</code> is within the bounds
     *    (0 to 19) and the object at <code>index</code> in
     *    <code>students</code> is a <code>student</code> element.
     *    
     * <dt>Postcondition:
     *    <dd><code>studentCount</code> is decremented by one, and the
     *    <code>student</code> at the specified <code>index</code> is
     *    removed from <code>students</code> and is returned. If the specified
     *    index was out of bounds or the line was empty or there was no student
     *    at the specified index, the respective exception is thrown.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates that the input <code>index</code> is out of bounds.
     * 
     * @throws EmptyLineException
     *    Indicates that the line is currently empty and that a student
     *    cannot be removed because there are no students in the line.
     * 
     * @throws NullPointerException
     *    Indicates that there is no student at the specified index.
     **/
    
    public Student removeStudent(int index) throws
    ArrayIndexOutOfBoundsException, EmptyLineException, NullPointerException
    {
       if (!(studentCount>0))
            throw new EmptyLineException("The line is empty");
        
        if (index>=numStudents())
            throw new NullPointerException();
        
        if (index<CAPACITY && index>=0) 
        {
            Student temp = (Student)students[index].clone();
            for(int i=index+1; i<students.length; i++)
            {
                students[i-1] = students[i];
            }
            students[students.length-1]=null;
            studentCount--;
            return temp;
        }
        else
        throw new ArrayIndexOutOfBoundsException("Please input "
          + "a correct index"); 
    }

    /**
     * Adds and returns the input student at the specified index and throws
     * <code>DeanException</code> if <code>studentCount</code> is 20, otherwise
     * <code>studentCount</code> is incremented by 1. 
     * 
     * <dt>Precondition:
     *    <dd><code>index</code> is within bounds (0 to 19)
     * 
     * @param index
     *    The <code>index</code> that the <code>student</code> will be added
     *    to in <code>students</code>
     * @param student
     *    <code>student</code> that will be added into <code>Students</code>
     * @return
     *    Returns the added student at the specified index if the index is
     *    within bounds (0 to 19) 
     *    
     * <dt>Postcondition:
     *    <dd>Input <code>student</code> is returned and added into
     *    <code>students</code> at the input <code>index</code>. If
     *    <code>studentCount</code> is equal to 20, <code>DeanException</code>
     *    is thrown. <code>studentCount</code> is incremented if it is
     *    less than or equal to 19. If the input index was out of bounds,
     *    <code>InvalidArgumentException</code> is thrown.
     *    
     * @throws DeanException
     *    Indicates that the line is full and that the last student on the line
     *    has been removed in order to make space for the new student
     *    
     * @throws InvalidArgumentException
     *    Indicates that the input index is out of bounds
     */
    
    public Student addStudent(int index, Student student) throws DeanException, InvalidArgumentException
    {
        if (index>studentCount || index<0)
            throw new InvalidArgumentException("This index is out of bounds (0-19)");

        if (studentCount==CAPACITY)
        {
            Student temp = students[CAPACITY-1];
            if (index<students.length && index>=0)
                for (int i = students.length-1; i>index; i--)
                {
                    students[i] = students[i-1];
                }
            students[index] = student;
            throw new DeanException(temp);
        }
        else
        {
            studentCount++;
            if (index<students.length && index>=0)
                for (int i = students.length-1; i>index; i--)
                {
                    students[i] = students[i-1];
                }
            students[index] = student;
        }

        return student;
    }
    
    /**
     * Updates a student's <code>money</code> based on user input
     * 
     * <dt>Precondition:
     *    <dd>There must be a <code>student</code> in the <code>students</code>
     *    array at the specified <code>index</code> and the input
     *    <code>money</code> must be greater or equal to <code>0.01</code>.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>money</code> of <code>Student</code> at the specified
     *    <code>index</code> is set to the specified <code>money</code> input.
     *    If there are no students on the line, <code>EmptyLineException</code>
     *    is thrown. If the input <code>index</code> is out of bounds,
     *    <code>ArrayIndexOutOfBounds</code> is thrown. If the input
     *    <code>money</code> is not above or equal to 0.01, the
     *    <code>InsufficientFundsException</code> is thrown
     * 
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates that the input <code>index</code> is out of bounds
     *    
     * @throws EmptyLineException
     *    Indicates that there are no students on the line to update
     *    
     * @throws InsufficientFundsException
     *    Indicates the input <code>money</code> is not above or equal to 0.01
     */

    public void updateStudent() throws ArrayIndexOutOfBoundsException, EmptyLineException, InsufficientFundsException
    {
        if (studentCount==0)
            throw new EmptyLineException("There are no students on the line");

        System.out.println("What is the index of the student whose money you wish to update?");
        int index = inputScanner.nextInt();

        if (index<0 || index>CAPACITY)
            throw new ArrayIndexOutOfBoundsException("This index is out of bounds (0-19)");
        if (index>studentCount)
            throw new ArrayIndexOutOfBoundsException("There is no student there");

        System.out.println("Please enter the new money amount: ");
        double money = inputScanner.nextDouble();
        if (money<=0)
        {
            throw new InsufficientFundsException("A student needs at least $0.01 to buy lunch, the line has not been updated");
        }
        this.getStudent(index).setMoney(money);
        System.out.println(this.getStudent(index).getName() + " now has $" + money);
    }

    /**
     * Swaps the position of two <code>student</code> objects in
     * <code>students</code> based on the specified indices.
     * 
     * <dt>Precondition:
     *    <dd><code>index1</code> and <code>index2</code> must be within the
     *    bounds (0 to 19) and the <code>student</code> objects in the
     *    <code>students</code> array must be present at the specified indices.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>student</code> at <code>index1</code> and the
     *    <code>student</code> at <code>index2</code> in <code>students</code>
     *    array have swapped positions. If either indices are out of bounds,
     *    <code>ArrayIndexOutOfBoundsException</code> is thrown. If there is
     *    no <code>student</code> at either indices, then the
     *    <code>NullPointerException</code> is thrown. If the line is empty,
     *    <code>EmptyLineException</code> is thrown.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates the input <code>index</code> is out of bounds (0 to 19)
     *    
     * @throws NullPointerException 
     *    Indicates there is no <code>student</code> object at the specified
     *    <code>index</code> in the <code>students</code> array.
     *    
     * @throws EmptyLineException
     *    Indicates there are no <code>student</code> objects in the
     *    <code>students</code> array.
     */
    public void swapStudent() throws ArrayIndexOutOfBoundsException, NullPointerException, EmptyLineException
    {
        Student temp = new Student("", 0);

        if (studentCount<=1)
            throw new EmptyLineException("There are not enough students in the line to swap places with");

        System.out.println("What is the index of the first student you want to swap?");
        int index1 = inputScanner.nextInt();

        if (index1<CAPACITY && index1>=0)
        {
            if(students[index1] instanceof Student)
            {
                temp = students[index1];
            }
            else
                throw new NullPointerException();
        }
        else
            throw new ArrayIndexOutOfBoundsException("This index is out of bounds (0-19)");

        System.out.println("What is the index of the second student you want to swap?");
        int index2 = inputScanner.nextInt();

        if (index1<CAPACITY && index1>=0)
        {
            if(students[index2] instanceof Student)
            {
                students[index1]=students[index2];
                students[index2]=temp;
                System.out.println(this.getStudent(index1).getName() + " has swapped places with " + this.getStudent(index2).getName());
            }
            else
                throw new NullPointerException();
        }
        else
            throw new ArrayIndexOutOfBoundsException("This index is out of bounds (0-19)");
    }

    /**
     * Returns a clone of this <code>StudentLine</code>
     * 
     * @return
     *    Returns a <code>StudentLine</code> object, <code>temp</code>, with
     *    the same <code>student</code> objects in the <code>students</code>
     *    array.
     * 
     * <dt>Postcondition:
     *    <dd><code>temp</code> is returned as a <code>StudentLine</code>
     *    object with the same <code>student</code> objects in its
     *    <code>students</code> array as this <code>StudentLine</code>.
     *    <code>temp</code> has had its <code>studentCount</code> set to the
     *    <code>studentCount</code> of this <code>StudentLine</code>.
     **/
    public StudentLine clone()
    {
        Student[] tempArray = students.clone();
        StudentLine temp = new StudentLine(tempArray);
        temp.setStudentCount(this.numStudents());
        return temp;
    }

    /** 
     * Returns a true/false boolean depicting if the input <code>obj</code> is
     * the same as this <code>StudentLine</code>
     * 
     * <dt>Precondition:
     *    <dd>The input <code>obj</code> is of type <code>StudentLine</code>
     *    
     * @param obj
     *    The input object that is compared to this <code>StudentLine</code>
     *    
     * @return
     *    Returns a true/false boolean based on whether <code>obj</code> is of
     *    the <code>StudentLine</code> type and if all the <code>student</code>
     *    objects in the <code>students</code> array are equal to each other.
     *    
     * <dt>Postcondition:
     *    <dd><code>true</code> is returned if the input is of the type
     *    <code>StudentLine</code> and if all its <code>student</code> objects
     *    in its <code>students</code> array are equal to the array in this
     *    <code>StudentLine</code>. If they are not equal or if the input is
     *    not a <code>StudentLine</code> then <code>false</code> is returned.
     **/
    public boolean equals(Object obj)
    {
        if (obj instanceof StudentLine) 
        {
            StudentLine temp = (StudentLine) obj;
            for (int i=0; i<CAPACITY; i++)
            {
                if(!(this.getStudent(i) instanceof Student) && !(temp.getStudent(i) instanceof Student))
                    return true;
                if(this.getStudent(i) instanceof Student && temp.getStudent(i) instanceof Student)
                {
                    if (!(this.getStudent(i).equals(temp.getStudent(i))))
                        return false;
                }
                if(!(this.getStudent(i) instanceof Student) && temp.getStudent(i) instanceof Student)
                {
                    return false;
                }
                if(!(temp.getStudent(i) instanceof Student) && this.getStudent(i) instanceof Student)
                {
                    return false;
                }
            }
            return true;
        }
        else
            return false;

    }

    /** 
     * Returns a string representation of this <code>StudentLine</code>
     * 
     * @return
     *    Returns each <code>student</code> in the <code>students</code> array
     *    in its string form using its <code>equals</code> method.
     *    
     * <dt>Postcondition:
     *    <dd>A string is returned with each <code>student</code> represented
     *    in its string form. If a <code>null</code> object is found in the
     *    <code>students</code> array, the for loop continues for the remainder
     *    of the <code>student</code> objects in the array.
     */
    public String toString()
    {
        String forPrint="";
        for (int i = 0; i < students.length; i++)
        {
            if (students[i] != null)
                forPrint += students[i].toString() + "\n";
            else
                continue;
        }
        return forPrint;
    }

}

/*
 *  Ernest Lee
 * 111075566
 * HOMEWORK #1
 * CSE 214 R02
 * David S. Li
 * GRAD TA
 * 
 */
