import java.awt.Color;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Vector;
import java.util.Stack;
import java.util.BitSet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write a description of class InputProcessor here.
 *
 * @author Christian Bonin
 * @version 5/6/2019
 */

public class InputProcessor
{
    private int xPos, yPos;         //current print position
    private Tree<String> tree;
    private String[] setA;
    private String[] setB;
    private String[] setC;
    private String[] uni;
    private BitSet bitSetA;
    private BitSet bitSetB;
    private BitSet bitResult;
    private Graph graph;
    
    /**
     * Initializes coords to top left of the window
     * Makes an instance of a graph to store in graph variable
     */
    public InputProcessor()
    {
        xPos = 10;
        yPos = 20;
        graph = new Graph();
    }

    /**
     * arguments are split into an array by whitespace, first element is command
     * following elements are arguments
     * display is the class containing Jframe and Canvas
     */
    public void processToDisplay(String input, Display display) throws IOException
    {
        String[] arr = input.split("\\s"); //splits input into an array by whitespace 
        
        if(arr[0].equals("help"))
        {
            help(display);
            return;
        }
        
        if(arr[0].equals("bp")){
            if(arr.length < 2)
            {
                bp("Usage bp [String toPrint]", display);
                return;
            }
            String toPrint = "";
            for(int i = 1; i < arr.length; i++)
                toPrint += arr[i] + " ";
            bp(toPrint, display);
        }
        
        if(arr[0].equals("binomexp")){
            binomExp(arr, display);
            return;
        }
        
        if(arr[0].equals("mkset")){
            mkSet(arr, display);
            return;
        }
        
        if(arr[0].equals("mkvertex")){
            mkVertex(arr, display);
            return;
        }
        
        if(arr[0].equals("connect")){
            connect(arr, display);
            return;
        }
        
        if(arr[0].equals("repaintgraph")){
            repaintGraph(display);
            return;
        }
        
        if(arr[0].equals("spantree")){
            spanningTree(display);
            return;
        }
        
        if(arr[0].equals("cleargraph")){
            graph = new Graph();
            return;
        }
        
        if(arr[0].equals("powerset"))
        {
            powerSet(arr, display);
            return;
        }
        
        if(arr[0].equals("powersettofile"))
        {
            powerSetToFile(arr, display);
            return;
        }
        
        if(arr[0].equals("setcombos"))
        {
            setCombos(arr, display);
            return;
        }
        
        if(arr[0].equals("setcombostofile"))
        {
            setCombosToFile(arr, display);
            return;
        }
        
        if(arr[0].equals("setpermutes"))
        {
            setPermutes(arr, display);
            return;
        }
        
        if(arr[0].equals("setpermutestofile"))
        {
            setPermutesToFile(arr, display);
            return;
        }
        
        if(arr[0].equals("mergesets"))
        {
            if(arr.length < 3)
            {
                bp("Usage: mergeSets [Set A|B|C] [Set A|B|C]", display);
                return;
            }
            
            mergeSets(arr, display);
            return;
        }
        
        if(arr[0].equals("bits"))
        {
            bits(arr, display);
            return;
        }
        
        if(arr[0].equals("xorsets"))
        {
            if(arr.length < 3)
            {
                bp("Usage: xorSets [Set A|B|C] [Set A|B|C]", display);
                return;
            }
            xorSets(arr, display);
        }
        
        if(arr[0].equals("orsets"))
        {
            if(arr.length < 3)
            {
                bp("Usage: orSets [Set A|B|C] [Set A|B|C]", display);
                return;
            }
            orSets(arr, display);
        }
        
        if(arr[0].equals("andsets"))
        {
            if(arr.length < 3)
            {
                bp("Usage: andSets [Set A|B|C] [Set A|B|C]", display);
                return;
            }
            andSets(arr, display);
        }
        
        if(arr[0].equals("orbits"))
        {
            if(bitSetA != null && bitSetB != null)
                bitResult = orBits(display);
            else
                bp("BitSets must be created first", display);
            return;
        }
        
        if(arr[0].equals("xorbits"))
        {
            if(bitSetA != null && bitSetB != null)
                bitResult = xorBits(display);
            else
                bp("BitSets must be created first", display);
            return;
        }
        
        if(arr[0].equals("andbits"))
        {
            if(bitSetA != null && bitSetB != null)
                bitResult = andBits(display);
            else
                bp("BitSets must be created first", display);
            return;
        }
        
        if(arr[0].equals("bitstoset"))
        {
            if(arr.length < 2){
                bp("Usage: bitsToSet [Universal Set A|B|C] (bit result must already exist)", display);
                return;
            }
            bitsToSet(arr[1], display);
            return;
        }
        
        // BITTREE
        if(arr[0].equals("bittree")){
            if(arr.length < 2){
                bp("Usage: bitTree [depth]", display);
                return;
            }
            bitTree(Integer.parseInt(arr[1]), display);
            return;
        }
        
        // TREE
        if(arr[0].equals("tree")){
            if(arr.length < 2 || arr.length > 4)
            {
                bp("Usage: tree [value] [opt: lhs] [opt: rhs]", display);
                return;
            }
            visualTree(arr, display);
            return;
        }
        
        //APPEND TO TREE
        if(arr[0].equals("appendtree")){
            if(arr.length < 2)
            {
                bp("Usage: appendTree [valueToAppend] [<-] travs left [->] travs right", display);
                return;
            }
            appendTree(arr, display);
            return;
        }
        
        //NUMBER PERMUTATIONS
        if(arr[0].equals("permute")){
           permute(arr, display);
           return;
        }
        
        //NUMBER COMBINATIONS
        if(arr[0].equals("combo") || arr[0].equals("combination")){
           combo(arr, display);
           return;
        }
        
        //FACTORIAL
        if(arr[0].equals("factorial")){
            factorial(arr, display);
            return;
        }
        
        //moves print location to new x and y position
        if(arr[0].equals("move") || arr[0].equals("mv")){
            move(arr, display);
            return;
        }
        
        //clears screen
        if(arr[0].equals("clear")){
            display.clear();
            xPos = 10;
            yPos = 20;
            return;
        }
        
        //fills screen w/ selected color
        if(arr[0].equals("fill")){
            for(int i=0; i<3; i++)
                display.fill();
            xPos = 10;
            yPos = 20;
            return;
        }
        
        //selects color
        if(arr[0].equals("setcolor")){
            if(arr.length != 2){
                bp("Usage: setColor [String color]", display);
                return;
            }
            setColor(arr[1], display);
            return;
        }
    }
    
