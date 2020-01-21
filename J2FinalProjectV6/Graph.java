import java.util.ArrayList;
import java.util.Arrays;
/**
 * Write a description of class Graph here.
 *
 * @author Christian Bonin
 * @version 5/15/2019
 */
public class Graph
{
    private int[][] adjMatrix;
    private int size = 0;
    public ArrayList<Vertex> vertexes;
    
    /**
     * Constructor for objects of class Graph
     */
    public Graph()
    {
        adjMatrix = new int[0][0];
        vertexes = new ArrayList<Vertex>();
    }

    public void addVertex(int x, int y)
    {
        int[][] matrix = new int[size+1][size+1];
        for(int i = 0; i < size; i++)
        {
            matrix[i] = Arrays.copyOf(adjMatrix[i], size+1);
        }
        adjMatrix = matrix;
        size++;
        for(int i = 0; i < size; i++)
        {
            adjMatrix[size-1][i] = 0;
        }
        vertexes.add(new Vertex(x, y));
    }
    
    public void connect(int u, int v)
    {
        if(u < size && v < size){
            adjMatrix[u][v] = 1;
            adjMatrix[v][u] = 1;
        }
    }
    
    public int size(){
        return size;
    }
    
    public Vertex get(int i)
    {
        return vertexes.get(i);
    }
    
    public int[][] getMatrix()
    {
        return adjMatrix;
    }
}
