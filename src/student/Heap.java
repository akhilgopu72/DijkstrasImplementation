package student;

/* Time spent on a6:  3 hours and 30 minutes.

 * Name(s):
 * Netid(s): 
 * What I thought about this assignment: 
 * 
 *
 *
 */

import java.util.*;

/** An instance is a min-heap of distinct elements of type E.
 *  Priorities are double values. Since it's a min-heap, the value
 *  with the smallest priority is at the root of the heap. */
public class Heap<E> {

    private int size; // number of elements in the heap

    /** The heap invariant is given below. Note that / denotes int division.
     * 
     *  b[0..size-1] is viewed as a min-heap, i.e. 
     *  0. The values in b[0..size-1] are all different.
     *  1. Each array element in b[0..size-1] contains a value of the heap.
     *  2. The children of each b[i] are b[2i+1] and b[2i+2].
     *  3. The parent of each b[i] is b[(i-1)/2].
     *  4. Each b[i]'s priority is >= its parent's priority.
     *  5. Priorities for the b[i] used for the comparison in point 4
     *     are given in map. Field map contains one entry for each element of
     *     the heap, so map and b have the same size.
     *     For each element e in the heap, its map entry contains in the Prindex
     *     (for PRIority and INDEX) object the priority of e and its index in b.
     */
    private ArrayList<E> b;
    private HashMap<E, Prindex> map= new HashMap<E, Prindex>();

    /** Constructor: an empty heap. */
    public Heap() {
        b= new ArrayList<E>();
    }

    /** Return a string that gives this heap, in the format:
     * [item0:priority0, item1:priority1, ..., item(N-1):priority(N-1)]
     * Thus, the list is delimited by '['  and ']' and ", " (i.e. a
     * comma and a space char) separate adjacent items. */
    @Override public String toString() {
        String s= "[";
        for (E t : b) {
            if (s.length() > 1) {
                s = s + ", ";
            }
            s = s + t + ":" + map.get(t).priority;
        }
        return s + "]";
    }

    /** Return a string that gives the priorities in this heap,
     * in the format: [priority0, priority1, ..., priority(N-1)]
     * Thus, the list is delimited by '['  and ']' and ", " (i.e. a
     * comma and a space char) separate adjacent items. */
    public String toStringPriorities() {
        String s= "[";
        for (E t : b) {
            if (s.length() > 1) {
                s = s + ", ";
            }
            s = s + map.get(t).priority;
        }
        return s + "]";
    }

    /** Return the number of elements in this heap.
     * This operation takes constant time. */
    public int size() {
        return size;
    }

    /** Add e with priority p to the heap.
     *  Throw an illegalArgumentException if e is already in the heap.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the queue. */ 
    public void add(E e, double p) throws IllegalArgumentException {
        //TODO 1: Do add and bubbleUp together.
    	if (b.contains(e))
    		throw new IllegalArgumentException();
    	map.put(e, new Prindex(size,p));
    	b.add(e);
    	bubbleUp((int) size);
    	size = size + 1;
    	

    }

    /** Return the element of this heap with lowest priority, without
     *  changing the heap. This operation takes constant time.
     *  Throw a HeapException if the heap is empty. */
    public E peek() {
        // TODO 2: Do peek.
    	if (b.size() == 0)
    		throw new HeapException();

        return b.get(0);
    }

    /** Remove and return the element of this heap with lowest priority.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  Throw a HeapException if the heap is empty. */
    public E poll() {
        // TODO 3: Do poll and bubbleDown together.
        // Do NOT create new map entries
        // Look in the specification at the required time bounds
    	if (b.size() == 0)
    		throw new HeapException();
    	if (size == 1)
    	{
    		size--;
    		map.remove(b.get(0));
    		return b.remove(0);
    	}
    	E answer = b.get(0);
    	b.set(0, b.get(size-1));
    	b.remove(size-1);
    	size = size - 1;
    	bubbleDown(0);
        return answer;
    }

    /** Change the priority of element e to p.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  Throw an illegalArgumentException if e is not in the heap. */
    public void changePriority(E e, double p) {
        // TODO 4: Do updatePriority.
        // Do NOT create new map entries
    	if (b.contains(e) == false)
    		throw new IllegalArgumentException();
    	map.get(e).priority = p;
    	bubbleUp(b.indexOf(e));
    	bubbleDown(b.indexOf(e));

    }

    /** Bubble b[k] up in heap to its right place.
     *  Precondition: Each b[i]'s priority >= parent's priority 
     *                except perhaps for b[k] */
    private void bubbleUp(int k) {
        // TODO1 Do add and bubbleUp together.
        // Do not use recursion; use iteration.
        // Do NOT create new map entries
    	
        	

			while (k >0 && map.get(b.get((k-1)/2)).priority >= map.get(b.get(k)).priority)
			{
				
	        	E temp = b.get((k-1)/2);
				E temp1 = b.get(k);
				b.set(k, temp);
				b.set((k-1)/2, temp1);
				int temp2 = map.get(b.get((k-1)/2)).index;
				int temp3 = map.get(b.get(k)).index;
				map.get(b.get((k-1)/2)).index = temp3;
				map.get(b.get(k)).index = temp2;
				k = (k-1)/2;

			}
    	


    }

    /** Bubble b[k] down in heap until it finds the right place.
     *  Precondition: Each b[i]'s priority <= childrens' priorities 
     *                except perhaps for b[k] */
    private void bubbleDown(int k) {
        // TODO 3:  Do poll and bubbleDown together.
        // Do not use recursion; use iteration.
        // Do NOT create new map entries
    	
		

    	while (k*2 + 1 < size && map.get(b.get(k*2 + 1)).priority <= map.get(b.get(k)).priority
    			|| (k*2 + 2< size && map.get(b.get(k*2 + 2)).priority <= map.get(b.get(k)).priority))
    	{
    		int i = smallerChildOf(k);
    		if (map.get(b.get(k)).priority<= map.get(b.get(i)).priority)
    			return;
    		E temp = b.get((i));
			E temp1 = b.get(k);
			b.set(k, temp);
			b.set(i, temp1);
			int temp2 = map.get(b.get(i)).index;
			int temp3 = map.get(b.get(k)).index;
			map.get(b.get(i)).index = temp3;
			map.get(b.get(k)).index = temp2;
			k = i;
    	}



    }

    /** Return the index of the smaller child of b[q]
     *  Precondition: left child exists: 2n+1 < size of heap */
    private int smallerChildOf(int n) {
        // You do not have to implement this method. We found it useful.
        // Implement it if you want.
        // Change its specification if you want.
        double temp = map.get(b.get(n*2 + 1)).priority;
        if (n*2 +2 > b.size()-1)
        	return (n*2+1);
       
        double temp1 = map.get(b.get(n*2 + 2)).priority;
        double difference = temp - temp1; 
        if (difference < 0)
        	return (n*2+1);
        else
        	return (n*2+2);
        
    }

    /** An instance contains the priority and index an element of the heap. */
    private static class Prindex {
        private double priority; // priority of this element
        private int index;  // index of this element in map

        /** Constructor: an instance in b[i] with priority p. */
        private Prindex(int i, double p) {
            index= i;
            priority= p;
        }
    }
}