    /**
     * Prints a listing of the most useful commands to the screen
     */
    public void help(Display display){
        xPos = 10; yPos = 20;
        bp("Commands: ", display);
        bp("permute [n# elements] [r# permutations] - gets # permutations", display);
        bp("combo [n# elements] [r# permutations] - gets # combinations", display);
        bp("mkset [A|B|C] { e1, e2, e3, ... eN } - stores sorted collection of strings, removes duplicates", display);
        bp("andSets [Set A|B|C] [Set A|B|C] - bitwise ands given sets", display);
        bp("orSets [Set A|B|C] [Set A|B|C] - bitwise ors given sets", display);
        bp("xorSets [Set A|B|C] [Set A|B|C] - bitwise xors given sets", display);
        bp("powerSet [Set A|B|C] - generates powerset of given set", display);
        bp("powerSetToFile [Set A|B|C] [filename.txt] - outputs to file instead of printing", display);
        bp("setPermutes [set A|B|C] [int r] - generates all permutations of set choosing r", display);
        bp("setPermutesToFile [set A|B|C] [int r] [filename.txt] - output set permutes to file", display);
        bp("setCombos [Set A|B|C] [int r] - gets all r combinations of set", display);
        bp("setCombosToFile [set A|B|C] [int r] [filename.txt] - output set combos to file", display);
        bp("factorial [int n] - gets factorial of given number", display);
        bp("bitTree [depth] - prints a bitree of given depth", display);
        bp("tree [value] [opt: lhs] [opt: rhs] - prints start of string tree", display);
        bp("appendTree [valueToAppend] [<] travs left [>] travs right - append vals to tree", display);
        bp("bits [Universal Set A|B|C] [Subset A|B|C] [BitSet A|B] - convert sets to bits", display);
        bp("andBits - perform and on bitset a and b", display);
        bp("orBits - perform or on bitset a and b", display);
        bp("xorBits - perform xor on bitset a and b", display);
        bp("bitsToSet [Universal Set A|B|C] - convert result of bit operation back to set", display);
        bp("binomExp [(coefaX +|- coefbY)^pow] - ex (3x + 4y)^3 - expands a binomial", display);
        xPos = 750; yPos = 20;
        bp("", display);
        bp("mkVertex [int x] [int y] - plots a vertex on graph", display);
        bp("connect [int v1] [int v2] - connects given verticies on graph", display);
        bp("repaintGraph - repaints graph onto screen", display);
        bp("spantree - paints a minimum spanning tree for current graph onto screen", display);
        bp("clearGraph - clears all data associated with current graph", display);
        bp("move [int xPos] [int yPos] - moves print location to given coords", display);
        bp("setColor [color] - sets print color", display);
        bp("clear - clears the screen", display);
        bp("fill - fills screen with current color", display);
        bp("help - display this menu again", display);
        bp("Made by Christian Bonin - last updated 5/16/2019", display);
    }
    
