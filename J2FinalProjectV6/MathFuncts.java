import java.util.Arrays;
import java.util.BitSet;
import java.util.Vector;
import java.util.Collections;
/**
 * Write a description of class MathFuncts here.
 *
 * @author Christian Bonin
 * @version 5/12/2019
 */
public class MathFuncts
{
    /**
     * returns the number of possible permutations of n choosing r
     */
    public static double permute(int n, int r)
    {
        double result = 1;
        for(double i = n; i > n-r; i--)
            result *= i;
        return result;
    }
    
    /**
     * returns the number of possible combinations of n choosing r
     */
    public static double combo(int n, int r)
    {
        double result = 1;
        for(int i = n; i > n-r; i--)
            result *= i;
        return result/factorial(r);
    }
    
    /**
     * Calculates and returns the factorial of a given n
     */
    public static double factorial(double n)
    {
        if(n < 0)  return -1;
        if(n == 0) return 1;
        if(n == 1) return 1;
        for(double i = n-1; i > 1; i--)
            n*=i;
        return n;
    }
    
    /**
     * sorts, removes duplicate elements, and returns an array of String values
     */
    public static String[] mkSet(String[] arr)
    {
        String[] set;
             
        if(arr[2].length() > 1 && arr[2].contains("{"))
            arr[2] = arr[2].substring(1);
        if(arr[arr.length-1].length() > 1 && arr[arr.length-1].contains("}"))
            arr[arr.length-1] = arr[arr.length-1].substring(0, arr[arr.length-1].length()-1);
        
        if(arr[2].equals("{"))
            set = Arrays.copyOfRange(arr, 3, arr.length-1);
        else
            set = Arrays.copyOfRange(arr, 2, arr.length);
        
        //removing duplicate elements from set
        int finalSize = set.length;
        Arrays.sort(set);
        
        for(int i = 0; i < set.length-1; i++) //traverse entire array
        {
            if(set[i] != null && set[i].equals(set[i+1]))   
            {
                String duplicate = set[i];
                i++;    //move i to the first duplicated element
                
                //then, as long as we havent broken the loop or run out of elements, set all duplicates to null
                    //and decrement the new array's size each time
                while(i < set.length){
                    set[i] = null;
                    i++;
                    finalSize--;  
                    if(i >= set.length || !set[i].equals(duplicate)){
                        i--;    //if this element isnt a duplicate we'll need to move i one back so we can check it also
                        break;
                    }
                }
            }
        }
        
        String[] temp = Arrays.copyOf(set, set.length);
        set = new String[finalSize];
        
        int index = 0;
        for(int i = 0; i < temp.length; i++)
        {
            if(temp[i] != null){
                set[index] = temp[i];
                index++;
            }
        }
        return set;
    }
    
    /**
     * given a universal and subset, returns a bitset representation of the subset
     */
    public static BitSet bits(String[] uni, String[] sub)
    {
        BitSet bits = new BitSet(uni.length);
        
        int index;
        for(int i = 0; i < sub.length; i++){
            index = Arrays.binarySearch(uni, sub[i]);
            if(index >=0)   bits.set(index);
        }
        
        return bits;
    }
    
    public static String[] powerSets(String[] uniSet)
    {
        BitTree tree = new BitTree(false);
        BitTree.construct(tree, uniSet.length);
        BitSet[] bitSets = tree.getBitSets(); 
        String[] subSets = new String[bitSets.length];
        for(int i = 0; i < bitSets.length; i++)
        {
            String setString = "{ ";
            for(int j = 0; j < bitSets[i].length()-1; j++)
            {
                if(bitSets[i].get(j))
                    setString += uniSet[j] + ", ";
            }
            if(bitSets[i].length() > 0) setString += uniSet[bitSets[i].length()-1] + " }";
            else                        setString += " }";
            subSets[i] = setString;
        }
        return subSets;
    }
    
