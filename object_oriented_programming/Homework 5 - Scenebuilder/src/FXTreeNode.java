/**
 * The <code>FXTreeNode</code> class generates a node that is stored in the
 * <code>FXComponentTree</code>. Each node has its own
 * <code>ComponentType</code>, parent, indicator, and index. Each node is
 * assigned a <code>Type</code> dependent on its <code>ComponentType</code>
 * and can be one of two types, <code>Container</code> or <code>Control</code>.
 * Containers do not have text but can have children. Controls have text but do
 * not have children. Each container can only have a maximum of 10 children.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 */
public class FXTreeNode
{
    private String text;
    private ComponentType type;
    private Type bigType;
    private FXTreeNode parent;
    private FXTreeNode[] children; // Just like in hw 1, there should be no
                                   // holes in the array.
    final int maxChildren = 10;
    // The current amount of children in the node's children array
    private int size;
    // The height used in the node's visual representation
    private int height;
    // The indicator used in the node's visual representation
    private String indicator;
    // The index used in the node's saved representation
    private String index;

    /**
     * Returns an instance of the <code>FXTreeNode</code> with its fields 
     * initialized.
     * 
     * @param text
     * The text of the node
     * 
     * @param type
     * The <code>ComponentType</code> of the node
     * 
     * <dt>Postcondition:
     *    <dd>The node's <code>bigType</code> is set to <code>Control</code> if
     *    its <code>type</code> is Button, TextArea, Label. If its
     *    <code>type</code> is VBox, HBox, AnchorPane then its bigType is set
     *    to <code>Container</code>. If its bigType is Container, then it has
     *    "" text but its children array is initialized. If it is a Control,
     *    then its text is initialized but its children array is null. The
     *    node's height is set to 0.
     */
    public FXTreeNode(String text, ComponentType type)
    {
        indicator = "";
        this.type = type;
        parent = null;
        height = 0;
        size = 0;

        switch (type)
        {
        case Button:
            bigType = Type.Control;
            break;
        case AnchorPane:
            bigType = Type.Container;
            break;
        case HBox:
            bigType = Type.Container;
            break;
        case Label:
            bigType = Type.Control;
            break;
        case TextArea:
            bigType = Type.Control;
            break;
        case VBox:
            bigType = Type.Container;
            break;
        }

        switch (bigType)
        {
        case Container:
            this.text = null;
            children = new FXTreeNode[maxChildren];
            break;
        case Control:
            this.text = text;
            children = null;
            break;
        }
    }

    /**
     * Default no-args constructor
     */
    public FXTreeNode()
    {
        parent = null;
        size = 0;
    }

    /**
     * Returns whether this node is a Container or not
     * 
     * @return
     * Returns a boolean as true if <code>bigType</code> is Container and false
     * if not.
     */
    public boolean isContainer()
    {
        if (bigType == Type.Container)
            return true;
        else
            return false;
    }

    /**
     * Returns whether this node is a Control or not
     * 
     * @return
     * Returns a boolean as true if <code>bigType</code> is Control and false
     * if not.
     */
    public boolean isControl()
    {
        if (bigType == Type.Control)
            return true;
        else
            return false;
    }

    /**
     * Removes all the children in the node's children array and all of the
     * children in its children's children arrays.
     * 
     * @param node
     * The node that is getting its children removed.
     * 
     * <dt>Precondition:
     *    <dd>The node must be a container and it must have at least one child
     *    to remove.
     *    
     * <dt>Postcondition:
     *    <dd>The node has all of its children removed and all of its
     *    children's children are removed. If the node was not a container or
     *    if the node had no children to remove, the node is unchanged.
     */
    public void removeChild(FXTreeNode node)
    {
        if (!node.isContainer())
        {
            return;
        }
        if (node.getSize() == 0)
            return;
        for (int i = 0; i < node.getSize(); i++)
        {
            removeChild(node.getChildren()[i]);
            node.getChildren()[i] = null;
        }
        node.setSize(node.getSize() - 1);
    }

