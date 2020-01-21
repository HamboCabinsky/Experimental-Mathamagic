import java.util.ArrayList;
import java.util.Queue;
/**
 * Write a description of class MemoryQueue here.
 *
 * @author Christian Bonin
 * @version 5/7/2019
 */
public class MemoryQueue<E> extends ArrayList<E>
{
    // instance variables - replace the example below with your own
    private int maxSize;
    public static final int DEFAULT_MAX_SIZE = 30;
    
    /**
     * Arraylist adds elements at the end but still has easy access to them
     * I wanted something that would delete overflowing elements automatically
     * but could quickly recall the most recently added
     * Removing elements in chunks of 10 for the sake of efficiency
     */
    public MemoryQueue()
    {
        maxSize = DEFAULT_MAX_SIZE;
    }
    
    public MemoryQueue(int maxSize)
    {
        this.maxSize = maxSize;
    }
    
    public boolean add(E elem){
        super.add(elem);
        if(size() >= maxSize)
            removeRange(0, 10);
        return true;
    }
    
    public E recall(int depth)
    {
        int last = size()-1;
        return get(last-depth);
    }
}
