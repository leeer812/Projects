/**
 * The <code>PackageStack</code> class inititalizes a <code>PackageStack</code>
 * object with a <code>CAPACITY</code> which limits the amount of packages
 * in the stack to the capacity, a <code>packageCount</code> which is
 * incremented and decremented each time a <code>Package</code> is added to the
 * stack. A reference to the <code>top</code> package is provided.A boolean
 * <code>isFloorStack</code> is used to determine whether the package stack is
 * a floor stack when instantiating the object. If the stack is a floor stack,
 * then <code>CAPACITY</code> is not utilized when adding packages and there
 * is no limit.
 * 
 * <p>A <code>toString</code> method is provided for converting this stack's
 * packages into a <code>String</code> representation. Packages can be added
 * and removed from the stack with the <code>push</code> and <code>pop</code>
 * methods. The <code>peek</code> method returns the <code>top</code> of this
 * stack, while the <code>isEmpty</code> and <code>isFull</code> methods check
 * to see if there are no packages in the stack or if there are already 7
 * packages in the stack if the stack is a floor stack. We can also reverse
 * the order of this stack with the <code>reverse</code> method.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #3
 * CSE214-R02
 * TA: David S. Li
 */

public class PackageStack
{
    /**
     * Invariants:
     * <code>CAPACITY</code> is always set to 7 for every package stack.
     * <code>isFloorStack</code> retains its value once the package stack has
     * been instantiated.
     */
    
    //  The capacity specifies the maximum amount of packages that can be added
    //  into the stack if the stack is not a floor stack
    private final int CAPACITY = 7;
    // The packageCount specifies how many packages are in the stack
    private int packageCount;
    // The boolean specifies whether the PackageStack is a floor stack or not
    private boolean isFloorStack;
    // The top references the package at the top of the stack
    private Package top;
    
    /**
     * Returns an instance of the <code>PackageStack</code> object with its
     * <code>isFloorStack</code> boolean set to true and its <code>top</code>
     * set to null.
     */
    
    public PackageStack()
    {
        isFloorStack = true;
        top = null;
    }
    
    /**
     * Returns an instance of the <code>PackageStack</code> object with its
     * <code>isFloorStack</code> boolean set to false and its <code>top</code>
     * set to null. Its <code>packageCount</code> is set to the specified input
     * <code>packageCount</code> (typically 0).
     * 
     * @param packageCount
     *    The input <code>packageCount</code> that this package's
     *    <code>packageCount</code> will be set to.
     */
    
    public PackageStack(int packageCount)
    {
        this.packageCount = packageCount;
        isFloorStack = false;
        top = null;
    }
    
    /**
     * Returns a <code>String</code> representation of all of the 
     * <code>Package</code> objects in this <code>PackageStack</code>.
     * 
     * @return
     *    Returns a <code>String</code> representation of all of the
     *    <code>Package</code> objects in this <code>PackageStack</code>. If
     *    the stack is empty, then "empty" is returned.
     * 
     * <dt>Postcondition:
     *    Returns a <code>String</code> representation of all of the
     *    <code>Package</code> objects in this <code>PackageStack</code>. If
     *    the stack is empty, then "empty" is returned.
     */
    public String toString()
    {
        Package tempCursor = top;
        String temp = "";
        if (this.isEmpty())
            return "empty";
        /*while (tempCursor != null)
        {
            temp += tempCursor.toString();
            tempCursor = tempCursor.getPrev();
        }*/
        while (tempCursor.getPrev() != null)
        {
            tempCursor = tempCursor.getPrev();
        }
        while (tempCursor != null)
        {
            temp += tempCursor.toString();
            tempCursor = tempCursor.getNext();
        }
        return temp;
    }
    
