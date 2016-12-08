
import java.util.*;

/*
 *  Trie class.  Each node is associated with a prefix of some key 
 *  stored in the trie.   (Any string is a prefix of itself.)
 */

public class Trie {
    private TrieNode root;

    // Empty trie has just a root node.  All the children are null.

    public Trie() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }


    /*
     * Insert key into the trie.  First, find the longest
     * prefix of a key that is already in the trie (use getPrefixNode() below).
     * Then, add TrieNode(s) such that the key is inserted
     * according to the specification in PDF.
     */
    public void insert(String key) {
        //guard for empty strings
        if (key.length()!=0){
            //skip the longest prefix already in the tree
            TrieNode curnode = getPrefixNode(key);

            //we start adding missing characters starting from this index
            int idx = curnode.depth;

            //start adding char by char from [idx] -> [key.length()-1]
            while (idx < key.length()) {
                //get the character that we want to add
                char c = key.charAt(idx);
                //we create a new child to with the current char
                //then we move to this new node
                curnode = curnode.createChild(c);
                //move to the next char
                idx++;
            }
            //this must be an end of a word
            curnode.setEndOfKey(true);
        }
    }

    // insert each key in the list (keys)

    public void loadKeys(ArrayList<String> keys) {
        for (int i = 0; i < keys.size(); i++) {
            insert(keys.get(i));
        }
        return;
    }

    /*
     * Given an input key, return the TrieNode corresponding the longest prefix that is found.
     * If no prefix is found, return the root.
     * In the example in the PDF, running getPrefixNode("any") should return the
     * dashed node under "n", since "an" is the longest prefix of "any" in the trie.
     */
    private TrieNode getPrefixNode(String key) {
        //start from root
        TrieNode res = this.getRoot();
        //we start matching character by character
        int idx = 0;
        //while we still have characters to match and the current
        //node have a child with the required character, move to that child
        //and re-do the same operation for the next character
        while (idx < key.length() && res.getChild(key.charAt(idx)) != null) {
            //move to the next child
            res = res.getChild(key.charAt(idx));
            //move to the next character
            idx++;
        }
        //return the node at the longest prefix
        //which may match the whole word if the condition
        // "idx<key.length()" is broken in the while loop
        return res;
    }

	/*
     * Similar to getPrefixNode() but now return the prefix as a String, rather than as a TrieNode.
	 */

    public String getPrefix(String key) {
        return getPrefixNode(key).toString();
    }


    /*
     *  Return true if key is contained in the trie (i.e. it was added by insert), false otherwise.
     *  Hint:  any string is a prefix of itself, so you can use getPrefixNode().
     */
    public boolean contains(String key) {
        //the trie always contains the empty string
        if(key.length()==0)
            return true;
        //check if we have the key as a prefix
        TrieNode res = getPrefixNode(key);
        //if it exists the prefix must match the key and must be the end of a word
        return (res.depth==key.length() && res.isEndOfKey());
    }

    /*
     *  Return a list of all keys in the trie that have the given prefix.
     */
    public ArrayList<String> getAllPrefixMatches(String prefix) {
        //declare the resulting arraylist
        ArrayList<String> stringList = new ArrayList<String>();
        //get the longest prefix that is in the tree
        TrieNode curnode = getPrefixNode(prefix);
        //if the longest prefix equals the prefix, then we can
        //starting suggesting words by searching for all
        //words in the subtree of "curnode"
        if(curnode.depth==prefix.length()) {
            //scan for words in the tree rooted at curnode
            ArrayList<TrieNode> temp = scanSubTree(curnode);
            //store all the resulting words
            for (int i = 0; i < temp.size(); i++)
                stringList.add(temp.get(i).toString());
        }
        //return result
        return stringList;
    }
    /*
        This is a recursive method scan the subtree rooted at cur for any nodes
        that are endOfKey, which means that it returns all TrieNodes that are
        the end of a word in the subtree rooted at cur in a recursive fashion
     */
    private ArrayList<TrieNode> scanSubTree(TrieNode cur) {
        //if we are not at a leaf node
        ArrayList<TrieNode> result = new ArrayList<TrieNode>();

        // if the current node already match, we add it
        if (cur.isEndOfKey())
            result.add(cur);


        //search for more matchings in the subtrees
        for (int i = 0; i < TrieNode.NUMCHILDREN; i++) {
            //check if the subtree have matchings, if yes collect them
            if (cur.children[i]!=null){
                //aggregate the results from all childrens
                ArrayList<TrieNode> tempRes=scanSubTree(cur.children[i]);
                //collect every matching in the subtree
                for(int j=0;j<tempRes.size();j++)
                    result.add(tempRes.get(j));
            }
        }
        //return all the nodes that have the strings as a prefix
        return result;
    }
    /*
	 *  A node in a Trie (prefix) tree.  
	 *  It contains an array of children: one for each possible character.
	 *  The ith child of a node corresponds to character (char)i
	 *  which is the UNICODE (and ASCII) value of i. 
	 *  Similarly the index of character c is (int)c.
	 *  So children[97] = children[ (int) 'a']  would contain the child for 'a' 
	 *  since (char)97 == 'a'   and  (int)'a' == 97
	 */

    private class TrieNode {
		/*  
		 *   Highest allowable character index is NUMCHILDREN-1
		 *   (assuming one-byte ASCII i.e. "extended ASCII")
		 *   
		 *   NUMCHILDREN is constant (static and final)
		 *   To access it, write "TrieNode.NUMCHILDREN"
		 */

        public static final int NUMCHILDREN = 256;

        private TrieNode parent;
        private TrieNode[] children;
        private int depth;            // 0 for root, 1 for root's children, 2 for their children, etc..
        private char charInParent;    // Character associated with edge between this node and its parent.
        // See comment above for relationship between an index in 0 to 255 and a char value.
        private boolean endOfKey;   // Set to true if prefix associated with this node is also a key.

        // Constructor for new, empty node with NUMCHILDREN children.  All the children are null.

        public TrieNode() {
            children = new TrieNode[NUMCHILDREN];
            endOfKey = false;
            depth = 0;
            charInParent = (char) 0;
        }


        /*
         *  Add a child to current node.  The child is associated with the character specified by
         *  the method parameter.  Make sure you set all fields in the child node.
         *
         *  To implement this method, see the comment above the inner class TrieNode declaration.
         */
        public TrieNode createChild(char c) {
            //create our new child object
            TrieNode child = new TrieNode();
            //if we don't have a child
            if (this.children[c] == null) {
                // set the character
                child.charInParent = c;
                // set parent
                child.parent = this;
                // set depth
                child.depth = this.depth + 1;
                // assign new child
                this.children[c] = child;
            }
            return child;
        }

        // Get the child node associated with a given character, i.e. that character is "on"
        // the edge from this node to the child.  The child could be null.

        public TrieNode getChild(char c) {
            return children[c];
        }

        // Test whether the path from the root to this node is a key in the trie.
        // Return true if it is, false if it is prefix but not a key.

        public boolean isEndOfKey() {
            return endOfKey;
        }

        // Set to true for the node associated with the last character of an input word

        public void setEndOfKey(boolean endOfKey) {
            this.endOfKey = endOfKey;
        }

		/*  
		 *  Return the prefix (as a String) associated with this node.  This prefix
         *  is defined by descending from the root to this node.  However, you will
         *  find it is easier to implement by ascending from the node to the root,
         *  composing the prefix string from its last character to its first.  
		 *
		 *  This overrides the default toString() method.
		 */
        public String toString() {
            // we still have more characters on the path from the current node to root
            if (this.parent != null)
                return this.parent.toString() + this.charInParent;
            else // we have reached root, which means no more characters
                return "";
        }
    }


}
