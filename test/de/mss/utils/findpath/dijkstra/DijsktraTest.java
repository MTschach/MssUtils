package de.mss.utils.findpath.dijkstra;

import java.util.List;

import org.junit.Test;

import de.mss.utils.findpath.NodeType;
import junit.framework.TestCase;

public class DijsktraTest extends TestCase {


   @Test
   public void testNoPath() {
      Dijkstra d = new Dijkstra(null, new Node("D", 2, 2));
      d.findPath();
      assertEquals(-1, d.getMinimumDistance());

      d = new Dijkstra(new Node("S", 2, 2), null);
      d.findPath();
      assertEquals(-1, d.getMinimumDistance());

      final Node s = new Node("S", 2, 2, NodeType.START);
      final Node z = new Node("D", 3, 4, NodeType.DESTINATION);
      s.addNeighbour(z);
      d = new Dijkstra(s, z);
      d.findPath();
      assertEquals(2, d.getMinimumDistance());
      d.setDestinationNode(null);
      assertEquals(2, d.getMinimumDistance());
   }


   @Test
   public void testOk() {
      final Node frankfurt = new Node("Frankfurt", 50117, 8683, NodeType.START);
      final Node mannheim = new Node("Mannheim", 49483, 8467);
      final Node wuerzburg = new Node("Würzburg", 49800, 9933);
      final Node kassel = new Node("Kassel", 51317, 9500);
      final Node karlsruhe = new Node("Karlsruhe", 49017, 8400);
      final Node erfurt = new Node("Erfurt", 50986, 11022);
      final Node nuernberg = new Node("Nürnberg", 49450, 11083);
      final Node stuttgard = new Node("Stuttgart", 48783, 9183);
      final Node augsburg = new Node("Augsburg", 48367, 10900);
      final Node muenchen = new Node("München", 48133, 11567, NodeType.DESTINATION);

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

      assertEquals(3717, d.getMinimumDistance());

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
      assertEquals("MinimumDistance {-1} ", d.toString());

      d.setStartNode(new Node("s", 1, 1, NodeType.START));
      assertEquals("StartNode {Name {s} NodeType {START} } MinimumDistance {-1} ", d.toString());

      d.setDestinationNode(new Node("d", 2, 2, NodeType.DESTINATION));
      assertEquals("StartNode {Name {s} NodeType {START} } DestinationNode {Name {d} NodeType {DESTINATION} } MinimumDistance {-1} ", d.toString());
   }
}
