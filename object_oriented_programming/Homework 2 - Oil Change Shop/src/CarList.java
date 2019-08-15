/**
 * The <code>CarList</code> class initiates the <code>CarList</code> object
 * with a null <code>head</code>, <code>tail</code>, and <code>cursor</code>.
 * The class has methods to return the number of cars in the list, to get the
 * data at the cursor, to reset the location of the cursor to the front of the
 * list, to move the cursor forwards and backwards, to add a 
 * <code>CarListNode</code> object into the list, and to remove nodes
 * from the list at the cursor. The class also has its own overridden
 * <code>toString</code> method which returns a <code>String</code>
 * representation of the nodes inside the list.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #1
 * CSE214-R02
 * TA: David S. Li
 */

public class CarList
{

    /** Initializes the head, or the first node, of the list */
    private CarListNode head; 

    /** Initializes the tail, or the last node, of the list */
    private CarListNode tail;

    /** Initializes the cursor of the list */
    private CarListNode cursor;

    /** The current amount of <code>CarListNode</code> objects in the list */
    private int carCount = 0;

    /**
     * Returns an instance of the <code>CarList</code> object which has its
     * <code>head</code>, <code>tail</code>, and <code>cursor</code> all set
     * to null. 
     * 
     * <dt>Postcondition:
     *    <dd>The <code>CarList</code> object is initiated with
     *    <code>head</code>, <code>tail</code>, and <code>cursor</code> all set
     *    to null. 
     */

    public CarList()
    {
        head = null;
        tail = null;
        cursor = null;
    }


    /**
     * Returns the number of <code>CarListNode</code> objects in the list.
     *
     * @return
     *    Returns how many cars are currently in the list.
     */
    public int numCars()
    {
        return carCount;
    }

    /**
     * Returns the <code>Car</code> wrapped in the <code>CarListNode</code>
     * referenced by the <code>cursor</code>. 
     *
     * <dt>Precondition:
     *    <dd><code>Cursor</code> is not null
     *
     * @return
     *    Returns the <code>Car</code> object inside the
     *    <code>CarListNode</code> referenced by <code>cursor</code>.
     *    If there is no <code>Car</code> object inside the
     *    <code>CarListNode</code> object referenced by <code>cursor</code> or
     *    if <code>cursor</code> does not reference a <code>CarListNode</code>,
     *    then null is returned.
     *    
     * <dt>Postcondition:
     *    <dd>The <code>Car</code> wrapped in the <code>CarListNode</code> in
     *    <code>cursor</code> is returned. If there is no car in the node or if
     *    cursor does not reference a node, null is returned.
     */
    public Car getCursorCar()
    {
        if (cursor != null)
            return cursor.getCar();
        else
            return null;
    }

    /**
     * Sets the reference of <code>cursor</code> equal to the <code>head</code>
     * also known as the first node in the list.
     * 
     * <dt>Postcondition:
     *    <dd>If the <code>head</code> is not null, then <code>cursor</code>
     *    references the <code>head</code>. If the head is null, then the list
     *    must be empty and the cursor will also be set to null.
     *    
     * @throws EndOfListException 
     *    Indicates that the cursor has reached the end of the list and will
     *    no longer go backwards.
     */
    public void resetCursorToHead() throws EndOfListException
    {
        if (head != null)
            while (cursor.getPrev() != null)
            {
                cursorBackward();
            }
        else
            cursor = null;
    }

    /**
     * Sets the reference of <code>cursor</code> equal to the <code>tail</code>
     * also known as the last node in the list.
     * 
     * <dt>Postcondition:
     *    <dd>If the <code>head</code> is not null, then <code>cursor</code>
     *    references the <code>tail</code>. If the head is null, then the list
     *    must be empty and the cursor will also be set to null.
     *    
     * @throws EndOfListException 
     *    Indicates that the cursor has reached the end of the list and will
     *    no longer go backwards.
     */
    public void cursorToTail() throws EndOfListException
    {
        if (head != null)
            while (cursor.getNext() != null)
            {
                cursorForward();
            }
        else
            cursor = null;
    }

