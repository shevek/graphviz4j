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
public abstract class GraphVizElement<T extends GraphVizElement<?>> {

    @Nonnull
    private final GraphVizGraph graph;
    @CheckForNull
    private GraphVizLabel label;
    @CheckForNull
    private String color;
    @CheckForNull
    private String style;

    /* pp */ GraphVizElement(@Nonnull GraphVizGraph graph) {
        this.graph = graph;
    }

    public GraphVizGraph getGraph() {
        return graph;
    }

    @CheckForNull
    public GraphVizLabel getLabel() {
        return label;
    }

    @Nonnull
    public GraphVizLabel label() {
        if (label == null)
            label = new GraphVizLabel();
        return label;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T label(@Nonnull CharSequence csq) {
        label().set(csq);
        return (T) this;
    }

    @CheckForNull
    public String getColor() {
        return color;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T color(@Nonnull String color) {
        this.color = color;
        return (T) this;
    }

    @CheckForNull
    public String getStyle() {
        return style;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T style(@Nonnull String style) {
        this.style = style;
        return (T) this;
    }
}
