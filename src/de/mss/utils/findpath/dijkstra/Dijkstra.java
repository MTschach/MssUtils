package de.mss.utils.findpath.dijkstra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Dijkstra implements Serializable {

   private static final long serialVersionUID = 6793995987200391038L;


   private Node             startNode       = null;
   private Node             destinationNode = null;
   private final List<Node> priorityList    = new ArrayList<>();
   private double             minimumDistance = -1.0;


   public Dijkstra(Node s, Node d) {
      setStartNode(s);
      setDestinationNode(d);
   }


   private void calculateNextNode(Node node) {
      this.priorityList.remove(node);

      if (node.equals(this.destinationNode)) {
         return;
      }

      final Map<Node, Double> nodeList = new HashMap<>();
      nodeList.putAll(node.getNeighbours());
      nodeList.remove(node.getPredecessor());
      //      Collections.sort(nodeList);

      for (final Entry<Node, Double> n : nodeList.entrySet()) {
         final double distance = node.distance(n.getKey());
         final double nDistance = n.getKey().calculateDistanceRecursive();
         if (nDistance == -1 || nDistance > node.calculateDistanceRecursive() + distance) {
            n.getKey().setPredecessor(node);
         }

         if (!this.priorityList.contains(n.getKey())) {
            this.priorityList.add(n.getKey());
         }
      }
   }


   public void findPath() {
      if (this.startNode == null || this.destinationNode == null) {
         return;
      }

      this.priorityList.add(this.startNode);

      while (!this.priorityList.isEmpty()) {
         calculateNextNode(this.priorityList.get(0));
      }
   }


   public double getMinimumDistance() {
      if (this.minimumDistance == -1 && this.destinationNode != null) {
         this.minimumDistance = this.destinationNode.calculateDistanceRecursive();
      }

      return this.minimumDistance;
   }


   public List<Node> getPath() {
      final List<Node> path = new ArrayList<>();
      Node n = this.destinationNode;
      while (n != null) {
         path.add(n);
         n = n.getPredecessor();
      }

      return path;
   }


   public void setDestinationNode(Node d) {
      this.destinationNode = d;
   }


   public void setStartNode(Node s) {
      this.startNode = s;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();

      if (this.startNode != null) {
         sb.append("StartNode {" + this.startNode.toString() + "} ");
      }

      if (this.destinationNode != null) {
         sb.append("DestinationNode {" + this.destinationNode.toString() + "} ");
      }

      sb.append("MinimumDistance {" + getMinimumDistance() + "} ");

      return sb.toString();
   }
}
