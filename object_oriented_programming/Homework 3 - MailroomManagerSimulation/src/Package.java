/**
 * The <code>Package</code> class initializes a <code>Package</code> object
 * with a recipient name, an arrival date, the package weight. The package is
 * linked to the <code>next</code> package and to the <code>prev</code> package
 * in the <code>PackageStack</code> that it is being held in. The name, arrival
 * date, package weight, and its <code>next</code> and <code>prev</code>
 * package all have their respective get and set methods. 
 * A <code>toString</code> method is also available to provide a 
 * <code>String</code> representation of this <code>Package</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #3
 * CSE214-R02
 * TA: David S. Li
 */

public class Package
{
    /**
     * Invariants:
     * The <code>Package</code> object's <code>recipient</code>,
     * <code>arrivalDate</code>, <code>weight</code> should not change
     * after the <code>Package</code> has been initialized.
     */
    
    // The name of the recipient of this package
    private String recipient;
    // The date that this package arrived on
    private int arrivalDate;
    // The weight of this package
    private double weight;
    // The package that this package's next link is linked to
    private Package next;
    // The package that this package's prev link is linked to
    private Package prev;

    /**
     * Instantiates a <code>Package</code> object, setting its
     * <code>recipient</code>, <code>arrivalDate</code>, and
     * <code>weight</code> to the input parameters. Its <code>next</code>
     * and <code>prev</code> links are set to null.
     * 
     * @param recipient
     *    The name of the recipient of this package
     * @param arrivalDate
     *    The arrival date of this package
     * @param weight
     *    The weight of this package
     */
    public Package(String recipient, int arrivalDate, double weight)
    {
        this.recipient = recipient;
        this.arrivalDate = arrivalDate;
        this.weight = weight;
        this.next = null;
        this.prev = null;
    }
    
    /**
     * The <code>toString</code> method for the <code>Package</code> class
     * returns a <code>String</code> representation of this
     * <code>Package</code>.
     * 
     * @return
     *    Returns a <code>String</code> containing this package's
     *    <code>recipient</code> and <code>arrivalDate</code>.
     */
    public String toString()
    {
        return String.format("%s%d%s", "[" + this.recipient + " ", this.arrivalDate, "]");
    }

    /**
     * Gets the reference to the <code>Package</code> in this 
     * <code>Package</code> object's <code>next</code> value.
     * 
     * @return
     *    Returns the <code>Package</code> in the <code>next</code> field.
     */
    public Package getNext() 
    {
        return next;
    }

    /**
     * Sets this <code>Package</code> object's <code>next</code> value to the
     * input <code>next</code> package.
     * 
     * @param next
     *    The <code>Package</code> that this package's <code>next</code> will
     *    be linked to.
     */
    public void setNext(Package next)
    {
        this.next = next;
    }

    /**
     * Gets the reference to the <code>Package</code> in this 
     * <code>Package</code> object's <code>prev</code> value.
     * 
     * @return
     *    Returns the <code>Package</code> in the <code>prev</code> field.
     */
    public Package getPrev() {
        return prev;
    }

    /**
     *  Sets this <code>Package</code> object's <code>prev</code> value to the
     * input <code>prev</code> package.
     * 
     * @param prev
     *    The <code>Package</code> that this package's <code>prev</code> will
     *    be linked to.
     */
    public void setPrev(Package prev) {
        this.prev = prev;
    }

    /**
     * Returns the <code>recipient</code> name of this <code>Package</code>.
     * 
     * @return
     *    Returns the <code>String</code> in <code>recipient</code>
     */
    public String getRecipient() 
    {
        return recipient;
    }

    /**
     * Sets the <code>recipient</code> of this <code>Package</code> to the
     * input <code>recipient</code> name.
     * 
     * @param recipient
     *    The input <code>recipient</code> name that this <code>Package</code>
     *    object's <code>recipient</code> field will be set to.
     */
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }

    /**
     * Returns the <code>arrivalDate</code> of this <code>Package</code>
     * 
     * @return
     *    Returns the <code>arrivalDate</code> of the package
     */
    public int getArrivalDate()
    {
        return arrivalDate;
    }

    /**
     * Sets the <code>arrivalDate</code> of this <code>Package</code> to the
     * specified input <code>arrivalDate</code>.
     * 
     * @param arrivalDate
     * 
     */
    public void setArrivalDate(int arrivalDate) 
    {
        this.arrivalDate = arrivalDate;
    }

    /**
     * Returns the <code>weight</code> of this package
     * 
     * @return
     *    Returns the <code>weight</code> associated with this package
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * Sets the <code>weight</code> of this <code>Package</code> to the input
     * <code>weight</code>
     * 
     * @param weight
     *    The input <code>weight</code> that this package's <code>weight</code>
     *    will be set to
     */
    public void setWeight(double weight) 
    {
        this.weight = weight;
    }
    
    /**
     * Returns a duplicate of this <code>Package</code> with the same
     * <code>recipient</code>, <code>arrivalDate</code> and <code>weight</code>
     * 
     * @return
     *    Returns a new <code>Package</code> object with this package's
     *    recipient, arrival date, and weight
     */
    public Package fullClone() {
        return new Package(this.recipient, this.arrivalDate, this.weight);
    }
}
