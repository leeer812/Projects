/**
 * The <code>CarListNode</code> class initiates a CarListNode object to wrap 
 * the given <code>Car</code> input object which can be placed in a
 * linked list data structure. The node holds the <code>Car</code> object's
 * data and has the capacity to reference a <code>CarListNode</code> behind
 * and in front of it which serve as links to the previous and next nodes in
 * the list. Getter and Setter methods are provided for the input Car object's
 * make and owner name.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */

public class CarListNode
{
    private Car data; // The input car object
    private CarListNode next; // Serves as a link to the next node in the list
    private CarListNode prev; // Link to the previous node in the list
    
    /**
     * Returns an instance of the <code>CarListNode</code> object which 
     * contains the data of a <code>Car</code> object and is linked to
     * a <code>next</code> and <code>prev</code> node which are initially
     * initialized as null before being placed in the list.
     * 
     * <dt>Precondition:
     *    <dd>The input car <code>initData</code> must not be null.
     *    
     * @param initData
     *    The input <code>Car</code> object that data will refer to
     *    
     * <dt>Postcondition:
     *    <dd>The <code>data</code> object's <code>make</code> and
     *    <code>owner</code> are set to the <code>initData</code> object's
     *    make and owner name. The <code>next</code> and <code>prev</code>
     *    nodes are initialized as null so that they can be referenced when
     *    this node is being added into the list.
     *    
     * @throws IllegalArgumentException
     *    Indicates that the user is trying to add a car that does not exist.
     */
    public CarListNode(Car initData) throws IllegalArgumentException
    {
        if (initData != null)
        {
            setData(initData);
            next = null;
            prev = null;
        }
        else
            throw new IllegalArgumentException("There is no car being added.");
    }
    
    /**
     * Returns the <code>make</code> of the <code>Car</code> stored within
     * <code>data</code>
     * 
     * @return
     *    Returns the make of this node's <code>Car</code>
     */
    public Make getMake()
    {
        return data.getMake();
    }
    
    /**
     * Sets the <code>make</code> of the <code>Car</code> stored within
     * <code>data</code> to the input <code>makeName</code>
     * 
     * @param makeName
     *    The make that this node's <code>Car</code> is set to
     *    
     * <dt>Postcondition:
     *    <dd>This node's <code>Car</code> object's <code>make</code> is set to
     *    the input <code>makeName</code>.
     */
    public void setMake(Make makeName)
    {
        data.setMake(makeName);
    }
    
/**
 * Returns the <code>owner</code> of the <code>Car</code> stored within
 * <code>data</code>
 * 
 * @return
 *    Returns the owner name of this node's <code>Car</code>
 */
    public String getOwner()
    {
        return data.getOwner();
    }
    
    /**
     * Sets the <code>owner</code> of the <code>Car</code> stored within
     * <code>data</code> to the input <code>makeName</code>
     * 
     * @param ownerName
     *    The owner name that this node's <code>Car</code> is set to
     *    
     * <dt>Postcondition:
     *    <dd>This node's <code>Car</code> object's <code>owner</code> is set
     *    to the input <code>ownerName</code>.
     */
    public void setOwner(String ownerName)
    {
        data.setOwner(ownerName);
    }
    
    /**
     * Returns the reference to the Car wrapped by this <code>CarListNode</code>
     * 
     * @return
     *    <dd>Returns the <code>Car</code> reference in the <code>data</code>
     *    field. 
     *    
     */
    
    public Car getCar()
    {
            return data;
    }
    
    /**
     * Sets the <code>data</code> object equal to the input <code>Car</code>
     * 
     * @param data
     *    The <code>Car</code> that <code>data</code> will be set to.
     */
    public void setData(Car data)
    {
        this.data = data;
    }

    /**
     * Returns the reference to the next <code>CarListNode</code> linked to
     * this node
     * 
     * @return
     *    <dd>Returns the <code>CarListNode</code> reference in the
     *    <code>next</code> field. 
     */
    
    public CarListNode getNext()
    {
            return next;
    }

    /**
     * Sets the <code>next</code> field equal to the input
     * <code>CarListNode</code>.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>next</code> must be of the type
     *    <code>CarListNode</code>.
     * 
     * @param next
     *    The input <code>CarListNode</code> that <code>next</code>
     *    will be set to.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>next</code> field's <code>CarListNode</code> is set to
     *    the input <code>CarListNode</code>.
     */
    public void setNext(CarListNode next)
    {
            this.next = next;
    }
    
    /**
     * Returns the reference to the prev <code>CarListNode</code> linked to
     * this node
     * 
     * @return
     *    <dd>Returns the <code>CarListNode</code> reference in the
     *    <code>prev</code> field.
     */
    
    public CarListNode getPrev()
    {
            return prev;
    }

    /**
     * Sets the <code>prev</code> field equal to the input
     * <code>CarListNode</code>.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>prev</code> must be of the type
     *    <code>CarListNode</code>.
     * 
     * @param prev
     *    The input <code>CarListNode</code> that <code>prev</code>
     *    will be set to.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>prev</code> field's <code>CarListNode</code> is set to
     *    the input <code>CarListNode</code>.
     */
    
    public void setPrev(CarListNode prev)
    {
            this.prev = prev;
    }
}
