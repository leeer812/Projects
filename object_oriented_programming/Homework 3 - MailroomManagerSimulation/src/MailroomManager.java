// Imports the DecimalFormat needed to format the weight of the packages
import java.text.DecimalFormat;
// Imports the Scanner which is used for user input
import java.util.Scanner;

/**
 * The <code>MailroomManager</code> class is the main class of the Mailroom
 * Manager Simulator and allows the user to manipulate six different stacks.
 * Five of the stacks are categorized alphabetically and contains packages
 * under its respective category. The sixth stack is a floor stack that
 * holds all the other packages. The user can advance the day, sort the
 * packages by their names in alphabetical order, find the packages in the
 * wrong stacks and place them in the floor stack, look for the packages for
 * a given name, deliver a package into the mail room, get a package for a
 * given name, print the stacks, and empty the floor of all packages. Packages
 * remain in the mail room and are sent back to their sender after five days.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #3
 * CSE214-R02
 * TA: David S. Li
 */
public class MailroomManager
{
    // Initializes package stack 1 which holds packages whose recipients' names
    // begin from A to G
    private static PackageStack stack1AG;
    // Initializes package stack 2 which holds packages whose recipients' names
    // begin from H to J
    private static PackageStack stack2HJ;
    // Initializes package stack 3 which holds packages whose recipients' names
    // begin from K to M
    private static PackageStack stack3KM;
    // Initializes package stack 4 which holds packages whose recipients' names
    // begin from N to R
    private static PackageStack stack4NR;
    // Initializes package stack 5 which holds packages whose recipients' names
    // begin from S to Z
    private static PackageStack stack5SZ;
    /** Initializes the floor stack which temporarily holds packages when going
    through package stacks to get a package for a given person. The floor
    stack can also hold packages that were placed in the wrong stack. Unlike
    the package stacks, the floor stack does not have a capacity.*/
    private static PackageStack floor;
    // Initializes the currentStack
    private static PackageStack currentStack;
    // Initializes the otherStack
    private static PackageStack otherStack;
    // Initializes and instantiates the day counter to 0
    private static int dayCount = 0;
    // Initializes and instantiates the ON boolean to true
    private static boolean ON = true;
    
    /**
     * The <code>sortPackage</code> method sorts all of the
     * <code>PackageStack</code>s' packages by their recipients' names in
     * alphabetical order.
     * 
     * <dt>Postcondition:
     *    <dd>All the <code>PackageStack</code>s are sorted by their recipient
     *    names in alphabetical order, starting from the lowest letter to the
     *    highest letter.
     * 
     * @throws FullStackException
     *    Indicates that the <code>PackageStack</code> is full
     *    
     * @throws EmptyStackException
     *    Indicates that the <code>PackageStack</code> is empty
     */
    
    public static void sortPackage() throws FullStackException,
      EmptyStackException
    {
        // stackCount indicates which stack we are currently sorting
        int stackCount = 1;
        while (stackCount < 7)
        {
            switch (stackCount)
            {
            case 1:
                currentStack = stack1AG;
                break;
            case 2:
                currentStack = stack2HJ;
                break;
            case 3:
                currentStack = stack3KM;
                break;
            case 4:
                currentStack = stack4NR;
                break;
            case 5:
                currentStack = stack5SZ;
                break;
            case 6:
                currentStack = floor;
                break;
            }
            sortStack(currentStack);
            stackCount++;
        }
    }

    /**
     * Sorts the given <code>PackageStack</code> using this recursive method
     * and the recursive method <code>sortedInsert</code>
     * 
     * <p> While the stack is not empty, all the objects in the stack will be
     * popped and stored as <code>temp</code> packages. Once the stack is empty
     * each <code>temp</code> package will be inserted into the stack if it is
     * in sorted order. If it is not in sorted order, then the top element on
     * the stack will be popped and we will try to insert our <code>temp</code>
     * element again. This process will be repeat until the <code>temp</code>
     * is successfully inserted and then all the previous top elements that
     * were popped will be pushed back on to the stack. This process repeats
     * until every element has been added to the stack in sorted order.
     * 
     * @param stack
     *    The <code>PackageStack</code> that will be sorted
     *    
     * @throws EmptyStackException
     *    Indicates the <code>PackageStack</code> is empty
     *    
     * @throws FullStackException
     *    Indicates the <code>PackageStack</code> is full
     */
    private static void sortStack(PackageStack stack) throws
      EmptyStackException, FullStackException
    {
        Package temp = null;
        if (!stack.isEmpty())
        {
            temp = stack.pop();
            sortStack(stack);
            sortedInsert(stack, temp);
        }
    }

