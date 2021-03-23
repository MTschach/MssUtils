package de.mss.utils.findpath.dijkstra;

import org.junit.Test;

import de.mss.utils.findpath.NodeType;
import junit.framework.TestCase;

public class NodeTest extends TestCase {

   private Node classUnderTest;
   private Node n1;
   private Node n2;


   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.classUnderTest = new Node("TestNode", 12, 87, NodeType.START);
      setUpMap();
   }


   private void setUpMap() {
      this.n1 = new Node("N1", 2, 4);
      this.n2 = new Node("N2", 7, 85, NodeType.DESTINATION);

      this.classUnderTest.addNeighbour(this.n1);
      this.n1.addNeighbour(this.n2);
   }


   @Test
   public void testAddNeighbour() {
      this.classUnderTest.addNeighbour(null);
      this.classUnderTest.addNeighbour(this.n1);
   }


   @Test
   public void testCalculateDistanceRecursivePredesessorNoStart() {
      final Node t = new Node("t", 4, 7);
      this.classUnderTest.setPredecessor(t);

      assertEquals(-1, this.classUnderTest.calculateDistanceRecursive());
   }


   @Test
   public void testCalculateDistanceRecursivePredesessorStart() {
      final Node t = new Node("t", 4, 7);
      t.setPredecessor(this.classUnderTest);

      assertEquals(80, t.calculateDistanceRecursive());
   }


   @Test
   public void testCalculateDistanceRecursiveStartNode() {
      assertEquals(0, this.classUnderTest.calculateDistanceRecursive());
   }


   @Test
   public void testCalculateDistanceRecursiveStartNoPredecessor() {
      final Node t = new Node("t", 2, 3);
      assertEquals(-1, t.calculateDistanceRecursive());
   }


   @Test
   public void testCompare() {
      assertEquals(-1, this.classUnderTest.compareTo(null));

      assertEquals(0, this.classUnderTest.compareTo(this.classUnderTest));

      assertEquals(1, this.classUnderTest.compareTo(this.n1));

      assertEquals(-1, this.n1.compareTo(this.classUnderTest));

   }


   @Test
   public void testDistance() {
      assertEquals(0, this.classUnderTest.distance(null));
      assertEquals(83, this.classUnderTest.distance(this.n1));
      assertEquals(5, this.classUnderTest.distance(this.n2));
   }


   @Test
   public void testGetter() {
      assertEquals(12, this.classUnderTest.getX());
      assertEquals(87, this.classUnderTest.getY());
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

      this.classUnderTest.setPredecessor(new Node("T", 1, 1));

      assertEquals("Name {TestNode} NodeType {START} Predecessor {Name {T} NodeType {NORMAL} } ", this.classUnderTest.toString());
   }
}