    /**
     * Sets the reference of <code>cursor</code> to its next link. 
     * 
     * <dt>Precondition:
     *    <dd>The <code>cursor</code> object's next link must not be null. 
     *
     * <dt>Postcondition:
     *    <dd>If <code>cursor</code> object's next link is not null, then the
     *    <code>cursor</code> object's reference is set to the next link. If
     *    the next link is null, then <code>EndOfListException</code> is
     *    thrown.
     *
     * @throws EndOfListException
     *    Indicates that the cursor is at the end of the list and cannot move
     *    forwards.
     */
    public void cursorForward() throws EndOfListException
    {
        if (cursor != null && cursor.getNext() != null)
        {
            cursor = cursor.getNext();
        }
        else
            throw new EndOfListException("The cursor is at the end of the "
              + "list.");
    }

    /**
     * Sets the reference of <code>cursor</code> to its previous link. 
     * 
     * <dt>Precondition:
     *    <dd>The <code>cursor</code> object's previous link must not be null. 
     *
     * <dt>Postcondition:
     *    <dd>If <code>cursor</code> object's previous link is not null,
     *    then the <code>cursor</code> object's reference is set to the
     *    previous link. If the previous link is null, then the
     *    <code>EndOfListException</code> is thrown.
     *
     * @throws EndOfListException
     *    Indicates that the cursor is at the end of the list and cannot move
     *    backwards
     */
    public void cursorBackward() throws EndOfListException
    {
        if (cursor != null && cursor.getPrev() != null)
        {
            cursor = cursor.getPrev();
        }
        else
            throw new EndOfListException("The cursor is at the front of the "
              + "list.");
    }

    /**
     * Adds a new <code>CarListNode</code> to the list behind the cursor.
     * 
     * <dt>Precondition:
     *    <dd>The input <code>Car</code> must not be null.
     *
     * @param newCar
     *    The <code>Car</code> that is going to wrapped in the new node and
     *    inserted into the list behind the cursor.
     *    
     * <dt>Postcondition:
     *    <dd> If the <code>cursor</code> is not null, then the new node is
     *    placed into the list either as the new <code>head</code> if the
     *    <code>cursor</code> was the <code>head</code>, or as a new node
     *    behind the original <code>cursor</code>. The new node references the
     *    node at <code>cursor</code> as its next link and the node at
     *    <code>cursor</code> references the new node as its previous link. If
     *    the <code>cursor</code> is null, then the new node is added to the
     *    list as the only node in the list and is referenced as the
     *    <code>head</code>, <code>tail</code>, and <code>cursor</code>. The
     *    list's <code>carCount</code> is incremented if a car is added to the
     *    list.
     *    
     * @throws IllegalArgumentException
     *    Indicates the input <code>Car</code> was null
     */
    public void insertBeforeCursor(Car newCar) throws IllegalArgumentException
    {
        if (newCar == null)
        {
            throw new IllegalArgumentException("There is no car being added.");
        }
    
        CarListNode newNode = new CarListNode(newCar);
        if (cursor != null)
        {
            if (cursor.getPrev()==null)
            {
                newNode.setNext(cursor);
                head.setPrev(newNode);
                head = newNode;
            }
            else if (cursor.getNext()==null)
            {
                newNode.setNext(cursor);
                newNode.setPrev(cursor.getPrev());
                newNode.getPrev().setNext(newNode);
                tail.setPrev(newNode);
            }
            else
            {
                newNode.setNext(cursor);
                newNode.setPrev(cursor.getPrev());
                newNode.getPrev().setNext(newNode);
                cursor.setPrev(newNode);
            }
        }
        else
        {
            head = newNode;
            tail = newNode;
            cursor = newNode;
        }
        carCount++;
    }


