/**
 * The <code>OilChangeManager</code> class lets the user manipulate the three
 * available <code>CarList</code> objects (Joe's, Donny's, and the finished
 * list) by by performing various operations to the lists inserting and cutting
 * cars from the list to manipulate each <code>CarList</code> object's
 * <code>CarListNode</code> objects.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */

import java.util.Scanner; // Imports the scanner.

public class OilChangeManager
{
    private static CarList listD; // Initialization of listD
    private static CarList listJ; // Initialization of listJ
    private static CarList listFinal; // Initialization of listFinal
    private static Car cutCar; // Initialization of the cutCar

    /**
     * The <code>merge</code> method allows the user to merge the other list
     * with the list of his or her choice. 
     * 
     * <dt>Postcondition:
     *    <dd>Donny's list is merged into Joe's list if the user input
     *    <code>"j"</code>, and Joe's list is merged into Donny's list if
     *    the user input <code>"d"</code>. Case does not matter for the input.
     * 
     * @throws EmptyListException
     *    Indicates that both the lists are empty and that the lists cannot
     *    be merged together.
     *    
     * @throws IllegalArgumentException
     *    Indicates that the car being added was null
     */

    public static void merge() throws EmptyListException,
      IllegalArgumentException
    {
        try
        {
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Please select a destination list - Joe (J) "
              + "or Donny (D):");
            String choice = inputScanner.nextLine();

            if (choice.toLowerCase().equals("j"))
            {
                listJ = listJ.mergeList(listD);
                listD = new CarList();
                System.out.println("Donny's list has been merged with "
                  + "Joe's list.");
            }
            else if (choice.toLowerCase().equals("d"))
            {
                listD = listD.mergeList(listJ);
                listJ = new CarList();
                System.out.println("Joe's list has been merged "
                  + "with Donny's list.");
            }
        }
        catch (EmptyListException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The <code>addCar</code> method returns a new <code>Car</code> with a
     * <code>make</code> and owner <code>name</code> input by the user.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>make</code> must be one of the available makes.
     * 
     * @return
     *    Returns a new <code>Car</code> object with the input
     *    <code>make</code> and owner <code>name</code>
     *    
     * <dt>Postcondition:
     *    <dd>A new <code>Car</code> object is returned with the input
     *    <code>make</code> and owner <code>name</code>. If the user input
     *    a make not within the range of available makes, then the
     *    <code>IllegalArgumentException</code> is thrown.
     *    
     * @throws IllegalArgumentException
     *    Indicates that the input <code>make</code> is not within the range
     *    of available makes.
     */

    public static Car addCar() throws IllegalArgumentException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.format("%s%n%s%n", "Please input the MAKE of the car: " 
          ,"The available makes are: FORD, GMC, CHEVY, JEEP, DODGE, "
          + "CHRYSLER, LINCOLN");
        Make make = null;
        String makeInput = inputScanner.nextLine();
        
        switch (makeInput.toLowerCase())
        {
        case "ford":
            make = Make.Ford;
            break;
        case "gmc":
            make = Make.GMC;
            break;
        case "chevy":
            make = Make.Chevy;
            break;
        case "jeep":
            make = Make.Jeep;
            break;
        case "dodge":
            make = Make.Dodge;
            break;
        case "chrysler":
            make = Make.Chrysler;
            break;
        case "lincoln":
            make = Make.Lincoln;
            break;
        }

        if (make == null)
            throw new IllegalArgumentException("We do not service " 
              + makeInput);
        System.out.println("Please input the car's owner's name: ");
        String name = inputScanner.nextLine();
        return new Car(make, name);
    }

    /**
     * Prints Joe's, Donny's, and the finished list.
     */

    public static void printLists()
    {
        System.out.format("%s%n", "Joe's list:");
        System.out.format("%-17s%s%n", "Make:", "Owner:");
        System.out.println("-----------------------");
        System.out.println(listJ.toString());
        System.out.format("%s%n", "Donny's list:");
        System.out.format("%-17s%s%n", "Make:", "Owner:");
        System.out.println("-----------------------");
        System.out.println(listD.toString());
        System.out.format("%s%n", "Finished list:");
        System.out.format("%-17s%s%n", "Make:", "Owner:");
        System.out.println("-----------------------");
        System.out.println(listFinal.toStringFinal());
    }

