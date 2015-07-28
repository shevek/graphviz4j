/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public abstract class GraphVizScopePredicate implements Predicate<GraphVizScope> {

    @Nonnull
    public static GraphVizScopePredicate all() {
        return new GraphVizScopePredicate() {
            @Override
            public boolean apply(GraphVizScope t) {
                return true;
            }
        };
    }

    @Nonnull
    public static GraphVizScopePredicate include(final Class<? extends GraphVizScope>... types) {
        return new GraphVizScopePredicate() {
            @Override
            public boolean apply(GraphVizScope t) {
                for (Class<? extends GraphVizScope> type : types)
                    if (type.isInstance(t))
                        return true;
                return false;
            }
        };
    }

    @Nonnull
    public static GraphVizScopePredicate exclude(final Class<? extends GraphVizScope>... types) {
        return new GraphVizScopePredicate() {
            @Override
            public boolean apply(GraphVizScope t) {
                for (Class<? extends GraphVizScope> type : types)
                    if (type.isInstance(t))
                        return false;
                return true;
            }
        };
    }
}
