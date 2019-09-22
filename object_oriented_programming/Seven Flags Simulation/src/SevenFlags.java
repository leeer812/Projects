import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * The <code>SevenFlags</code> class is the main class of the
 * SevenFlagsSimulation and creates a simulation of an amusement park with
 * four rides filled with three different types of people with different
 * priorities in boarding rides. Each ride runs for a certain amount of time
 * and can only hold a certain amount of people on the ride and on its lines.
 * <code>SevenFlags</code> takes user input for every variable and allows the
 * simulation to run based on different parameters every simulation. People
 * choose rides randomly and the chances at which they choose the rides can be
 * manually chosen. Time is manually advanced and at the end of the simulation,
 * statistics displaying information about the amount of rides each type of
 * person went on and how many people each ride ran for are provided.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #4
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class SevenFlags
{
    // The current time of the simulation
    private static int time = 0;
    // The amount of regular people in the simulation
    private static int REGCOUNT;
    // The amount of silver people in the simulation
    private static int SILVERCOUNT;
    // The amount of gold people in the simulation
    private static int GOLDCOUNT;
    // The total amount of people in the simulation
    private static int PEOPLECOUNT;
    // The total time that BSOD runs for
    private static int BSOD_DURATION;
    // The total amount of people that BSOD can hold on the ride
    private static int BSOD_CAPACITY;
    // The total amount of people that BSOD can hold on its holding queue
    private static int BSOD_HSIZE;
    // The total time that KK runs for
    private static int KK_DURATION;
    // The total amount of people that KK can hold on the ride
    private static int KK_CAPACITY;
    // The total amount of people that KK can hold on its holding queue
    private static int KK_HSIZE;
    // The total time that GF runs for
    private static int GF_DURATION;
    // The total amount of people that GF can hold on the ride
    private static int GF_CAPACITY;
    // The total amount of people that GF can hold on its holding queue
    private static int GF_HSIZE;
    // The total time that ToT runs for
    private static int ToT_DURATION;
    // The total amount of people that ToT can hold on the ride
    private static int ToT_CAPACITY;
    // The total amount of people that ToT can hold on its holding queue
    private static int ToT_HSIZE;
    // Indicates that the complete input has not been received yet and will
    // continue attempting to get input from the user
    private static boolean inputReceived = false;
    // Indicates the simulation has not been ended yet
    private static boolean ON = true;
    private static Ride BSOD;
    private static Ride KK;
    private static Ride ToT;
    private static Ride GF;
    private static Ride rides[] = new Ride[4];
    // Indicates how many seats are available on all the rides, collectively
    private static int rideSeatsAvailable;
    // A list of every gold person
    private static List<Person> goldList = new ArrayList<Person>();
    // A list of every silver person
    private static List<Person> silverList = new ArrayList<Person>();
    // A list of every regular person
    private static List<Person> regList = new ArrayList<Person>();
    // The array of probabilities that will be used when people select rides
    private static double[] probabilities = new double[4];
    
    /**
     * The main method of the <code>SevenFlags</code> class. The main method
     * prompts the user for the input and will continue prompting the user
     * until all the input has been received successfully within the valid
     * range. Rides and people will then be made with the received input.
     * People are then added to the rides at random in the following order:
     * Gold people are all assigned to one ride, Silver people are all assigned
     * to one ride, and regular people are all assigned to one ride. Gold is
     * then assigned to another ride and silver are assigned another ride.
     * Lastly, gold is assigned another ride. The user is then prompted to
     * advance the time until they want to end the program and the simulation
     * statistics are displayed.
     */
    public static void main(String[] args)
    {
        Scanner inputScanner = new Scanner(System.in);
        String choice = "";
        while (inputReceived == false)
        {
            try { getInput(); }
            catch (Exception e)
            {
                if (e.getMessage() != null)
                    System.out.println(e.getMessage());
                else
                    System.out.println("Please enter a valid number");
            }
        }
        makeRides();
        populatePeopleList();
        addPeopleToRides();
        printRides();
        while (ON = true)
        {
            System.out.format("%s%n%s%n", "T) Advance the time",
              "Q) End the simulation");
            choice = inputScanner.nextLine();
            switch (choice.toLowerCase())
            {
            case "t":
                advanceTime();
                printRides();
                break;
            case "q":
                simulationStats();
                System.exit(0);
                break;
            }
        }
    }
    
    /**
     * Prints out the simulation statistics. The simulation statistics display
     * how many times each person type has gone on a ride on average, and how
     * many people each ride has completed rides for.
     */
    private static void simulationStats() 
    {
        double goldRun = 0;
        for (int i = 0; i < goldList.size(); i++)
        {
            goldRun += goldList.get(i).getRunAmount();
        }
        goldRun /= goldList.size();
        double silverRun = 0;
        for (int i = 0; i < silverList.size(); i++)
        {
            silverRun += silverList.get(i).getRunAmount();
        }
        silverRun /= silverList.size();
        double regRun = 0;
        for (int i = 0; i < regList.size(); i++)
        {
            regRun += regList.get(i).getRunAmount();
        }
        regRun /= regList.size();
        System.out.format("%s%.2f%s%n", "On average, Gold customers have tak"
          + "en ", goldRun, " rides.");
        System.out.format("%s%.2f%s%n", "On average, Silver customers have tak"
          + "en ", silverRun, " rides.");
        System.out.format("%s%.2f%s%n", "On average, Regular customers have"
          + " taken ", regRun, " rides.");
        System.out.format("%s%d%s%n", "BSOD has completed rides for ", BSOD.
          getRunAmount(), " people");
        System.out.format("%s%d%s%n", "KK has completed rides for ", KK.
          getRunAmount(), " people");
        System.out.format("%s%d%s%n", "ToT has completed rides for ", ToT.
                getRunAmount(), " people");
        System.out.format("%s%d%s%n", "GF has completed rides for ", GF.
                getRunAmount(), " people");
    }

    /**
     * Advances the time of the simulation. 
     * 
     * <dt>Postcondition:
     *    <dd>Time is incremented by 1 and every ride's <code>timeLeft</code>
     *    is decremented by 1. If the time it has left to run is 0, then all
     *    the riders are dequeued and the people in its holding queue and
     *    virtual line are added to the ride. Once the ride is full or all 
     *    the people in the holding queue and virtual line are added,
     *    then the dequeued riders are added to randomly selected rides.
     */
    private static void advanceTime() 
    {
        Ride[] rides = {BSOD, KK, ToT, GF};
        Ride currentRide;
        Person p;
        Stack<Person> dequeuedGold = new Stack<Person>();
        Stack<Person> dequeuedSilver = new Stack<Person>();
        Stack<Person> dequeuedReg = new Stack<Person>();
        int rideSeats;
        time++;
        
        // Advance time through every ride
        for (int i = 0; i < rides.length; i++)
        {
            currentRide = rides[i];
            currentRide.decrementTimeLeft();
            if (currentRide.getTimeLeft() <= 0)
            {
                
                currentRide.incrementRunAmount();
                currentRide.resetTimeLeft();
                Stack<Person> oldRiders = currentRide.dequeueFromRide();
                while (!oldRiders.isEmpty())
                {
                    p = oldRiders.pop();
                    switch (p.getPass())
                    {
                    case Gold:
                        dequeuedGold.push(p);
                        break;
                    case Silver:
                        dequeuedSilver.push(p);
                        break;
                    case Regular:
                        dequeuedReg.push(p);
                        break;
                    }
                }
                currentRide.advanceLines();
                rideSeats = currentRide.getNumPeopleOnRide();
                rideSeatsAvailable += currentRide.getRideCapacity() -rideSeats;
            }
        }
        
        while (!dequeuedGold.isEmpty())
        {
            p = dequeuedGold.pop();
            while (p.getNumLines() < p.getMaxLines())
            {
                addPersonToRide(p);
            }
        }
        while (!dequeuedSilver.isEmpty())
        {
            p = dequeuedSilver.pop();
            while (p.getNumLines() < p.getMaxLines())
            {
                addPersonToRide(p);
            }
        }
        while (!dequeuedReg.isEmpty())
        {
            p = dequeuedReg.pop();
            while (p.getNumLines() < p.getMaxLines())
            {
                addPersonToRide(p);
            }
        }
    }
    
    /**
     * Prints a <code>String</code> representation of each ride and of each
     * person's lines and their statuses.
     */
    private static void printRides()
    {
        System.out.println("The time is now: " + time);
        for(int i = 0; i < rides.length ; i++)
        {
            System.out.println(rides[i].toString());
        }
        
        System.out.format("%s%n%-4s%-6s%s%n", "Regular Customers:", "Num",
          "Line", "Status");
        System.out.println("----------------");
        
        for (int i = 0; i < regList.size(); i++)
        {
            System.out.format("%-4d%-6s%s%n", i+1, regList.get(i).getLines()
              .get(0).getName(), regList.get(i).getStatus().toString());
        }
        
        System.out.format("%n%s%n%-4s%-7s%-7s%s%n", "Silver Customers:", "Num",
                "Line 1", "Line 2", "Status");
        System.out.println("------------------------");
              for (int i = 0; i < silverList.size(); i++)
              {
                  System.out.format("%-4d%-7s%-7s%s%n", i+1, silverList.get(i)
                    .getLines().get(0).getName(), silverList.get(i).getLines()
                    .get(1).getName(), silverList.get(i).getStatus().
                    toString());
              }
        System.out.format("%n%s%n%-4s%-7s%-7s%-7s%s%n", "Gold Customers:",
          "Num", "Line 1", "Line 2", "Line 3", "Status");
              System.out.println("-------------------------------");
              for (int i = 0; i < goldList.size(); i++)
              {
                  System.out.format("%-4d%-7s%-7s%-7s%s%n", i+1, goldList.
                    get(i).getLines().get(0).getName(), goldList.get(i).
                    getLines().get(1).getName(), goldList.get(i).
                    getLines().get(2).getName(), goldList.get(i).getStatus()
                    .toString());
              }
    }
    
    /**
     * Adds the input <code>Person</code> to a randomly selected ride
     * 
     * @param p
     * The input <code>Person</code> that will be added to a ride
     * 
     * <dt>Postcondition:
     *    <dd>The input <code>Person</code> is added to a ride selected
     *    randomly according to the probabilities in <code>probabilities</code>
     *    . If a there are still space available on any of the four rides, then
     *    the <code>Person</code> will be added to a random ride until they are
     *    on a ride. If the total amount of seats available on rides have
     *    changed after the <code>Person</code> was added, then
     *    <code>rideSeatsAvailable</code> is decremented.
     */
    public static void addPersonToRide(Person p)
    {
        Ride[] rides = {BSOD, KK, ToT, GF};
        // Selects a ride randomly based on the probabilities array
        Ride selection = RandomGenerator.selectRide(rides, probabilities);
        // Records the current amount of people on the ride
        int rideCount = selection.getNumPeopleOnRide();
        
        /*
         * Adds the person to the ride and decrements rideSeatsAvailable if the
         * total amount of people on the ride changed. If it didn't change,
         * then check to see if there are seats available on a different ride.
         * If there are, then remove the person from the line they were just
         * added to and randomly assign them to a new ride until they end up
         * on a ride.
         */
        selection.addToRide(p);
        if (selection.getNumPeopleOnRide() != rideCount)
            rideSeatsAvailable--;
        else if (rideSeatsAvailable > 0)
        {
            while (selection.getNumPeopleOnRide() == rideCount)
            {
                if (selection.getHoldingQueue().remove(p))
                {
                    p.removeLine(selection.getHoldingQueue());
                    selection.getHoldingQueue().decrementSize();
                }
                else if (selection.getVirtualLine().remove(p))
                {
                    p.removeLine(selection.getVirtualLine());
                    selection.getVirtualLine().decrementSize();
                }
                p.setStatus(Status.Available);
                selection = RandomGenerator.selectRide(rides, probabilities);
                rideCount = selection.getNumPeopleOnRide();
                selection.addToRide(p);
                if (selection.getNumPeopleOnRide() != rideCount)
                    rideSeatsAvailable--;
            }
        }
    }

    /**
     * Adds every gold person to a ride
     */
    public static void addGoldToRide()
    {
        Person p;
        for (int i = 0; i < goldList.size(); i++)
        {
            p = goldList.get(i);
            addPersonToRide(p);
        }
    }
    
    /**
     * Adds every silver person to a ride
     */
    public static void addSilverToRide()
    {
        Person p;
        for (int i = 0; i < silverList.size(); i++)
        {
            p = silverList.get(i);
            addPersonToRide(p);
        }
    }
    
    /**
     * Adds every regular person to a ride
     */
    public static void addRegToRide()
    {
        Person p;
        for (int i = 0; i < regList.size(); i++)
        {
            p = regList.get(i);
            addPersonToRide(p);
        }
    }

    /**
     * First adds all the gold people to a ride, then adds all the silver
     * people to a ride, and then adds all the regular people to a ride. All
     * the gold people are then added to a second ride and so are the silver
     * people. All the gold people are then added to another.
     */
    private static void addPeopleToRides() 
    {
        addGoldToRide();
        addSilverToRide();
        addRegToRide();
        addGoldToRide();
        addSilverToRide();
        addGoldToRide();
    }

    /**
     * Creates and adds the specified amount of gold, silver, and regular
     * people to their respective lists.
     */
    private static void populatePeopleList()
    {
        int temp = PEOPLECOUNT;
        while (temp > 0)
        {
            for(int i = 1; i <= GOLDCOUNT; i++)
            {
                Person p = new Person(i, Pass.Gold);
                goldList.add(p);
                temp--;
            }
            for(int i = 1; i <= SILVERCOUNT; i++)
            {
                Person p = new Person(i, Pass.Silver);
                silverList.add(p);
                temp--;
            }
            for(int i = 1; i <= REGCOUNT; i++)
            {
                Person p = new Person(i, Pass.Regular);
                regList.add(p);
                temp--;
            }
        }
    }

    /**
     * Initializes all four rides with the specified input.
     */
    private static void makeRides()
    {
        BSOD = new Ride("Blue Scream of Death", "BSOD");
        BSOD.setDuration(BSOD_DURATION);
        BSOD.setTimeLeft(BSOD_DURATION);
        BSOD.setRideCapacity(BSOD_CAPACITY);
        BSOD.getHoldingQueue().setMaxSize(BSOD_HSIZE);
        BSOD.getPeopleRiding().setMaxSize(BSOD_CAPACITY);
        
        KK = new Ride("Kingda Knuth", "KK");
        KK.setDuration(KK_DURATION);
        KK.setTimeLeft(KK_DURATION);
        KK.setRideCapacity(KK_CAPACITY);
        KK.getHoldingQueue().setMaxSize(KK_HSIZE);
        KK.getPeopleRiding().setMaxSize(KK_CAPACITY);
        
        ToT = new Ride("i386 Tower of Terror", "ToT");
        ToT.setDuration(ToT_DURATION);
        ToT.setTimeLeft(ToT_DURATION);
        ToT.setRideCapacity(ToT_CAPACITY);
        ToT.getHoldingQueue().setMaxSize(ToT_HSIZE);
        ToT.getPeopleRiding().setMaxSize(ToT_CAPACITY);
        
        GF = new Ride("GeForce", "GF");
        GF.setDuration(GF_DURATION);
        GF.setTimeLeft(GF_DURATION);
        GF.setRideCapacity(GF_CAPACITY);
        GF.getHoldingQueue().setMaxSize(GF_HSIZE);
        GF.getPeopleRiding().setMaxSize(GF_CAPACITY);
        
        rideSeatsAvailable = BSOD_CAPACITY + KK_CAPACITY + ToT_CAPACITY +
          GF_CAPACITY;
        
        rides[0] = BSOD;
        rides[1] = KK;
        rides[2] = ToT;
        rides[3] = GF;
    }

    /**
     * Prompts the user for the input necessary to initiate the people, rides,
     * and the probabilities array.
     * 
     * <dt>Postcondition:
     *    <dd>If any of the input were out of range, then
     *    <code>InputMismatchException</code> is thrown. If all of the input
     *    were in the valid range, then <code>inputReceived</code> is set to
     *    true.
     * 
     * @throws InputMismatchException
     * Indicates that the input was in the invalid range
     */
    private static void getInput() throws InputMismatchException
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.format("%s%n%s", "Welcome to Seven Flags!", "Please enter "
          + "the number of regular customers: ");
        REGCOUNT = scanner.nextInt();
        
        System.out.println("Please enter the number of silver customers: ");
        SILVERCOUNT = scanner.nextInt();
        
        System.out.println("Please enter the number of gold customers: ");
        GOLDCOUNT = scanner.nextInt();
        PEOPLECOUNT = REGCOUNT + SILVERCOUNT + GOLDCOUNT;
        
        if(GOLDCOUNT <= 0 || SILVERCOUNT <= 0 || REGCOUNT <= 0) 
            throw new InputMismatchException("Invalid people count, simulation"
              + " needs at least 1 of each type of person");
        
        
        System.out.println("Please enter the probability of the first ride out"
          + " of 100:");
        int zero = scanner.nextInt();
        probabilities[0] = zero / 100.0;
        if (!(probabilities[0] >= 0 && probabilities[0] <= 1))
            throw new InputMismatchException("Probabilities need to be with"
              + "in 0 to 100");
        
        System.out.println("Please enter the probability of the second ride"
          + " out of 100:");
        int one = scanner.nextInt();
        probabilities[1] = one / 100.0;
        if (!(probabilities[0] >= 0 && probabilities[1] <= 1))
            throw new InputMismatchException("Probabilities need to be within"
              + " 0 to 100");
        
        System.out.println("Please enter the probability of the third ride out"
          + " of 100:");
        int two = scanner.nextInt();
        probabilities[2] = two / 100.0;
        if (!(probabilities[0] >= 0 && probabilities[2] <= 1))
            throw new InputMismatchException("Probabilities need to be within"
              + " 0 to 100");
        
        System.out.println("Please enter the probability of the fourth ride"
          + " out of 100: ");
        int three = scanner.nextInt();
        probabilities[3] = three / 100.0;
        if (!(probabilities[0] >= 0 && probabilities[3] <= 1))
            throw new InputMismatchException("Probabilities need to be within"
              + " 0 to 100");
        
        int probability = zero + one + two + three;
        if (probability != 100)
            throw new InputMismatchException("Total probability must add up to"
              + " 100");
        
        System.out.println("Please enter the duration of Blue Scream of Death:"
          + " (minutes): ");
        BSOD_DURATION = scanner.nextInt();
        System.out.println("Please enter the capacity of Blue Scream of Death:"
          );
        BSOD_CAPACITY = scanner.nextInt();
        System.out.println("Please enter the holding queue size for Blue Screa"
          + "m of Death: ");
        BSOD_HSIZE = scanner.nextInt();
        
        if (BSOD_DURATION <= 0 || BSOD_CAPACITY <= 0 || BSOD_HSIZE <= 0 )
            throw new InputMismatchException("Invalid input, ride properties"
              + " need to be at least 1");
        
        System.out.println("Please enter the duration of Kingda Knuth:"
          + " (minutes): ");
        KK_DURATION = scanner.nextInt();
        System.out.println("Please enter the capacity of Kingda Knuth:");
        KK_CAPACITY = scanner.nextInt();
        System.out.println("Please enter the holding queue size for Kingda "
          + "Knuth:");
        KK_HSIZE = scanner.nextInt();        
        
        if (KK_DURATION <= 0 || KK_CAPACITY <= 0 || KK_HSIZE <= 0 )
            throw new InputMismatchException("Invalid input, ride properties"
              + " need to be at least 1");
        
        System.out.println("Please enter the duration of i386 Tower of Terror:"
          + " (minutes): ");
        ToT_DURATION = scanner.nextInt();
        System.out.println("Please enter the capacity of i386 Tower of "
          + "Terror:");
        ToT_CAPACITY = scanner.nextInt();
        System.out.println("Please enter the holding queue size for i386 Tower"
          + " of Terror:");
        ToT_HSIZE = scanner.nextInt();
        
        if (ToT_DURATION <= 0 || ToT_CAPACITY <= 0 || ToT_HSIZE <= 0 )
            throw new InputMismatchException("Invalid input, ride properties"
              + " need to be at least 1");
        
        System.out.println("Please enter the duration of GeForce:"
                + " (minutes): ");
        GF_DURATION = scanner.nextInt();
        System.out.println("Please enter the capacity of GeForce:");
        GF_CAPACITY = scanner.nextInt();
        System.out.println("Please enter the holding queue size for GeForce:");
        GF_HSIZE = scanner.nextInt();
        
        if (GF_DURATION <= 0 || GF_CAPACITY <= 0 || GF_HSIZE <= 0 )
            throw new InputMismatchException("Invalid input, ride properties"
              + " need to be at least 1");
        
        inputReceived = true;
    }
}
