/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Easy utilities for emitting GraphViz graphs.
 *
 * @author shevek
 */
public class GraphVizUtils {

    /* pp */ static int hashCode(@CheckForNull Object o) {
        return o == null ? 0 : o.hashCode();
    }

    /* pp */ static boolean equals(@CheckForNull Object a, @CheckForNull Object b) {
        if (a == b)
            return true;
        if (a == null)
            return false;
        return a.equals(b);
    }

    /**
     * This supports a one-liner: GraphVizUtils.toGraphVizGraph(object).writeTo(new File(...)).
     *
     * @param object The object to graph.
     * @return A newly constructed GraphVizGraph.
     */
    @Nonnull
    public static GraphVizGraph toGraphVizGraph(@Nonnull GraphVizable object) {
        Preconditions.checkNotNull(object, "GraphVizable object was null.");
        GraphVizGraph graph = new GraphVizGraph();
        object.toGraphViz(graph);
        return graph;
    }

    /**
     * Equivalent to {@link #toGraphVizGraph(GraphVizable) toGraphVizGraph}(object).{@link GraphVizGraph#writeTo(java.io.File) writeTo}(file).
     *
     * @param file The file to which to write the graph.
     * @param object The object to convert.
     * @throws IOException If an I/O error occurs.
     */
    public static void toGraphVizFile(@Nonnull File file, @Nonnull GraphVizable object) throws IOException {
        toGraphVizGraph(object).writeTo(file);
    }
}
