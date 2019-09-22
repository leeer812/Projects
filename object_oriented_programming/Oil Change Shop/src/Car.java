/**
 * The <code>Car</code> class initiates the <code>Car</code> object with a make
 * and owner name. The class is equipped with methods that return and set the
 * make and owner name. <code>Car</code> overrides the <code>toString</code>
 * method and displays a <code>String</code> representation of its make and
 * owner name.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */

public class Car
{
    private Make make; // The make of the car
    private String owner; // The owner of the car
    
    /**
     * Invariants:
     * <code>make</code> represents the make of the car and should not
     * change after the car has been initialized.
     * <code>owner</code> represents the name of the owner of the car
     * and should not change after the car has been initialized.
     */
    
    /**
     * Returns an instance of the <code>Car</code> object
     * 
     * <dt>Precondition:
     *    <dd>The input <code>makeName</code> must be one of the seven
     *    available makes within the <code>make</code> <code>enum</code>.
     *    
     * @param makeName
     *    The make that the car will be
     *    
     * @param ownerName
     *    The name of the owner of the car
     *    
     * <dt>Postcondition:
     *    <dd>The <code>make</code> and <code>owner</code> of this Car object
     *    is set to the input <code>makeName</code> and <code>ownerName</code>.
     */
    
    public Car(Make makeName, String ownerName)
    {
        this.setMake(makeName);
        this.setOwner(ownerName);
    }

    /**
     * returns this Car's make
     * 
     * @return
     *    Returns the <code>make</code> of this car
     */
    public Make getMake()
    {
        return make;
    }

    /**
     * Sets the <code>make</code> of this car to the specified input
     * 
     * @param makeName
     *    The input <code>make</code> that this <code>Car</code> object's
     *    <code>make</code> will be set to.
     */
    public void setMake(Make makeName)
    {
        this.make = makeName;
    }

    /**
     * Returns a <code>String</code> representation of this car's owner
     * 
     * @return
     *    Returns <code>owner</code> which is the name of the car's owner.
     */
    public String getOwner() 
    {
        return owner;
    }

    /**
     * Sets this car's owner's name, <code>owner</code> to the specified name.
     * 
     * @param owner
     *    The name that <code>owner</code> will be set to.
     *    
     * <dt>Postcondition:
     *    <dd>This <code>Car</code> object's <code>owner</code> field is set to
     *    the input <code>owner</code>.
     */
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    /**
     * Returns a <code>String</code> representation of this <code>Car</code>
     * object's <code>make</code> and <code>owner</code>. The string is
     * returned in a table like format.
     * 
     * @return
     *    Returns this <code>Car</code> object's <code>make</code> and its
     *    <code>owner</code>.
     */
    public String toString()
    {
            return String.format("%-17s%s%n", make, owner);
    }
}
