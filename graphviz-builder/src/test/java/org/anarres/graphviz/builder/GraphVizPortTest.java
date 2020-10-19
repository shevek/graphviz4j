/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shevek
 */
public class GraphVizPortTest implements GraphVizScope {

    private static final Logger LOG = LoggerFactory.getLogger(GraphVizPortTest.class);

    @Test
    public void testPortsDependOnNodes() throws Exception {
        GraphVizGraph graph = new GraphVizGraph();
        GraphVizPort prev = null;
        for (int i = 0; i < 4; i++) {
            GraphVizPort curr = graph.node(this, "n" + i).port("id");   // This must be the same.
            if (prev != null)
                graph.edge(prev, curr);
            prev = curr;
        }
        assertEquals(3, graph.getEdges().size());   // n-1 edges for n nodes.
    }
}
