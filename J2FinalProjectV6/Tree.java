

/**
 * Very minimalistic generic binary tree class
 * I started by using a nested Node class but ultimately
 * found this a lot easier to work with
 * 
 * @author Christian Bonin
 * @version 5/7/2019
 */
public class Tree<V>
{
    // instance variables - replace the example below with your own
    V value;
    Tree<V> lhs;
    Tree<V> rhs;
    
    /**
     * Constructor for objects of generic Tree class
     */
    public Tree(V value)
    {
        this.value = value;
        lhs = null;
        rhs = null;
    }
    
    public void addLeft(V value)
    {
        Tree<V> lhs = new Tree<V>(value);
        this.lhs = lhs;
    }
        
    public void addRight(V value)
    {
        Tree<V> rhs = new Tree<V>(value);
        this.rhs = rhs;
    }

}