    /**
     * given a universal set and subset, gets its bitset representation and stores it in the given bitset a or b
     * and prints it to the screen
     */
    public void bits(String[] arr, Display display)
    {
        if(arr.length < 4)
        {
            bp("Usage: bits [Universal Set A|B|C] [Subset A|B|C] [BitSet A|B]", display);
            return;
        }
        
        String[] uni;
        String[] sub;
        
        if(arr[1].equals("a"))
            uni = setA;
        else if(arr[1].equals("b"))
            uni = setB;
        else if(arr[1].equals("c"))
            uni = setC;
        else
            uni = this.uni;
        
        if(arr[2].equals("a"))
            sub = setA;
        else if(arr[2].equals("b"))
            sub = setB;
        else
            sub = setC;
        
        BitSet bits = MathFuncts.bits(uni, sub);
       
        if(arr[3].equals("a"))
                bitSetA = bits;
            else if(arr[3].equals("b"))
                bitSetB = bits;
        
        String bitString = "";
        
        //converts the boolean bitset to a string of 0s and 1s for printing
        for(int i = 0; i < uni.length; i++)
            bitString += bits.get(i) ? "1" : "0";
        
        bp(bitString, display);
    }
    
    /**
     * Expands a binomial expression. Will first print in such a way as to show the binomial coeff separated from simplified output and then prints
     * again with simplified output.
     */
    public void binomExp(String[] arr, Display display)
    {
        String eq, result;
        if(arr.length < 2){
            bp("Usage: binomExp [(coefaX +|- coefbY)^pow] - ex (3x + 4y)^3", display);
        }
        if(arr.length == 4){
            eq = arr[1] + arr[2] + arr[3];  //if input was entered with spaces, concat it all together
        }else{
            eq = arr[1];                    //otherwise the entire expression should just be first arg
        }
        result = MathFuncts.binomExpand(eq);
        bp(result, display);
        result = MathFuncts.simplifyBinom(result);
        bp(result, display);
    }
    
    /**
     * merges two sets into a universal set
     */
    public void mergeSets(String[] arr, Display display)
    {
        String[] a;
        String[] b;
        String[] toMake;
        if(arr[1].equals("a"))
            a = setA;
        else if(arr[1].equals("b"))
            a = setB;
        else
            a = setC;
        
        if(arr[2].equals("a"))
            b = setA;
        else if(arr[2].equals("b"))
            b = setB;
        else
            b = setC;
            
        toMake = new String[a.length+b.length+2];
        toMake[0] = "mkSet";
        toMake[1] = "u";
        for(int i = 0; i < a.length; i++)
            toMake[i+2] = a[i];
        for(int i = 0; i < b.length; i++)
            toMake[i+a.length+2] = b[i];
        mkSet(toMake, display);
    }
    
    /**
     * Makes a universal set from the given two subsets and gets their bitset respresentations, prints them to the screen
     */
    public void getBits(String[] arr, Display display)
    {    
        String[] merge = {"mergesets", arr[1], arr[2]};
        mergeSets(merge, display);
        String[] bits = {"bits", "u", arr[1], "a"};
        bits(bits, display);
        bits[2] = arr[2];
        bits[3] = "b";
        bits(bits, display);
    }
    
    public void andSets(String[] arr, Display display)
    {
        getBits(arr, display);
        bitResult = andBits(display);
        bitsToSet("u", display);
    }
    
    public void orSets(String[] arr, Display display)
    {
        getBits(arr, display);
        bitResult = orBits(display);
        bitsToSet("u", display);
    }
    
