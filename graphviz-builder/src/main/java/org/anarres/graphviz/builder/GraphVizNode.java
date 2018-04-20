/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizNode extends GraphVizObject<GraphVizNode> {

    /** Use GraphVizAttribute. */
    @Deprecated
    public static final String ATTR_SHAPE = "shape";
    /** Use GraphVizAttribute. */
    @Deprecated
    public static final String ATTR_HREF = "href";

    private Map<GraphVizPort.Key, GraphVizPort> ports;
    private int counter = 0;

    /* pp */ GraphVizNode(@Nonnull GraphVizGraph graph, @Nonnull Key key, int id) {
        super(graph, key, "n" + id);
    }

    @CheckForNull
    public String getShape() {
        return getAttribute(GraphVizAttribute.shape);
    }

    @Nonnull
    public GraphVizNode shape(@CheckForNull String shape) {
        return attr(GraphVizAttribute.shape, shape);
    }

    @Nonnull
    public GraphVizPort port(@Nonnull Object object) {
        GraphVizPort.Key key = new GraphVizPort.Key(getScope(), object);
        if (!getGraph().isScopeVisible(key.getScope()))
            return new GraphVizPort(this, key, -1);
        if (ports == null)
            ports = new LinkedHashMap<GraphVizPort.Key, GraphVizPort>();
        GraphVizPort port = ports.get(key);
        if (port == null) {
            int id = counter++;
            port = new GraphVizPort(this, key, id);
            ports.put(key, port);
        }
        return port;
    }

    @CheckForNull
    public String toLabelString() {
        GraphVizLabel label = getLabel();
        if (ports == null || ports.isEmpty()) {
            if (label == null)
                return null;
            return label.toString();
        }
        GraphVizRecordLabel recordLabel = new GraphVizRecordLabel();
        if (label != null)
            recordLabel.title(label.getBuffer().toString());
        for (Map.Entry<Key, GraphVizPort> e : ports.entrySet())
            recordLabel.field(e.getValue());
        return recordLabel.toString();
    }
}
