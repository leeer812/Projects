import java.util.Scanner; //Imports the scanner

/**
 * The <code>LunchLineSimulator</code> class lets the user manipulate the
 * <code>StudentLine</code> by performing various operations to the line
 * such as by adding students, and removing students to manipulate each
 * <code>StudentLine</code> object's <code>students</code> array.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */

public class LunchLineSimulator
{
    
    // initialization of reality A 
    private static StudentLine realityA;
    
    // initialization of reality B 
    private static StudentLine realityB;
    
    // initialization of ON boolean for indicating whether the program
    // should run or quit.
     
    private static boolean ON = true;
    
    // The input scanner.
    static Scanner inputScanner = new Scanner(System.in);

    /**
     * The method that is called when user inputs <code>a</code> into the menu;
     * Adds a <code>student</code> into the current reality's
     * <code>StudentLine</code>.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>index</code> must be within bounds (0 to 19) and
     *    not be higher than the current <code>studentCount</code> in order to
     *    prevent creating holes in the <code>students</code> array. The input
     *    <code>money</code> must also be at least 0.01
     * 
     * @param sL 
     *    The <code>StudentLine</code> that the <code>student</code> object
     *    will be added into.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>student</code> with input <code>name</code> and
     *    <code>money</code> is added to the current reality's
     *    <code>StudentLine</code> and <code>studentCount</code> is
     *    incremented by one. The user is alerted that the <code>student</code>
     *    has been added at the input position. If <code>StudentCount</code> is
     *    already at <code>CAPACITY</code>, then <code>DeanException</code>
     *    is thrown and <code>studentCount</code> is not incremented.
     *    If the input <code>index</code> is out of bounds, then
     *    <code>InvalidArgumentException</code> is thrown. If the input
     *    <code>money</code> is not at least 0.01, then the
     *    <code>InsufficientFundsException</code> is thrown.
     *    
     * @throws InvalidArgumentException
     *    Indicates that the input <code>index</code> is not within bounds
     *    (0 to 19)
     *    
     * @throws DeanException 
     *    Indicates that the line is full and that the last
     *    <code>student</code> object on the line has been removed to make
     *    space for the newly added <code>student</code>.
     *    
     * @throws InsufficientFundsException
     *    Indicates that the input <code>money</code> amount was not above
     *    or equal to the minimum amount of 0.01
     */
    public static void a(StudentLine sL) throws InvalidArgumentException, DeanException, InsufficientFundsException 
    {
        Scanner inputScanner2 = new Scanner(System.in);
        try
        {
            System.out.println("What is the name of the student you want to add?");
            String name = inputScanner2.nextLine();
            System.out.println("How much money does " + name + " have?");
            double money = (double)inputScanner.nextDouble();
            if (money<=0)
            {
                throw new InsufficientFundsException("A student needs atleast $0.01 to buy lunch! The line has not been updated.");
            }
            Student s = new Student(name, money);
            for (int i=0; i<20; i++)
            {
                if (sL.getStudent(i) == null)
                {
                    sL.addStudent(i, s);
                    System.out.println(name + " has been added to position " +(i+1) + " with $" + money);
                    break;
                }
                else if (i==19)
                {
                    sL.addStudent(19, s);
                    System.out.println(name + " has been added to position " +(i+1) + " with $" + money);
                }
            }
        }
        catch(InvalidArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        catch(DeanException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(e.getMessage());
        }

    }

    /**
     * The method that is called when <code>b</code> is input into the menu
     * 'Bullies' a student out of the line at the specified <code>index</code>
     * by removing the <code>student</code> at the input <code>index</code>.
     * 
     * <dt>Precondition:
     *    <dd><code>index</code> must be within bounds (0 to 19) and there must
     *    be a <code>student</code> object at the specified <code>index</code>
     *    in the <code>students</code> array.
     *
     * @param sL
     *    The <code>StudentLine</code> that the <code>student</code> object
     *    will be removed from.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>student</code> at the specified <code>index</code> is
     *    removed from the <code>StudentLine</code> object's
     *    <code>students</code> array. The <code>studentCount</code> of the
     *    <code>StudentLine</code> object is decremented if the student is
     *    removed successfully. The user is alerted that the student at
     *    the specified position was removed from the line. If the input
     *    <code>index</code> was out of bounds, then the
     *    <code>ArrayIndexOutOfBoundsException</code> is thrown. If
     *    <code>studentCount</code> is 0, <code>EmptyLineException</code> is
     *    thrown. If there is no <code>student</code> object at the specified
     *    <code>index</code>, then <code>NullPointerException</code> is thrown.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates that the input <code>index</code> was out of bounds
     * 
     * @throws EmptyLineException 
     *    Indicates that the <code>StudentLine</code> object's
     *    is empty and there are no <code>student</code> objects to remove from
     *    the <code>students</code> array.
     * 
     * @throws NullPointerException the null pointer exception
     *    Indicates there is no <code>student</code> at the specified position
     *    in the <code>StudentLine</code> object's <code>students</code> array.
     */
    public static void b(StudentLine sL) throws ArrayIndexOutOfBoundsException, EmptyLineException, NullPointerException
    {
        System.out.println("What is the index of the kid who you want to bully?");
        String name = "";
        int index = inputScanner.nextInt();
        try
        {
            name = sL.removeStudent(index).getName();
            System.out.println(name + " has been bullied for all his lunch money and has left the lunch line.");
        }
        catch (EmptyLineException e)
        {
            System.out.println(e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NullPointerException e)
        {
            System.out.println("There is no student there");
        }
    }

    /**
     * The method that is called when <code>c</code> is input into the menu.
     * Adds a new <code>student</code> who 'cuts' another student at the
     * specified position in the line.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>money</code> for the new <code>student</code> is
     *    at least 0.01. The input <code>index</code> must be within bounds
     *    (0 to 19) and there must be a <code>student</code> object in the
     *    <code>StudentLine</code> object's <code>students</code> array at that
     *    position.
     *
     * @param sL
     *    The <code>StudentLine</code> object that the <code>student<code> is
     *    being added into.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>student</code> with input <code>name</code> and
     *    <code>money</code> is added to the current reality's
     *    <code>StudentLine</code> in the <code>students</code> array in the
     *    position of the specified <code>index</code> and the original
     *    <code>student</code> in that position and every <code>student</code>
     *    behind that student has each of their positions shifted down one on
     *    the line. <code>studentCount</code> is incremented by one. The user
     *    is alerted that the <code>student</code> has been added at the input
     *    position. If <code>StudentCount</code> is already at
     *    <code>CAPACITY</code>, then <code>DeanException</code> is thrown and
     *    <code>studentCount</code> is not incremented. If the input
     *    <code>index</code> is out of bounds, then
     *    <code>ArrayIndexOutOfBoundsException</code> is thrown. If the input
     *    <code>money</code> is not at least 0.01, then the
     *    <code>InsufficientFundsException</code> is thrown.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates that the input <code>index</code> was out of bounds
     *    
     * @throws InvalidArgumentException
     *    Indicates that the input <code>index</code> was out of bounds
     *    
     * @throws DeanException
     *    Indicates that the line is full and that the last
     *    <code>student</code> object on the line has been removed to make
     *    space for the newly added <code>student</code>.
     *    
     * @throws InsufficientFundsException 
     *    Indicates that the input <code>money</code> amount was not above
     *    or equal to the minimum amount of 0.01

     */
    public static void c(StudentLine sL) throws ArrayIndexOutOfBoundsException, InvalidArgumentException, DeanException, InsufficientFundsException
    {
        Scanner inputScanner2 = new Scanner(System.in);
        String name="";
        double money=0;

        try
        {
            System.out.println("What is the name of the student who is cutting?");
            name = inputScanner2.nextLine();
            System.out.println("How much money does " + name + " have?");
            money = inputScanner.nextDouble();
            if (money<=0)
            {
                throw new InsufficientFundsException("A student needs atleast $0.01 to buy lunch! The line has not been updated.");
            }
            System.out.println("What index do you want " + name + " to cut?");
            Student cutter = new Student(name, money);
            int index = inputScanner.nextInt();
            Student cuttie = new Student(sL.getStudent(index).getName(), sL.getStudent(index).getMoney());
            sL.addStudent(index, cutter);
            System.out.println(name + " has cut " + cuttie.getName() + " at position " +(index+1) + " and has $" + money);
        }
        catch (NullPointerException e)
        {
            System.out.println("There is no student at this index");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
        }
        catch (DeanException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The method that is called when user inputs <code>d</code> into the menu
     * Duplicates the current reality's <code>StudentLine</code> into the other
     * reality's <code>StudentLine</code>. All of the contents will be cloned.
     *
     * @param sL 
     *    The <code>StudentLine</code> that will be duplicated.
     * 
     * @return
     *    Returns a clone of this <code>StudentLine</code> with the same
     *    contents and number of students.
     *    
     * <dt>Postcondition:
     *    <dd><sL.clone</code> is returned with the same properties as the
     *    input <code>StudentLine</code>.
     */
    public static StudentLine d(StudentLine sL)
    {
        return sL.clone();
    }

    /**
     * The method that is called when user inputs <code>e</code> into the menu
     * Checks to see if the two realities are equal to each other.
     * 
     * @param sL1
     *    The first reality's <code>StudentLine</code>
     * @param sL2 
     *    The second reality's <code>StudentLine</code>
     *    
     * <dt>Postcondition:
     *    <dd>The user is alerted if the realities are equal if the two
     *    <code>StudentLine</code> objects have the same <code>student</code>
     *    objects in the same order.
     */
    public static void e(StudentLine sL1, StudentLine sL2)
    {
        if (sL1.equals(sL2))
            System.out.println("The two realities are equal!");
        else
            System.out.println("The two realities are not equal.");
    }

    /**
     * Convenience testing method:
     * Fills the <code>StudentLine</code> with students.
     *
     * @param sL
     *    The <code>StudentLine</code> whose <code>students</code> array will
     *    be filled with <code>student</code> objects
     *    
     * @throws InvalidArgumentException
     *    Indicates that the index is out of bounds (0 to 19)
     *    
     * @throws DeanException the dean exception
     *    Indicates that the line is full and that the last
     *    <code>student</code> object on the line has been removed to make
     *    space for the newly added <code>student</code>.
     * 
     * <dt>Postcondition:
     *    <dd>The <code>StudentLine</code> is entirely filled with
     *    <code>student</code> objects and <code>DeanException</code> is thrown
     *    if there were any <code>student</code> objects in the array prior to
     *    the method being called.
     */
    public static void fillLine(StudentLine sL) throws InvalidArgumentException, DeanException
    {
        for (int i = 0; i<20; i++)
        {
            Student s = new Student(""+i, i);
            sL.addStudent(i, s);
        }
    }

    /**
     * The method that is called when user inputs <code>q</code> into the menu.
     * Ends the program
     * 
     * <dt>Postcondition:
     *    <dd>The program ends, displaying an exit message to the user and no
     *    longer accepts user input.
     */
    public static void q()
    {
        System.out.println("Thank you for playing!");
        System.exit(0);
    }

    /**
     * The method that is called when user inputs <code>s</code> into the menu.
     * Serves the first <code>student</code> in the line.
     * 
     * <dt>Precondition:
     *    <dd>There must be a <code>student</code> object in the
     *    <code>students</code> array.
     *
     * @param sL
     *    The <code>StudentLine</code> that the <code>student</code> object
     *    will be removed from.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>student</code> at <code>index</code> 0 is
     *    removed from the <code>StudentLine</code> object's
     *    <code>students</code> array. The <code>studentCount</code> of the
     *    <code>StudentLine</code> object is decremented if the student is
     *    removed successfully. The user is alerted that the student at
     *    the specified position was removed from the line. If
     *    <code>studentCount</code> is 0, <code>EmptyLineException</code> is
     *    thrown.
     * 
     * @throws EmptyLineException 
     *    Indicates that the <code>StudentLine</code> object's
     *    is empty and there are no <code>student</code> objects to remove from
     *    the <code>students</code> array.
     */
    public static void s(StudentLine sL) throws EmptyLineException
    {
        try
        {
            String name = sL.removeStudent(0).getName();
            System.out.println(name + " has been served and has left the lunch line.");
        }
        catch (EmptyLineException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The method that is called when user inputs <code>t</code> into the menu.
     * Swaps the positions of two <code>student</code> objects in the
     * <code>StudentLine</code> object's <code>students</code> array.
     * 
     * <dt>Precondition:
     *    <dd>There must be <code>student</code> objects at the specified
     *    <code>index</code> in the <code>students</code> array of the
     *    <code>StudentLine</code> object. The <code>index</code> must be
     *    within bounds (0 to 19).
     *
     * @param sL
     *    The <code>StudentLine</code> that will have its <code>student</code>
     *    objects' positions swapped.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates the input <code>index</code> was out of bounds.
     *    
     * @throws NullPointerException
     *    Indicates that there is no <code>student</code> at the input
     *    <code>index</code>.
     *    
     * @throws EmptyLineException
     *    Indicates that there are no <code>student</code> objects to remove
     *    from the line.
     */
    public static void t(StudentLine sL) throws ArrayIndexOutOfBoundsException, NullPointerException, EmptyLineException
    {
        try 
        {
            sL.swapStudent();
        }
        catch (NullPointerException e)
        {
            System.out.println("There is no student at this index");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
        }
        catch (EmptyLineException e)
        {
            System.out.println(e.getMessage());
        }

    }

    /**
     * The method that is called when user inputs <code>u</code> into the menu.
     * Updates a <code>student</code> object's <code>money</code>.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>index</code> must be within bounds (0 to 19).
     *    There must be a <code>student</code> in the <code>StudentLine</code>
     *    object's <code>students</code> array at the specified position. The
     *    <code>money</code> input must be at least 0.01.
     *
     * @param sL
     *    The <code>StudentLine</code> object that will have its
     *    <code>student</code> in the <code>students</code> array updated.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>student</code> object's <code>money</code>, at the
     *    input <code>index</code> in the <code>students</code> array in the
     *    <code>StudentLine</code> object, is changed to the input
     *    <code>money</code>. If the input <code>index</code> was out of
     *    bounds then <code>ArrayIndexOutOfBoundsException</code> is thrown.
     *    If the line is empty, then <code>EmptyLineException</code> is thrown.
     *    If the input <code>money</code> was not at least 0.01, then the
     *    <code>InsufficientFundsException</code> is thrown.
     *    
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates the input <code>index</code> was out of bounds.
     *    
     * @throws EmptyLineException 
     *    Indicates that there are no <code>student</code> objects in the line.
     *    
     * @throws InsufficientFundsException
     *    Indicates the <code>money</code> input was not the minimum amount: 0.01.
     */
    public static void u(StudentLine sL) throws ArrayIndexOutOfBoundsException, EmptyLineException, InsufficientFundsException
    {
        try 
        {
            sL.updateStudent();
        }
        catch (EmptyLineException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(e.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The main method of the <code>LunchLineSimulator</code> class which shows
     * the user a menu that allows them to manipulate the lunch line. Two
     * <code>students</code> arrays are initialized and used to initiate two
     * <code>StudentLine</code> objects depicted as <code>realityA</code> and
     * <code>realityB</code>. 
     * <p>
     * The user is presented with a menu that performs operations based on the
     * user input into the menu: (a) add or (b) remove a student, (c) cut an
     * existing student on the line, (d) duplicate the current reality's
     * <code>StudentLine</code>, (e) check if the two realities'
     * <code>StudentLine</code> objects are equal, (o) switch to the
     * other reality's <code>StudentLine</code>, (p) print all of the current
     * students in this reality's line, (q) end the program, (s) serve the
     * first student on the line, (t) swap the positions of two students on
     * the line, (u) and to update an existing student's <code>money</code>.
     *
     * @throws ArrayIndexOutOfBoundsException
     *    Indicates the input <code>index</code> was out of bounds.
     *    
     * @throws EmptyLineException the empty line exception
     *    Indicates there are no students on the line to manipulate
     *    
     * @throws InvalidArgumentException the invalid argument exception
     *    Indicates the input <code>index</code> was out of bounds.
     *    
     * @throws DeanException the dean exception
     *    Indicates the line was full when adding a <code>student</code> object
     *    was attempted and alerts the user that the last student on the line
     *    was removed to make space for the newly added student.
     *    
     * @throws InsufficientFundsException 
     *    Indicates that the <code>money</code> input was not at least 0.01.
     */
    public static void main(String[] args) throws ArrayIndexOutOfBoundsException, EmptyLineException, InvalidArgumentException, DeanException, InsufficientFundsException
    {   
        Student[] studentsA = new Student[20];
        Student[] studentsB = new Student[20];
        realityA = new StudentLine(studentsA);
        realityB = new StudentLine(studentsB);
        int currentReality = 1; // 1 refers to realityA, 2 refers to realityB

        while(ON = true) //Repeatedly asks the user for input until the program ends
        {
            System.out.println("What do you want to do? Type HELP for instructions");
            String action = inputScanner.next();

            switch(action.toLowerCase()) //Interprets every input as a lower case character
            {
            case "help":
                System.out.println("A: Add a student");
                System.out.println("B: Bully a student");
                System.out.println("C: Cut a student");
                System.out.println("D: Duplicate the current reality's lunch line into the other reality's lunch line");
                System.out.println("E: Check to see if the two reality's lunch lines are equal");
                System.out.println("O: Switch to the other reality's lunch line");
                System.out.println("P: Print the current reality's lunch line");
                System.out.println("Q: Quit");
                System.out.println("S: Serves the first student in lunch line");
                System.out.println("T: Swaps two students");
                System.out.println("U: Updates a student's amount of money");
                System.out.println();
                break;
            case "a":
                if(currentReality==1)
                {
                    a(realityA);
                    break;
                }
                else if(currentReality==2)
                {
                    a(realityB);
                    break;
                }
            case "b":
                if(currentReality==1)
                    b(realityA);
                if(currentReality==2)
                    b(realityB);
                break;
            case "c":
                if(currentReality==1)
                    c(realityA);
                if(currentReality==2)
                    c(realityB);
                break;
            case "d":
                if(currentReality==1)
                {
                    StudentLine temp = d(realityA);
                    realityB = temp;
                    System.out.println("Reality A has been copied into Reality B");
                    break;
                }
                else if (currentReality==2)
                {
                    StudentLine temp = d(realityB);
                    realityA = temp;
                    System.out.println("Reality B has been copied into Reality A");
                    break;
                }
            case "e":
                e(realityA, realityB);
                break;
            case "o":
                switch (currentReality) 
                {
                case 1:
                    currentReality=2;
                    System.out.println("We have switched to reality B");
                    break;
                case 2:
                    currentReality=1;
                    System.out.println("We have switched to reality A");
                    break;
                }
                break;
            case "p":
                if (currentReality==1)
                {
                    System.out.println("We are currently on reality A");
                    System.out.format("%-20s%6s%n", "Name", "Money");
                    System.out.println(realityA.toString());
                }
                if (currentReality==2)
                {
                    System.out.println("We are currently on reality B");
                    System.out.format("%-20s%6s%n", "Name", "Money");
                    System.out.println(realityB.toString());
                }
                break;
            case "q":
                q();
                break;
            case "t":
                if (currentReality==1)
                    t(realityA);
                else
                    t(realityB);
                break;
            case "u":
                if(currentReality==1)
                    u(realityA);
                if(currentReality==2)
                    u(realityB);
                break;
            case "s":
                if(currentReality==1)
                    s(realityA);
                if(currentReality==2)
                    s(realityB);
                break;
            // Test method: prints out the number of students
            // in the current line
            case "n":
                if (currentReality==1)
                    System.out.println(realityA.numStudents());
                if (currentReality==2)
                    System.out.println(realityB.numStudents());
                break;
            // Test method: fills the current reality's line
            // entirely with students
            case "f":
                try {
                    if (currentReality==1)
                        fillLine(realityA);
                    if (currentReality==2)
                        fillLine(realityB);
                    break;
                }
                catch (DeanException e)
                {
                    System.out.println(e.getMessage());
                }


            }

        }
    }
}