    public void xorSets(String[] arr, Display display)
    {
        getBits(arr, display);
        bitResult = xorBits(display);
        bitsToSet("u", display);
    }
    
    public BitSet andBits(Display display)
    {
        BitSet temp = (BitSet) bitSetA.clone();
        temp.and(bitSetB);
        String bitString = "";
        for(int i = 0; i < temp.length(); i++)
            bitString += temp.get(i) ? "1" : "0";
        bp(bitString, display);
        return temp;
    }
    
    public BitSet orBits(Display display)
    {
        BitSet temp = (BitSet) bitSetA.clone();
        temp.or(bitSetB);
        String bitString = "";
        for(int i = 0; i < temp.length(); i++)
            bitString += temp.get(i) ? "1" : "0";
        bp(bitString, display);
        return temp;
    }
    
    public BitSet xorBits(Display display)
    {
        BitSet temp = (BitSet) bitSetA.clone();
        temp.xor(bitSetB);
        String bitString = "";
        for(int i = 0; i < temp.length(); i++)
            bitString += temp.get(i) ? "1" : "0";
        bp(bitString, display);
        return temp;
    }
    
    /**
     * Gets every possible combination of a given number of elements from a given set and prints it to the screen
     */
    public void setCombos(String[] arr, Display display)
    {
        int x = xPos;
        int y = yPos;
        if(arr.length < 3){
            bp("Usage: setCombos [set A|B|C] [int r]", display);
            return;
        }
        String[] uniSet;
        if(arr[1].equals("a"))
            uniSet = setA;
        else if(arr[1].equals("b"))
            uniSet = setB;
        else if(arr[1].equals("c"))
            uniSet = setC;
        else
            uniSet = uni;
            
        String[][] combos = MathFuncts.setCombos(uniSet, Integer.parseInt(arr[2]));
        for(int i = 0; i < combos.length; i++)
        {
            for(int k = 0; k < 3; k++)
                display.paint(Arrays.toString(combos[i]), x, y, 11);
            y += 18;
            if(y > 675){
                x += 5*Arrays.toString(combos[i]).length() + 25;
                y = yPos;
            }
        }
    }
    
    /**
     * Gets setcombos but outputs to file instead of printing
     */
    public void setCombosToFile(String[] arr, Display display) throws IOException
    {
        int x = xPos;
        int y = yPos;
        if(arr.length < 3){
            bp("Usage: setCombosToFile [set A|B|C] [int r] [filename.txt]", display);
            return;
        }
        String[] uniSet;
        if(arr[1].equals("a"))
            uniSet = setA;
        else if(arr[1].equals("b"))
            uniSet = setB;
        else if(arr[1].equals("c"))
            uniSet = setC;
        else
            uniSet = uni;
            
        String[][] combos = MathFuncts.setCombos(uniSet, Integer.parseInt(arr[2]));
        File file = new File(arr[3]);
        try{
            file.createNewFile();
        }catch(Exception e)
        {
            bp("File creation failed", display);
            return;
        }
        
        FileWriter writer = new FileWriter(file);
        for(int i = 0; i < combos.length; i++)
        {
            writer.write(Arrays.toString(combos[i]) + "\r\n");
        }
        writer.close();
        bp("wrote to file " + arr[3], display);
    }
    
    /**
     * Gets every possible permutation of a given number of elements of a given set and prints it to the screen.
     */
    public void setPermutes(String[] arr, Display display)
    {
        int x = xPos;
        int y = yPos;
        if(arr.length < 3){
            bp("Usage: setPermutes [set A|B|C] [int r]", display);
            return;
        }
        String[] uniSet;
        if(arr[1].equals("a"))
            uniSet = setA;
        else if(arr[1].equals("b"))
            uniSet = setB;
        else if(arr[1].equals("c"))
            uniSet = setC;
        else
            uniSet = uni;
            
        Vector<String> permutes = MathFuncts.setPermutes(uniSet, Integer.parseInt(arr[2]));
        
        for(int i = 0; i < permutes.size(); i++)
        {
            for(int k = 0; k < 3; k++)
                display.paint("{ " + permutes.get(i) + "}", x, y, 11);
            y += 18;
            if(y > 675){
                x += 5*permutes.get(i).length() + 25;
                y = yPos;
            }
        }
    }
    
