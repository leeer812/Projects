import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The <code>FXComponentTree</code> class serves as the tree manager for the
 * <code>FXComponentTree</code> The class holds references to the root and
 * cursor and is able to generate and save the tree to and from a file. Children
 * can be added and removed from the tree at the cursor. The cursor can be moved
 * up to the cursor's parents or down to the cursor's children. The cursor's
 * text can also be edited.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 */

public class FXComponentTree
{
    private FXTreeNode root;
    private FXTreeNode cursor;

    /**
     * Returns an instance of the <code>FXComponentTree</code>
     * <dt>Postcondition:
     * <dd>The root is instantiated and the cursor is set to root.
     */
    public FXComponentTree()
    {
        root = null;
        cursor = root;
    }

    /**
     * Moves the cursor to the root.
     */
    public void cursorToRoot()
    {
        cursor = root;
    }

    /**
     * Validates the input <code>index</code>
     * 
     * <dt>Precondition:
     * <dd>The input index must be within 0 to the maximum amount of allowed
     * children by <code>FXTreeNode</code>, denoted by <code>maxChildren</code>
     * 
     * @param index
     * The input <code>index</code>
     * 
     * <dt>Postcondition:
     *    <dd>If the input index was not within 0 to 10, then an
     *    <code>InvalidIndexException</code> is thrown.
     * 
     * @throws InvalidIndexException
     * Indicates the input index was invalid
     */
    public void checkIndex(int index) throws InvalidIndexException
    {
        if (index < 0 || index > cursor.getMaxChildren())
        {
            throw new InvalidIndexException(
                "The input index must be within 1" + " to 10");
        }
    }

    /**
     * Removes the input <code>FXTreeNode</code>'s children
     * 
     * <dt>Precondition:
     *     <dd>The input node must be a container or have at least one child.
     * 
     * @param node
     * The input <code>FXTreeNode</code> whose children will be removed
     *    
     * <dt>Postcondition:
     *    <dd>All of the node's children and its children's children are
     *    removed.
     */
    public void removeChild(FXTreeNode node)
    {
        // If the node is not a container, then do nothing
        if (!node.isContainer())
        {
            return;
        }
        // If the node has no children, then do nothing
        if (node.getSize() == 0)
            return;
        // Go through each of the nodes in its children array
        for (int i = 0; i < node.getSize(); i++)
        {
            // Remove all the children in the current node's children arrays
            removeChild(node.getChildren()[i]);
            // Set the child of the current node in children[i] to null
            node.getChildren()[i] = null;
        }
        node.setSize(node.getSize()-1);
    }

    /**
     * Deletes the child of the cursor at the specified index. All of the
     * children of the child are also deleted.
     * 
     * <dt>Precondition:
     *    <dd>The input index must be valid.
     *    
     * @param index
     * The input <code>index</code>
     *    
     * @throws InvalidIndexException
     * Indicates that the input index was invalid
     */
    public void deleteChild(int index) throws InvalidIndexException
    {
        checkIndex(index);
        // Removes the child at the specified index if there is one
        if (cursor.getChildren()[index] != null)
        {
            cursor.removeChildAtIndex(index);
            cursor.setSize(cursor.getSize() - 1);
        }
        else
            System.out.println("There is no child to remove at that index.");
    }

    /**
     * Adds a child to the cursor's children array at the specified index.
     * 
     * <dt>Precondition:
     *    <dd>The cursor must be a container and the cursor's children array
     *    must not be full.
     * 
     * @param index
     * The input index
     *    
     * @param node
     * The <code>FXTreeNode</code> that will be added to the cursor's children
     * array.
     * 
     * @throws ArrayHoleException
     * Indicates that the input index was too high and a hole would be created
     * in the cursor's children array if a node was added there.
     * 
     * @throws InvalidIndexException
     * Indicates the input index was invalid
     */
    public void addChild(int index, FXTreeNode node)
        throws ArrayHoleException, InvalidIndexException
    {
        if (!cursor.isContainer())
        {
            System.out.println("The node at the cursor is not a container");
            return;
        }
        if (cursor.getSize() == cursor.getMaxChildren())
        {
            System.out.println("The node's children array is full");
            return;
        }
        checkIndex(index);
        if (cursor.getSize() < index)
            throw new ArrayHoleException(
                "Adding a node here would cause a" + " hole in the array");
        /* If the node is not being added to the last empty spot in the array,
         * then all of the nodes after the input index must be moved back one. 
         * The respective method for this is called. If the node is being added
         * to the last empty spot in the array, then no nodes need to be moved
         * before it is added.
         */
//        if (cursor.getSize() != index)
//        {
            cursor.addChildToIndex(node, index);
//        }
//        else
//            cursor.addChild(node);
    }