    /**
     * When called by <code>sortStack</code>, <code>sortedInsert</code> will
     * insert the input <code>element</code> into the stack
     * 
     * @param stack
     *    The <code>PackageStack</code> that the <code>element</code> will be
     *    inserted into
     *    
     * @param element
     *    The <code>Package</code> that will be inserted into the
     *    <code>PackageStack</code>
     *    
     * <dt>Postcondition:
     *    <dd>If the <code>PackageStack</code> was empty or if the top element
     *    has a name higher in value than <code>element</code>, then the
     *    <code>element</code> will be inserted into the stack. Otherwise, the
     *    top element will be popped and <code>element</code> is tried against
     *    the next top element. Once the <code>element</code> has been added,
     *    then all the previous top elements that were popped to make space for
     *    the <code>element<code>
     *    
     * @throws FullStackException
     *    Indicates that the stack is full and cannot hold any more packages
     *    
     * @throws EmptyStackException
     *    Indicates that the stack is empty and cannot be popped
     */
    
    private static void sortedInsert(PackageStack stack, Package element)
      throws FullStackException, EmptyStackException
    {
        String topRecipient = null;
        if (!stack.isEmpty())
        {
            topRecipient = stack.peek().getRecipient();
        }
        
        String elementRecipient = element.getRecipient();
        if (stack.isEmpty() || topRecipient.compareTo(elementRecipient) >= 0)
        {
            stack.push(element);
        }
        else
        {
            Package temp = stack.pop();
            sortedInsert(stack, element);
            stack.push(temp);
        }
    }

