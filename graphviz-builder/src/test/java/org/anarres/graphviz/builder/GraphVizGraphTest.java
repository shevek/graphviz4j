/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.StringWriter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author shevek
 */
public class GraphVizGraphTest implements GraphVizScope {

    private static final Logger LOG = LoggerFactory.getLogger(GraphVizGraphTest.class);

    @Test
    public void testWriter() throws Exception {
        GraphVizGraph graph = new GraphVizGraph();
        assertNotNull(graph.node(this, "foo"));
        assertSame(graph.node(this, "foo"), graph.node(this, "foo"));
        graph.node(this, "foo").label("something");
        graph.node(this, "bar").label("something");
        graph.edge(this, "bar", "foo");
        graph.edge(this, "foo", "bar").label("Some label");

        graph.label("This is my graph\ntitle");

        graph.cluster(this, "foo").label("My cluster").add(this, "foo");

        {
            StringWriter writer = new StringWriter();
            graph.writeTo(writer);
            LOG.info("Graph is " + writer);
        }

        {
            LOG.info("Graph is " + graph);
        }
    }
}
