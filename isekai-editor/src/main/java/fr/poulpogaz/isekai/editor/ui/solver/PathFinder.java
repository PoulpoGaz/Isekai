package fr.poulpogaz.isekai.editor.ui.solver;

import fr.poulpogaz.isekai.commons.pack.Tile;
import fr.poulpogaz.isekai.editor.pack.solver.State;
import org.joml.Vector2i;

import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

    private static final Vector2i[] DIRS = new Vector2i[] {
            new Vector2i(-1, 0),
            new Vector2i(1, 0),
            new Vector2i(0, -1),
            new Vector2i(0, 1)
    };

    /**
     * @return an array of size 3 containing the original
     *          crate position (index 0), where it goes (index 1),
     *          where the player should be to do this move (index 2)
     */
    public static Vector2i[] getMovement(State state1, State state2, int width, int height) {
        List<Integer> state1Crates = Arrays.stream(state1.cratesIndex).boxed().collect(Collectors.toList());
        List<Integer> state2Crates = Arrays.stream(state2.cratesIndex).boxed().collect(Collectors.toList());

        List<Integer> state1Copy = state1Crates.stream().toList();
        state1Crates.removeAll(state2Crates);
        state2Crates.removeAll(state1Copy);

        int mvt1X = (state1Crates.get(0) % width);
        int mvt1Y = (state1Crates.get(0) / width);
        Vector2i mvt1 = new Vector2i(mvt1X, mvt1Y);

        int mvt2X = (state2Crates.get(0) % width);
        int mvt2Y = (state2Crates.get(0) / width);
        Vector2i mvt2 = new Vector2i(mvt2X, mvt2Y);

        return new Vector2i[] {
                mvt1, mvt2,
                mvt1.mul(2, new Vector2i()).sub(mvt2)
        };
    }

    private static Node[] createNodes(Tile[] map, int width, int height, int[] crates) {
        Node[] nodes = new Node[map.length];

        int x = 0;
        int y = 0;
        for (int i = 0; i < map.length; i++) {
            boolean aCrate = false;

            for (int crate : crates) {
                if (crate == i) {
                    aCrate = true;
                    break;
                }
            }

            if ((map[i] == Tile.FLOOR || map[i] == Tile.TARGET) && !aCrate) {
                nodes[i] = new Node(null, new Vector2i(x, y), Integer.MAX_VALUE);
            }

            x++;
            if (x >= width) {
                x = 0;
                y++;
            }
        }

        return nodes;
    }

    // Dijkstra
    public static List<Vector2i> findPath(Tile[] map, int width, int height, State state1, Vector2i dest) {
        int destI = dest.y * width + dest.x;

        Node[] nodes = createNodes(map, width, height, state1.cratesIndex);
        nodes[state1.playerIndex].distance = 0; // init

        PriorityQueue<Node> queue = new PriorityQueue<>(nodes.length, PathFinder::nodeComparator);
        for (Node n : nodes) {
            if (n != null) {
                queue.offer(n);
            }
        }

        dijkstra:
        while (!queue.isEmpty()) {
            Node n = queue.poll();

            for (Vector2i dir : DIRS) {
                Vector2i newPos = n.position.add(dir, new Vector2i());

                if (newPos.x < 0 || newPos.y < 0 || newPos.x >= width || newPos.y >= height) {
                    continue;
                }

                int i = newPos.y * width + newPos.x;
                if (nodes[i] != null && queue.contains(nodes[i])) {
                    Node child = nodes[i];

                    child.parent = n;
                    child.distance = n.distance + 1;

                    if (i == destI) {
                        break dijkstra;
                    }

                    // force update of priority queue
                    queue.remove(child);
                    queue.add(child);
                }
            }
        }

        return constructPath(nodes[destI]);
    }

    private static int nodeComparator(Node a, Node b) {
        return Integer.compare(a.distance, b.distance);
    }

    private static List<Vector2i> constructPath(Node dest) {
        List<Vector2i> points = new ArrayList<>();

        Node parent = dest;
        while (parent != null) {
            points.add(parent.position);

            parent = parent.parent;
        }

        Collections.reverse(points);
        return points;
    }

    private static final class Node {
        private Node parent;
        private Vector2i position;
        private int distance;

        private Node(Node parent, Vector2i position, int distance) {
            this.parent = parent;
            this.position = position;
            this.distance = distance;
        }

        public Node parent() {
            return parent;
        }

        public Vector2i position() {
            return position;
        }

        public int distance() {
            return distance;
        }
    }
}