    /**
     * Sets the current node’s text to the specified text.
     * 
     * @param text
     * The input <code>text</code> that the node's text will be set to
     */
    public void setTextAtCursor(String text)
    {
        if (cursor.getBigType() != Type.Control)
            return;
        cursor.setText(text);
    }

    /**
     * Moves the cursor to the child node of the of the cursor corresponding to
     * the specified index.
     * 
     * <dt>Precondition:
     *    <dd>The input index must be valid. The cursor must be a container and
     *    there must be a child at the specified index of the cursor's children
     *    array.
     * 
     * @param index
     * The index of the the child array that <code>cursor</code> will
     * be moved to
     * 
     * <dt>Postcondition:
     *    <dd>The cursor is moved to the child at the specified index of its
     *    children array. If the input index was invalid then
     *    <code>InvalidIndexException</code> is thrown. If the cursor was not a
     *    container or if it did not have a child at the specified index, then
     *    the user is notified as such.
     * 
     * @throws InvalidIndexException
     * Indicates the input index was invalid
     */
    public void cursorToChild(int index) throws InvalidIndexException
    {
        checkIndex(index);
        if (cursor.getBigType() != Type.Container)
        {
            System.out.println("The cursor is at a control and controls do not"
                + "have children to move to");
            return;
        }
        if (cursor.getChildren()[index] != null)
            cursor = cursor.getChildren()[index];
        else
        {
            System.out.println("The input index does not have a child in it.");
            return;
        }
        moveNotification();
    }

    /**
     * Moves the cursor to the parent of the current node.
     */
    public void cursorToParent()
    {
        cursor = cursor.getParent();
        moveNotification();
    }

    /**
     * Notifies the user that the cursor has been moved to a new node.
     */
    private void moveNotification()
    {
        Type t = cursor.getBigType();
        if (t == Type.Control)
            System.out.println("The cursor has been moved to "
                + cursor.typeToString() + ": " + cursor.getText() + ".");
        else
            System.out.println(
                "The cursor has been moved to " + cursor.typeToString() + ".");
    }

    /**
     * Generates the FXComponentTree based on the file name that is passed in.
     * 
     * <dt>Precondition:
     *    <dd>There must be a file with the given file name in the same folder
     *    as the program files. The file must be formatted in the same way as
     *    prompted in the instructions.
     * 
     * @param filename
     * The file name that the program will try to locate to generate a tree
     * 
     * @return
     * Returns a fully built <code>FXComponentTree</code> based on the text
     * file passed into the program.
     * 
     * <dt>Postcondition:
     *    <dd>A fully built tree is returned with the specifications that the
     *    input file had. If the input file was not found, then the
     *    <code>FileNotFoundException</code> is thrown. If the format of the
     *    file was wrong, then either an <code>InvalidIndexException</code> or
     *    <code>ArrayHoleException</code> will be thrown.
     * 
     * @throws InvalidIndexException
     * Indicates the input index was invalid
     * 
     * @throws ArrayHoleException
     * Indicates that there would have been a hole if a node was added to the
     * specified index of the node's children array
     * 
     * @throws FileNotFoundException
     * Indicates that there was no file found with the given file name.
     */

    public FXComponentTree readFromFile(String filename)
        throws ArrayHoleException, InvalidIndexException, FileNotFoundException
    {
        FXComponentTree tempTree = new FXComponentTree();
        // Stores the given file's contents in a File
        File file = new File(filename);
        Scanner input = new Scanner(file);

        // Reads the file until there is no more content
        while (input.hasNextLine())
        {
            String thisLine = input.nextLine();
            if (!thisLine.isEmpty())
            {
                String[] indices = null;
                String component = "", text = "";
                ComponentType type = null;

                String rawIndex = "";
                // Splits the current line by spaces
                String[] line = thisLine.split(" ");
                // Traverses entire line array
                for (int i = 0; i < line.length; i++)
                {
                    // The index is first in the array and is split by '-'
                    // The node's index will be set to indices
                    if (i == 0)
                    {
                        rawIndex = line[i];
                        indices = rawIndex.split("-");
                    }
                    // The component type is second in the array.
                    else if (i == 1)
                    {
                        component = line[i];
                        switch (component.toLowerCase())
                        {
                        case "anchorpane":
                            type = ComponentType.AnchorPane;
                            break;
                        case "vbox":
                            type = ComponentType.VBox;
                            break;
                        case "hbox":
                            type = ComponentType.HBox;
                            break;
                        case "label":
                            type = ComponentType.Label;
                            break;
                        case "button":
                            type = ComponentType.Button;
                            break;
                        case "textarea":
                            type = ComponentType.TextArea;
                            break;
                        }
                    }
                    // The text is split by spaces and is after the indices and
                    // component. The text is augmented by the content in the
                    // line array until there is no more content.
                    else
                    {
                        if (i == line.length - 1)
                            text += line[i];
                        else
                            text += line[i] + " ";
                    }
                }

                // Creates a new node with the input content
                FXTreeNode node = new FXTreeNode(text, type);
                node.setIndex(rawIndex);

                // Moves the cursor back to the root. If the root was null,
                // then set the root to the new node.
                tempTree.cursorToRoot();
                if (tempTree.getCursor() == null)
                {
                    node.setHeight(0);
                    tempTree.setRoot(node);
                }
                /*
                 * Traverses the children array by moving the cursor to the
                 * specified index of the cursor's children array until the
                 * last index of the indices array is accessed. The new node is
                 * added to the specified index of the cursor's children array.
                 */
                else
                {
                    for (int i = 1; i < indices.length; i++)
                    {
                        int index = Integer.parseInt(indices[i]);
                        if (i == indices.length - 1)
                            tempTree.addChild(index, node);
                        else
                            tempTree.setCursor(
                                tempTree.getCursor().getChildren()[index]);
                    }
                }
            }
        }
        // Moves the cursor back to the root
        tempTree.cursorToRoot();
        System.out.println(filename + " has been loaded.");
        return tempTree;
    }

