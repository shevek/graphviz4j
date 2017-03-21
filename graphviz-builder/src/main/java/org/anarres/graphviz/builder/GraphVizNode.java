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

    public static final String ATTR_SHAPE = "shape";
    public static final String ATTR_HREF = "href";

    /* pp */ GraphVizNode(@Nonnull GraphVizGraph graph, @Nonnull Key key, int id) {
        super(graph, key, "n" + id);
    }

    @CheckForNull
    public String getShape() {
        return getAttribute(ATTR_SHAPE);
    }

    @Nonnull
    public GraphVizNode shape(@CheckForNull String shape) {
        return attr(ATTR_SHAPE, shape);
    }

    @CheckForNull
    public String getHref() {
        return getAttribute(ATTR_HREF);
    }

    @Nonnull
    public GraphVizNode href(@CheckForNull String href) {
        return attr(ATTR_HREF, href);
    }
}
