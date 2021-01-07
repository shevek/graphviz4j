/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public abstract class GraphVizElement<T extends GraphVizElement<?>> {

    /** Use GraphVizAttribute. */
    @Deprecated
    public static final String ATTR_COLOR = "color";
    /** Use GraphVizAttribute. */
    @Deprecated
    public static final String ATTR_STYLE = "style";

    @Nonnull
    private final GraphVizGraph graph;
    private final Set<String> comments = new LinkedHashSet<String>();
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

    @CheckForNull
    public String getAttribute(@Nonnull GraphVizAttribute name) {
        return getAttribute(name.getName());
    }

    @CheckForNull
    public String getAttribute(@Nonnull GraphVizHtmlAttribute name) {
        return getAttribute(name.getName());
    }

    /** Sets (or removes) an arbitrary String attribute on this element. */
    public void setAttribute(@Nonnull String name, @CheckForNull String value) {
        if (value == null)
            attributes.remove(name);
        else
            attributes.put(name, value);
    }

    /**
     * Sets (or removes) a well-known attribute on this element.
     *
     * Well-known attributes share the same namespace as string attributes.
     */
    public void setAttribute(@Nonnull GraphVizAttribute name, @CheckForNull String value) {
        setAttribute(name.getName(), value);
    }

    /**
     * Sets (or removes) a well-known attribute on this element.
     *
     * Well-known attributes share the same namespace as string attributes.
     */
    public void setAttribute(@Nonnull GraphVizHtmlAttribute name, @CheckForNull String value) {
        setAttribute(name.getName(), value);
    }

    /**
     * Sets (or removes) an arbitrary String attribute on this element.
     *
     * @return This object.
     */
    @Nonnull
    public T attr(@Nonnull String name, @CheckForNull String value) {
        setAttribute(name, value);
        return (T) this;
    }

    /**
     * Sets (or removes) a well-known attribute on this element.
     *
     * Well-known attributes share the same namespace as string attributes.
     *
     * @return This object.
     */
    @Nonnull
    public T attr(@Nonnull GraphVizAttribute name, @CheckForNull String value) {
        return attr(name.name(), value);
    }

    /**
     * Sets (or removes) a well-known attribute on this element.
     *
     * Well-known attributes share the same namespace as string attributes.
     *
     * @return This object.
     */
    @Nonnull
    public T attr(@Nonnull GraphVizHtmlAttribute name, @CheckForNull String value) {
        return attr(name.getName(), value);
    }

    @CheckForNull
    public String getColor() {
        return getAttribute(GraphVizAttribute.color);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T color(@CheckForNull String color) {
        return attr(GraphVizAttribute.color, color);
    }

    @CheckForNull
    public String getStyle() {
        return getAttribute(GraphVizAttribute.style);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T style(@CheckForNull String style) {
        return attr(GraphVizAttribute.style, style);
    }

    @CheckForNull
    public String getHref() {
        return getAttribute(GraphVizAttribute.href);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T href(String value) {
        return attr(GraphVizAttribute.href, value);
    }

    @CheckForNull
    public String getTooltip() {
        return getAttribute(GraphVizAttribute.tooltip);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public T tooltip(String value) {
        return attr(GraphVizAttribute.tooltip, value);
    }
}
