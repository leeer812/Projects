import java.util.ArrayList;
import java.util.List;
/**
 * The <code>Person</code> class initiates the <code>Person</code> object with
 * a <code>number</code> as its name, a <code>pass</code> to denote its
 * <code>maxLines</code>, its current <code>numLines</code>, the amount of
 * rides it has gone through with <code>runAmount</code>, a list of the lines
 * the person is on with <code>lines</code>, and its current
 * <code>status</code>. Methods are provided to manipulate each of these fields
 * with getters and setters. A person can be added to multiple lines, but it
 * can only be added if its <code>numLines</code> is less than its
 * <code>maxLines</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #4
 * CSE214-R02
 * TA: David S. Li
 */
public class Person
{
    // Indicates the person's number for its name
    private int number;
    // Indicates how many lines the person can be on based on its pass
    private int maxLines;
    // Indicates how many lines the person is currently on
    private int numLines;
    // Indicates how rides this person has been on
    private int runAmount;
    // A list of each line that the person is currently on
    private List<VirtualLine> lines;
    // Indicates the current status of the person
    private Status status;
    // Indicates this person's pass
    private Pass pass;
    
    /**
     * Adds the input <code>line</code> into <code>lines</code> and
     * increments this <code>Person</code>'s <code>numLines</code> by 1
     * 
     * @param line
     * Indicates the input <code>line</code> that will be input into this
     * <code>Person</code>'s <code>lines</code>
     * 
     * <dt>Postcondition:
     *    <dd><code>line</code> is successfully added into <code>lines</code>
     *    in the last position of the <code>ArrayList</code> and
     *    <code>numLines</code> is incremented by 1
     */
    public void addLine(VirtualLine line)
    {
        lines.add(line);
        numLines++;
    }
    
    /**
     * Removes the target <code>targetLine</code> from <code>lines</code> if
     * the targetLine is present in the list of lines.
     * 
     * @param targetLine
     * The <code>VirtualLine</code> to be removed from <code>lines</code>
     * 
     * <dt>Precondition:
     *    <dd>The <code>targetLine</code> must be within <code>lines</code>,
     *    otherwise <code>lines</code> is unchanged and null is returned
     * 
     * @return
     * Returns <code>targetLine</code> if it was present in <code>lines</code>,
     * otherwise returns null
     * 
     * <dt>Postcondition:
     *    <dd><code>targetLine</code> is returned if it was present in lines
     *    and <code>numLines</code> is decremented by 1. If it was not present,
     *    then null is returned.
     */
    public VirtualLine removeLine(VirtualLine targetLine)
    {
        for (int i = 0; i < numLines; i++)
        {
            if (lines.get(i) == targetLine)
            {
                numLines--;
                return lines.remove(i);
            }
        }
        return null;
    }
    
    /**
     * Finds the target <code>targetLine</code> from <code>lines</code> if
     * the targetLine is present in the list of lines.
     * 
     * @param targetLine
     * The <code>VirtualLine</code> to be found from <code>lines</code>
     * 
     * <dt>Precondition:
     *    <dd>The <code>targetLine</code> must be within <code>lines</code>,
     *    otherwise <code>lines</code> is unchanged and null is returned
     * 
     * @return
     * Returns <code>targetLine</code> if it was present in <code>lines</code>,
     * otherwise returns null
     * 
     * <dt>Postcondition:
     *    <dd><code>targetLine</code> is returned if it was present in lines
     *    If it was not present, then null is returned.
     */
    public VirtualLine findLine(VirtualLine targetLine)
    {
        for (int i = 0; i < numLines; i++)
        {
            if (lines.get(i) == targetLine)
                return lines.get(i);
        }
        return null;
    }
    
    /**
     * Returns an instance of the <code>Person</code> object with the
     * <code>input</code> number as this person's <code>number</code>.
     * <code>lines</code> is initialized as a new ArrayList and
     * <code>status</code> is initialized as <code>Available</code>.
     * 
     * @param number
     * The input <code>number</code> that this person's number will be set to
     * 
     * <dt>Precondition:
     *    <dd>The input <code>number</code> must be within the valid range of
     *    at least 1
     *    
     * <dt>Postcondition:
     *    <dd>A <code>Person</code> object is returned with its number, status,
     *    and lines initialized if the input <code>number</code> was within
     *    the valid range. Otherwise, the <code>IllegalArgumentException</code>
     *    is thrown.
     * 
     * @throws IllegalArgumentException
     * Indicates that the input <code>number</code> was invalid
     */
    public Person(int number) throws IllegalArgumentException
    {
        if (number > 0)
        {
            this.number = number;
            lines = new ArrayList<VirtualLine>();
            status = Status.Available;
        }
        else
            throw new IllegalArgumentException("The name parameter is invalid");
    }
    