    /**
     * Adds a new <code>Package</code> into this <code>PackageStack</code>
     * 
     * <dt>Precondition:
     *    <dd>If this is a floor stack, then <code>packageCount</code> must be
     *    less than <code>CAPACITY</code>
     *    
     * @param x
     *    The <code>Package</code> that will be pushed into this
     *    <code>PackageStack</code>
     *    
     * <dt>Postcondition:
     *    <dd>If this stack is not a floor stack, then the input
     *    <code>Package</code> is pushed onto this stack if the
     *    <code>packageCount</code> is less than the <code>CAPACITY</code>.
     *    If there are already 7 packages in the stack, then the
     *    <code>FullStackException</code> is thrown. If this stack is a floor
     *    stack, then a package is pushed onto the top of this stack regardless
     *    of the amount of packages inside. If a package was successfully added
     *    then <code>packageCount</code> is incremented.
     *    
     * @throws FullStackException
     *    Indicates that the <code>PackageStack</code> is already has seven
     *    packages and cannot hold the package being pushed into the stack
     */
    public void push(Package x) throws FullStackException
    {
        if (isFloorStack == false)
            if (!(this.isFull()))
            {
                if (this.isEmpty())
                {
                    top = x;
                    packageCount++;
                }
                else
                {
                    top.setNext(x);
                    x.setPrev(top);
                    top = x;
                    packageCount++;
                }
            }
            else
                throw new FullStackException("The stack is full");
        else
            if (this.isEmpty())
            {
                top = x;
                packageCount++;
            }
            else
            {
                top.setNext(x);
                x.setPrev(top);
                top = x;
                packageCount++;
            }
    }
    
    /**
     * Removes and returns the <code>Package</code> on the top of the
     * <code>PackageStack</code>. 
     * 
     * <dt>Precondition:
     *    <dd>There must be a package in the stack to remove, otherwise the
     *    <code>EmptyStackException</code> is thrown.
     *    
     * @return
     *    Returns the <code>Package</code> at the top of the
     *    <code>PackageStack</code> if there is a package in the stack.
     *    
     * <dt>Postcondition:
     *    <dd>If there was a package in the stack, then the
     *    <code>Package</code> referenced by <code>top</code> is removed from
     *    the stack and is returned. If there were no packages in the stack,
     *    then the <code>EmptyStackException</code> is thrown to indicate so.
     *    If a package was removed, then <code>packageCount</code> is
     *    decremented.
     *    
     * @throws EmptyStackException
     *    Indicates that the stack is empty and that no package can be popped
     *    from the stack
     */
    public Package pop() throws EmptyStackException
    {
        Package temp = top.fullClone();
        if (this.isEmpty())
            throw new EmptyStackException("There is no package in the stack");
        if (top.getPrev() != null)
            top.getPrev().setNext(null);
        top = top.getPrev();
        packageCount--;
        return temp;
            
    }
    
    /**
     * Returns the <code>Package</code> referenced by <code>top</code>
     * 
     * <dt>Precondition:
     *    <dd>There must be a <code>Package</code> in the stack
     *    
     * @return
     *    Returns the <code>Package</code> referenced by <code>top</code> if
     *    <code>top</code> is not null.
     *    
     * <dt>Postcondition:
     *    <dd>If <code>top</code> is not null, then the <code>Package</code>
     *    referenced by <code>top</code> is returned. Otherwise, the
     *    <code>EmptyStackException</code> is thrown to indicate the stack is
     *    empty.
     *    
     * @throws EmptyStackException
     *    Indicates that the stack is empty
     */
    
    public Package peek() throws EmptyStackException
    {
        if (this.isEmpty())
            throw new EmptyStackException("There is no package in the stack");
        return top;
    }
    
    /**
     * Indicates whether this <code>PackageStack</code> is empty or not
     * 
     * @return
     *    Returns a <code>boolean</code> with the value of <code>true</code> if
     *    there are no packages in the stack, otherwise the boolean is returned
     *    with a value of <code>false</code>.
     */
    public boolean isEmpty()
    {
        if (packageCount == 0)
            return true;
        else
            return false;
    }
    
    /**
     * Indicates whether this <code>PackageStack</code> is full or not
     * 
     * @return
     *    Returns a <code>boolean</code> with the value of <code>true</code> if
     *    this stack is not a floor stack and has the same amount of packages
     *    as allowed by the <code>CAPACITY</code>. Otherwise, the boolean is
     *    returned with a value of <code>false</code>.
     */
    public boolean isFull()
    {
        if (isFloorStack == false)
            if (packageCount == CAPACITY)
                return true;
            else
                return false;
        else
            return false;
    }
    
    /**
     * Returns a <code>PackageStack</code> with its <code>Package</code>
     * objects in reverse order.
     * 
     * @return
     *    Returns a <code>PackageStack</code> with all of this stack's packages
     *    in reverse order
     */
    public PackageStack reverse() {
        PackageStack temp;
        if(this.isFloorStack) temp = new PackageStack();
        else temp = new PackageStack(0);
        while(!this.isEmpty()) 
        {
            try {temp.push(this.pop());} catch(Exception e) {}
        }
        return temp;
    }
    
}
