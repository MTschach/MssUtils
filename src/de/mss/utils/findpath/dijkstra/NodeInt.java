package de.mss.utils.findpath.dijkstra;

import de.mss.utils.findpath.NodeType;

public class NodeInt extends Node {

   private static final long serialVersionUID = -7986718756107743029L;

   public NodeInt(String n, int x, int y) {
      super(n, x, y);
   }


   public NodeInt(String n, int x, int y, NodeType t) {
      super(n, x, y, t);
   }


   @Override
   protected double calcDistance(Node n) {
      if (n == null) {
         return 0.0;
      }

      return Math.max(Math.abs((int)this.getX() - (int)n.getX()), Math.abs((int)this.getY() - (int)n.getY()));
   }
}
