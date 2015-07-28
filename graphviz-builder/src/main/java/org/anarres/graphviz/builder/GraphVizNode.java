/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizNode extends GraphVizObject<GraphVizNode> {

    @CheckForNull
    private String shape;

    /* pp */ GraphVizNode(@Nonnull GraphVizGraph graph, @Nonnull Key key, int id) {
        super(graph, key, "n" + id);
    }

    @CheckForNull
    public String getShape() {
        return shape;
    }

    @Nonnull
    public GraphVizNode shape(@CheckForNull String shape) {
        this.shape = shape;
        return this;
    }
}
