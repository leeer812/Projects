/**
 * <code>Type</code> is a custom <code>enum</code> with 2 different
 * available <code>Type</code>s that a <code>FXTreeNode</code> can have. A
 * <code>FXTreeNode</code> can only have one <code>ComponentType</code> at a
 * time. <code>Type</code> is instantiated when a <code>FXTreeNode</code> is
 * instantiated and is set to <code>Control</code> if its
 * <code>ComponentType</code> is <code>Button</code>, <code>Label</code> or
 * <code>TextArea</code>. If its <code>ComponentType</code> is
 * <code>HBox</code>, <code>VBox</code> or <code>AnchorPane</code> then
 * <code>Type</code> is set to <code>Container</code>.
 * 
 * @author Ernest Lee
 * ID: 111075566
 * Homework #5
 * CSE214-R02
 * TA: David S. Li
 */
public enum Type
{
    Container, Control;
}
