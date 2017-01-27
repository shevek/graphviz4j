/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder.jgrapht;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.MoreObjects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import org.anarres.graphviz.builder.GraphVizEdge;
import org.anarres.graphviz.builder.GraphVizGraph;
import org.anarres.graphviz.builder.GraphVizNode;
import org.anarres.graphviz.builder.GraphVizScope;
import org.jgrapht.Graph;

/**
 *
 * @author shevek
 */
public class JGraphTGraphVizBuilder<V, E> implements GraphVizScope {

    private static final Function<Object, String> EDGE_LABEL_NULL = Functions.constant(null);

    private final Graph<V, E> graph;
    private final GraphVizScope scope = this;
    private Function<? super V, String> nodeLabelFunction = Functions.toStringFunction();
    private Function<? super E, String> edgeLabelFunction = EDGE_LABEL_NULL;
    private Function<? super E, String> edgeHeadLabelFunction = EDGE_LABEL_NULL;
    private Function<? super E, String> edgeTailLabelFunction = EDGE_LABEL_NULL;

    public JGraphTGraphVizBuilder(@Nonnull Graph<V, E> graph, @CheckForNull Function<? super V, String> nodeLabelFunction) {
        this.graph = graph;
        this.nodeLabelFunction = (Function<? super V, String>) MoreObjects.firstNonNull(nodeLabelFunction, Functions.toStringFunction());
    }

    public JGraphTGraphVizBuilder(@Nonnull Graph<V, E> graph) {
        this(graph, null);
    }

    @Nonnull
    public JGraphTGraphVizBuilder<V, E> withNodeLabelFunction(Function<? super V, String> nodeLabelFunction) {
        this.nodeLabelFunction = nodeLabelFunction;
        return this;
    }

    @Nonnull
    public JGraphTGraphVizBuilder<V, E> withEdgeLabelFunction(Function<? super E, String> edgeLabelFunction) {
        this.edgeLabelFunction = edgeLabelFunction;
        return this;
    }

    @Nonnull
    public JGraphTGraphVizBuilder<V, E> withEdgeHeadLabelFunction(Function<? super E, String> edgeHeadLabelFunction) {
        this.edgeHeadLabelFunction = edgeHeadLabelFunction;
        return this;
    }

    @Nonnull
    public JGraphTGraphVizBuilder<V, E> withEdgeTailLabelFunction(Function<? super E, String> edgeTailLabelFunction) {
        this.edgeTailLabelFunction = edgeTailLabelFunction;
        return this;
    }

    public void build(@Nonnull GraphVizGraph out) {
        for (V node : graph.vertexSet()) {
            GraphVizNode n = out.node(scope, node);
            String label = nodeLabelFunction.apply(node);
            if (label != null)
                n.label(label);
        }
        for (E edge : graph.edgeSet()) {
            GraphVizEdge e = out.edge(scope, graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
            String label = edgeLabelFunction.apply(edge);
            if (label != null)
                e.label(label);
            String headLabel = edgeHeadLabelFunction.apply(edge);
            if (headLabel != null)
                e.headLabel(headLabel);
            String tailLabel = edgeTailLabelFunction.apply(edge);
            if (tailLabel != null)
                e.tailLabel(tailLabel);
        }
    }
}
