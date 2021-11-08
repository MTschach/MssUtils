package de.mss.utils.findpath.dijkstra;

import org.junit.Test;

import de.mss.utils.findpath.NodeType;
import junit.framework.TestCase;

public class NodeTest extends TestCase {

   private NodeInt classUnderTest;
   private NodeInt n1;
   private NodeInt n2;


   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.classUnderTest = new NodeInt("TestNode", 12, 87, NodeType.START);
      setUpMap();
   }


   private void setUpMap() {
      this.n1 = new NodeInt("N1", 2, 4);
      this.n2 = new NodeInt("N2", 7, 85, NodeType.DESTINATION);

      this.classUnderTest.addNeighbour(this.n1);
      this.n1.addNeighbour(this.n2);
   }


   @Test
   public void testAddNeighbour() {
      this.classUnderTest.addNeighbour(null);
      this.classUnderTest.addNeighbour(this.n1);
   }


   @Test
   public void testCalcDistance() {
      final Node n = new Node("node");
      n.addNeighbour(null);
   }


   @Test
   public void testCalcDistance1() {
      final Node n = new Node("node");
      final Node otherNode = new Node("node1", 1.0, 0.0);
      n.addNeighbour(otherNode);
   }


   @Test
   public void testCalculateDistanceRecursivePredesessorNoStart() {
      final NodeInt t = new NodeInt("t", 4, 7);
      this.classUnderTest.setPredecessor(t);

      assertEquals(Double.valueOf(-1.0), Double.valueOf(this.classUnderTest.calculateDistanceRecursive()));
   }


   @Test
   public void testCalculateDistanceRecursivePredesessorStart() {
      final NodeInt t = new NodeInt("t", 4, 7);
      t.setPredecessor(this.classUnderTest);

      assertEquals(Double.valueOf(80.0), Double.valueOf(t.calculateDistanceRecursive()));
   }


   @Test
   public void testCalculateDistanceRecursiveStartNode() {
      assertEquals(Double.valueOf(0.0), Double.valueOf(this.classUnderTest.calculateDistanceRecursive()));
   }


   @Test
   public void testCalculateDistanceRecursiveStartNoPredecessor() {
      final NodeInt t = new NodeInt("t", 2, 3);
      assertEquals(Double.valueOf(-1.0), Double.valueOf(t.calculateDistanceRecursive()));
   }


   @Test
   public void testCompare() {
      assertEquals(-1, this.classUnderTest.compareTo(null));

      assertEquals(0, this.classUnderTest.compareTo(this.classUnderTest));

      assertEquals(1, this.classUnderTest.compareTo(this.n1));

      assertEquals(-1, this.n1.compareTo(this.classUnderTest));

   }


   @Test
   public void testConstructorName() {
      final Node n = new Node("node");
      assertEquals("name", "node", n.getName());
      assertEquals("x", Double.valueOf(0), Double.valueOf(n.getX()));
      assertEquals("y", Double.valueOf(0), Double.valueOf(n.getY()));
      assertEquals("type", NodeType.NORMAL, n.getType());
   }


   @Test
   public void testConstructorNameType() {
      final Node n = new Node("node", NodeType.DESTINATION);
      assertEquals("name", "node", n.getName());
      assertEquals("x", Double.valueOf(0), Double.valueOf(n.getX()));
      assertEquals("y", Double.valueOf(0), Double.valueOf(n.getY()));
      assertEquals("type", NodeType.DESTINATION, n.getType());
   }


   @Test
   public void testDistance() {
      assertEquals(Double.valueOf(0.0), Double.valueOf(this.classUnderTest.distance(null)));
      assertEquals(Double.valueOf(83.0), Double.valueOf(this.classUnderTest.distance(this.n1)));
      assertEquals(Double.valueOf(5.0), Double.valueOf(this.classUnderTest.distance(this.n2)));
   }


   @Test
   public void testGetter() {
      assertEquals(Double.valueOf(12.0), Double.valueOf(this.classUnderTest.getX()));
      assertEquals(Double.valueOf(87.0), Double.valueOf(this.classUnderTest.getY()));
      assertEquals(NodeType.START, this.classUnderTest.getType());
      assertNull(this.classUnderTest.getPredecessor());
      assertNotNull(this.classUnderTest.getNeighbours());
      assertEquals("TestNode", this.classUnderTest.getName());
      assertTrue(this.classUnderTest.isNeighbour(this.n1));
   }


   @Test
   public void testToString() {
      this.classUnderTest.setName(null);
      this.classUnderTest.setPredecessor(null);
      this.classUnderTest.setType(null);

      assertEquals("", this.classUnderTest.toString());

      this.classUnderTest.setName("TestNode");

      assertEquals("Name {TestNode} ", this.classUnderTest.toString());

      this.classUnderTest.setType(NodeType.START);

      assertEquals("Name {TestNode} NodeType {START} ", this.classUnderTest.toString());

      this.classUnderTest.setPredecessor(new NodeInt("T", 1, 1));

      assertEquals("Name {TestNode} NodeType {START} Predecessor {Name {T} NodeType {NORMAL} } ", this.classUnderTest.toString());
   }
}