    /**
     * Gets setpermutes but outputs to a file instead of printing
     */
    public void setPermutesToFile(String[] arr, Display display) throws IOException
    {
        int x = xPos;
        int y = yPos;
        if(arr.length < 3){
            bp("Usage: setPermutesToFile [set A|B|C] [int r] [filename.txt]", display);
            return;
        }
        String[] uniSet;
        if(arr[1].equals("a"))
            uniSet = setA;
        else if(arr[1].equals("b"))
            uniSet = setB;
        else if(arr[1].equals("c"))
            uniSet = setC;
        else
            uniSet = uni;
            
        Vector<String> permutes = MathFuncts.setPermutes(uniSet, Integer.parseInt(arr[2]));
        
        File file = new File(arr[3]);
        try{
            file.createNewFile();
        }catch(Exception e)
        {
            bp("File creation failed", display);
            return;
        }
        
        FileWriter writer = new FileWriter(file);
        for(int i = 0; i < permutes.size(); i++)
        {
            writer.write(permutes.get(i) + "\r\n");
        }
        writer.close();
        bp("wrote to file " + arr[3], display);
    }
    
    /**
     * Will take a universal set and from it calculate all possible subsets of the given set and print them to the display
     */
    public void powerSet(String[] arr, Display display)
    {
        int x = xPos;
        int y = yPos;
        if(arr.length < 2){
            bp("Usage: powerSet [set A|B|C]", display);
            return;
        }
        String[] uniSet;
        if(arr[1].equals("a"))
            uniSet = setA;
        else if(arr[1].equals("b"))
            uniSet = setB;
        else if(arr[1].equals("c"))
            uniSet = setC;
        else
            uniSet = uni;
        
        String[] powerSet = MathFuncts.powerSets(uniSet);
        for(int i = 0; i < powerSet.length; i++)
        {
            for(int k = 0; k < 3; k++)
                display.paint(powerSet[i], x, y, 11);
            y += 18;
            if(y > 675){
                x += 5*powerSet[i].length() + 25;
                y = yPos;
            }
        }
       
    }
    
    /**
     * Gets powerset but outputs to file instead of printing
     */
    public void powerSetToFile(String[] arr, Display display) throws IOException
    {
        if(arr.length < 2){
            bp("Usage: powerSetToFile [set A|B|C] [filename.txt]", display);
            return;
        }
        
        String[] uniSet;
        if(arr[1].equals("a"))
            uniSet = setA;
        else if(arr[1].equals("b"))
            uniSet = setB;
        else if(arr[1].equals("c"))
            uniSet = setC;
        else
            uniSet = uni;
            
        String[] powerSet = MathFuncts.powerSets(uniSet);
        File file = new File(arr[2]);
        try{
            file.createNewFile();
        }catch(Exception e)
        {
            bp("File creation failed", display);
            return;
        }
        
        FileWriter writer = new FileWriter(file);
        for(int i = 0; i < powerSet.length; i++)
        {
            writer.write(powerSet[i] + "\r\n");
        }
        writer.close();
        bp("wrote to file " + arr[2], display);
    }
    
    /**
     * Will take the result of a bit operation (and/or/xor etc) and convert it back to a set and print it
     */
    public void bitsToSet(String uni, Display display)
    {
        if(bitResult == null){
            bp("must perform a bit calculation first", display);
            return;
        }
        String[] uniSet;
        if(uni.equals("a"))
            uniSet = setA;
        else if(uni.equals("b"))
            uniSet = setB;
        else if(uni.equals("c"))
            uniSet = setC;
        else
            uniSet = this.uni;
            
        String setString = "{ ";
        for(int i = 0; i < bitResult.length()-1; i++)
        {
            if(bitResult.get(i))
                setString += uniSet[i] + ", ";
        }
        setString += uniSet[bitResult.length()-1] + " }";
        bp(setString, display);
    }

    /**
     * stores a given set of String values in an array and prints it to the screen, removing any duplicate elements of the set and sorting it
     */
    public void mkSet(String[] arr, Display display)
    {
        if(arr.length < 2)
        {
            bp("Usage: mkset [A|B|C] { e1, e2, e3, ... eN }, assign to set A, B, or C then follow with set inside { }", display);
            return; 
        }
        
        String[] set = MathFuncts.mkSet(arr);
        
        if(arr[1].equals("a"))
            setA = set;
        else if(arr[1].equals("b"))
            setB = set;
        else if(arr[1].equals("c"))
            setC = set;
        else
            uni = set;
        
        bp(arr[1].toUpperCase() + " = " + Arrays.toString(set), display);
    }
    
