package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KdTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private KdTree tree;
    private Map<Point, Long> pointIdMap = new HashMap<>();
    private MyTrieSet trie = new MyTrieSet();
    private Map<String, List<Node>> cleanNameNodesMap = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();

        nodes.forEach(node -> {
            if (hasNeighbours(node)) {
                Point point = new Point(node.lon(), node.lat());
                pointIdMap.put(point, node.id());
                points.add(point);
            }

            if (node.name() != null) {
                String cleanName = cleanString(node.name());
                trie.add(cleanName);

                List<Node> lst = cleanNameNodesMap.getOrDefault(cleanName, new ArrayList<>());
                lst.add(node);
                cleanNameNodesMap.put(cleanName, lst);
            }
        });


        tree = new KdTree(points);
    }

    private boolean hasNeighbours(Node node) {
        return this.neighbors(node.id()).size() > 0;
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nearest = tree.nearest(lon, lat);
        return pointIdMap.get(nearest);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        Set<String> set = new HashSet<>();

        for (var name : trie.keysWithPrefix(cleanString(prefix))) {
            cleanNameNodesMap.get(name).forEach(node -> set.add(node.name()));
        }

        return new LinkedList<>(set);
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        var res = new LinkedList<Map<String, Object>>();
        cleanNameNodesMap.get(cleanString(locationName))
                .forEach(node -> {
                    var m = new HashMap<String, Object>();
                    m.put("lat", node.lat());
                    m.put("lon", node.lon());
                    m.put("name", node.name());
                    m.put("id", node.id());
                    res.add(m);
                });
        return res;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
