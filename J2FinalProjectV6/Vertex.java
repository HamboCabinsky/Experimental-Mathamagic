import java.util.ArrayList;


/**
 * Write a description of class Vertex here.
 *
 * @author Christian Bonin
 * @version 5/15/2019
 */
public class Vertex
{
    // instance variables - replace the example below with your own
    public int x;
    public int y;
    
    /**
     * Constructor for objects of class Vertex
     */
    public Vertex(int x, int y)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
    }
    
    /**
     * returns distance between this vertex and another given vertex
     */
    public int weight(Vertex other)
    {
        return (int)Math.sqrt(Math.pow(Math.abs(x-other.x),2)+Math.pow(Math.abs(y-other.y),2));
    }
    
}