    /**
     * A recursive method to adjust the index of the node after its parent's
     * children array has been altered through the removal of a child. The
     * method sets its 'parent' index to the new parent's index.
     * 
     * @param node
     * The node whose index will be updated.
     * 
     * <dt>Precondition:
     *    <dd>The node must not be null.
     *    
     * <dt>Postcondition:
     *    <dd>The node and all of its children have its index updated to match
     *    the index of its parent. If the node is null, then the index is
     *    unchanged.
     */
    public void moveIndexDown(FXTreeNode node)
    {
        // Returns if the node is null
        if (node == null)
            return;
        // The rootIndex is equal to the index of the parent
        String rootIndex = node.getParent().getIndex();
        // The personalIndex is equal to the index after the parent index in
        // this node
        String personalIndex = node.getIndex().substring(rootIndex.length(),
            node.getIndex().length());
        // Set this node's index to the updated index
        node.setIndex(rootIndex + personalIndex);
        // Set all of this node's children's index to the updated indices
        for (int i = 0; i < node.getSize(); i++)
        {
            moveIndexDown(node.getChildren()[i]);

        }
    }
    
    public void moveIndexUp(FXTreeNode node)
    {
        if (node == null)
            return;
        String rootIndex = node.getParent().getIndex();
        String personalIndex = node.getIndex().substring(rootIndex.length(),
            node.getIndex().length());
        node.setIndex(rootIndex + personalIndex);
        for (int i = 0; i < node.getSize(); i++)
        {
            moveIndexUp(node.getChildren()[i]);
        }
    }

    /**
     * Removes the child at the specified index of this node's children array.
     * 
     * @param index
     * The index of the child that will be removed
     * 
     * <dt>Precondition:
     *    <dd>This node must be a container.
     *    
     * <dt>Postcondition:
     *    <dd>The node at the specified index in this node's children array is
     *    removed and all of the remaining children in the children array have
     *    their indexes updated accordingly.
     */
    public void removeChildAtIndex(int index)
    {
        if (!isContainer())
        {
            System.out.println("The node at the  is not a container");
            return;
        }
        // Indicates how many elements were in front of the node being removed
        int amountToMove = size - index;
        // Remove all of the children of the child at the specified index
        removeChild(children[index]);
        // Removes the child at specified index
        children[index] = null;
        for (int j = 0; j < amountToMove; j++)
        {
            if (children[index + j + 1] != null)
            {
                // Updates the index of the children in the children array
                String oldIndex = children[index + j + 1].getIndex();
                int newIndex = Integer.parseInt(oldIndex
                    .substring(oldIndex.length() - 1, oldIndex.length())) - 1;
                // Moves the node one slot down
                children[index + j] = children[index + j + 1];
                children[index + j].setIndex(
                  oldIndex.substring(0, oldIndex.length() - 1) + newIndex);
                moveIndexDown(children[index + j]);
            }
            else
                children[index + j] = children[index + j + 1];
        }
    }

    /**
     * Adds a node to the specified index of this node's children array
     * 
     * @param node
     * The node being added to this node's children array
     * 
     * @param index
     * The index that the node will be added to in this node's children array
     * 
     * <dt>Precondition:
     *    <dd>This node must be a container and its size has to be less than
     *    10.
     *    
     * <dt>Postcondition:
     *    <dd>A node is added to this node's children array at the specified
     *    index with its parent set to this node, and its height set to the
     *    parent's height + 1. All of the previously existing elements in the
     *    children array have their indices updated and this node's size is
     *    incremented.
     */
    public void addChildToIndex(FXTreeNode node, int index)
    {
        if (!isContainer() || size == maxChildren)
            return;
        node.setParent(this);
        node.setHeight(this.height + 1);
        int amountToMove = size - index;
        for (int j = amountToMove; j > 0; j--)
        {
            if (children[index + j - 1] != null)
            {
            // Moves the node in the children array up one to make space for
            // the new node being added
            children[index + j] = children[index + j - 1];
            // Updates the index of the node in the children array to account
            // for its changed position in the array
            String oldIndex = children[index + j].getIndex();
            int newIndex = Integer.parseInt(
                oldIndex.substring(oldIndex.length() - 1, oldIndex.length()))
                + 1;
            children[index + j].setIndex(
                oldIndex.substring(0, oldIndex.length() - 1) + newIndex);
            moveIndexUp(children[index + j]);
            }
            else
            {
//                String oldIndex = children[index].getIndex();
//                int newIndex = Integer.parseInt(
//                    oldIndex.substring(oldIndex.length() - 1, oldIndex.length()))
//                    + 1;
//                children[index + j].setIndex(
//                    oldIndex.substring(0, oldIndex.length() - 1) + newIndex);
//                moveIndexUp(children[index + j]);
                children[index] = node;
            }
                
        }
        children[index] = node;
        size++;
    }

