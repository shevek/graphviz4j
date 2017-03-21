/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public abstract class GraphVizElement<T extends GraphVizElement<?>> {

    public static final String ATTR_COLOR = "color";
    public static final String ATTR_STYLE = "style";

    @Nonnull
    private final GraphVizGraph graph;
    private final Set<String> comments = new HashSet<String>();
    @CheckForNull
    private GraphVizLabel label;
    @Nonnull
    private final Map<String, String> attributes = new LinkedHashMap<String, String>();

    /* pp */ GraphVizElement(@Nonnull GraphVizGraph graph) {
        this.graph = graph;
    }

    @Nonnull
    public GraphVizGraph getGraph() {
        return graph;
    }

    @Nonnull
    public Collection<? extends String> getComments() {
        return comments;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T comment(String text) {
        comments.add(text);
        return (T) this;
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

    @Nonnull
    public Map<? extends String, ? extends String> getAttributes() {
        return attributes;
    }

    @CheckForNull
    public String getAttribute(@Nonnull String name) {
        return attributes.get(name);
    }

    public void setAttribute(@Nonnull String name, @CheckForNull String value) {
        if (value == null)
            attributes.remove(name);
        else
            attributes.put(name, value);
    }

    @Nonnull
    public T attr(@Nonnull String name, @CheckForNull String value) {
        setAttribute(name, value);
        return (T) this;
    }

    @CheckForNull
    public String getColor() {
        return getAttribute(ATTR_COLOR);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T color(@Nonnull String color) {
        return attr(ATTR_COLOR, color);
    }

    @CheckForNull
    public String getStyle() {
        return getAttribute(ATTR_STYLE);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T style(@Nonnull String style) {
        return attr(ATTR_STYLE, style);
    }
}