    /**
     * Generates a text file that reflects the structure of the FXComponentTree.
     * The format of the tree of the file should match the format of the input
     * file.
     * 
     * @param tree
     * The tree that will be written to a text file.
     * 
     * <dt>Postcondition:
     *    A text file with the specified fileName is created with the exact
     *    representation needed to recreate the current tree.
     * 
     * @throws FileNotFoundException
     * Indicates there was no file found with the specified file name
     */
    public static void writeToFile(FXComponentTree tree)
        throws FileNotFoundException
    {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please input the name of the file to write to"
            + "(with .txt extension)");

        String fileName = scan.nextLine();
        File newFile = new File(fileName);
        PrintWriter printer = new PrintWriter(newFile);

        printer.write(tree.saveFile());
        printer.close();

        Scanner file = new Scanner(newFile);
        File tempFile = new File("temp.txt");
        PrintWriter writer = new PrintWriter(tempFile);

        while (file.hasNext())
        {
            String line = file.nextLine();
            if (!line.isEmpty())
            {
                writer.println(line);
            }
        }

        file.close();
        writer.close();

        newFile.delete();
        tempFile.renameTo(newFile);

        System.out.println("Your file has been created!");
    }

    /**
     * // Extra Credit Postconditions: A valid FXML file that can be opened in
     * SceneBuilder has been created
     * 
     * @param tree
     */
    public static void exportToFXML(FXComponentTree tree)
    {

    }

    /**
     * The <code>toString</code> helper method for displaying a visual
     * representation of the tree.
     * 
     * @param node
     * The node that is currently being printed.
     * 
     * @return
     * Returns a string representation of the current node
     */
    public String toStringHelp(FXTreeNode node)
    {
        if (node == null)
            return "";
        String tempString = "";
        tempString += node.visualString(this);
        for (int i = 0; i < node.getSize(); i++)
            tempString += toStringHelp(node.getChildren()[i]);
        return tempString;
    }

    /**
     * The <code>toString</code> helper method for saving the tree
     * 
     * @param node
     * The node that is currently being saved
     * 
     * @return
     * Returns a saved representation of the current node
     */
    
    public String saveHelper(FXTreeNode node)
    {
        if (node == null)
            return "";
        String tempString = "";
        tempString += node.toString();
        for (int i = 0; i < node.getSize(); i++)
            tempString += saveHelper(node.getChildren()[i]);
        return tempString;
    }

    /**
     * Returns the root of the tree
     * 
     * @return
     * Returns the root of the tree
     */
    public FXTreeNode getRoot()
    {
        return root;
    }

    /**
     * Sets the root of the tree to the input node
     * 
     * @param root
     * The input node that the root will be set to
     */
    public void setRoot(FXTreeNode root)
    {
        this.root = root;
    }

    /**
     * Returns the cursor of the tree
     * 
     * @return
     * Returns the cursor of the tree
     */
    public FXTreeNode getCursor()
    {
        return cursor;
    }

    /**
     * Sets the cursor to the input node
     * 
     * @param cursor
     * The input node that cursor will be set to
     */
    public void setCursor(FXTreeNode cursor)
    {
        this.cursor = cursor;
    }

    /**
     * Returns a visual <code>String</code> representation of this tree
     * 
     * @return
     * Returns a <code>String</code> visually representing the tree
     */
    public String toString()
    {
        String tempString = "";
        FXTreeNode tempCursor = root;
        tempString += toStringHelp(tempCursor);
        return tempString;
    }

    /**
     * Returns a saved <code>String</code> representation of this tree
     * 
     * @return
     * Returns a <code>String</code> representing how the tree will be saved
     */
    public String saveFile()
    {
        String tempString = "";
        FXTreeNode tempCursor = root;
        tempString += saveHelper(tempCursor);
        return tempString;
    }
}