    /**
     * Adds a child to this node's children array at the last empty slot.
     * 
     * @param node
     * The node being added to this node's children array
     * 
     * <dt>Precondition:
     *    <dd>This node must be a container and its size must be less than 10.
     *    
     * <dt>Postcondition:
     *    <dd>The node's parent is set to this node and its height is set to
     *    its parent's height + 1. The node is added to this node's children
     *    array and this node's size is incremented by 1. If this node is not
     *    a container or its size is already 10, then this node is unchanged
     *    and the input node is not added.
     */
    public void addChild(FXTreeNode node)
    {
        if (!isContainer() || size == maxChildren)
            return;
        node.setParent(this);
        node.setHeight(this.height + 1);
        children[size] = node;
        size++;
    }

    /**
     * Returns a visual representation of this node with spacing, an indicator,
     * the node's <code>ComponentType</code>, and the node's <code>text</code>.
     * The spacing is determined by the height of the node and its indicator
     * is determined by its position in the tree. 
     * 
     * @param tree
     * The tree that this node is in
     * 
     * @return
     * Returns a <code>String</code> containing this node's spacing, indicator,
     * <code>ComponentType</code>, and <code>text</code>
     * 
     * <dt>Postcondition:
     *    <dd>A <code>String</code> is returned with this node's spacing,
     *    indicator, <code>ComponentType</code>, and <code>text</code>. If this
     *    node is the cursor of the input tree, then indicator is set to "==>",
     *    if the height is 0, then indicator is blank. If height is 1, then
     *    indicator is set to "--+". If height is 2 or higher, then the
     *    indicator is set to "+--".
     */
    public String visualString(FXComponentTree tree)
    {
        int depth = height;
        String space = "";
        for (int i = 1; i < depth; i++)
        {
            space += "   ";
        }
        if (this == tree.getCursor())
            indicator = "==>";
        else if (depth == 0)
            indicator = "";
        else if (depth == 1)
            indicator = "--+";
        else if (depth >= 2)
            indicator = "+--";

        String compType = "";
        String textContent = "";

        if (text != null)
            textContent = " " + text;

        switch (type)
        {
        case AnchorPane:
            compType = "AnchorPane";
            break;
        case Button:
            compType = "Button";
            break;
        case HBox:
            compType = "HBox";
            break;
        case Label:
            compType = "Label";
            break;
        case TextArea:
            compType = "TextArea";
            break;
        case VBox:
            compType = "VBox";
            break;
        }
        
        if (bigType == Type.Container)
            return String.format("%s%s%s%s%n", space, indicator, compType,
                textContent);
        else
            return String.format("%s%s%s%s%n", space, indicator, compType +":",
                textContent);

    }

    /**
     * Returns the <code>String</code> save representation of this node.
     * 
     * <dt>Postcondition:
     *    <dd>A <code>String</code> with this node's index, component type,
     *    and text is returned.
     */
    public String toString()
    {
        String compType = "";
        String textContent = "";

        if (text != null)
            textContent = " " + text;

        switch (type)
        {
        case AnchorPane:
            compType = "AnchorPane";
            break;
        case Button:
            compType = "Button";
            break;
        case HBox:
            compType = "HBox";
            break;
        case Label:
            compType = "Label";
            break;
        case TextArea:
            compType = "TextArea";
            break;
        case VBox:
            compType = "VBox";
            break;
        }
        return String.format("%s %s%s%n", index, compType, textContent);
    }

