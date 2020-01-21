import java.util.BitSet;
import java.lang.Math;

/**
 * A tree where all nodes except root are 0s and 1s
 * Useful for making power sets
 *
 * @author Christian Bonin
 * @version 5/9/2019
 */
public class BitTree extends Tree<Boolean>
{
    BitSet[] bitSets;
    
    public BitTree(boolean value)
    {
        super(value);
    }
    
    public static void construct(BitTree tree, int depth)
    {
        if(depth > 0)
        {
            tree.addLeft();
            tree.addRight();
            construct((BitTree)tree.lhs, depth-1);
            construct((BitTree)tree.rhs, depth-1);
        }
    }
    
    public void addLeft()
    {
        BitTree lhs = new BitTree(true);
        this.lhs = lhs;
    }
        
    public void addRight()
    {
        BitTree rhs = new BitTree(false);
        this.rhs = rhs;
    }
    
    public int getDepth(){
        BitTree currentNode = this;
        int depth = 0;
        while(currentNode.lhs != null)
        {
            currentNode = (BitTree) currentNode.lhs;
            depth++;
        }
        return depth;
    }
    
    public BitSet[] getBitSets()
    {
        int depth = getDepth();
        int setsIndex = 0;
        bitSets = new BitSet[(int)Math.pow(2, depth)];
        BitTree left = (BitTree) lhs;
        BitTree right = (BitTree) rhs;
        BitSet temp;
        
        while(lhs.value != null)
        {
            temp = null;
            temp = left.getBitSetsSub(setsIndex, depth);
            if(temp != null){
                bitSets[setsIndex] = temp;
                setsIndex++;
            }
        }
        while(rhs.value != null)
        {
            temp = null;
            temp = right.getBitSetsSub(setsIndex, depth);
            if(temp != null){
                bitSets[setsIndex] = temp;
                setsIndex++;
            }
        }
        bitSets = sort();
        return bitSets;
    }
    
    public BitSet getBitSetsSub(int setsIndex, int depth)
    {
        BitSet bits = new BitSet(depth);
        BitTree currentNode = this;
        if(value.equals(true))    bits.set(0);
        int index = 1;
        for(int i = 0; i < depth; i++){
            //while we havent reached a null going down the left side
            while(currentNode.lhs != null && currentNode.lhs.value != null)
            {
                currentNode = (BitTree) currentNode.lhs;
                if(currentNode.value.equals(true))
                    bits.set(index);
                index++;
            }
            //while we havent reached a null going down the right side
            while(currentNode.rhs != null && currentNode.rhs.value != null)
            {
                currentNode = (BitTree) currentNode.rhs;
                if(currentNode.value.equals(true))
                    bits.set(index);
                index++;
            }
            if(currentNode.lhs == null && currentNode.rhs == null)              break;
            if(currentNode.lhs.value == null && currentNode.rhs.value == null)  break;
        }
        
        currentNode.value = null;
        
        //if we got a path all the way down the tree then we want this bitset
        if(index == depth)
        {
            return bits;
        }
        //else return null
        return null;
    }
    
    public BitSet[] sort()
    {
        BitSet[] temp = new BitSet[bitSets.length];
        int index = 0;
        int card = 0;
        while(index < temp.length)
        {
            for(int i = 0; i < temp.length; i++)
            {
                if(bitSets[i].cardinality() == card){
                    temp[index] = bitSets[i];
                    index++;
                }
            }
            card++;
        }
        return temp;
    }
}
