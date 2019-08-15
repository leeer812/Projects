import java.util.LinkedList;

/**
 * The <code>VirtualLine</code> class extends, and inherits all of the methods
 * of, the <code>LinkedList</code> class. The amount of people in the line is
 * tracked with its <code>size</code>. If this list belongs to a ride, then the
 * ride's name is stored within <code>name</code>. People can be enqueued and
 * dequeued into the line. A specific person can be also be removed if the
 * person is present in the line. Each of its fields have their own respective
 * getters and setters for manipulation. The VirtualLine class also has its own
 * <code>toString</code> method that returns a string representation of every
 * person in the line.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #4
 * CSE214-R02
 * TA: David S. Li
 *
 */
public class VirtualLine extends LinkedList<Person>
{
    // The current size of the VirtualLine
    protected int size = 0;
    // The name of the ride that this VirtualLine belongs to
    protected String name = "";
    
    /**
     * Returns an instance of the <code>VirtualLine</code> object with its
     * <code>name</code> set to the input <code>n</code>
     * 
     * @param n
     * The input <code>n</code> that <code>name</code> will be set to
     */
    public VirtualLine(String n)
    {
        name = n;
    }
    
    /**
     * Removes the specified <code>Person</code> from the line if it is present
     * in the line. 
     * 
     * <dt>Precondition:
     *    <dd>The input <code>Person</code> must be present inside the line
     * 
     * @param p
     * The input <code>Person</code> that will be searched for and removed if
     * present in the line
     * 
     * <dt>Postcondition:
     *    <dd>If the input <code>Person</code> was present in the line, then
     *    the <code>Person</code> is removed from the line and the size of the
     *    line is decremented. If the input <code>Person</code> was not found,
     *    then the line is unchanged.
     */
    public void removePerson(Person p)
    {
        Person tempPerson = p;
        int counter = this.size();
        
        while (counter > 0)
        {
            if (this.peek() == tempPerson)
            {
                this.dequeue();
                counter--;
            }
            else
                this.enqueue(this.dequeue());
            counter--;
        }
    }
    
    /**
     * Adds the input <code>Person</code> to the end of the line and increments
     * the size by 1
     * 
     * @param p
     * The input <code>Person</code> that will be added to the end of the line.
     */
    public void enqueue(Person p)
    {
        this.add(p);
        size++;
    }

    /**
     * Removes and returns the first <code>Person</code> on the line. If a
     * person was removed successfully, then the size is decremented by 1
     * 
     * <dt>Precondition:
     *    <dd>The size of the line must be greater than 0, indicating that
     *    there is at least 1 person in the line to remove.
     *    
     * <dt>Postcondition:
     *    <dd>If there was at least 1 person in the line, then the first person
     *    on the line is removed and returned. The size of the line is
     *    decremented by 1 if the person is removed successfully. If there was
     *    no one in the line, then the line remains unchanged and null is
     *    returned.
     *    
     * @return
     * Returns the first person on the line if <code>size</code> is greater
     * than 0. Otherwise null is returned.
     * 
     */
    public Person dequeue()
    {
        if (size > 0)
        {
            size--;
            return this.remove();
        }
        else
            return null;
    }
    
    /**
     * Returns this line's <code>name</code>
     * 
     * @return
     * Returns <code>name</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Sets <code>name</code> to the input <code>name</code>
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the first <code>Person</code> on the line. If there is no person
     * on the line, then null is returned.
     * 
     * @return
     * Returns the first <code>Person</code> on the line. Null is returned if
     * there are no people on the line.
     */
    public Person peek()
    {
        return (Person)this.peekFirst();
    }
    
    /**
     * Returns a <code>String</code> representation of all the people on this
     * line
     * 
     * @return
     * Returns a <code>String</code> representation of all the people on this
     * line
     */
    public String toString()
    {
        VirtualLine tempLine = new VirtualLine(this.name);
        String tempString = "";
        while (!isEmpty())
        {
            Person tempPerson = this.dequeue();
            tempLine.enqueue(tempPerson);
            tempString += tempPerson.toString();
        }
        while (!tempLine.isEmpty())
        {
            this.enqueue(tempLine.dequeue());
        }
        return tempString;
    }
    
    /**
     * Returns the <code>size</code> of this line
     * 
     * @return
     * Returns <code>size</code>
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets <code>size</code> to the input <code>s</code>
     * 
     * @param s
     * The input <code>s</code> that <code>size</code> will be set to
     */
    public void setSize(int s) {
        this.size = s;
    }
    
    /**
     * Increments <code>size</code> by 1
     */
    public void incrementSize()
    {
        size++;
    }
    
    /**
     * Decrements <code>size</code> by 1
     */
    public void decrementSize()
    {
        size--;
    }

}