    /**
     * based on Prim's algorithm
     * makes a minimum spanning tree from the current graph and prints it
     * should call setcolor with a new color before calling this or clear the screen
     */
    public void spanningTree(Display display)
    {
        Graph spanTree = new Graph();
        Vertex v0 = graph.get(0);
        spanTree.addVertex(v0.x, v0.y);     //The vertex to begin the spanning tree
        ArrayList<Vertex> Ref = new ArrayList<Vertex>();    //A reference to the index of each vertex in original graph
        for(int i = 0; i < graph.size(); i++)
            Ref.add(graph.get(i));
            
        ArrayList<Vertex> V = new ArrayList<Vertex>();  //The vertices remaining to be processed
        int[][] connections = graph.getMatrix();        //Holds data on how the vertices are connected in original graph
        for(int i = 1; i < graph.size(); i++)
            V.add(graph.get(i));
        
        int minweight;
        Vertex start, end, toAdd;
        int startIndex, endIndex;
        int spanIndex;
        for(int i = 1; i < graph.size(); i++)   //while there are more vertices to add
        {
            startIndex = 0;
            endIndex = 0;
            spanIndex = 0;
            toAdd = V.get(0);
            minweight = 2147483647;
            for(int j = 0; j < spanTree.size(); j++)    //check every vertex in spantree against all remaining vertices
            {
                start = spanTree.get(j);
                for(int l = 0; l < Ref.size(); l++)     //we need this because the object reference is not the same in spantree as it is in reference
                    if(Ref.get(l).x == start.x && Ref.get(l).y == start.y) startIndex = l;
                    
                for(int k = 0; k < V.size(); k++)
                {
                    end = V.get(k);
                    
                    endIndex = Ref.indexOf(end);
                    if(connections[startIndex][endIndex] == 1)  //if there is a connection between the vertex in spantree and this remaining vertex
                    {
                        int weight = start.weight(end);
                        if(weight < minweight){                 //if its weight is less than the current minimum weight, make this the next connection
                            spanIndex = j;
                            minweight = weight;
                            toAdd = end;
                        }
                    }
                }
            }
            spanTree.addVertex(toAdd.x, toAdd.y);
            V.remove(toAdd);
            spanTree.connect(spanIndex, i);
        }
        paintGraph(spanTree, display);
    }
    
    public void mkVertex(String[] arr, Display display)
    {
        if(arr.length < 3){
            bp("Usage: mkVertex [int x] [int y]", display);
            return;
        }
        
        int x = Integer.parseInt(arr[1]);
        int y = Integer.parseInt(arr[2]);
        
        paintVertex(x, y, graph.size(), display);
        
        graph.addVertex(x, y);
    }
    
    public void connect(String[] arr, Display display)
    {
        if(arr.length < 3){
            bp("Usage: connect [int v1] [int v2]", display);
            return;
        }
        int vert1 = Integer.parseInt(arr[1]);
        int vert2 = Integer.parseInt(arr[2]);
        graph.connect(vert1, vert2);
        paintConnection(vert1, vert2, display);
    }
    
    public void paintConnection(int vert1, int vert2, Display display)
    {
        Vertex v1 = graph.get(vert1);
        Vertex v2 = graph.get(vert2);
        if(vert1 < graph.size() && vert2 < graph.size())
        {
            display.line(v1.x+10, v1.y+10, v2.x+10, v2.y+10);
            paintVertex(v1.x, v1.y, vert1, display);
            paintVertex(v2.x, v2.y, vert2, display);
        }
    }
    
    public void paintVertex(int x, int y, int vN, Display display)
    {
        int size = 20;
        for(int i = 0; i < 3; i++)
            display.paintOval(x, y, size, size);
        
        Color temp = display.getColor();    
        if(temp == Color.white)
            display.setColor(Color.black);
        else
            display.setColor(Color.white);
            
        for(int i = 0; i < 3; i++)
            display.paint("v"+vN, x+size/4, (int)(y+size/1.5), 10);
        display.setColor(temp);
    }
    
