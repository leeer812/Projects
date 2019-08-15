import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The <code>Ride</code> class generates an instance of a <code>Ride</code>
 * object with a run duration, the amount of time it has left to run, the
 * amount of people on the ride, the maximum amount of people on the ride,
 * its name and abbreviated name, a <code>VirtualLine</code>, a
 * <code>HoldingQueue</code>, and a list of people currently on the ride.
 * People can be added to the ride and dequeued from the ride and its lines.
 * The <code>Ride</code> class imports <code>ArrayList</code>.
 * @author goreg
 *
 */
public class Ride
{
    // The amount of time that the ride runs for
    private int duration;
    // The amount of time the ride has left to run for
    private int timeLeft;
    // The current amount of people on the ride
    private int numPeopleOnRide;
    // The maximum number of people allowed on the ride
    private int RIDECAPACITY;
    // The total amount of complete runs the ride has completed
    private int runAmount;
    // The name of the ride
    private String name;
    // The abbreviated version of the ride's name
    private String shortName;
    private VirtualLine virtualLine;
    // The list of people currently on the ride
    private HoldingQueue peopleRiding;
    private HoldingQueue holdingQueue;
    private List<Person> peopleOnRide;
    
    /**
     * Returns an instance of <code>Ride</code> with its name set to the input
     * <code>n</code> and its abbreviated name set to <code>sN</code>. The
     * default amount of people on the ride is set to 0. Its virtual line,
     * holding queue, <code>peopleRiding</code> and <code>peopleOnRide</code>
     * are all instantiated.
     * 
     * @param n
     * The input <code>n</code> that <code>name</code> will be set to
     * 
     * @param sN
     * The input <code>sN</code> that <code>shortName</code> will be set to
     */
    public Ride(String n, String sN)
    {
        name = n;
        shortName = sN;
        numPeopleOnRide = 0;
        virtualLine = new VirtualLine(shortName);
        holdingQueue = new HoldingQueue(shortName);
        peopleRiding = new HoldingQueue(shortName);
        peopleOnRide = new ArrayList<Person>();
    }

    /**
     * Returns the amount of time that each run of this <code>Ride</code> takes
     * 
     * @return
     * Returns <code>duration</code>
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the <code>duration</code> of this <code>Ride</code> to the input
     * <code>duration</code>
     * 
     * @param duration
     * The input <code>duration</code> that <code>duration</code> will be set to
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns the amount of time left on this current run of the
     * <code>Ride</code>
     * 
     * @return
     * Returns <code>timeLeft</code>
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Sets the <code>timeLeft</code> of this <code>Ride</code> to the input
     * <code>timeLeft</code>
     * 
     * @param timeLeft
     * The input <code>timeLeft</code> that <code>timeLeft</code> will be set
     * to
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Returns the name of this <code>Ride</code>
     * 
     * @return
     * Returns <code>name</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the <code>name</code> of this <code>Ride</code> to the input
     * <code>name</code>
     * @param name
     * The input <code>name</code> that <code>name</code> will be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the <code>virtualLine</code> of this <code>Ride</code>
     * 
     * @return
     * Returns the <code>virtualLine</code> of this <code>Ride</code>
     */
    public VirtualLine getVirtualLine() {
        return virtualLine;
    }

    /**
     * Sets this <code>Ride</code>'s <code>virtualLine</code> to the input
     * <code>virtualLine</code>
     * 
     * @param virtualLine
     * The input <code>virtualLine</code> that <code>virtualLine</code> will
     * be set to
     */
    public void setVirtualLine(VirtualLine virtualLine) {
        this.virtualLine = virtualLine;
    }
    
    /**
     * Enqueues the input <code>Person</code> into <code>virtualLine</code>
     * 
     * @param p
     * The input <code>Person</code> that will be enqueued into
     * <code>virtualLine</code>
     */
    public void enqueueVirtualLine(Person p)
    {
        virtualLine.enqueue(p);
    }
    
    /**
     * Dequeues <code>virtualLine</code>
     * 
     * @return
     * Returns the first <code>Person</code> in <code>virtualLine</code>. If
     * there is no people in <code>virtualLine</code>, then null is returned
     */
    public Person dequeueVirtualLine()
    {
        return virtualLine.dequeue();
    }

    /**
     * Returns this <code>Ride</code>'s <code>holdingQueue</code>
     * 
     * @return
     * Returns this <code>Ride</code>'s <code>holdingQueue</code>
     */
    public HoldingQueue getHoldingQueue() {
        return holdingQueue;
    }

    /**
     * 
     * @param holdingQueue
     */
    public void setHoldingQueue(HoldingQueue holdingQueue) {
        this.holdingQueue = holdingQueue;
    }
    
    public void enqueueHoldingQueue(Person p)
    {
        holdingQueue.enqueue(p);
    }
    
    public Person dequeueHoldingQueue()
    {
        return holdingQueue.dequeue();
    }

    public List<Person> getPeopleOnRide() {
        return peopleOnRide;
    }

    public void setPeopleOnRide(List<Person> peopleOnRide) {
        this.peopleOnRide = peopleOnRide;
    }

    public int getRideCapacity()
    {
        return RIDECAPACITY;
    }

    public void setRideCapacity(int rideCapacity) {
        this.RIDECAPACITY = rideCapacity;
    }
    