    public static String[][] setCombos(String[] uniSet, int r)
    {
        BitTree tree = new BitTree(false);
        BitTree.construct(tree, uniSet.length);
        BitSet[] bitSets = tree.getBitSets(); 
        String[][] subSets = new String[(int)combo(uniSet.length, r)][r];
        int comboIndex = 0;
        for(int i = 0; i < bitSets.length; i++)
        {
            int setIndex = 0;
            if(bitSets[i].cardinality() == r){
                for(int j = 0; j < bitSets[i].length(); j++)
                {
                    if(bitSets[i].get(j)){
                         subSets[comboIndex][setIndex] = uniSet[j];
                         setIndex++;
                    }
                }
                comboIndex++;
            }
        }
        return subSets;
    }
    
    public static Vector<String> setPermutes(String[] uniSet, int r)
    {
        String[][] setCombos = setCombos(uniSet, r);
        Vector<String> permutes = new Vector<String>();
        for(int i = 0; i < setCombos.length; i++)
        {
            Vector<String> set = new Vector<String>();
            for(int j = 0; j < r; j++)
            {
                set.add(setCombos[i][j]);
            }
            permutesSub("", set, permutes);
        }
        Collections.sort(permutes);
        return permutes;
    }
    
    public static void permutesSub(String setSoFar, Vector<String> setRemaining, Vector<String> permutes)
    {
        if(setRemaining.size() == 0){
            permutes.add(setSoFar);
            return;
        }
        for(int i = 0; i < setRemaining.size(); i++){
            Vector<String> newSet = (Vector<String>)setRemaining.clone();
            permutesSub(setSoFar + newSet.remove(i) + " ", newSet, permutes);
        }
    }
    
    public static String binomExpand(String eq)
    {
        String result = "";
        String numStr = "";
        int coeffA = 1;
        int coeffB = 1;
        int power = 1;
        char var1 = 'x';
        char var2 = 'y';
        if(eq.charAt(0) == '(')
            eq = eq.substring(1);
        else
            return "Invalid input";
        if(eq.charAt(0) == '-')
        {
            coeffA *= -1;
            eq = eq.substring(1);
        }
        while(Character.isDigit(eq.charAt(0))){
            numStr += eq.charAt(0);
            eq = eq.substring(1);
        }
        
        if(numStr.length() > 0)
        {
            coeffA *= Integer.parseInt(numStr);
            numStr = "";
        }
        
        if(Character.isLetter(eq.charAt(0)))
        {
            var1 = eq.charAt(0);
            eq = eq.substring(1);
        }
        
        if(eq.charAt(0) == ' ')
            eq = eq.substring(1);
            
        if(eq.charAt(0) == '-')
            coeffB *= -1;
        
        eq = eq.substring(1);
        
        if(eq.charAt(0) == ' ')
            eq = eq.substring(1);
        
        while(Character.isDigit(eq.charAt(0))){
            numStr += eq.charAt(0);
            eq = eq.substring(1);
        }
        
        if(numStr.length() > 0)
        {
            coeffB *= Integer.parseInt(numStr);
            numStr = "";
        }
        
        if(Character.isLetter(eq.charAt(0)))
        {
            var2 = eq.charAt(0);
            eq = eq.substring(1);
        }
        
        if(eq.charAt(0) == ')')
             eq = eq.substring(1);
        else
            return "Invalid input";
        
        if(eq.length() > 0 && eq.charAt(0) == '^')
             eq = eq.substring(1);
        
        while(eq.length() > 0 && Character.isDigit(eq.charAt(0))){
           numStr += eq.charAt(0);
           eq = eq.substring(1);
        }
        
        if(numStr.length() > 0)
        {
            power = Integer.parseInt(numStr);
        }
        
        for(int i = 0; i < power; i++)
        {
            result += "(" + (int)combo(power, i) + ")";
            if(coeffA != 1)
                result += "(" + coeffA + var1 + ")^" + (power-i);
            else
                result += var1 + "^" + (power-i);
            
            if(coeffB != 1)
                result += "(" + coeffB + var2 + ")^" + i;
            else
                result += var2 + "^" + i;    
                
            result += " + ";
        }
        if(coeffA != 1)
            result += "(" + coeffA + var1 + ")^0";
        else
            result += var1 + "^0";
        
        if(coeffB != 1)
            result += "(" + coeffB + var2 + ")^" + power;
        else
            result += var2 + "^" + power;    
        
        return result;
    }
    
