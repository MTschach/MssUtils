package de.mss.utils.findpath.dijkstra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.mss.utils.findpath.NodeType;

public class Node implements Comparable<Node>, Serializable {

   private static final long serialVersionUID = -7986718756107743029L;

   private String            name;

   private final List<Node>  neighbours       = new ArrayList<>();

   private Node              predecessor      = null;

   private NodeType          nodeType         = NodeType.NORMAL;

   private int               x                = 0;
   private int               y                = 0;


   public Node(String n, int x, int y) {
      setX(x);
      setY(y);
      setName(n);
   }


   public Node(String n, int x, int y, NodeType t) {
      setX(x);
      setY(y);
      setName(n);
      setType(t);
   }


   public void addNeighbour(Node n) {
      if (n == null) {
         return;
      }

      if (!this.neighbours.contains(n)) {
         this.neighbours.add(n);
      }

      if (!n.isNeighbour(this)) {
         n.addNeighbour(this);
      }
   }


   public int calculateDistanceRecursive() {
      int distance = 0;

      if (this.predecessor != null) {
         distance += this.predecessor.distance(this);

         final int distancePredecessor = this.predecessor.calculateDistanceRecursive();

         if (distancePredecessor == -1) {
            distance = distancePredecessor;
         } else {
            distance += distancePredecessor;
         }

         return distance;
      } else if (this.nodeType != NodeType.START) {
         return -1;
      } else {
         return 0;
      }
   }


   @Override
   public int compareTo(Node other) {
      if (other == null) {
         return -1;
      }

      final double difference = this.calculateDistanceRecursive() - other.calculateDistanceRecursive();

      if (difference == 0) {
         return 0;
      } else if (difference > 0) {
         return 1;
      }

      return -1;
   }


   public int distance(Node n) {
      if (n == null) {
         return 0;
      }

      return Math.max(Math.abs(this.x - n.getX()), Math.abs(this.y - n.getY()));
   }


   public String getName() {
      return this.name;
   }


   public List<Node> getNeighbours() {
      return this.neighbours;
   }


   public Node getPredecessor() {
      return this.predecessor;
   }


   public NodeType getType() {
      return this.nodeType;
   }


   public int getX() {
      return this.x;
   }


   public int getY() {
      return this.y;
   }


   public boolean isNeighbour(Node n) {
      return this.neighbours.contains(n);
   }


   public void setName(String n) {
      this.name = n;
   }


   public void setPredecessor(Node p) {
      this.predecessor = p;
   }


   public void setType(NodeType t) {
      this.nodeType = t;
   }


   private void setX(int v) {
      this.x = v;
   }


   private void setY(int v) {
      this.y = v;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      if (this.name != null) {
         sb.append("Name {" + this.name + "} ");
      }

      if (this.nodeType != null) {
         sb.append("NodeType {" + this.nodeType + "} ");
      }

      if (this.predecessor != null) {
         sb.append("Predecessor {" + this.predecessor.toString() + "} ");
      }

      return sb.toString();
   }
}