    /**
     * Returns this node's text
     * 
     * @return
     * Returns <code>text</code>
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets this node's text to the input
     * 
     * @param text
     * The input that <code>text</code> will be set to
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Returns this node's <code>ComponentType</code>
     * 
     * @return
     * Returns <code>type</code>
     */
    public ComponentType getType()
    {
        return type;
    }

    /**
     * Sets this node's <code>type</code> to the input
     * 
     * @param type
     * The input that <code>type</code> will be set to
     */
    public void setType(ComponentType type)
    {
        this.type = type;
    }

    /**
     * Returns the <code>bigType</code> of this node
     * 
     * @return
     * Returns <code>bigType</code>
     */
    public Type getBigType()
    {
        return bigType;
    }

    /**
     * Sets this node's <code>bigType</code> to the input
     * 
     * @param bigType
     * The input that <code>bigType</code> will be set to
     */
    public void setBigType(Type bigType)
    {
        this.bigType = bigType;
    }

    /**
     * Returns the parent of this node
     * 
     * @return
     * Returns <code>parent</code>
     */
    public FXTreeNode getParent()
    {
        return parent;
    }

    /**
     * Sets this node's <code>parent</code> to the input
     * 
     * @param parent
     * The input that <code>parent</code> will be set to
     */
    public void setParent(FXTreeNode parent)
    {
        this.parent = parent;
    }

    /**
     * Returns this node's children array
     * 
     * @return
     * Returns <code>children</code>
     */
    public FXTreeNode[] getChildren()
    {
        return children;
    }

    /**
     * Sets this node's children array to the input
     * 
     * @param children
     * The input that <code>children</code> will be set to
     */
    public void setChildren(FXTreeNode[] children)
    {
        this.children = children;
    }

    /**
     * Returns the maximum amount of children this node can have
     * 
     * @return
     * Returns <code>maxChildren</code>
     */
    public int getMaxChildren()
    {
        return maxChildren;
    }

    /**
     * Returns the amount of children in this node's children array
     * 
     * @return
     * Returns <code>size</code>
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Sets <code>size</code> to the input
     * 
     * @param size
     * The input that <code>size</code> will be set to
     */
    public void setSize(int size)
    {
        this.size = size;
    }

    /**
     * Returns this node's index for its save representation
     * 
     * @return
     * Returns <code>index</code>
     */
    public String getIndex()
    {
        return index;
    }

    /**
     * Returns this node's <code>height</code>
     * 
     * @return
     * Returns <code>height</code>
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Sets this node's <code>height</code> to the input
     * 
     * @param height
     * The input <code>height</code> will be set to
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Sets this node's <code>index</code> to the input
     * 
     * @param index
     * The input that <code>index</code> will be set to
     */
    public void setIndex(String index)
    {
        this.index = index;
    }

    /**
     * Returns this node's indicator
     * 
     * @return
     * Returns <code>indicator</code>
     */
    public String getIndicator()
    {
        return indicator;
    }

    /**
     * Sets this node's indicator to the input
     * 
     * @param indicator
     * The input that <code>indicator</code> will be set to
     */
    public void setIndicator(String indicator)
    {
        this.indicator = indicator;
    }

    /**
     * Returns a <code>String</code> representation of this node's
     * <code>ComponentType</code>
     * 
     * @return
     * Returns a <code>String</code> representation of this node's
     * <code>ComponentType</code>
     */
    public String typeToString()
    {
        String tempString = "";

        switch (type)
        {
        case AnchorPane:
            tempString = "AnchorPane";
            break;
        case Button:
            tempString = "Button";
            break;
        case HBox:
            tempString = "HBox";
            break;
        case Label:
            tempString = "Label";
            break;
        case TextArea:
            tempString = "TextArea";
            break;
        case VBox:
            tempString = "VBox";
            break;
        }
        return tempString;
    }
}