    public String toString()
    {
        String temp = "";
        temp += String.format("%s%d%s%n%s%n%s%n%s%n", name + " - Time rema"
          + "ining: ", timeLeft, " min", "On Ride: " +
          peopleRiding.toString(), "Holding Queue: " + holdingQueue.toString(),
          "Virtual Queue: " + virtualLine.toString());
        return temp;
    }
    
    public void addPersonToHoldingQueue(Person p)
    {
        p.setStatus(Status.Holding);
        p.addLine(holdingQueue);
        holdingQueue.enqueue(p);
    }
    
    public Person removePersonFromHoldingQueue()
    {
        Person p = holdingQueue.dequeue();
        p.setStatus(Status.Available);
        p.removeLine(holdingQueue);
        return p;
    }
    
    public void advanceLines()
    {
        /* While there is space on the ride and there are people on HQUEUE
         * we want to take people off HQUEUE and put them onto the ride. While
         * there is space in HQUEUE, we want to put people on VLINE into HQUEUE
         */
        int vLineCount = virtualLine.getSize();
        
        while (numPeopleOnRide < RIDECAPACITY && (holdingQueue.getSize() + vLineCount > 0))
        {
            // Take person off HQUEUE and add onto ride
            if (holdingQueue.getSize() > 0)
            {
                Person p = removePersonFromHoldingQueue();
                p.setStatus(Status.OnRide);
                peopleRiding.enqueue(p);
                p.addLine(peopleRiding);
                numPeopleOnRide++;
            }
            // If there are no people on HQUEUE, place people on VLINE on ride
            else if (vLineCount > 0)
            {
                if (virtualLine.getFirst().getStatus() == Status.Available)
                {
                    Person p = virtualLine.dequeue();
                    p.removeLine(virtualLine);
                    p.addLine(peopleRiding);
                    p.setStatus(Status.OnRide);
                    peopleRiding.enqueue(p);
                    numPeopleOnRide++;
                }
                else
                {
                    virtualLine.enqueue(virtualLine.dequeue());
                }
                vLineCount--;
            }
        }
        while (holdingQueue.getSize() < holdingQueue.getMaxSize() && (vLineCount > 0))
        {
            if (virtualLine.getFirst().getStatus() == Status.Available)
            {
                Person p = virtualLine.dequeue();
                p.removeLine(virtualLine);
                addPersonToHoldingQueue(p);
            }
            else
                virtualLine.enqueue(virtualLine.dequeue());
            vLineCount--;
        }
    }
    
    public void addToRide(Person p)
    {
        Status stat = p.getStatus();
        if (p.getNumLines() >= p.getMaxLines())
            return;
        
        if (stat == Status.Available || stat == Status.Holding)
        {
            if (numPeopleOnRide < RIDECAPACITY)
            {
                peopleRiding.enqueue(p);
                numPeopleOnRide++;
                p.setStatus(Status.OnRide);
                p.addLine(peopleRiding);
                if (virtualLine.contains(p))
                {
                    p.removeLine(virtualLine);
                    virtualLine.removePerson(p);
                }
                if (holdingQueue.contains(p))
                {
                    p.removeLine(holdingQueue);
                    holdingQueue.remove(p);
                    holdingQueue.decrementSize();
                }
            }
            else if (stat == Status.Available)
            {
                if (holdingQueue.getSize() < holdingQueue.getMaxSize())
                {
                    holdingQueue.enqueue(p);
                    p.setStatus(Status.Holding);
                    p.addLine(holdingQueue);
                    if (virtualLine.contains(p))
                    {
                        p.removeLine(virtualLine);
                        virtualLine.removePerson(p);
                    }
                }
                else
                {
                    p.addLine(virtualLine);
                    virtualLine.enqueue(p);
                }
            }
            else
            {
                p.addLine(virtualLine);
                virtualLine.enqueue(p);
            }
        }
        else
        {
            p.addLine(virtualLine);
            virtualLine.enqueue(p);
        }
    }
    
    public Stack<Person> dequeueFromRide()
    {
        Stack<Person> stack = new Stack<Person>();
        Person p;
        for (int i = 0; i < numPeopleOnRide; i++)
        {
            p = peopleRiding.dequeue();
            p.removeLine(peopleRiding);
            numPeopleOnRide--;
            p.setStatus(Status.Available);
            p.incrementRunAmount();
            stack.push(p);
        }
        return stack;
    }
    
    public int getNumPeopleOnRide() {
        return numPeopleOnRide;
    }

    public void setNumPeopleOnRide(int numPeopleOnRide) {
        this.numPeopleOnRide = numPeopleOnRide;
    }

    public int getRunAmount() {
        return runAmount;
    }

    public void setRunAmount(int runAmount) {
        this.runAmount = runAmount;
    }
    
    public void incrementRunAmount()
    {
        runAmount++;
    }
    
    public void decrementTimeLeft()
    {
        timeLeft--;
    }
    
    public void incrementTimeLeft()
    {
        timeLeft++;
    }
    
    public void resetTimeLeft()
    {
        timeLeft = duration;
    }

    public HoldingQueue getPeopleRiding() {
        return peopleRiding;
    }

    public void setPeopleRiding(HoldingQueue peopleRiding) {
        this.peopleRiding = peopleRiding;
    }
}
