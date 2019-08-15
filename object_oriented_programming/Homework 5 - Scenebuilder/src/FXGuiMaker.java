import java.io.FileNotFoundException;

import java.util.Scanner;
/**
 * The <code>FXGuiMaker</code> class takes in a text file, generates a
 * <code>FXComponentTree</code>, and provides an interface for a user to
 * manipulate the tree. The tree can be printed and can augmented with new
 * children or decreased in size by removing children. Its children can be
 * edited. The manipulated tree can also be saved to a new text file.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 *
 */

public class FXGuiMaker
{
    // Instantiates the tree that will be modified by the program
    static FXComponentTree tree = new FXComponentTree();
    // Indicates whether a tree has been loaded by the program
    static boolean loaded;

    /**
     * The main method of the <code>FXGuiMaker</code> class that allows the
     * user to load, print, save, and edit the tree by adding, deleting, and
     * editing existing children
     */
    public static void main(String[] args)
    {
        Scanner inputScanner = new Scanner(System.in);
        String choice;
        boolean on = false;
        loaded = false;

        while (on == false)
        {
            System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
                "L: Load from file", "P: Print",
                "C: Cursor to Child (Index Number)",
                "A: Add Child (Index, Type, Prompt for Text)",
                "U: Cursor Up" + " (To Parent)", "E: Edit Text",
                "D: Delete Child (Index Number)", "R: Cursor to Root",
                "S: Save to Text File", "X: Export to FXML File", "Q: Quit");
            choice = inputScanner.nextLine();
            switch (choice)
            {
            case "l":
                loadFile();
                break;
            case "s":
                writeFile(tree);
                break;
            case "p":
                System.out.println(tree.toString());
                break;
            case "c":
                moveCursorToChild(tree);
                break;
            case "r":
                moveCursorToRoot(tree);
                break;
            case "a":
                try
                {
                    addChild(tree);
                }
                catch (Exception e)
                {
                    System.out.println("The root can only hold 1 child.");
                }
                break;
            case "u":
                moveCursorUp(tree);
                break;
            case "e":
                editCursorText(tree);
                break;
            case "d":
                deleteChild(tree);
                break;
            case "q":
                System.exit(0);
                break;
            }
        }
    }

    /**
     * Adds a child to the children array of the cursor of the loaded tree.
     * 
     * @param tree
     * The tree that is being modified
     * 
     * <dt>Precondition:
     *    <dd>A tree must have already been loaded. The cursor must be at a
     *    container node and the node must have less than 10 children. If the
     *    node is the root, then the root must have less than 1 child.
     *    
     * <dt>Postcondition:
     *    <dd>A child with the given specification is added to the tree in the
     *    cursor's children array if the requirements are met. If the tree was
     *    not loaded, then the user is notified as such. If the cursor was not
     *    at a container node or the node already has 10 children, then the
     *    user is notified. If the node already has one root, then the
     *    <code>RootFullException</code> is thrown.
     *    
     * @throws RootFullException
     * Indicates that the root is full
     */
    private static void addChild(FXComponentTree tree) throws RootFullException
    {
        if (loaded == false)
        {
            System.out.println("A tree has not been loaded yet");
            return;
        }
        
        if (tree.getCursor() == tree.getRoot())
        {
            if (tree.getRoot().getSize() >= 1)
                throw new RootFullException("Root can only hold 1 child");
        }

        Scanner scan = new Scanner(System.in);
        String text = "";
        String component = "";
        int index = -1;
        ComponentType type = null;

        while (type == null)
        {
            System.out
                .println("Please select a component type: H) HBox, V) VBox,"
                    + "T) TextArea, B) Button, L) Label");
            component = scan.nextLine().toLowerCase();
            text = "";

            switch (component)
            {
            case "v":
                type = ComponentType.VBox;
                break;
            case "h":
                type = ComponentType.HBox;
                break;
            case "l":
                type = ComponentType.Label;
                break;
            case "b":
                type = ComponentType.Button;
                break;
            case "t":
                type = ComponentType.TextArea;
                break;
            }
            if (type == null)
                System.out.println("That was an invalid type.");
        }

        if (type == ComponentType.Button || type == ComponentType.TextArea
            || type == ComponentType.Label)
        {
            System.out.println("Please input the text of the component");
            text = scan.nextLine();
        }

        FXTreeNode node = new FXTreeNode(text, type);

        System.out.println("Please enter an index (starting with 1): ");

        try
        {
            index = scan.nextInt();
            index--;
            tree.addChild(index, node);
            tree.setCursor(node);
            // Sets the node's index to its saved representation
            node.setIndex(node.getParent().getIndex() + "-" + index);
        }
        catch (ArrayHoleException e)
        {
            // TODO Auto-generated catch block
            System.out.println(
                "Adding a child there would create a hole in the" + " array");
        }
        catch (InvalidIndexException e)
        {
            System.out.println("The input index was invalid.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Invalid input index.");
        }
    }

    /**
     * Deletes the specified child from the cursor.
     * 
     * @param tree
     * The tree being modified
     * 
     * <dt>Precondition:
     *    <dd>The tree must be loaded and the cursor must be a container with
     *    at least one child to delete.
     *    
     * <dt>Postcondition:
     *    <dd>The specified child is deleted from the cursor's children array.
     *    If the tree was not loaded or the cursor was not at a container or
     *    did not have any children to delete, then the user is notified.
     */
    private static void deleteChild(FXComponentTree tree)
    {
        if (loaded == false)
        {
            System.out.println("A tree has not been loaded yet");
            return;
        }
        FXTreeNode cursor = tree.getCursor();
        if (cursor.getBigType() != Type.Container)
        {
            System.out.println("The cursor is at a node that is not a container"
                + ". Controls do not have children to remove.");
            return;
        }
        System.out.println("Please input the index of the child you want to"
            + " remove from the cursor's children array (Starting from 1):");
        Scanner scan = new Scanner(System.in);
        int index = -1;

        try
        {
            index = scan.nextInt();
            FXTreeNode node = tree.getCursor().getChildren()[index - 1];
            tree.deleteChild(index - 1);
            System.out.println(node.typeToString() + " has been removed.");

        }
        catch (InvalidIndexException e)
        {
            System.out.println("The input index was invalid");
        }
        catch (Exception e)
        {
            System.out.println("That was an invalid index");
        }

    }

    /**
     * Edits the text of the node currently being referenced by the cursor of
     * the tree.
     * 
     * @param tree
     * The tree being modified
     * 
     * <dt>Precondition:
     *    <dd>The tree must be loaded and the cursor must be a control.
     *    
     * <dt>Postcondition:
     *    <dd>The cursor's text is changed to the specified text. If the tree
     *    was not loaded or the cursor was not a control, then the user is
     *    notified as such.
     */
    private static void editCursorText(FXComponentTree tree)
    {
        if (loaded == false)
        {
            System.out.println("A tree has not been loaded yet");
            return;
        }
        FXTreeNode cursor = tree.getCursor();
        if (cursor.getBigType() == Type.Container)
        {
            System.out.println("The cursor is at a container and containers do"
                + " not have text to edit");
            return;
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter new text:");
        cursor.setText(scan.nextLine());
        System.out.println("The text has been edited");
    }

    /**
     * Moves the cursor to the root
     * 
     * @param tree
     * The tree being modified
     * 
     * <dt>Precondition:
     *    <dd>The tree must be loaded
     *    
     * <dt>Postcondition:
     *    <dd>The cursor is moved to the root. If the tree wasn't loaded, then
     *    the user is notified.
     */
    private static void moveCursorToRoot(FXComponentTree tree)
    {
        if (loaded == false)
        {
            System.out.println("A tree has not been loaded yet");
            return;
        }
        if (tree.getCursor() == tree.getRoot())
        {
            System.out.println("The cursor is already at the root of the tree");
        }
        else
        {
            tree.cursorToRoot();
            System.out.println("Cursor moved to root.");
        }
    }

    /**
     * Moves the cursor up to its parent
     * 
     * @param tree
     * The tree being modified
     * 
     * <dt>Precondition:
     *    <dd>The cursor must have a parent (is not the root). The tree must be
     *    loaded.
     *    
     * <dt>Postcondition:
     *    <dd>The cursor is moved to its parent. If the cursor was at the root
     *    or if the tree wasn't loaded, then the user is notified as such.
     */
    private static void moveCursorUp(FXComponentTree tree)
    {
        if (loaded == false)
        {
            System.out.println("A tree has not been loaded yet");
            return;
        }
        if (tree.getCursor() == tree.getRoot())
        {
            System.out.println("The cursor is already at the root of the tree");
        }
        else
            tree.cursorToParent();
    }

    /**
     * Moves the cursor to the specified child in the cursor's children array.
     * 
     * @param tree
     * The tree being modified
     * 
     * <dt>Precondition:
     *    <dd>The tree must be loaded and the cursor must be at a container
     *    node. The node must have a child to move to in its children array.
     *    
     * <dt>Postcondition:
     *    <dd>The cursor is moved to the specified child of the cursor's
     *    children array. If the tree was not loaded or the cursor was not at
     *    a container node or the node had no children, then the user is
     *    notified as such.
     */
    private static void moveCursorToChild(FXComponentTree tree)
    {
        if (loaded == false)
        {
            System.out.println("A tree has not been loaded yet");
            return;
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input the index that you want to move the"
            + " cursor to: (Starting from 1):");

        try
        {
            int index = scan.nextInt();
            index--;
            tree.cursorToChild(index);
        }
        catch (Exception e)
        {
            System.out.println("Invalid index.");
        }

    }

    /**
     * Creates a text file that holds the content of the current loaded tree.
     * 
     * @param tree
     * The tree being saved
     * 
     * <dt>Postcondition:
     *    <dd>A text file is created that holds the content of the loaded tree.
     */
    private static void writeFile(FXComponentTree tree)
    {
        try
        {
            tree.writeToFile(tree);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Loads the tree in the specified text file
     * 
     * <dt>Precondition:
     *    <dd>The text file must be in the same folder where the program is
     *    located and the text file must be in the proper format.
     * 
     * <dt>Postcondition:
     *    <dd>The text file's tree is generated and loaded into the program. If
     *    the text file was not found or the text file was not saved in the
     *    correct format, then the user is notified as such and the current
     *    tree is unchanged.
     */
    private static void loadFile()
    {
        FXComponentTree oldTree = tree;
        FXComponentTree tempTree = new FXComponentTree();
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file being loaded"
            + " (with .txt extension)");
        String fileName = scan.nextLine();
        boolean preload = loaded;
        try
        {
            tree = tempTree.readFromFile(fileName);
            loaded = true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println(fileName + " was not found.");
            tree = oldTree;
            if (preload == false)
                loaded = false;
            else
                loaded = true;
        }
        catch (NullPointerException | ArrayHoleException |
            InvalidIndexException e)
        {
            System.out.println("The format of " + fileName
              + " was incorrect.");
            tree = oldTree;
            if (preload == false)
                loaded = false;
            else
                loaded = true;
        }
    }
}
