/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizCluster extends GraphVizObject<GraphVizCluster> {

    @CheckForNull
    private final GraphVizCluster parent;
    @Nonnull
    private final Set<GraphVizCluster.Key> clusterKeys = new LinkedHashSet<GraphVizCluster.Key>();
    @Nonnull
    private final Set<GraphVizNode.Key> nodeKeys = new LinkedHashSet<GraphVizNode.Key>();

    /* pp */ GraphVizCluster(@Nonnull GraphVizGraph graph, @Nonnull Key key, @CheckForNull GraphVizCluster parent, int id) {
        super(graph, key, (parent == null ? "cluster_c" : parent.getId() + "_") + id);
        this.parent = parent;
    }

    @CheckForNull
    public GraphVizCluster getParent() {
        return parent;
    }

    @Nonnull
    /* pp */ Set<? extends Key> getClusterKeys() {
        return clusterKeys;
    }

    @Nonnull
    /* pp */ Set<? extends GraphVizNode.Key> getNodeKeys() {
        return nodeKeys;
    }

    // Force this to take at least one object so people don't miss the scope argument.
    @Nonnull
    public GraphVizCluster add(@Nonnull GraphVizNode... nodes) {
        for (GraphVizNode node : nodes)
            nodeKeys.add(node.getKey());
        return this;
    }

    @Nonnull
    public GraphVizCluster add(@Nonnull GraphVizScope scope, @Nonnull Object... objects) {
        for (Object object : objects)
            nodeKeys.add(new GraphVizObject.Key(scope, object));
        return this;
    }

    @Nonnull
    public GraphVizCluster add(@Nonnull GraphVizScope scope, @Nonnull Iterable<Object> objects) {
        for (Object object : objects)
            nodeKeys.add(new GraphVizObject.Key(scope, object));
        return this;
    }
}