    /**
     * Adds a new car to the end of the list as the new <code>tail</code>.
     *
     * <dt>Precondition:
     *    <dd>The input <code>Car</code> must not be null.
     *
     * @param newCar
     *    The <code>Car</code> that is going to wrapped in the new node and
     *    inserted into the list behind the cursor.
     *    
     * <dt>Postcondition:
     *    <dd> If the <code>tail</code> is not null, then the new node is
     *    placed into the list as the new <code>tail</code>. The new node
     *    references the old <code>tail</code> as its previous link and the old
     *    <code>tail</code> references the new <code>tail</code> as its next
     *    link. If the <code>tail</code> is null, then the new node is added to
     *    the list as the only node in the list and is referenced as the
     *    <code>head</code>, <code>tail</code>, and <code>cursor</code>. The
     *    list's <code>carCount</code> is incremented if a car is added to the
     *    list.
     *    
     * @throws IllegalArgumentException
     *    Indicates the input <code>Car</code> was null
     */
    public void appendToTail(Car newCar) throws IllegalArgumentException
    {

        CarListNode newNode = new CarListNode(newCar);
        if (tail != null) 
        {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        else
        {
            head = newNode;
            cursor = newNode;
            tail = newNode;
        }
        carCount++;
    }

    /**
     * Returns a new list that has been merged the input list with this list.
     * 
     * <dt>Precondition:
     *    <dd>There must be cars in either Joe's or Donny's list to merge.
     * 
     * @param otherList
     *    The list that will be merged into this list
     *    
     * @return
     *    Returns a new <code>CarList</code> that intertwines this list with
     *    the input list so that the order of the cars are kept. The order of
     *    this list is prioritized so that the first entry on this list is added
     *    first and then the first entry on the input list is added.
     *    
     * <dt>Postcondition:
     *    <dd> A new merged list is returned so that the first car on this list
     *    is first, and the first car on the input list is second, and the
     *    second car on this list is third and so on until there are no more
     *    cars on both lists.
     *    
     * @throws EmptyListException
     *    Indicates that the there are no cars in both lists so the lists
     *    cannot be merged.
     *    
     * @throws IllegalArgumentException
     *    Indicates that there is no car to add.
     */
    public CarList mergeList(CarList otherList) throws EmptyListException, 
    IllegalArgumentException
    {
        CarList mergedList = new CarList();
        CarListNode tempCursorThis = this.head;
        CarListNode tempCursorOther = otherList.head;

        if (numCars()==0 && otherList.numCars()==0)
        {
            throw new EmptyListException("There are no cars on the "
              + "list to merge");
        }
        
        while (tempCursorThis != null && tempCursorOther != null)
        {
            mergedList.appendToTail(tempCursorThis.getCar());
            mergedList.appendToTail(tempCursorOther.getCar());
            tempCursorThis = tempCursorThis.getNext();
            tempCursorOther = tempCursorOther.getNext();
        }
        while (tempCursorThis == null && tempCursorOther != null)
        {
            mergedList.appendToTail(tempCursorOther.getCar());
            tempCursorOther = tempCursorOther.getNext();
        }
        while (tempCursorOther == null && tempCursorThis != null)
        {
            mergedList.appendToTail(tempCursorThis.getCar());
            tempCursorThis = tempCursorThis.getNext();
        }

        CarListNode temp = mergedList.head;
        while (temp != null && this.cursor != null)
        {
            if (temp.getCar().equals(this.cursor.getCar()))
                mergedList.cursor = temp;
            temp = temp.getNext();

        }
        return mergedList;
    }

    /**
     * Sorts the list by <code>make</code> alphabetically from A to Z. For ease
     * of reading, <code>CarListNode</code> objects (one, two and three) are
     * made to reference the node we are pointing at, the node after it, and
     * the node after it. While the cursor is not null, the nodes' links will
     * be swapped by moving the cursor one node forward and repeating the swap
     * until the cursor has reached the end of the list. The loop repeats once
     * the cursor reaches the end of list, N amount of times, N being the
     * amount of cars in the list.
     * 
     * <dt>Precondition:
     *    <dd><code>carCount</code> must be greater or equal to 2
     *    
     * <dt>Postcondition:
     *    <dd>The list is sorted by <code>make</code> alphabetically from
     *    A to Z.
     */

    public void sortList()
    {
        CarListNode one = null, two = null, three = null;
        CarListNode cursor = null;
        String oneMake = "";
        String twoMake = "";
        
        if (carCount < 2)
        {
            System.out.println("There are not enough cars in the list"
              + " to swap");
            return;
        }
        
        for (int i = 0; i < this.carCount; i++)
        {
            one = head;
            try
            {
                cursor = new CarListNode(head.getCar());
            } 
            catch (IllegalArgumentException e)
            {}
            cursor.setNext(head.getNext());
            cursor.setPrev(head.getPrev());
            while (cursor != null)
            {
                oneMake = one.getMake().toString();
                
                if (one.getNext() != null)
                {
                    {
                        two = one.getNext();
                        twoMake = two.getMake().toString();
                    }
                    if (two.getNext() != null)
                    {
                        {
                            three = two.getNext();
                        }
                    }

                }
                
                if (one == head)
                {
                    if (two != null)
                    {
                        if (oneMake.compareTo(twoMake) > 0)
                        {
                            one.setNext(two.getNext());
                            one.setPrev(two);
                            two.setNext(one);
                            two.setPrev(null);
                            head = two;
                            if (three != null)
                                three.setPrev(one);
                        }
                    }
                }
                else if (two == tail)
                {
                    if (oneMake.compareTo(twoMake) > 0)
                    {
                        one.getPrev().setNext(two);
                        two.setPrev(one.getPrev());
                        one.setNext(null);
                        one.setPrev(two);
                        two.setNext(one);
                        tail = one;
                    }
                }
                else if (one == tail && two == null)
                {
                    if (cursor.getNext() != null)
                    {
                        one = cursor.getNext();
                        cursor = cursor.getNext();
                        two = null;
                        three = null;
                    }
                    else
                    {
                        one = null;
                        cursor = null;
                        two = null;
                        three = null;
                        break;
                    }
                }
                else
                {
                    if (oneMake.compareTo(twoMake) > 0)
                    {
                        one.getPrev().setNext(two);
                        two.setPrev(one.getPrev());
                        one.setNext(three);
                        one.setPrev(two);
                        two.setNext(one);
                        three.setPrev(one);
                    }
                }
                
                if (cursor.getNext() != null)
                {
                    one = cursor.getNext();
                    cursor = cursor.getNext();
                    two = null;
                    three = null;
                }
                else
                {
                    one = null;
                    cursor = null;
                    two = null;
                    three = null;
                    break;
                }
                
            }
        }
    }
    

    /**
     * Removes the <code>CarListNode</code> at the cursor by removing any links
     * to the cursor.
     * 
     * <dt>Precondition:
     *    <dd>The <code>cursor</code> must not be null.
     *
     * @return
     *    Returns the <code>Car</code> wrapped in the removed
     *    <code>CarListNode</code>.
     *    
     * <dt>Postcondition:
     *    <dd>If the <code>cursor</code> is not null, then the
     *    <code>CarListNode</code> referenced by <code>cursor</code> is removed
     *    from the list. If the <code>cursor</code> is the <code>head</code>
     *    but is not also the <code>tail</code>, then the node after the
     *    <code>cursor</code> becomes the new head. If the cursor the
     *    <code>tail</code> of the list, then the node before the
     *    <code>tail</code> becomes the new <code>tail</code>. If the
     *    <code>cursor</code> is both the <code>tail</code> and
     *    <code>head</code>, then the <code>cursor</code>, <code>tail</code>,
     *    and <code>head</code> are set to null. If a car is removed, then the
     *    removed <code>Car</code> is returned and <code>carCount</code> is
     *    decremented.
     *    
     * @throws EndOfListException
     *    Indicates that there are no cars in the list to remove.
     */

    public Car removeCursor() throws EndOfListException
    {
        if (cursor != null)
        {
            if (cursor == tail && cursor == head)
            {
                System.out.println("4");
                Car temp = cursor.getCar();
                head = null;
                cursor = null;
                tail = null;
                carCount--;
                return temp;
            }
            else if (!(cursor == (tail)) && !(cursor == (head)))
            {
                Car temp = cursor.getCar();
                System.out.println("1");

                cursor.getPrev().setNext(cursor.getNext());
                cursor.getNext().setPrev(cursor.getPrev());
                cursor = cursor.getPrev();
                carCount--;
                return temp;
            }
            else if (cursor == (tail) && !(cursor == (head)))
            {
                Car temp = cursor.getCar();
                System.out.println("2");
                tail = tail.getPrev();
                tail.setNext(null);
                carCount--;
                cursor = tail;
                return temp;
            }
            else 
            {
                Car temp = cursor.getCar();
                System.out.println("3");
                head = head.getNext();
                head.setPrev(null);
                carCount--;
                cursor = head;
                return temp;
            }
        }
        else
            throw new EndOfListException("The cursor is null.");

    }

    /**
     * Returns a <code>String</code> representation of this
     * <code>CarList</code>.
     * 
     * @return
     *    Returns a <code>String</code> containing the information of every
     *    <code>Car</code> in every <code>CarListNode</code> in this list. IF
     *    there are no <code>CarListNode</code> objects in the list, then the
     *    <code>String</code> is returned as "[EMPTY]".
     *    
     * <dt>Postcondition:
     *    <dd>A <code>String</code> containing the information of every
     *    <code>Car</code> in every <code>CarListNode</code> in this list is
     *    returned. An arrow <code>"->"</code> is used to indicate if a
     *    <code>CarListNode</code> is currently referenced as the list's
     *    <code>cursor</code>. If there are no <code>CarListNode</code> objects
     *    in the list, then the <code>String</code> is returned as "[EMPTY]".
     */

    public String toString()
    {
        String temp = "";
        CarListNode tempCursor = head;

        if (carCount == 0)
        {
            temp += String.format("%s%n", "[EMPTY]");
            return temp;
        }

        while (tempCursor != null)
        {
            if (tempCursor==cursor)
            {
                temp += String.format("%s%-15s%s%n", "->", 
                  tempCursor.getMake(), tempCursor.getOwner());
            }
            else
            {
                temp += tempCursor.getCar();
            }
            tempCursor = tempCursor.getNext();
        }
        return temp;
    }

    /**
     * Returns a <code>String</code> representation of the
     * finished <code>CarList</code>.
     * 
     * @return
     *    Returns a <code>String</code> containing the information of every
     *    <code>Car</code> in every <code>CarListNode</code> in this list. IF
     *    there are no <code>CarListNode</code> objects in the list, then the
     *    <code>String</code> is returned as "[EMPTY]".
     *    
     * <dt>Postcondition:
     *    <dd>A <code>String</code> containing the information of every
     *    <code>Car</code> in every <code>CarListNode</code> in this list is
     *    returned. If there are no <code>CarListNode</code> objects
     *    in the list, then the <code>String</code> is returned as "[EMPTY]".
     */

    public String toStringFinal()
    {
        String temp = "";
        CarListNode tempCursor = head;

        if (carCount == 0)
        {
            temp += String.format("%s%n", "[EMPTY]");
            return temp;
        }

        while (tempCursor != null)
        {
            temp += tempCursor.getCar();
            tempCursor = tempCursor.getNext();
        }
        return temp;
    }


}