    /**
     * Moves the top <code>Package</code> in the user specified
     * <code>PackageStack</code> to a user specified <code>PackageStack</code>
     * 
     * <dt>Precondition:
     *    <dd>There must be a package in the source stack to pop and the
     *    destination stack must not already be full.
     *    
     * <dt>Postcondition:
     *    <dd>If there is a package in the source stack and the destination
     *    stack was not full, then the top package on the source stack is moved
     *    to the top of the destination stack. If there was a package in the
     *    source stack but the destination stack was full, then the package
     *    will be deposited into the next available stack. If all available
     *    package stacks are full, then the package will be deposited onto the
     *    top of the floor stack. If there was no package in the source stack
     *    then <code>EmptyStackException</code> is thrown.
     * 
     * @throws FullStackException
     *    Indicates that the destination stack was full and cannot hold more
     *    packages
     *    
     * @throws EmptyStackException
     *    Indicates that there were no packages to remove in the source stack
     */
    public static void movePackage() throws FullStackException,
      EmptyStackException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please enter the source stack"
          + "(enter 0 for floor):");
        int stackSource = inputScanner.nextInt();

        // stackSource indicates which stack the package will be taken from
        switch (stackSource)
        {
        case 0:
            currentStack = floor;
            break;
        case 1:
            currentStack = stack1AG;
            break;
        case 2:
            currentStack = stack2HJ;
            break;
        case 3:
            currentStack = stack3KM;
            break;
        case 4:
            currentStack = stack4NR;
            break;
        case 5:
            currentStack = stack5SZ;
            break;
        }
        
        if (currentStack.isEmpty())
        {
            System.out.println("There is no package in this stack to move!");
            return;
        }
        
        System.out.println("Please enter the destination stack"
          + "(enter 0 for floor):");
        int stackDestination = inputScanner.nextInt();

        // Indicates where the package was initially sent to
        switch (stackDestination)
        {
        case 0:
            otherStack = floor;
            break;
        case 1:
            otherStack = stack1AG;
            break;
        case 2:
            otherStack = stack2HJ;
            break;
        case 3:
            otherStack = stack3KM;
            break;
        case 4:
            otherStack = stack4NR;
            break;
        case 5:
            otherStack = stack5SZ;
            break;
        }

            int destination = stackDestination;
            int tryCount = 0;
            boolean added = false;

            while (added == false)
            {
            if (tryCount < 5)
            {
                switch (destination)
                {
                case 0:
                    otherStack = floor;
                    break;
                case 1:
                    otherStack = stack1AG;
                    break;
                case 2:
                    otherStack = stack2HJ;
                    break;
                case 3:
                    otherStack = stack3KM;
                    break;
                case 4:
                    otherStack = stack4NR;
                    break;
                case 5:
                    otherStack = stack5SZ;
                    break;
                }
                
                if (!otherStack.isFull())
                {
                    otherStack.push(currentStack.pop());
                    if (destination != stackDestination)
                    {
                        System.out.format("%s%d%s%d%s%n", "Because Stack ",
                          stackDestination, " was full, the package was moved "
                          + "to Stack ", destination, " instead.");
                    }
                    break;
                }
                else
                {
                    destination++;
                    tryCount++;
                }
        }
            else
            {
                floor.push(currentStack.pop());
                System.out.format("%s%d%s%n", "Because Stack ",
                        stackDestination, " was full, the package was moved "
                        + "to the Floor Stack instead");
                break;
            }
    }
    }

    /**
     * Removes all the packages in the floor stack
     * 
     * <dt>Postcondition:
     *    <dd>All the packages in the floor stack are removed.
     *    
     * @throws EmptyStackException
     *    Indicates that there are no packages in the floor stack to remove.
     */
    public static void emptyFloor() throws EmptyStackException
    {
        while (!floor.isEmpty())
        {
            floor.pop();
        }
        System.out.println("The floor has been emptied.");
    }

    /**
     * Finds all the packages that are in the wrong <code>PackageStack</code>s
     * and moves them to the floor.
     * 
     * <p>All the packages in a stack are popped into a temporary stack and if
     * the package does not fit the criteria of the current stack, then the
     * package is pushed into the floor instead of the temporary stack. Once
     * there are no more packages in the stack, then all the packages in the
     * temporary stack are popped and pushed back into the stack. This process
     * is repeated with every stack.
     * 
     * <dt>Postcondition:
     *    <dd>There are no packages in any of the <code>PackageStack</code>s
     *    that are in the wrong stack. Any packages that were in the wrong
     *    stack are moved to the floor stack.
     *  
     * @throws EmptyStackException
     *    Indicates that the stack is empty
     *    
     * @throws FullStackException
     *    Indicates that the stack is full
     */
    
    public static void findWrongStack() throws EmptyStackException,
      FullStackException
    {
        // stackCount indicates which stack is currently being searched through
        int stackCount = 1;
        while (stackCount != 6)
        {
            PackageStack tempStack = new PackageStack(0);
            switch (stackCount)
            {
            case 1:
                currentStack = stack1AG;
                break;
            case 2:
                currentStack = stack2HJ;
                break;
            case 3:
                currentStack = stack3KM;
                break;
            case 4:
                currentStack = stack4NR;
                break;
            case 5:
                currentStack = stack5SZ;
                break;
            }

            while (!(currentStack.isEmpty()))
            {
                String recipientName = currentStack.peek().getRecipient()
                  .substring(0, 1).toLowerCase();
                switch (stackCount)
                {
                case 1:
                    if (recipientName.compareTo("g") > 0)
                    {
                        floor.push(currentStack.pop());
                    }
                    else
                    {
                        tempStack.push(currentStack.pop());
                    }
                    break;
                case 2:
                    if (recipientName.compareTo("j") > 0 ||
                          recipientName.compareTo("h") < 0)
                    {
                        floor.push(currentStack.pop());
                    }
                    else
                    {
                        tempStack.push(currentStack.pop());
                    }
                    break;
                case 3:
                    if (recipientName.compareTo("m") > 0 ||
                          recipientName.compareTo("k") < 0)
                    {
                        floor.push(currentStack.pop());
                    }
                    else
                    {
                        tempStack.push(currentStack.pop());
                    }
                    break;
                case 4:
                    if (recipientName.compareTo("r") > 0 ||
                          recipientName.compareTo("n") < 0)
                    {
                        floor.push(currentStack.pop());
                    }
                    else
                    {
                        tempStack.push(currentStack.pop());
                    }
                    break;
                case 5:
                    if (recipientName.compareTo("z") > 0 ||
                          recipientName.compareTo("s") < 0)
                    {
                        floor.push(currentStack.pop());
                    }
                    else
                    {
                        tempStack.push(currentStack.pop());
                    }
                    break;
                }
            }

            tempStack = tempStack.reverse();

            switch (stackCount)
            {
            case 1:
                stack1AG = tempStack;
                break;
            case 2:
                stack2HJ = tempStack;
                break;
            case 3:
                stack3KM = tempStack;
                break;
            case 4:
                stack4NR = tempStack;
                break;
            case 5:
                stack5SZ = tempStack;
                break;
            }
            stackCount++;
        }
        System.out.println("Misplaced packages have been moved to the floor");
    }

    /**
     * Returns the amount of packages that a recipient has in the mailroom
     * 
     * <dt>Postcondition:
     *    <dd>The number of packages awaiting a recipient is returned. Every
     *    package that the input recipient has in any of the stacks are
     *    returned with the stack that it is in along with its arrival date
     *    and weight.
     *    
     * @throws EmptyStackException
     *    Indicates that the stack is empty
     *    
     * @throws FullStackException
     *    Indicates that the stack is full
     */
    
    public static void lookForPackage() throws EmptyStackException,
      FullStackException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please input the name of the recipient:");
        String recipient = inputScanner.nextLine();
        String packageNames = "";
        int packageCount = 0;

        // stackCount indicates which stack is currently being searched through
        int stackCount = 1;
        while (stackCount != 7) 
        {
            PackageStack tempStack;
            if (stackCount == 6)
                tempStack = new PackageStack();
            else
                tempStack = new PackageStack(0);

            switch (stackCount) 
            {
            case 1:
                currentStack = stack1AG;
                break;
            case 2:
                currentStack = stack2HJ;
                break;
            case 3:
                currentStack = stack3KM;
                break;
            case 4:
                currentStack = stack4NR;
                break;
            case 5:
                currentStack = stack5SZ;
                break;
            case 6:
                currentStack = floor;
                break;
            }

            while (!currentStack.isEmpty()) 
            {
                if (currentStack.peek().getRecipient().equals(recipient)) 
                {
                    int arrival = currentStack.peek().getArrivalDate();
                    double weight = currentStack.peek().getWeight();
                    packageCount++;
                    if (stackCount == 6)
                    {
                        packageNames += String.format("%s%d%s%s%d%s%.2f%s%n",
                          "Package ", packageCount, " is in the Floor Stack ",
                          ", it was delivered on day ", arrival, ", and weighs"
                          + " ", weight, " lbs.");
                    }
                    else
                    {
                        packageNames += String.format("%s%d%s%d%s%d%s%.2f%s%n",
                          "Package ", packageCount, " is in Stack ", stackCount
                          ,", it was delivered on day ", arrival,
                          ", and weighs" + " ", weight, " lbs.");
                    }
                    tempStack.push(currentStack.pop());
                } 
                else 
                {
                    tempStack.push(currentStack.pop());
                }
            }

            tempStack = tempStack.reverse();

            switch (stackCount) {
            case 1:
                stack1AG = tempStack;
                break;
            case 2:
                stack2HJ = tempStack;
                break;
            case 3:
                stack3KM = tempStack;
                break;
            case 4:
                stack4NR = tempStack;
                break;
            case 5:
                stack5SZ = tempStack;
                break;
            case 6:
                floor = tempStack;
                break;
            }
            stackCount++;
        }
        System.out.format("%s%s%d%s%n", recipient, " has ", packageCount,
                " packages total.");
        System.out.println(packageNames);
    }

    /**
     * Advances the day counter by one. Any packages that have been in the
     * mail room for at least 5 day are removed from the
     * <code>PackageStack</code>. 
     * 
     * <dt>Postcondition:
     *    <dd><code>dayCount</code> is incremented by 1. Any package in any
     *    of the <code>PackageStack</code>s whose <code>arrivalDate</code> is
     *    5 less than <code>dayCount</code> is removed from the stack.
     *    
     * @throws EmptyStackException
     *    Indicates that the stack is empty
     *    
     * @throws FullStackException
     *    Indicates that the stack is full
     */
    
    public static void advanceDay() throws EmptyStackException,
      FullStackException 
    {
        dayCount++;
        int removedCount = 0;
        int stackCount = 1;
        while (stackCount != 7) 
        {
            PackageStack tempStack;
            if (stackCount == 6)
                tempStack = new PackageStack();
            else
                tempStack = new PackageStack(0);

            switch (stackCount) 
            {
            case 1:
                currentStack = stack1AG;
                break;
            case 2:
                currentStack = stack2HJ;
                break;
            case 3:
                currentStack = stack3KM;
                break;
            case 4:
                currentStack = stack4NR;
                break;
            case 5:
                currentStack = stack5SZ;
                break;
            case 6:
                currentStack = floor;
                break;
            }

            while (!currentStack.isEmpty()) 
            {
                if (dayCount - currentStack.peek().getArrivalDate() >= 5) 
                {
                    currentStack.pop();
                    removedCount++;
                } 
                else 
                {
                    tempStack.push(currentStack.pop());
                }
            }

            tempStack = tempStack.reverse();

            switch (stackCount) {
            case 1:
                stack1AG = tempStack;
                break;
            case 2:
                stack2HJ = tempStack;
                break;
            case 3:
                stack3KM = tempStack;
                break;
            case 4:
                stack4NR = tempStack;
                break;
            case 5:
                stack5SZ = tempStack;
                break;
            case 6:
                floor = tempStack;
                break;
            }
            stackCount++;
        }
        if (removedCount > 0)
        {
            System.out.println("The day has advanced to " + dayCount + ". "
              + removedCount + " packages have been returned back tosender.");
        }
        else
        {
            System.out.println("The day has advanced to " + dayCount);
        }
    }

    /**
     * Delivers a package with the input <code>recipient</code> name and weight
     * to its respective <code>PackageStack</code>
     * 
     * <dt>Precondition:
     *    <dd>The destination stack for the recipient must not be full
     *    
     * <dt>Postcondition:
     *    <dd>If the destination stack for the recipient is not full, then the
     *    <code>Package</code> is pushed onto the top of the stack with its
     *    input recipient name and weight. If the destination stack was full,
     *    then the next respective stack is checked to see if there is space.
     *    If none of the <code>PackageStack</code>s have space, then the
     *    package is deposited onto the top of the floor stack. The package is
     *    delivered with the current <code>dayCount</code> as its
     *    <code>arrivalDate</code>.
     * 
     * @throws FullStackException
     *    Indicates the stack is full
     */
    
    public static void deliverPackage() throws FullStackException
    {
        DecimalFormat format = new DecimalFormat("#.##");
        PackageStack[] array = {null, stack1AG, stack2HJ, stack3KM, stack4NR,
          stack5SZ};
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please input the recipient name:");
        String rName = inputScanner.nextLine();
        System.out.println("Please enter the weight (lbs):");
        double w = inputScanner.nextDouble();
        Package newPackage = new Package(rName, dayCount, w);
        int destination = 0;
        int originalDestination = 0;
        int checkCount = 0;
        boolean finalDestination = false;

        String compareThis = rName.substring(0, 1);
        if (compareThis.toLowerCase().compareTo("g") <= 0)
        {
            currentStack = stack1AG;
            destination = 1;
            originalDestination = 1;
        }
        else if (compareThis.toLowerCase().compareTo("j") <= 0)
        {
            currentStack = stack2HJ;
            destination = 2;
            originalDestination = 2;
        }
        else if (compareThis.toLowerCase().compareTo("m") <= 0)
        {
            currentStack = stack3KM;
            destination = 3;
            originalDestination = 3;
        }
        else if (compareThis.toLowerCase().compareTo("r") <= 0)
        {
            currentStack = stack4NR;
            destination = 4;
            originalDestination = 4;
        }
        else if (compareThis.toLowerCase().compareTo("z") <= 0)
        {
            currentStack = stack5SZ;
            destination = 5;
            originalDestination = 5;
        }

        while (!finalDestination)
        {
            if (!array[destination].isFull())
            {
                currentStack = array[destination];
                break;
            }
            else
            {
                if (checkCount < 5)
                {
                    if (destination==5)
                    {
                        destination = 1;
                        checkCount++;
                    }
                    else
                    {
                        destination++;
                        checkCount++;
                    }
                }
                else
                {
                    currentStack = floor;
                    break;
                }
            }
        }

        try
        {
            currentStack.push(newPackage);
        }
        catch (Exception e)
        {
        }

        if (destination == originalDestination)
        {
            System.out.format("%s%s%s%s%n", "A ", format.format(w), " lb pack"
              + "age is awaiting pickup by ", rName);
        }
        else
        {
            if (checkCount != 5)
            {
                System.out.format("%s%s%s%s%s%d%s%d%n", "A ", format.format(w),
                  " lb package is awaiting pickup by ", rName, ". As Stack ",
                  originalDestination, " was full, it was placed in Stack ",
                  destination);
            }
            else
            {
                System.out.format("%s%s%s%s%s%d%s%n", "A ", format.format(w),
                  " lb package is awaiting pickup by ", rName, ". As Stack ",
                  originalDestination, " was full, it was placed in the"
                  + "Floor Stack");
            }

        }
    }

    /**
     * Gets the top most package in the <code>PackageStack</code>s for the
     * input recipient.
     * 
     * <dt>Precondition:
     *    <dd>There must be a package in the <code>PackageStack</code>s or the
     *    floor for the recipient
     * 
     * <dt>Postcondition:
     *    <dd>The top most package in the <code>PackageStack</code> for the
     *    input recipient is returned and removed from the stack.
     *    and removed from the stack. If there is no package awaiting the
     *    recipient, the user is notified as such.
     *    
     * @throws FullStackException
     *    Indicates that the stack was full
     *    
     * @throws EmptyStackException
     *    Indicates that the stack was empty
     */
    
    public static void getPackage() throws FullStackException,
      EmptyStackException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Please input the name of the recipient:");
        String recipient = inputScanner.nextLine();

        int stackCount = 1;
        int lookCount = 0;
        boolean found = false;
        
        outerloop:
        while (stackCount != 6) 
        {
            found = false;
            lookCount = 0;
            switch (stackCount) 
            {
            case 1:
                currentStack = stack1AG;
                break;
            case 2:
                currentStack = stack2HJ;
                break;
            case 3:
                currentStack = stack3KM;
                break;
            case 4:
                currentStack = stack4NR;
                break;
            case 5:
                currentStack = stack5SZ;
                break;
            }

            while (!currentStack.isEmpty()) 
            {
                if (currentStack.peek().getRecipient().equals(recipient)) 
                {
                    double w = currentStack.peek().getWeight();
                    int date = currentStack.peek().getArrivalDate();
                    System.out.println("Move " + lookCount + " packages from"
                      + "Stack " + stackCount + " to floor.");
                    printStacks();
                    System.out.println("Give " + recipient + " " + w 
                            + " lb package deli" + "vered on day " + date);
                    currentStack.pop();
                    System.out.println("Return " + lookCount + " packages to "
                      + "Stack " + stackCount + " from floor.");
                    for (int i = 0; i < lookCount; i++)
                    {
                        currentStack.push(floor.pop());
                    }
                    printStacks();
                    found = true;
                    break outerloop;
                } 
                else 
                {
                    floor.push(currentStack.pop());
                    lookCount++;
                }
            }

            if (found == false)
            {
                for (int i = 0; i < lookCount; i++)
                {
                    currentStack.push(floor.pop());
                }
            }
            stackCount++;
        }
        if (found == false)
            System.out.println("There was no package in the mail room for "
              + recipient);

    }

    /**
     * Prints the contents of each <code>PackageStack</code> and floor stack
     * 
     * <dt>Postcondition:
     *    <dd>The contents of each stack are printed
     */
    public static void printStacks()
    {
        System.out.println("Current Packages:");
        System.out.println("--------------------------------");
        System.out.format("%s%s%n", "Stack 1 (A-G):|", stack1AG.toString());
        System.out.format("%s%s%n", "Stack 2 (H-J):|", stack2HJ.toString());
        System.out.format("%s%s%n", "Stack 3 (K-M):|", stack3KM.toString());
        System.out.format("%s%s%n", "Stack 4 (N-R):|", stack4NR.toString());
        System.out.format("%s%s%n", "Stack 5 (S-Z):|", stack5SZ.toString());
        System.out.format("%s%s%n", "Floor Stack:|", floor.toString());
    }

    /**
     * The main method of the <code>MailroomManager</code> class provides the
     * user with a menu that lets the user manipulate the mail room and its
     * <code>PackageStack<code>s by delivering packages to the mail room, get
     * a person's package, advance the day, print all the contents of the
     * stacks, move a package from one stack to another, find all the packages
     * in the wrong stacks and move them to the floor, list all the packages
     * waiting for a recipient, and empty the floor of all packages.
     * 
     * @throws FullStackException
     *    Indicates that the stack is full and no more packages can be added
     *    to it
     *    
     * @throws EmptyStackException
     *    Indicates that the stack is empty and no packages can be removed
     *    from it
     */
    public static void main(String []args) throws FullStackException,
      EmptyStackException
    {
        stack1AG = new PackageStack(0);
        stack2HJ = new PackageStack(0);
        stack3KM = new PackageStack(0);
        stack4NR = new PackageStack(0);
        stack5SZ = new PackageStack(0);
        floor = new PackageStack();
        
        Scanner inputScanner = new Scanner(System.in);
        System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
         "It " + "is day " + dayCount, "Menu:", "D) Deliver a package",
         "G) Get" + " someone's package", "T) Make it tomorrow",
         "P) Print the stacks", "M) Move a package from one stack to another", 
         "F) Find" + " packages in the wrong stacks and move to floor",
         "L) List all packages awaiting a user", "E) Empty the floor, moving"
         + "all " + "packages to the trash", "H) Display the help menu ",
         "S) Sort the stacks alphabetically", "Q) Quit");
        while (ON)
        {
            String choice = inputScanner.nextLine();
            switch (choice.toLowerCase())
            {
            case "d":
                try 
                {
                    deliverPackage();
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }
                break;
            case "h":
                System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n"
                  + "%s%n%s%n", "It is day " + dayCount, "Menu:", 
                  "D) Deliver a " + "package", "G) Get someone's package", 
                  "T) Make it tomorrow", "P) Print the stacks", "M) Move a"
                  + " package from one stack to another", "F) Find packages "
                  + "in the wrong stacks and move to floor", "L) List all "
                  + "packages awaiting a user", "E) Empty the floor, moving "
                  + "all packages to the trash", "H) Display the help menu ",
                  "S) Sort the stacks alphabetically", "Q) Quit");
                break;
            case "g":
                getPackage();
                break;
            case "t":
                advanceDay();
                break;
            case "p":
                printStacks();
                break;
            case "m":
                movePackage();
                break;
            case "f":
                findWrongStack();
                break;
            case "l":
                lookForPackage();
                break;
            case "e":
                emptyFloor();
                break;
            case "s":
                sortPackage();
                break;
            case "q":
                System.out.println("Thank you for playing.");
                System.exit(0);
                break;
            }
        }
    }
}