    public static String simplifyBinom(String binom)
    {
        String result = "";
        String[] terms = binom.split("\\s");
        int biCoeff, coeffA, coeffB, coeff;
        int powA, powB;
        char var1, var2;
        String numStr, coeffStr, var1Str, var2Str;
        for(int i = 0; i < terms.length; i++)
        {
            numStr = "";
            biCoeff = 1;
            coeffA = 1;
            coeffB = 1;
            if(terms[i].length() == 1)
                result += " " + terms[i] + " ";
            else
            {
                //get binomial term if there is one
                if(terms[i].charAt(0) == '(')
                {
                    terms[i] = terms[i].substring(1);
                    //if theres no term we're at the coeff already, so have to check for neg
                    if(terms[i].charAt(0) == '-'){
                        coeffA *= -1;
                        terms[i] = terms[i].substring(1);
                    }
                }
                while(Character.isDigit(terms[i].charAt(0)))
                {
                    numStr += terms[i].charAt(0);
                    terms[i] = terms[i].substring(1);
                }
                if(terms[i].charAt(0) == ')'){
                    biCoeff = Integer.parseInt(numStr);
                    terms[i] = terms[i].substring(1);
                    numStr = "";
                }
                //get first term coeff if there is one
                if(terms[i].charAt(0) == '('){
                    terms[i] = terms[i].substring(1);
                    if(terms[i].charAt(0) == '-'){
                        coeffA *= -1;
                        terms[i] = terms[i].substring(1);
                    }
                }
                while(Character.isDigit(terms[i].charAt(0)))
                {
                    numStr += terms[i].charAt(0);
                    terms[i] = terms[i].substring(1);
                }
                if(numStr.length() > 0)
                {
                   coeffA *= Integer.parseInt(numStr);
                   numStr = "";
                }
                //charAt(0) will be first var now in any case
                var1 = terms[i].charAt(0);
                //get rid of it and paran or ^ if no paran
                terms[i] = terms[i].substring(2);
                if(terms[i].charAt(0) == '^')   //get rid of this if not already gone
                    terms[i] = terms[i].substring(1);
                while(Character.isDigit(terms[i].charAt(0)))
                {
                    numStr += terms[i].charAt(0);
                    terms[i] = terms[i].substring(1);
                }
                powA = Integer.parseInt(numStr);
                numStr = "";
                //get second term coeff if one
                if(terms[i].charAt(0) == '(')
                    terms[i] = terms[i].substring(1);
                if(terms[i].charAt(0) == '-'){
                    coeffB *= -1;
                    terms[i] = terms[i].substring(1);
                }
                while(Character.isDigit(terms[i].charAt(0)))
                {
                    numStr += terms[i].charAt(0);
                    terms[i] = terms[i].substring(1);
                }
                if(numStr.length() > 0)
                {
                   coeffB *= Integer.parseInt(numStr);
                   numStr = "";
                }
                
                var2 = terms[i].charAt(0);
                terms[i] = terms[i].substring(2);
                if(terms[i].length() > 0 && terms[i].charAt(0) == '^')   //get rid of this if not already gone
                    terms[i] = terms[i].substring(1);
                while(terms[i].length() > 0 && Character.isDigit(terms[i].charAt(0)))
                {
                    numStr += terms[i].charAt(0);
                    terms[i] = terms[i].substring(1);
                }
                powB = Integer.parseInt(numStr);
                numStr = "";
                coeff = (int)(Math.pow(coeffA, powA)*Math.pow(coeffB, powB)*biCoeff);
                if(coeff == 1)
                    coeffStr = "";
                else
                    coeffStr = "" + coeff;
                
                if(powA == 1)
                    var1Str = "" + var1;
                else if(powA == 0)
                    var1Str = "";
                else
                    var1Str = "" + var1 + "^" + powA;
                    
                if(powB == 1)
                    var2Str = "" + var2;
                else if(powB == 0)
                    var2Str = "";
                else
                    var2Str = "" + var2 + "^" + powB;
                
                result += coeffStr + var1Str + var2Str;
            }
        }
        return result;
    }
    
}
