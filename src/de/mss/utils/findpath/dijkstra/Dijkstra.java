package de.mss.utils.findpath.dijkstra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra implements Serializable {

   private static final long serialVersionUID = 6793995987200391038L;


   private Node             startNode       = null;
   private Node             destinationNode = null;
   private final List<Node> priorityList    = new ArrayList<>();
   private int              minimumDistance = -1;


   public Dijkstra(Node s, Node d) {
      setStartNode(s);
      setDestinationNode(d);
   }


   private void calculateNextNode(Node node) {
      this.priorityList.remove(node);

      if (node.equals(this.destinationNode)) {
         return;
      }

      final List<Node> nodeList = new ArrayList<>();
      nodeList.addAll(node.getNeighbours());
      nodeList.remove(node.getPredecessor());
      Collections.sort(nodeList);

      for (final Node n : nodeList) {
         final double distance = node.distance(n);
         final double nDistance = n.calculateDistanceRecursive();
         if (nDistance == -1 || nDistance > node.calculateDistanceRecursive() + distance) {
            n.setPredecessor(node);
         }

         if (!this.priorityList.contains(n)) {
            this.priorityList.add(n);
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


   public int getMinimumDistance() {
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
