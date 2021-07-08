package de.mss.utils.findpath.dijkstra;

import java.util.List;

import org.junit.Test;

import de.mss.utils.findpath.NodeType;
import junit.framework.TestCase;

public class DijsktraTest extends TestCase {


   @Test
   public void testNoPath() {
      Dijkstra d = new Dijkstra(null, new NodeInt("D", 2, 2));
      d.findPath();
      assertEquals(Double.valueOf(-1), Double.valueOf(d.getMinimumDistance()));

      d = new Dijkstra(new NodeInt("S", 2, 2), null);
      d.findPath();
      assertEquals(Double.valueOf(-1), Double.valueOf(d.getMinimumDistance()));

      final NodeInt s = new NodeInt("S", 2, 2, NodeType.START);
      final NodeInt z = new NodeInt("D", 3, 4, NodeType.DESTINATION);
      s.addNeighbour(z);
      d = new Dijkstra(s, z);
      d.findPath();
      assertEquals(Double.valueOf(2), Double.valueOf(d.getMinimumDistance()));
      d.setDestinationNode(null);
      assertEquals(Double.valueOf(2), Double.valueOf(d.getMinimumDistance()));
   }


   @Test
   public void testOk() {
      final NodeInt frankfurt = new NodeInt("Frankfurt", 50117, 8683, NodeType.START);
      final NodeInt mannheim = new NodeInt("Mannheim", 49483, 8467);
      final NodeInt wuerzburg = new NodeInt("Würzburg", 49800, 9933);
      final NodeInt kassel = new NodeInt("Kassel", 51317, 9500);
      final NodeInt karlsruhe = new NodeInt("Karlsruhe", 49017, 8400);
      final NodeInt erfurt = new NodeInt("Erfurt", 50986, 11022);
      final NodeInt nuernberg = new NodeInt("Nürnberg", 49450, 11083);
      final NodeInt stuttgard = new NodeInt("Stuttgart", 48783, 9183);
      final NodeInt augsburg = new NodeInt("Augsburg", 48367, 10900);
      final NodeInt muenchen = new NodeInt("München", 48133, 11567, NodeType.DESTINATION);

      frankfurt.addNeighbour(mannheim);
      frankfurt.addNeighbour(wuerzburg);
      frankfurt.addNeighbour(kassel);

      mannheim.addNeighbour(karlsruhe);

      wuerzburg.addNeighbour(erfurt);
      wuerzburg.addNeighbour(nuernberg);

      nuernberg.addNeighbour(stuttgard);
      nuernberg.addNeighbour(muenchen);

      kassel.addNeighbour(muenchen);

      karlsruhe.addNeighbour(augsburg);

      augsburg.addNeighbour(muenchen);

      final Dijkstra d = new Dijkstra(frankfurt, muenchen);
      d.findPath();

      assertEquals(Double.valueOf(3717), Double.valueOf(d.getMinimumDistance()));

      final List<Node> path = d.getPath();
      assertEquals(4, path.size());
      assertEquals("Frankfurt", path.get(3).getName());
      assertEquals("Würzburg", path.get(2).getName());
      assertEquals("Nürnberg", path.get(1).getName());
      assertEquals("München", path.get(0).getName());

   }


   @Test
   public void testToString() {
      final Dijkstra d = new Dijkstra(null, null);
      assertEquals("MinimumDistance {-1.0} ", d.toString());

      d.setStartNode(new NodeInt("s", 1, 1, NodeType.START));
      assertEquals("StartNode {Name {s} NodeType {START} } MinimumDistance {-1.0} ", d.toString());

      d.setDestinationNode(new NodeInt("d", 2, 2, NodeType.DESTINATION));
      assertEquals("StartNode {Name {s} NodeType {START} } DestinationNode {Name {d} NodeType {DESTINATION} } MinimumDistance {-1.0} ", d.toString());
   }
}