    /**
     * Prompts the user to select whether they would like
     * to manipulate Joe or Donny's lists.
     * 
     * @throws IllegalArgumentException
     *    Thrown when the a null <code>Car</code> is being added into
     *    the lists.
     *    
     * @throws EndOfListException
     *    Thrown when the <code>cursor</code> of the list has reached the end
     *    of the list and cannot go forward or backwards anymore.
     */
    public static void listAction() throws IllegalArgumentException,
      EndOfListException
    {
        System.out.println("Select Joe (J) or Donny (D)");
        Scanner inputScanner = new Scanner(System.in);
        String action = "";
        action = inputScanner.nextLine();

        switch (action.toLowerCase())
        {
        case "j":
            operateListJ();
            break;
        case "d":
            operateListD();
            break;
        }
    }

    /**
     * Allows the user to add a car to the end of the list, to move the
     * <code>cursor</code> forward or backwards or to move it directly to the
     * head or tail of the list. The user can also insert or paste a car
     * directly before the <code>cursor</code> or it can cut or remove the car
     * at the <code>cursor</code>. When an action has been completed, the user
     * is alerted with the details of the <code>car</code> that they have added
     * or removed, or if they moved the cursor they are notified that it has
     * been successfully or non-successfully moved backwards or forwards.
     *  
     * @throws IllegalArgumentException
     *    Indicates that the <code>car</code> being added was null
     *    
     * @throws EndOfListException
     *    Indicates that the <code>cursor</code> was at the end of the list and
     *    cannot be moved forward or backwards.
     */
    public static void operateListJ()throws IllegalArgumentException, EndOfListException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
          "Please select an action:", "A: Add a car to the end",
          "F: Cursor Forward", "H: Cursor to Head", "T: Cursor to" + " tail",
          "B: Cursor Backward", "I: Insert car before cursor", "X: " + 
          "Cut car at cursor", "V: Paste car before cursor", "R: remove car " +
          "at cursor");
        String action = "";
        action = inputScanner.nextLine();

        switch (action.toLowerCase())
        {
        case "a":
        {
            try {
                Car newCar = addCar();
                listJ.appendToTail(newCar);
                System.out.println("" + newCar.getOwner() + "'s "
                  + newCar.getMake() + " has been scheduled for an oil change"
                  + " with Joe and has been added to his list.");
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        }
        case "f":
            try
            {
                listJ.cursorForward();
                System.out.println("The cursor has been moved forward in Joe's"
                  + " list.");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }

            break;
        case "h":
            listJ.resetCursorToHead();
            System.out.println("The cursor has been reset to the head in "
              + "Joe's list.");
            break;
        case "t":
            listJ.cursorToTail();
            System.out.println("The cursor has been moved to the tail in Joe's"
              + " list");
            break;
        case "b":
            try
            {
                listJ.cursorBackward();
                System.out.println("The cursor has been moved backwards in"
                  + "Joe's list");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "i":
            try
            {
                Car newCar = addCar();
                listJ.insertBeforeCursor(newCar);
                System.out.println("" + newCar.getOwner() + "'s "
                  + newCar.getMake() + " has been scheduled for an oil change"
                  + "with Joe and has been added to his list before the"
                  + " cursor.");
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "x":
            try
            {
                cutCar = listJ.removeCursor();
                System.out.println("Cursor cut in Joe's list.");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "v":
            try
            {
                if (cutCar != null)
                {
                    listJ.insertBeforeCursor(cutCar);
                    cutCar = null;
                    System.out.println("Cursor pasted in Joe's list.");
                }
                else
                {
                    System.out.println("Nothing to paste.");
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "r":
            try
            {
                listJ.removeCursor();
                System.out.println("Cursor removed in Joe's list.");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    /**
     * Allows the user to add a car to the end of the list, to move the
     * <code>cursor</code> forward or backwards or to move it directly to the
     * head or tail of the list. The user can also insert or paste a car
     * directly before the <code>cursor</code> or it can cut or remove the car
     * at the <code>cursor</code>. When an action has been completed, the user
     * is alerted with the details of the <code>car</code> that they have added
     * or removed, or if they moved the cursor they are notified that it has
     * been successfully or non-successfully moved backwards or forwards.
     *  
     * @throws IllegalArgumentException
     *    Indicates that the <code>car</code> being added was null
     *    
     * @throws EndOfListException
     *    Indicates that the <code>cursor</code> was at the end of the list and
     *    cannot be moved forward or backwards.
     */
    public static void operateListD()throws IllegalArgumentException,
      EndOfListException
    {
        Scanner inputScanner = new Scanner(System.in);

        System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", "Please "
          + "select an action:", "A: Add a car to the end", "F: Cursor Forward"
          , "H: Cursor to Head", "T: Cursor to" + " tail", "B: Cursor Backward"
          , "I: Insert car before cursor", "X: " + "Cut car at cursor"
          ,"V: Paste car before cursor", "R: remove car " + "at cursor");
        String action = "";
        action = inputScanner.nextLine();

        switch (action.toLowerCase())
        {
        case "a":
        {
            try {
                Car newCar = addCar();
                listD.appendToTail(newCar);
                System.out.println("" + newCar.getOwner() + "'s "
                  + newCar.getMake() + " has been scheduled for an oil change"
                  + "with Donny and has been added to his list.");
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        }
        case "f":
            try
            {
                listD.cursorForward();
                System.out.println("The cursor has been moved forward in "
                  + "Donny's list.");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }

            break;
        case "h":
            listD.resetCursorToHead();
            System.out.println("The cursor has been reset to the head in "
              + "Donny's list.");
            break;
        case "t":
            listD.cursorToTail();
            System.out.println("The cursor has been moved to the tail in "
              + "Donny's list");
            break;
        case "b":
            try
            {
                listD.cursorBackward();
                System.out.println("The cursor has been moved backwards in "
                  + "Donny's list");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "i":
            try
            {
                Car newCar = addCar();
                listD.insertBeforeCursor(newCar);
                System.out.println("" + newCar.getOwner() + "'s "
                  + newCar.getMake() + " has been scheduled for an oil change "
                  + "with Donny and has been added to his list before the "
                  + "cursor.");
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "x":
            try
            {
                cutCar = listD.removeCursor();
                System.out.println("Cursor cut in Donny's list.");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "v":
            try
            {
                if (cutCar != null)
                {
                    listD.insertBeforeCursor(cutCar);
                    cutCar = null;
                    System.out.println("Cursor pasted in Donny's list.");
                }
                else
                {
                    System.out.println("Nothing to paste.");
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        case "r":
            try
            {
                listD.removeCursor();
                System.out.println("Cursor removed in Donny's list.");
            }
            catch (EndOfListException e)
            {
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    /**
     * The main method of the <code>OilChangeManager</code> class. The user is
     * prompted with a menu and is asked to provide a character to specify the
     * next action to take. L for list operations, M to merge the lists, P to
     * print all three lists, F to paste the cut car into the finished list,
     * and Q to quit the program.
     * 
     * @throws IllegalArgumentException
     *    Indicates when a null <code>Car</code> is attempted to be added
     *    into the list
     *    
     * @throws EndOfListException
     *    Indicates that the cursor is at the end of the list and cannot be
     *    moved forward or backwards
     *    
     * @throws EmptyListException
     *    Indicates that both lists are empty and cannot be merged.
     */
    public static void main(String[] args) throws IllegalArgumentException,
      EndOfListException, EmptyListException
    {
        listJ = new CarList();
        listD = new CarList();
        listFinal = new CarList();
        Scanner inputScanner = new Scanner(System.in);

        boolean ON = true;
        while (ON)
        {
            System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n", "L: List Operations "
              + "- Select Joe (J) or Donny (D)", "M: Merge Lists", 
              "P: Print Lists", "F: Paste car to finished list.",
              "S: Sort a List", "Q: Quit");
            String action = "";
            action = inputScanner.nextLine();
            
            switch (action.toLowerCase())
            {
            case "l":
                listAction();
                break;
            case "m":
                merge();
                break;
            case "p":
                printLists();
                break;
            case "f":
                paste();
                break;
            case "s":
                sortList();
                break;
            case "q":
                System.out.println("Have a nice retirement!");
                System.exit(0);
                break;
            }
        }
    }
    
    /**
     * Prompts the user to select a list to sort and then sorts the specified
     * list.
     * 
     * <dt>Postcondition:
     *    <dd>The selected list is sorted by <code>make</code> in alphabetical
     *    order, from A to Z.
     */

    private static void sortList()
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please select a list - Joe (J) or Donny (D)");
        String choice = inputScanner.nextLine();
        
        if (choice.toLowerCase().equals("j"))
            listJ.sortList();
        else if (choice.toLowerCase().equals("d"))
            listD.sortList();
    }

    /**
     * The <code>paste</code> method lets the user paste the car that they have
     * cut from either Donny's or Joe's list into the finished list.
     * 
     * <dt>Precondition:
     *    <dd>There must be a <code>Car</code> referenced in
     *    <code>cutCar</code>.
     *    
     * <dt>Postcondition:
     *    <dd>If <code>cutCar</code> is not null, then the <code>Car</code>
     *    referenced by <code>cutCar</code> is inserted into the finished list
     *    and <code>cutCar</code> is set to null. If <code>cutCar</code> is
     *    null, then the user is prompted that there was nothing to paste.
     */
    private static void paste()
    {
        try
        {
            if (cutCar != null)
            {
                listFinal.insertBeforeCursor(cutCar);
                cutCar = null;
                System.out.println("Cursor pasted in the Final list.");
            }
            else
            {
                System.out.println("Nothing to paste.");
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