    public void paintGraph(Graph g, Display display)
    {
        int[][] matrix = g.getMatrix();
        Vertex v1, v2;
        for(int i = 0; i < matrix.length; i++)
        {
            v1 = g.get(i);
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] == 1){
                    v2 = g.get(j);
                    display.line(v1.x+10, v1.y+10, v2.x+10, v2.y+10);
                    paintVertex(v2.x, v2.y, j, display);
                }
            }
            paintVertex(v1.x, v1.y, i, display);
        }
    }
    
    public void repaintGraph(Display display)
    {
        int[][] matrix = graph.getMatrix();
        Vertex v1, v2;
        for(int i = 0; i < matrix.length; i++)
        {
            v1 = graph.get(i);
            for(int j = 0; j < matrix[i].length; j++)
            {
                if(matrix[i][j] == 1){
                    v2 = graph.get(j);
                    display.line(v1.x+10, v1.y+10, v2.x+10, v2.y+10);
                    paintVertex(v2.x, v2.y, j, display);
                }
            }
            paintVertex(v1.x, v1.y, i, display);
        }
    }
    
    /**
     * moves print position to given x and y values
     */
    public boolean move(String[] arr, Display display)
    {
        if(arr.length != 3){
            bp("Usage: move [int xPos] [int yPos]", display);
            return false;
        }
        xPos = Integer.parseInt(arr[1]);
        yPos = Integer.parseInt(arr[2]);
        return true;
    }
    
    /**
     * returns the number of possible permutations of n choosing r where n is the first argument
     * arr[1] and r is the second argument arr[2]
     * prints error to screen if there are incorrect or incorrect number of arguments
     */
    public void permute(String[] arr, Display display)
    {
        if(arr.length != 3){
               bp("Usage: permute [n# elements] [r# permutations]", display);
               return;
        }
        
        int n = Integer.parseInt(arr[1]);
        int r = Integer.parseInt(arr[2]);
        if(n < 1 || r < 0 || r > n){
            bp("n must be >=1 and <= r and r must be >=0", display);
            return;
        }
        
        double result = MathFuncts.permute(n, r);
        bp("P(" + arr[1] + "," + arr[2] + ")= " + String.format("%.0f", result), display); //formatted to print as non-exponent number
    }
    
    /**
     * prints the start of a tree, a root node at the very least and optionally a
     * lhs branch and rhs branch, more nodes can be appended via command line with appendTree
     */
    public void visualTree(String[] arr, Display display)
    {
        int x = xPos; int y = yPos;
        xPos = 700;
        yPos = 30;
        tree = new Tree<String>(arr[1]);
        if(arr.length > 2)
            tree.addLeft(arr[2]);
        if(arr.length > 3)
            tree.addRight(arr[3]);
        bp(tree, display, 1); //overloaded print that handles printing trees recursively
        xPos = x; yPos = y;
    }
    
    /**
     * Constructs a tree of bits of a given depth and prints it to the display
     */
    public void bitTree(int depth, Display display)
    {
        int x = xPos; int y = yPos;
        xPos = 700;
        yPos = 30;
        BitTree bitTree = new BitTree(false);
        BitTree.construct(bitTree, depth);
        bp(bitTree, display, 1);
        Tree t = new Tree("root");
        bp(t, display, 1);
        xPos = x; yPos = y;
    }
    
    /**
     * allows for appending more nodes to tree at console
     */
    public boolean appendTree(String[] arr, Display display)
    {
        int x = xPos; int y = yPos;
        xPos = 700;
        yPos = 30;
        Tree<String> currentTree = tree;
        for(int i = 2; i < arr.length; i++){
            if(arr[i].equals("<")){
                if(currentTree.lhs != null) currentTree = currentTree.lhs;
                else{
                    currentTree.addLeft(arr[1]);
                    bp(tree, display, 1);
                    xPos = x; yPos = y;
                    return true;
                }
            }
            else if(arr[i].equals(">")){
                if(currentTree.rhs != null) currentTree = currentTree.rhs;
                else{
                    currentTree.addRight(arr[1]);
                    bp(tree, display, 1);
                    xPos = x; yPos = y;
                    return true;
                }
            }
            else{
                xPos = x; yPos = y;
                return false;
            }
        }
        xPos = x; yPos = y;
        return false;
    }
    
    /**
     * Calculates and returns number of combinations of n choosing r
     */
    public void combo(String[] arr, Display display)
    {
        
        if(arr.length != 3){
               bp("Usage: combo [n# elements] [r# permutations]", display);
               return;
           }
        
        int n = Integer.parseInt(arr[1]);
        int r = Integer.parseInt(arr[2]);
           
        if(n < 1 || r < 0 || r > n){
            bp("n must be >=1 and <= r and r must be >=0", display);
            return;
        }
        
        
        double result = MathFuncts.combo(n, r);
        bp("C(" + arr[1] + "," + arr[2] + ")= " + String.format("%.0f",result), display); //formatted to print as non-exponent number
        
    }
    
    /**
     * Calculates and returns the factorial of a given n
     */
    public void factorial(String[] arr, Display display)
    {
        if(arr.length < 2){
            bp("Usage: factorial [int n]", display);
            return;
        }
        double result = Double.parseDouble(arr[1]);
        result = MathFuncts.factorial(result);
        if(result == -1) return;
        bp(arr[1] + "!= " + String.format("%.0f",result), display);
    }
    
    /**
     * sets color to be used by graphics object in display class, any method called after will be 
     * done in the newly set color
     */
    public boolean setColor(String s, Display display)
    {
        switch(s){
            case "black": display.setColor(Color.black);
                return true;
            case "blue": display.setColor(Color.blue);
                return true;
            case "cyan": display.setColor(Color.cyan);
                return true;
            case "darkgray": display.setColor(Color.darkGray);
                return true;
            case "gray": display.setColor(Color.gray);
                return true;
            case "green": display.setColor(Color.green);
                return true;
            case "lightgray": display.setColor(Color.lightGray);
                return true;
            case "purple":
            case "magenta": display.setColor(Color.magenta);
                return true;
            case "orange": display.setColor(Color.orange);
                return true;
            case "pink": display.setColor(Color.pink);
                return true;
            case "red": display.setColor(Color.red);
                return true;
            case "white": display.setColor(Color.white);
                return true;
            case "yellow": display.setColor(Color.yellow);
                return true;
        }
        return false;
    }
    
    /**
     * call paint a few times to be sure to get past the buffer
     * think of it like adding a couple coats of paint for that nice finish
     */
    public void bp(String s, Display display){
        for(int i = 0; i < 3; i++)
            display.paint(s, xPos, yPos, 16);
        yPos+=30;
    }
    
    /**
     * recursively prints a tree structure
     * horizontal spacing is divided by depth to keep nodes from crowding in on each other
     * (starts out wide at the top, tree gets tighter as it descends)
     */
    public void bp(Tree t, Display display, int depth){
        Color temp = display.getColor();
        int nodeSize = 40;
        int xSpacing = 680;
        int ySpacing = 80;
        
        Integer beginX = xPos;
        Integer beginY = yPos;

        //where the child leaves will be relative to the root
        
        Integer ltx = xPos - xSpacing/(int)(Math.pow(2, depth));         
        Integer lty = yPos + ySpacing;
        Integer rtx = xPos + xSpacing/(int)(Math.pow(2, depth));
        Integer rty = yPos + ySpacing;
        
        int rightTxtShift = 6;
        int downTxtShift = 22;
        
        display.paintOval(xPos, yPos, nodeSize, nodeSize);
        
        //change color for text inside
        if(temp == Color.white)
                display.setColor(Color.black);  
        else
                display.setColor(Color.white);
        
        for(int i = 0; i < 3; i++)
            display.paint("" + t.value, xPos+rightTxtShift, yPos+downTxtShift, 12);   //print text inside the oval
        
        display.setColor(temp);                         //go back to original color
        
        //if there is a left branch, move into position and print it
        if(t.lhs != null){
            String[] arr = new String[] {"move", ltx.toString(), lty.toString()}; //forcing a call to move with an array of args
            move(arr, display);
            //draw line between nodes
            display.line(beginX+rightTxtShift*2, beginY+downTxtShift, xPos, yPos+downTxtShift);
            //recursively print left side
            bp(t.lhs, display, depth+1);
        }
        //same with right branch
        if(t.rhs != null){
            String[] arr = new String[] {"move", rtx.toString(), rty.toString()};
            move(arr, display);
            //draw line between nodes
            display.line(beginX+rightTxtShift*2, beginY+downTxtShift, xPos+rightTxtShift*2, yPos+downTxtShift); //line drawing needed some slight adjustment
            //recursively print right side                                                                    recycled existing textshift vars to help adjust
            bp(t.rhs, display, depth+1);
        }
        
        //back to original position for convenience
        xPos = beginX;
        yPos = beginY;
    }
}