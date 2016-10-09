
package student;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.Edge;
import graph.Node;

/** This class contains Dijkstra's shortest-path algorithm and some other methods. */
public class Paths {


    /** Return a list of the nodes on the shortest path from start to 
     * end, or the empty list if a path does not exist. */
    public static List<Node> shortestPath(Node start, Node end) {
        

        // The frontier set, as discussed in lecture 20
        LinkedList<Node> settledSet = new LinkedList<Node>();
        settledSet.add(start);
        if (start == end)
        	return settledSet;
        Heap<Node> frontier= new Heap<Node>();
        Heap<Node> settledSet1 = new Heap<Node>();
        HashMap<Node, SFInfo> map= new HashMap<Node, SFInfo>();
        map.put(start, new SFInfo());
        for (Edge edge : (start.getExits()))
        {
        	frontier.add(edge.getOther(start), edge.length);
        	map.put(edge.getOther(start), new SFInfo(start, edge.length));
        }
        settledSet1.add(start, 0);
        while (frontier.size() != 0)
        {
        	Node temp1 = frontier.peek();
        	int currDistance = pathDistance(listToLinkedList(buildPath(temp1, map)));
        	for (Edge edge : temp1.getExits())
        	{
        		Node other = edge.getOther(temp1);
        		if ((map.containsKey(other)) == false)
        		{
        			frontier.add(other, edge.length + currDistance);
        			map.put(other, new SFInfo(temp1, edge.length + currDistance));
        		}
        		else
        			if (currDistance + edge.length < map.get(other).distance)
        			{
        				try{
        				frontier.changePriority(other, currDistance + edge.length);
        				}
        				catch(Exception e)
        				{
        					settledSet1.changePriority(other, currDistance + edge.length);
        				}
        				map.get(other).distance = currDistance + edge.length;
        				map.get(other).backPointer = temp1;
        			}
        		
        	}
        	settledSet1.add(frontier.poll(), currDistance);
        	if (temp1 == end)
        		break;
        }
        if (map.containsKey(end))
        	return buildPath(end, map);
        return new LinkedList<Node>(); // no path found
    }
    
    public static LinkedList<Node> listToLinkedList (List<Node> alist)
    {
    	LinkedList<Node> links = new LinkedList<Node>();
    	for (Node n : alist)
    		links.add(n);
    	return links;
    }

    /** Return the path from the start node to end.
     *  Precondition: nodeInfo contains all the necessary information about
     *  the path. */
    public static List<Node> buildPath(Node end, HashMap<Node, SFInfo> nInfo) {
        LinkedList<Node> path= new LinkedList<Node>();
        Node p= end;
        // invariant: All the nodes from p's successor to the end are in
        //            path, in reverse order.
        while (p != null) {
            path.addFirst(p);
            p= nInfo.get(p).backPointer;
        }
        return path;
    }

    /** Return the sum of the weight of the edges on path path. */
    public static int pathDistance(LinkedList<Node> path) {
        synchronized(path) {
            Iterator<Node> iter= path.iterator();
            Node p= iter.next();  // First node on path
            int s= 0;
            // invariant: s = sum of weights of edges from start to p
            while (iter.hasNext()) {
                Node q= iter.next();
                s= s + p.getEdge(q).length;
                p= q;
            }
            return s;
        }
    }

    /** An instance contains information about a node: the previous node
     *  on a shortest path from the start node to this node and the distance
     *  of this node from the start node. */
    private static class SFInfo {
        private Node backPointer; // bckptr on path from start node to this one
        private int distance; // distance from start node to this one

        /** Constructor: an instance with distance d from the start node and
         *  backpointer p.*/
        private SFInfo(Node p, int d) {
            distance= d; //Distance from start node to this one.
            backPointer= p;  // Backpointer on the path (null if start node)
        }

        /** Constructor: an instance with a null previous node and distance 0. */
        private SFInfo() {}

        /** return a representation of this instance. */
        public String toString() {
            return "dist " + distance + ", bckptr " + backPointer;
        }
    }


}
