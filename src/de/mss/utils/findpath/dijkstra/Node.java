package de.mss.utils.findpath.dijkstra;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.mss.utils.findpath.NodeType;

public class Node implements Comparable<Node>, Serializable {

   private static final long   serialVersionUID = -7356676034409958568L;

   private String              name             = null;
   protected Map<Node, Double> neighbours       = new HashMap<>();

   protected Node              predecessor      = null;

   private NodeType            type             = NodeType.NORMAL;

   private double              x                = 0.0;
   private double              y                = 0.0;


   public Node(String n) {
      this.name = n;
   }


   public Node(String n, double px, double py) {
      this.name = n;
      this.x = px;
      this.y = py;
   }


   public Node(String n, double px, double py, NodeType t) {
      this.name = n;
      this.x = px;
      this.y = py;
      this.type = t;
   }


   public Node(String n, NodeType t) {
      this.name = n;
      this.type = t;
   }


   public void addNeighbour(Node otherNode) {
      addNeighbour(otherNode, calcDistance(otherNode));
   }


   public void addNeighbour(Node otherNode, double distance) {
      if (otherNode == null) {
         return;
      }

      this.neighbours.put(otherNode, Double.valueOf(distance));

      otherNode.getNeighbours().put(this, Double.valueOf(distance));
   }


   protected double calcDistance(Node otherNode) {
      if (otherNode == null) {
         return 0.0;
      }

      final double diffx = this.x - otherNode.getX();
      final double diffy = this.y - otherNode.getY();

      return Math.sqrt(diffx * diffx + diffy * diffy);
   }


   public double calculateDistanceRecursive() {
      double distance = 0;

      if (this.predecessor != null) {
         distance += this.predecessor.distance(this);

         final double distancePredecessor = this.predecessor.calculateDistanceRecursive();

         if (distancePredecessor == -1) {
            distance = distancePredecessor;
         } else {
            distance += distancePredecessor;
         }

         return distance;
      } else if (this.type != NodeType.START) {
         return -1;
      } else {
         return 0;
      }
   }


   @Override
   public int compareTo(Node otherNode) {
      if (otherNode == null) {
         return -1;
      }

      final double difference = this.calculateDistanceRecursive() - otherNode.calculateDistanceRecursive();

      if (difference == 0) {
         return 0;
      } else if (difference > 0) {
         return 1;
      }

      return -1;

   }


   public double distance(Node otherNode) {
      if (otherNode == null) {
         return 0.0;
      }

      final Double dist = this.neighbours.get(otherNode);

      return dist != null ? dist.doubleValue() : calcDistance(otherNode);
   }


   public String getName() {
      return this.name;
   }


   public Map<Node, Double> getNeighbours() {
      return this.neighbours;
   }


   public Node getPredecessor() {
      return this.predecessor;
   }


   public de.mss.utils.findpath.NodeType getType() {
      return this.type;
   }


   public double getX() {
      return this.x;
   }


   public double getY() {
      return this.y;
   }


   public boolean isNeighbour(Node otherNode) {
      return this.neighbours.containsKey(otherNode);
   }


   public void setName(String n) {
      this.name = n;
   }


   public void setPredecessor(Node otherNode) {
      this.predecessor = otherNode;
   }


   public void setType(NodeType t) {
      this.type = t;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      if (this.name != null) {
         sb.append("Name {" + this.name + "} ");
      }

      if (this.type != null) {
         sb.append("NodeType {" + this.type + "} ");
      }

      if (this.predecessor != null) {
         sb.append("Predecessor {" + this.predecessor.toString() + "} ");
      }

      return sb.toString();
   }
}
