package bearmaps.test;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class Mytest {
    @Test
    public void testCloset() {
        var graph = new AugmentedStreetMapGraph("../library-sp19/data/proj2c_xml/berkeley-2019.osm.xml");
        long id = graph.closest(37.875613, -122.26009);
        assertEquals(1281866063, id);
    }


    @Test
    public void test() {
        System.out.println(Math.log(8) / Math.log(2));
        System.out.println(Math.ceil(1.5));
    }
}
