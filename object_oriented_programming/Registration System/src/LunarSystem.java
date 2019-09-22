import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The <code>LunarSystem</code> is the main class of the <code>Lunar System
 * </code> is equipped with a <code>HashMap</code> for storing existing data.
 * The system allows users to register and de-register students into the
 * system. Students registered into the system can be registered into classes
 * and these classes can be viewed, sorted in various ways. Once the user is
 * done, the user can decide whether or not to save the data in the hash table
 * so that it can be loaded again for next use. 
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #6
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class LunarSystem
{
    private static HashMap<String, Student> database;
    
    /**
     * The main method of the <code>LunarSystem</code> class. Welcomes the
     * user and loads the existing database if there is one. The main menu is
     * then presented to the user asking for input.
     */
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Lunar System!");
        loadFile();
        mainMenu();
    }

    /**
     * The main menu of the <code>Lunar System</code>. Presents the user with
     * a menu with the options to login as REGISTRAR or with an already
     * registered student's webID, to save the database and quit, or to quit
     * without saving the database. The menu is looped until a valid choice is
     * selected.
     */
    private static void mainMenu()
    {
        Scanner scanner = new Scanner(System.in);
        boolean on = true;
        while (on)
        {
        System.out.format("%s%n%12s%n%26s%n%32s%n%n%s%n", "Menu:", "L) Login",
            "X) Save state and quit", "Q) Quit without saving state",
            "Please select an option:");
        
        String choice = scanner.nextLine().toLowerCase();
        
        switch (choice)
        {
            case "l":
                on = false;
                login();
                break;
            case "x":
                on = false;
                saveAndQuit();
                break;
            case "q":
                on = false;
                quit();
                break;
        }
        }
    }

    /**
     * Ends the program without saving the database.
     */
    private static void quit()
    {
        System.out.println("Good bye! Thank you for using Lunar.");
        System.exit(0);
    }

    /**
     * Saves the database for next use and ends the program.
     */
    private static void saveAndQuit()
    {
        saveFile();
        System.out.println("System state saved, system shut down for main"
            + "tenance.");
        quit();
    }

    /**
     * Prompts the user for a webID and attempts to login to the system as the
     * input webID. This is looped until the user logs in successfully.
     * 
     * <dt>Postcondition:
     *    <dd>If the user entered 'registrar' as the webID, then the
     *    registrar menu is presented to the user. If the user entered an
     *    existing webID, then the student menu is presented to the user.
     *    If neither was entered, then the user is prompted for another webID.
     */
    private static void login()
    {
        Scanner scanner = new Scanner(System.in);
       
        boolean on = true;
        
        while (on)
        {
            System.out.println("Please enter a webID ('REGISTRAR' to register "
                + "for classes):");
            String webID = scanner.nextLine().toLowerCase();
            if (webID.equals("registrar"))
            {
                registrarMenu();
                on = false;
            }
            // if the webID has already been registered into the system.
            else if (database.containsKey(webID))
            {
                studentMenu(webID);
                on = false;
            }
            else
                System.out.println("This student is not registered.");
        }
    }

    /**
     * The student menu of the <code>Lunar System</code>. The menu holds the
     * webID of the student that logged in and prompts the student to make a
     * choice of action. The student can add or drop a class, view existing
     * courses sorted by course name/department, or sorted by semester. The
     * student can log out once they are done. The user is prompted for an
     * action until proper input is received.
     * 
     * @param webID
     * The <code>webID</code> of the student that logged in.
     */
    private static void studentMenu(String webID)
    {
        Scanner scanner = new Scanner(System.in);
        // Converts the first character of the student's webID to upper-case
        // for printing purposes
        String name = webID.substring(0, 1).toUpperCase() +
            webID.substring(1, webID.length());
        boolean on = true;
        while (on)
        {
        System.out.format("%s%n%n%s%n%18s%n%19s%n%57s%n%43s%n%13s%n",
          "Welcome " + name + ".", "Options:",
          "A) Add a class",
          "D) Drop a class",
          "C) View your classes sorted by course name/department",
          "S) View your courses sorted by semester",
          "L) Logout");
        String choice = scanner.nextLine().toLowerCase();
        
        switch (choice)
        {
            case "a":
                on = false;
                addClass(webID);
                break;
            case "d":
                on = false;
                dropClass(webID);
                break;
            case "c":
                on = false;
                // view courses sorted by course name/department
                viewCoursesCND(webID);
                break;
            case "s":
                on = false;
                // view courses sorted by semester
                viewCoursesS(webID);
                break;
            case "l":
                on = false;
                mainMenu();
                break;
        }
        }
        
    }

    /**
     * Sorts the current logged in student's courses by semester and prints all
     * of the student's courses in sorted form. The user is then brought back
     * to the student menu.
     * 
     * @param webID
     * The <code>webID</code> of the student that is currently logged in.
     */
    private static void viewCoursesS(String webID)
    {
        // The student that is currently logged in
        Student s = database.get(webID);
        // sorts the student's courses by semester
        s.sortCoursesS();
        System.out.format("%s%n%s%n", "Dept. Course Semester",
            "-------------------------------");
        // Prints all the courses
        for (int i = 0; i < s.getCourseCount(); i++)
        {
            System.out.println(s.getCourses().get(i).studentMenuToString());
        }
        System.out.println();
        // returns the user to the student menu
        studentMenu(webID);
    }

    /**
     * Sorts the current logged in student's courses by course department and
     * course number and prints all of the student's courses in sorted form. 
     * The user is then brought back to the student menu.
     * 
     * @param webID
     * The <code>webID</code> of the student that is currently logged in.
     */
    private static void viewCoursesCND(String webID)
    {
        // the student that is currently logged in
        Student s = database.get(webID);
        // sorts the student's courses by department and course number
        s.sortCoursesCND();
        System.out.format("%s%n%s%n", "Dept. Course Semester",
            "-------------------------------");
        // prints all the courses
        for (int i = 0; i < s.getCourseCount(); i++)
        {
            System.out.println(s.getCourses().get(i).studentMenuToString());
        }
        System.out.println();
        // returns the user back to the student menu
        studentMenu(webID);
    }

    /**
     * Adds a class with the specified input to the student that is currently
     * logged in. The user is prompted for a course department, number, and
     * semester. A student can only be registered in a class once. If any of
     * the inputs for the course were invalid (incorrect format or wrong
     * interval for year), then the input is looped until valid. Once done,
     * the user is returned to the student menu.
     * 
     * @param webID
     * The <code>webID</code> of the student that is currently logged in.
     * 
     * <dt>Precondition:
     *    <dd>The input for the course is of valid format for the department:
     *    ("3 Letter Department" "SPACE" "3 Digit Course Number") and number:
     *    ("F/S" "Any number between 2010 and 2025"). The student must not
     *    already be registered in another course with the same department,
     *    course number, and semester.
     *   
     * <dt>Postcondition:
     *    <dd>If the input for the course was invalid, then the input is looped
     *    until valid. The student is registered into the class if they were not
     *    already in the class. The user is then returned to the student menu.
     * 
     */
    private static void addClass(String webID)
    {
        // gets the input for the course
        String input = inputCourse();
        // splits the string by department name (CSE) and number (214) because
        // the input is CSE space 214
        String[] department = input.split(" ");
        // parses the course number into an int
        int number = Integer.parseInt(department[1]);
        // gets input for semester
        String semester = inputSemester();
        Course c = new Course(department[0], number, semester);
        Student s = database.get(webID);
        // if the student is not already in the course
        if (s.inCourseSemester(c) == null)
        {
            // register the student into the class
            s.getCourses().add(c);
            // uses the season to display respective confirmation message
            String season = semester.substring(0, 1);
            if (season.equals("f"))
            {
                System.out.println(c.getDepartment().toUpperCase() + " "
                    + c.getNumber() + " added in Fall "
                    + semester.substring(1, 5));
            }
            else
            {
                System.out.println(c.getDepartment().toUpperCase() + " "
                    + c.getNumber() + " added in Spring "
                    + semester.substring(1, 5));
            }
            // increases the course count of the student by 1
            s.incrementCourseCount();
        }
        else
            // if the student is already in the course, display a notice msg
            System.out.println("This student is already in this course");
        // return the user to the student menu
        studentMenu(webID);
    }

    /**
     * Drops a class with the specified input to the student that is currently
     * logged in. The user is prompted for a course department, number, and
     * semester. The student must already be registered in the class. If any of
     * the inputs for the course were invalid (incorrect format or wrong
     * interval for year), then the input is looped until valid. Once done,
     * the user is returned to the student menu.
     * 
     * @param webID
     * The <code>webID</code> of the student that is currently logged in.
     * 
     * <dt>Precondition:
     *    <dd>The input for the course is of valid format for the department:
     *    ("3 Letter Department" "SPACE" "3 Digit Course Number") and number:
     *    ("F/S" "Any number between 2010 and 2025"). The student must already
     *    be registered in the input course.
     *   
     * <dt>Postcondition:
     *    <dd>If the input for the course was invalid, then the input is looped
     *    until valid. The student is de-registered from the input course if
     *    they were enrolled in it and their course count is decremented by 1.
     *    If they were not enrolled in it, then they are given a notice. The
     *    user is then returned to the student menu.
     * 
     */
    private static void dropClass(String webID)
    {
        // gets input for course
        String input = inputCourse();
        // splits input by space to receive department and course number
        String[] department = input.split(" ");
        // parses course number into an int
        int number = Integer.parseInt(department[1]);
        // gets input for semester
        String inputSem = inputSemester();
        Course c = new Course(department[0], number, inputSem);
        Student s = database.get(webID);
        // if the student is already in the course
        if (s.inCourseSemester(c) != null)
        {
            // deregister the student from the course and decrement the
            // student's course count by 1
            s.getCourses().remove(c);
            System.out.println("The class has been dropped.");
            s.decrementCourseCount();
        }
        else
            System.out.println("This student is not in this course");
        // return user to student menu
        studentMenu(webID);
    }

    /**
     * The registrar menu of the <code>Lunar System</code>. The menu holds the
     * prompts the registrar to make a choice of action. The user can register
     * and deregister students. The user can also view all the students that 
     * are currently enrolled in a certain course. The user can log out once
     * they are done. The user is prompted for an action until proper input is
     * received.
     */
    private static void registrarMenu()
    {
        Scanner scanner = new Scanner(System.in);
        boolean on = true;
        while (on)
        {
        System.out.format("%s%n%n%24s%n%27s%n%28s%n%12s%n", "Welcome Registrar.",
            "R) Register a student", "D) De-register a student", "E) View"
            + " course enrollment", "L) Logout");
        String choice = scanner.nextLine().toLowerCase();
        
        switch (choice)
        {
            case "r":
                on = false;
                registerStudent();
                break;
            case "d":
                on = false;
                deregisterStudent();
                break;
            case "e":
                on = false;
                viewCourseEnrollment();
                break;
            case "l":
            {
                on = false;
                System.out.println("Registrar logged out.");
                mainMenu();
                break;
            }
            
        }
        }
        
    }

    /**
     * Prompts the user for a <code>webID</code> to register a new student as
     * and if the id is valid, registers the student. Returns the user to the
     * registrar menu once done.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>webID</code> must not already be registered to a
     *    student in the system.
     *    
     * <dt>Postcondition:
     *    <dd>If the input <code>webID</code> was not pre-existing for an
     *    already registered student, then the student is registered into the
     *    system. If the student was already registered in the system, then
     *    the user is given a notice and is redirected back to the registrar
     *    menu.
     */
    private static void registerStudent()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a webID for the new student:");
        String webID = scanner.nextLine().toLowerCase();
        String name = webID.substring(0, 1).toUpperCase() +
            webID.substring(1, webID.length());
        if (database.containsKey(webID))
        {
            System.out.println(name + " is already registered.");
        }
        else
        {
            database.put(webID, new Student(webID));
            System.out.println(name + " has been registered.");
        }
        registrarMenu();
    }

    /**
     * Prompts the user for a <code>webID</code> to deregister an existing
     * student and if the id is valid, deregisters the student. Returns the user
     * to the registrar menu once done.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>webID</code> must already be registered to a
     *    student in the system.
     *    
     * <dt>Postcondition:
     *    <dd>If the input <code>webID</code> was pre-existing for an
     *    already registered student, then the student is deregistered from the
     *    system. If the student was not registered in the system, then
     *    the user is given a notice and is redirected back to the registrar
     *    menu.
     */
    private static void deregisterStudent()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the webID for the student being"
            + " deregistered:");
        String webID = scanner.nextLine().toLowerCase();
        String name = webID.substring(0, 1).toUpperCase() +
            webID.substring(1, webID.length());
        if (database.containsKey(webID))
        {
            database.remove(webID);
            System.out.println(name + " has been deregistered.");
        }
        else
        {
            System.out.println(name + " is not a registered student.");
        }
        registrarMenu();
    }

    /**
     * Prompts the user to input a course and displays all the students
     * registered in that course and the respective semester that the student
     * is taking the course in. Redirects the user back to the registrar menu
     * once done.
     * 
     * <dt>Postcondition:
     *    <dd>All the students in the course specified are printed with the
     *    respective semester that they are taking the course in. The user is
     *    then redirected back to the registrar menu.
     */
    private static void viewCourseEnrollment()
    {
        Scanner scanner = new Scanner(System.in);
        boolean on = true;
        String input = "";
        
        input = inputCourse();
        String[] choice = input.split(" ");
        
        int number = (Integer.parseInt(choice[1]));
        Course c = new Course(choice[0], number, null);
        
        System.out.format("%s%n%-11s%s%n%s%n", "Students Registered in " +
          c.getDepartment().toUpperCase() + " " + c.getNumber() + ":",
          "Student", "Semester", "-------------------");
        
        // Searches through every webID in the system to see if the student
        // is in the specified course
        for (String name: database.keySet())
        {
            Student s = database.get(name);
            // if the student is in the course then print the student's webID
            // and the semester that the student is taking the course in
            if (s.inCourse(c) != null)
                System.out.println(s.printNameSemester(c));
        }
        System.out.println();
        registrarMenu();
    }

    /**
     * Determines whether the input <code>String</code> consists of all numbers
     * 
     * @param substring
     * The <code>String</code> being checked to see if it consists of all numbers
     * 
     * @return
     * Returns a boolean as true if all the characters in the input <code>
     * String</code> are numbers, false if not.
     */
    private static boolean isNumeric(String substring)
    {
        try
        {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(substring, pos);
        return substring.length() == pos.getIndex();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Prompts the user to input a semester consisting of a season (F/S) and
     * a year between 2010 and 2025. If the input is incorrect, then the input
     * is looped until it is valid.
     * 
     * @return
     * Returns a <code>String</code> consisting of the input semester.
     */
    private static String inputSemester()
    {
        Scanner scanner = new Scanner(System.in);
        String semester = "";
        boolean on = true;
        
        while (on)
        {
            System.out.println("Please select a semester");
            semester = scanner.nextLine().toLowerCase();
            // checks to see if the input was 5 characters long and if the
            // last 4 characters were numbers, and if the numbers were within
            // 2010 to 2025, and if the first character was the letter f or s
            // if the input meets these criteria, then the semester is returned
            if (semester.length() == 5 &&
                isNumeric(semester.substring(1, 5)) &&
                Integer.parseInt(semester.substring(1, 5)) >= 2010 &&
                Integer.parseInt(semester.substring(1, 5)) <= 2025 &&
                (semester.substring(0, 1).equals("f") ||
                semester.substring(0,  1).equals("s")))
                return semester;
            else
                System.out.println("This is not a valid semester. A valid course"
                    + " is formatted with F/S and the year:"
                    + "'F2010' to 'S2025'");
        }
        return semester;
    }

    /**
     * Prompts the user to input a 3 letter course department and a 3 digit
     * course number, separated by a space. If the input is incorrect, then
     * the input is looped until it is valid.
     * 
     * @return
     * Returns a <code>String</code> consisting of the input department and
     * course number
     */
    private static String inputCourse()
    {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean on = true;
        while (on)
        {
            System.out.println("Please enter course:");
            input = scanner.nextLine().toLowerCase();
            // Checks to see if the input was 7 characters long, if the first
            // three characters are letters, if the last 3 characters are
            // numbers, and if these letters and numbers are separated by a 
            // space. If these criteria are met, then the input is returned.
            if (input.length() != 7 || !
                (Character.isWhitespace(input.charAt(3))) ||
                !isAlphabet(input.substring(0, 3)) ||
                !isNumeric(input.substring(4, 7)))
                System.out.println("This is not a valid course. A valid course"
                    + " is formatted like so 'CSE 214'");
            else
                return input;
        }
        return input;
    }

    /**
     * Checks to see if the input <code>String</code> consists of all letters
     * 
     * @param substring
     * The <code>String</code> being checked to see if it consists of all
     * letters.
     * 
     * @return
     * Returns a boolean with a value of true if all the characters in the
     * input <code>String</code> are letters, false if not.
     */
    private static boolean isAlphabet(String substring)
    {
        char[] chars = substring.toCharArray();
        for (char c : chars)
        {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Attempts to load the saved database in the 'lunar.ser' file found in the
     * program folder.
     * 
     * <dt>Postcondition:
     *    <dd>If there was no saved database in the 'lunar.ser' file, then the
     *    user is notified that there is no previous data found. If there was
     *    a previously saved database, then the database is loaded into the
     *    system and the user is notified that previous data has been loaded.
     */
    private static void loadFile()
    {
        try
        {
            FileInputStream file = new FileInputStream("lunar.ser");
            ObjectInputStream inStream = new ObjectInputStream(file);
            database = (HashMap<String, Student>)inStream.readObject();
            inStream.close();
            System.out.println("Previous data loaded.");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No previous data found.");
            database = new HashMap<String, Student>();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("An error occured.");
        }
        catch (IOException e)
        {
            System.out.println("An error occured.");
        }
    }
    
    /**
     * Saves the current database into the 'lunar.ser' file found in the
     * program folder.
     * 
     * <dt>Postcondition:
     *    <dd>The database is saved into 'lunar.ser'.
     */
    public static void saveFile()
    {
        try
        {
            FileOutputStream file = new FileOutputStream("lunar.ser");
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            outStream.writeObject(database);
            outStream.close();
        }
        catch (Exception e)
        {
            System.out.println("The database could not be saved.");
        }
    }

    /**
     * Returns the hash table database
     * 
     * @return
     * Returns <code>database</code>
     */
    public HashMap<String, Student> getDatabase()
    {
        return database;
    }

    /**
     * Sets the database to the input <code>HashTable</code>
     * 
     * @param database
     * The <code>HashTable</code> that <code>database</code> will be set to.
     */
    public void setDatabase(HashMap<String, Student> database)
    {
        this.database = database;
    }
}