    /**
     * Returns an instance of the <code>Person</code> object with the input
     * <code>number</code> and its number and <code>p</code> as its pass.
     * 
     * @param number
     * The input <code>number</code> that this <code>Person</code>'s number is
     * set to
     * 
     * @param p
     * The input <code>p</code> that this <code>Person</code>'s pass is set to
     * 
     * <dt>Precondition:
     *    <dd>The input <code>number</code> must be at least 1
     *    
     * <dt>Postcondition:
     *    <dd>The <code>Person</code> object is initialized with the input pass
     *    and number and has its status set to <code>Available</code>. If the
     *    pass was <code>Gold</code> then its <code>maxLines</code> will be set
     *    to 3. If the pass was <code>Silver</code> or <code>Regular</code>
     *    then its <code>maxLines</code> will be set to 2 or 1, respectively.
     *    If the input <code>number</code> was out of range, then an
     *    <code>IllegalArgumentException</code> is thrown.
     *    
     * @throws IllegalArgumentException
     * Indicates that the input <code>number</code> was out of range
     */
    public Person (int number, Pass p) throws IllegalArgumentException
    {
        if (number > 0)
        {
            this.number = number;
            this.pass = p;
            status = Status.Available;
            switch (pass)
            {
            case Gold:
                maxLines = 3;
                break;
            case Silver:
                maxLines = 2;
                break;
            case Regular:
                maxLines = 1;
                break;
            }
            lines = new ArrayList<VirtualLine>();
        }
        else
            throw new IllegalArgumentException("Name is invalid");
    }
    
    /**
     * Increases <code>runAmount</code> by 1
     */
    public void incrementRunAmount()
    {
        runAmount++;
    }

    /**
     * Returns this <code>Person</code>'s number
     * 
     * @return
     * Returns this <code>Person</code>'s <code>number</code>
     */
    public int getNumber() 
    {
        return number;
    }

    /**
     * Sets <code>number</code> to the input <code>number</code>
     * 
     * @param number
     * The input <code>number</code> that <code>number</code> will be set to
     */
    public void setNumber(int number)
    {
        this.number = number;
    }

    /**
     * Returns this <code>Person</code>'s <code>maxLines</code>
     * @return
     * Returns <code>maxLines</code>
     */
    public int getMaxLines() 
    {
        return maxLines;
    }

    /**
     * Sets <code>maxLines</code> to the input
     * 
     * @param maxLines
     * The input <code>maxLines</code> that this <code>Person</code>'s
     * <code>maxLines</code> will be set to
     */
    public void setMaxLines(int maxLines) 
    {
        this.maxLines = maxLines;
    }

    /**
     * Returns the ArrayList <code>lines</code> containing all of the lines
     * that this <code>Person</code> is currently in
     * 
     * @return
     * Returns <code>lines</code>
     */
    public List<VirtualLine> getLines() 
    {
        return lines;
    }

    /**
     * Sets <code>lines</code> to the input <code>lines</code>
     * 
     * @param lines
     * The input <code>lines</code> that <code>lines</code> will be set to
     */
    public void setLines(List<VirtualLine> lines)
    {
        this.lines = lines;
    }

    /**
     * Returns the current <code>status</code> of this <code>Person</code>
     * 
     * @return
     * Returns <code>status</code>
     */
    public Status getStatus() 
    {
        return status;
    }

    /**
     * Sets <code>status</code> to he input <code>status</code>
     * 
     * @param status
     * The input <code>status</code> that <code>status</code> will be set to
     */
    public void setStatus(Status status) 
    {
        this.status = status;
    }
    
    /**
     * Returns this <code>Person</code>'s <code>pass</code>
     * 
     * @return
     * Returns <code>pass</code>
     */
    public Pass getPass() {
        return pass;
    }

    /**
     * Sets <code>pass</code> to the input <code>pass</code>
     * 
     * @param pass
     * The input <code>pass</code> that <code>pass</code> will be set to
     */
    public void setPass(Pass pass) {
        this.pass = pass;
    }

    /**
     * Returns a <code>String</code> representation of this <code>Person</code>
     * object containing its <code>pass</code> and <code>number</code>
     * 
     * @return
     * Returns a <code>String</code> with this <code>Person</code>'s pass and
     * number
     */
    public String toString()
    {
        String passName = "";
        switch (pass)
        {
        case Regular:
            passName = "Regular";
            break;
        case Silver:
            passName = "Silver";
            break;
        case Gold:
            passName = "Gold";
            break;
        }
        return String.format("%s%d%s", passName + " ", number, " ");
    }

    /**
     * Returns the number of lines this <code>Person</code> is in. This is also
     * depicted in the amount of lines in <code>lines</code>
     * 
     * @return
     * Returns <code>numLines</code>
     */
    public int getNumLines() {
        return numLines;
    }

    /**
     * Sets this <code>Person</code>'s <code>numLines</code> to the input
     * <code>numLines</code>
     * 
     * @param numLines
     * The input <code>numLines</code> that <code>numLines</code> will be set
     * to
     */
    public void setNumLines(int numLines) {
        this.numLines = numLines;
    }
    
    /**
     * Decrements <code>numLines</code> by 1
     */
    public void decrementNumLines() {
        numLines--;
    }
    
    /**
     * Increments <code>numLines</code> by 1
     */
    public void incrementNumLines() {
        numLines++;
    }

    /**
     * Returns how many times this Person has finished a ride
     * 
     * @return
     * Returns <code>runAmount</code>
     */
    public int getRunAmount() {
        return runAmount;
    }

    /**
     * Sets this <code>Person</code>'s <code>runAmount</code> to the input
     * <code>runAmount</code>
     * 
     * @param runAmount
     * The input <code>runAmount</code> that <code>runAmount</code> will be set
     * to
     */
    public void setRunAmount(int runAmount) {
        this.runAmount = runAmount;
    }
}
