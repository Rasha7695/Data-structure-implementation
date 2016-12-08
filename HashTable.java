//Student Name: Andree Kaba
//McGill ID: 260493293
package a4q1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

class MyHashTable<K, V> implements Iterable<MyHashTable<K, V>.HashEntry> {
    /*
     *   Number of entries in the HashTable.
     */
    private int entryCount = 0;

    /*
     * Number of buckets. The constructor sets this variable to its initial value,
     * which eventually can get changed by invoking the rehash() method.
     */
    private int numBuckets;

    /**
     * Threshold load factor for rehashing.
     */
    private final double MAX_LOAD_FACTOR = 0.75;

    /**
     * buckets to store the key-value pairs.   Traditionally an array is used for the buckets
     * and a linked allEntries is use for the entries within each bucket.
     * <p>
     * We use an ArrayallEntries rather than array, since the former is simpler to use in Java.
     */

    ArrayList<LinkedList<HashEntry>> buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

    MyHashTable(int numBuckets) {
        //make sure that numBuckets is valid
        if(numBuckets<=0) {
            System.out.println("Cannot create a hashtable with zero buckets");
            return;
        }
        //create a table with "numBuckets" buckets
        this.numBuckets=numBuckets;
        //initialize the bucketlist
        this.buckets = new ArrayList<LinkedList<HashEntry>>();
        //initialize each bucket with an empty list
        for(int i=0;i<this.numBuckets;i++)
            this.buckets.add(new LinkedList<>());
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    private int hashFunction(K key) {

        return Math.abs(key.hashCode()) % numBuckets;
    }

    /**
     * Checking if the hash table is empty.
     */
    public boolean isEmpty() {
        if (entryCount == 0)
            return true;
        else
            return (false);
    }

    /**
     * return the number of entries in the hash table.
     */
    public int size() {
        return (entryCount);
    }

    /**
     * Adds a key-value pair to the hash table. If the load factor goes above the
     * MAX_LOAD_FACTOR, then call the rehash() method after inserting.
     * <p>
     * If there was a previous value for the given key in this hashtable, then return it.
     * Otherwise return null.
     */

    public V put(K key, V value) {
        //make sure that the key is valid, however, value can be null
        if(key==null){
            System.out.println("Inserting a null key into the table!!");
            return null;
        }
        //variable initialized to null to hold the return value
        V ret = null;

        //check if we already have the element
        HashEntry e=this.getEntry(key);

        //if we don't, just insert it
        if (e==null)
            this.buckets.get(hashFunction(key)).add(new HashEntry(key, value));
        else //else return the old value and replace it by the new one
        {
            //get the old value
            ret = e.getValue();
            //replace it with a new value
            int index = hashFunction(key);
            LinkedList<HashEntry> bucketList = buckets.get(index);
            for (HashEntry node : bucketList) {
                if (node.getKey() == key)
                    node.setValue(value);
            }
        }


        //increase the number of elements in the hash table
        entryCount++;

        // rehash if the load factor goes above the MAX_LOAD_FACTOR
        if (((double)this.size()/(double)numBuckets) > this.MAX_LOAD_FACTOR)
            this.rehash();

        return ret;
    }

    /**
     * Retrieves a value associated with some given key in the hash table.
     * Returns null if the key could not be found in the hash table)
     */
    public V get(K key) {
        //make sure that the key is valid
        if(key==null){
            System.out.println("Trying to access an element with a null key!!");
            return null;
        }

        //search for the element in the hash table
        HashEntry res=this.getEntry(key);
        //return the value of the element if we found it, null otherwise
        if(res==null)
            return null;
        else
            return res.getValue();
    }

    /**
     * Removes a key-value pair from the hash table.
     * Return value associated with the provided key.   If the key is not found, return null.
     */
    public V remove(K key) {
        //make sure that the key is valid
        if(key==null){
            System.out.println("Trying to remove an element with a null key!!");
            return null;
        }

        //variable to hold the return value
        V ret = null;

        //retrieve the element from the hash table
        HashEntry res=this.getEntry(key);

        //if not null we return the value associated with the provided key
        //and we remove the key-value pair
        if (res!=null) {
            this.buckets.get(hashFunction(key)).remove(res);
            ret = res.getValue();
        }

        //decrease the number of elements in the hash table
        entryCount--;

        return (ret);
    }

    /*
     *  This method is used for testing rehash().  Normally one would not provide such a method.
     */
    public int getNumBuckets() {
        return numBuckets;
    }

	/*
	 * Returns an iterator for the hash table. 
	 */

    //@Override
    public HashIterator iterator() {
        return new HashIterator();
    }

    /**
     * Removes all the entries from the hash table, but keeps the number of buckets intact.
     */
    public void clear() {
        for (int ct = 0; ct < buckets.size(); ct++) {
            buckets.get(ct).clear();
        }
        entryCount = 0;
    }

    /**
     * Create a new hash table that has twice the number of buckets.
     */

    public void rehash() {
        //create a new bucketList
        ArrayList<LinkedList<HashEntry>> newBucketList=new ArrayList<LinkedList<HashEntry>>();
        //double the number of buckets available
        this.numBuckets*=2;
        //initialize each new bucket with an empty list
        for(int i=0;i<numBuckets;i++)
            newBucketList.add(new LinkedList<>());
        //rehash all entries into the new buckets
        for (HashEntry item : this)
            newBucketList.get(hashFunction(item.getKey())).add(item);
        //replace the old buckets with the new ones
        this.buckets=newBucketList;
    }

    /*
     * Checks if the hash table contains the given key.
     * Return true if the hash table has the specified key, and false otherwise.
     */
    public boolean containsKey(K key) {
        return (get(key) != null);
    }
		
	/*
	 * return an ArrayList of the keys in the hashtable
	 */

    public ArrayList<K> keys() {
        ArrayList<K> listKeys = new ArrayList<K>();
        //scan all the entries in the table for keys
        for(HashEntry item:this)
            listKeys.add(item.getKey());
        return listKeys;
    }

    /*
     * return an ArrayList of the values in the hashtable
     */
    public ArrayList<V> values() {
        ArrayList<V> listValues = new ArrayList<V>();
        //scan all the entries in the table for values
        for(HashEntry item:this)
                listValues.add(item.getValue());
        return listValues;
    }

    @Override
    public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buckets.size(); i++) {
            sb.append("Bucket ");
            sb.append(i);
            sb.append(" has ");
            sb.append(buckets.get(i).size());
            sb.append(" entries.\n");
        }
        sb.append("There are ");
        sb.append(entryCount);
        sb.append(" entries in the hash table altogether.");
        return sb.toString();
    }

    /*
     *    Inner class:   Iterator for the Hash Table.
     */
    public class HashIterator implements Iterator<HashEntry> {
        LinkedList<HashEntry> allEntries;

        /**
         * Constructor:   make a linkedlist 'allEntries' of all the entries in the hash table
         */
        public HashIterator() {
            //initialization
            allEntries=new LinkedList<HashEntry>();
            //fill the allEntries variable with all key-value pairs
            //in all the buckets
            for(LinkedList<HashEntry> item:buckets)
                allEntries.addAll(item);
        }

        //  Override
        @Override
        public boolean hasNext() {
            return !allEntries.isEmpty();
        }

        //  Override
        @Override
        public HashEntry next() {
            return allEntries.removeFirst();
        }

        @Override
        public void remove() {

            // not implemented,  but must be declared because it is in the Iterator interface

        }
    }

    //  helper method

    private HashEntry getEntry(K key) {
        int index = hashFunction(key);
        LinkedList<HashEntry> bucketList = buckets.get(index);
        for (HashEntry node : bucketList) {
            if (node.getKey() == key)
                return node;
        }
        return null;
    }

    class HashEntry {

        private K key;
        private V value;

        /*
         * Constructor.
         */
        HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /*
         * Returns this hash entry's key.   Assume entry is not null.
         * @return This hash entry's key
         */
        K getKey() {
            return (key);
        }

        /**
         * Returns this hash entry's value.  Assume entry is not null.
         */
        V getValue() {
            return (value);
        }

        /**
         * Sets this hash entry's value.
         */
        void setValue(V value) {
            this.value = value;
        }
    }


}
