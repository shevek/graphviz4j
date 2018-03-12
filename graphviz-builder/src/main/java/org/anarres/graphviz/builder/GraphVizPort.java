/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizPort extends GraphVizObject<GraphVizPort> {

    private final String portId;

    /* pp */ GraphVizPort(@Nonnull GraphVizNode node, @Nonnull Key key, int portId) {
        super(node.getGraph(), key, node.getId() + ":p" + portId);
        this.portId = "p" + portId;
    }

    @Nonnull
    public String getPortId() {
        return portId;
    }
}
