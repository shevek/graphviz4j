/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shevek
 */
public class GraphVizGraph {

    private static final Logger LOG = LoggerFactory.getLogger(GraphVizGraph.class);
    private static final long serialVersionUID = 1L;
    private int counter = 0;
    @CheckForNull
    private GraphVizLabel label;
    private final Predicate<? super GraphVizScope> scopes;
    private transient final Set<String> comments = new HashSet<String>();
    private transient final Map<String, String> graphOptions = new HashMap<String, String>();
    private transient final Map<GraphVizNode.Key, GraphVizNode> nodes = new HashMap<GraphVizNode.Key, GraphVizNode>();
    private transient final Map<GraphVizEdge.Key, GraphVizEdge> edges = new HashMap<GraphVizEdge.Key, GraphVizEdge>();
    private transient final Map<GraphVizCluster.Key, GraphVizCluster> clusters = new HashMap<GraphVizCluster.Key, GraphVizCluster>();

    public GraphVizGraph(@Nonnull Predicate<? super GraphVizScope> scopes) {
        this.scopes = scopes;
        graphOptions.put("compound", "true");
    }

    public GraphVizGraph() {
        this(Predicates.alwaysTrue());
    }

    protected boolean isScopeVisible(@Nonnull GraphVizScope scope) {
        return scopes.apply(scope);
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
    public GraphVizGraph label(@Nonnull CharSequence csq) {
        label().set(csq);
        return this;
    }

    @Nonnull
    public Collection<? extends String> getComments() {
        return comments;
    }

    @Nonnull
    public GraphVizGraph comment(String text) {
        comments.add(text);
        return this;
    }

    @Nonnull
    public Map<? extends String, ? extends String> getGraphOptions() {
        return graphOptions;
    }

    @CheckForNull
    public String getGraphOption(@Nonnull String key) {
        return graphOptions.get(key);
    }

    @CheckForNull
    public String getGraphOption(GraphVizGraphOption key) {
        return getGraphOption(key.name());
    }

    public void setGraphOption(@Nonnull String key, @CheckForNull String value) {
        if (value == null)
            graphOptions.remove(key);
        else
            graphOptions.put(key, value);
    }

    public void setGraphOption(@Nonnull GraphVizGraphOption key, @CheckForNull String value) {
        setGraphOption(key.name(), value);
    }

    private void clear(@Nonnull GraphVizScope scope, @Nonnull Iterable<? extends GraphVizObject.Key> data) {
        Iterator<? extends GraphVizObject.Key> it = data.iterator();
        while (it.hasNext()) {
            GraphVizObject.Key key = it.next();
            if (Objects.equal(key.getScope(), scope))
                it.remove();
        }
    }

    public void clear(@Nonnull GraphVizScope scope) {
        clear(scope, clusters.keySet());
        for (GraphVizCluster cluster : clusters.values()) {
            clear(scope, cluster.getClusterKeys());
            clear(scope, cluster.getNodeKeys());
        }

        clear(scope, nodes.keySet());

        Iterator<? extends GraphVizEdge.Key> it = edges.keySet().iterator();
        while (it.hasNext()) {
            GraphVizEdge.Key key = it.next();
            if (Objects.equal(key.getSourceKey().getScope(), scope))
                it.remove();
            else if (Objects.equal(key.getTargetKey().getScope(), scope))
                it.remove();
        }
    }

    @Nonnull
    public Collection<? extends GraphVizNode> getNodes() {
        return nodes.values();
    }

    @Nonnull
    public GraphVizNode node(@Nonnull GraphVizScope scope, @Nonnull Object object) {
        GraphVizNode.Key key = new GraphVizNode.Key(scope, object);
        if (!isScopeVisible(scope))
            return new GraphVizNode(this, key, -1);
        GraphVizNode node = nodes.get(key);
        if (node == null) {
            int id = counter++;
            // LOG.info("n" + id + " -> " + object, new Exception());
            node = new GraphVizNode(this, key, id);
            nodes.put(key, node);
        }
        return node;
    }

    public boolean containsNode(@Nonnull GraphVizScope scope, @Nonnull Object object) {
        GraphVizNode.Key key = new GraphVizNode.Key(scope, object);
        return nodes.containsKey(key);
    }

    @Nonnull
    public Collection<? extends GraphVizEdge> getEdges() {
        return edges.values();
    }

    /**
     * Ensures that this graph contains a given edge.
     *
     * @param source The source node of the edge.
     * @param target The target node of the edge.
     * @param edgeId An optional identifier for this edge, allowing for multigraphs.
     * @return Either an existing, or a newly created edge.
     */
    @Nonnull
    public GraphVizEdge edge(@Nonnull GraphVizObject<?> source, @Nonnull GraphVizObject<?> target, @CheckForNull Object edgeId) {
        GraphVizEdge.Key key = new GraphVizEdge.Key(source.getKey(), target.getKey(), edgeId);
        if (!isScopeVisible(source.getScope()))
            return new GraphVizEdge(this, key, "", "");
        if (!isScopeVisible(target.getScope()))
            return new GraphVizEdge(this, key, "", "");
        GraphVizEdge edge = edges.get(key);
        if (edge == null) {
            edge = new GraphVizEdge(this, key, source.getId(), target.getId());
            edges.put(key, edge);
            if (source instanceof GraphVizCluster)
                edge.logicalTail(source.getId());
            if (target instanceof GraphVizCluster)
                edge.logicalHead(target.getId());
        }
        return edge;
    }

    @Nonnull
    public GraphVizEdge edge(@Nonnull GraphVizObject<?> source, @Nonnull GraphVizObject<?> target) {
        return edge(source, target, null);
    }

    @Nonnull
    public GraphVizEdge edge(@Nonnull GraphVizScope scope, @Nonnull Object source, @Nonnull Object target, Object edgeId) {
        return edge(node(scope, source), node(scope, target), edgeId);
    }

    @Nonnull
    public GraphVizEdge edge(@Nonnull GraphVizScope scope, @Nonnull Object source, @Nonnull Object target) {
        return edge(scope, source, target, null);
    }

    public boolean containsEdge(@Nonnull GraphVizObject<?> source, @Nonnull GraphVizObject<?> target, @CheckForNull Object edgeId) {
        GraphVizEdge.Key key = new GraphVizEdge.Key(source.getKey(), target.getKey(), edgeId);
        return edges.containsKey(key);
    }

    public boolean containsEdge(@Nonnull GraphVizObject<?> source, @Nonnull GraphVizObject<?> target) {
        return containsEdge(source, target, null);
    }

    public boolean containsEdge(@Nonnull GraphVizScope scope, @Nonnull Object source, @Nonnull Object target, @CheckForNull Object edgeId) {
        GraphVizObject.Key k_source = new GraphVizObject.Key(scope, source);
        GraphVizObject.Key k_target = new GraphVizObject.Key(scope, target);
        GraphVizEdge.Key key = new GraphVizEdge.Key(k_source, k_target, edgeId);
        return edges.containsKey(key);
    }

    public boolean containsEdge(@Nonnull GraphVizScope scope, @Nonnull Object source, @Nonnull Object target) {
        return containsEdge(scope, source, target, null);
    }

    @Nonnull
    private GraphVizCluster cluster(@CheckForNull GraphVizCluster parent, @Nonnull GraphVizScope scope, @Nonnull Object object) {
        GraphVizCluster.Key key = new GraphVizCluster.Key(scope, object);
        if (!isScopeVisible(scope))
            return new GraphVizCluster(this, key, null, -1);
        GraphVizCluster cluster = clusters.get(key);
        if (cluster == null) {
            cluster = new GraphVizCluster(this, key, parent, counter++);
            clusters.put(key, cluster);
        }
        return cluster;
    }

    @Nonnull
    public GraphVizCluster cluster(@Nonnull GraphVizScope scope, @Nonnull Object object) {
        return cluster(null, scope, object);
    }

    @Nonnull
    public GraphVizCluster subcluster(@Nonnull GraphVizCluster parent, @Nonnull Object object) {
        return cluster(parent, parent.getScope(), object);
    }

    private static boolean append(@Nonnull Appendable writer, @Nonnull String key, @CheckForNull Object value, boolean quote, boolean first) throws IOException {
        if (value == null)
            return first;
        if (!first)
            writer.append(',');
        writer.append(key).append('=');
        if (quote)
            writer.append('"');
        writer.append(String.valueOf(value));
        if (quote)
            writer.append('"');
        return false;
    }

    private static void writeIndent(@Nonnull Writer writer, @Nonnegative int depth) throws IOException {
        for (int i = 0; i < depth; i++)
            writer.write('\t');
    }

    private final CharMatcher NEWLINE = CharMatcher.is('\n');

    private void writeCommentsTo(@Nonnull Writer writer, @Nonnull Iterable<? extends String> comments, int indent) throws IOException {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < indent; i++)
            buf.append("\t");
        buf.append("// ");
        String prefix = buf.toString();

        // This is what is called "premature optimization".
        for (String comment : comments) {
            writer.write(prefix);
            if (NEWLINE.matchesAnyOf(comment))
                comment = NEWLINE.replaceFrom(comment, "\n" + prefix);
            writer.write(comment);
            writer.write('\n');
        }
    }

    private void writeClustersTo(
            @Nonnull Writer writer,
            @Nonnull Map<? extends GraphVizCluster, ? extends Iterable<? extends GraphVizCluster>> clusterMap,
            @CheckForNull GraphVizCluster parent,
            @Nonnegative int depth) throws IOException {
        Iterable<? extends GraphVizCluster> _clusters = clusterMap.remove(parent);
        if (_clusters == null)
            return;
        for (GraphVizCluster cluster : _clusters) {
            writeCommentsTo(writer, cluster.getComments(), depth + 1);
            writeIndent(writer, depth);
            writer.append("\tsubgraph ").append(cluster.getId()).append(" {\n");
            writeIndent(writer, depth);
            writer.append("\t\t// scope=").append(String.valueOf(System.identityHashCode(cluster.getKey().getScope()))).append("\n");
            writeIndent(writer, depth);
            writer.append("\t\t").append(cluster.getId()).append(" [label=\"\",shape=point,style=invis];\n");
            if (cluster.getLabel() != null) {
                writeIndent(writer, depth);
                writer.append("\t\t").append("label=\"").append(String.valueOf(cluster.getLabel())).append("\";\n");
            }
            for (Map.Entry<? extends String, ? extends String> e : cluster.getAttributes().entrySet())
                writer.append("\t\t").append(e.getKey()).append("=\"").append(String.valueOf(e.getValue())).append("\";\n");
            for (GraphVizNode.Key key : cluster.getNodeKeys()) {
                GraphVizNode node = node(key.getScope(), key.getObject());
                writeIndent(writer, depth);
                writer.append("\t\t").append(node.getId()).append(";\n");
            }
            writeClustersTo(writer, clusterMap, cluster, depth + 1);
            writeIndent(writer, depth);
            writer.append("\t}\n");
        }
    }

    /** This map allows null keys, and the null key should be present. */
    @Nonnull
    private Map<GraphVizCluster, List<GraphVizCluster>> newClusterMap() {
        Map<GraphVizCluster, List<GraphVizCluster>> clusterMap = new HashMap<GraphVizCluster, List<GraphVizCluster>>();
        for (GraphVizCluster child : clusters.values()) {
            @CheckForNull
            GraphVizCluster parent = child.getParent();
            List<GraphVizCluster> children = clusterMap.get(parent);
            if (children == null) {
                children = new ArrayList<GraphVizCluster>();
                clusterMap.put(parent, children);
            }
            children.add(child);
        }
        return clusterMap;
    }

    public void writeTo(@Nonnull Writer out) throws IOException {
        Writer writer;
        if (out instanceof BufferedWriter)
            writer = out;
        else if (out instanceof StringWriter)
            writer = out;
        else
            writer = new BufferedWriter(out);
        writeCommentsTo(writer, comments, 0);
        writer.write("digraph G {\n");
        // writer.write("\tcompound=true;\n");
        // writer.write("\tranksep=1.5;\n");
        writer.write("\tnode [shape=box];\n");
        for (Map.Entry<String, String> e : graphOptions.entrySet()) {
            writer.write("\t");
            writer.write(e.getKey());
            writer.write("=");
            writer.write(e.getValue());
            writer.write(";\n");
        }
        if (getLabel() != null) {
            writer.append("\t");
            append(writer, "label", getLabel(), true, true);
            writer.append(";\n");
        }

        for (GraphVizNode node : nodes.values()) {
            writeCommentsTo(writer, node.getComments(), 1);
            writer.append("\t").append(node.getId()).append(" [");
            boolean first = true;
            // first = append(writer, "color", node.getColor(), true, first);
            // first = append(writer, "style", node.getStyle(), true, first);
            // first = append(writer, "shape", node.getShape(), true, first);
            String text = node.toLabelString();
            if (text != null) {
                GraphVizLabel label = node.getLabel();
                // I don't like this much, it's too heuristic.
                // However, I think escaping of < is required by default, so this should actually be safe.
                boolean html = text.startsWith("<");
                if (html)
                    first = append(writer, "label", "<" + text + ">", false, first);
                else
                    first = append(writer, "label", text, true, first);
            }
            for (Map.Entry<? extends String, ? extends String> e : node.getAttributes().entrySet())
                first = append(writer, e.getKey(), e.getValue(), true, first);
            writer.append("];\n");
        }

        for (Map.Entry<GraphVizEdge.Key, GraphVizEdge> ee : edges.entrySet()) {
            // GraphVizEdge.Key key = ee.getKey();
            GraphVizEdge edge = ee.getValue();

            writeCommentsTo(writer, edge.getComments(), 1);
            writer.append("\t").append(edge.getSourceId());
            writer.append(" -> ").append(edge.getTargetId());
            writer.append(" [");
            boolean first = true;
            // first = append(writer, "color", edge.getColor(), true, first);
            // first = append(writer, "style", edge.getStyle(), true, first);
            first = append(writer, "arrowhead", edge.getHeadShape(), true, first);
            first = append(writer, "arrowtail", edge.getTailShape(), true, first);
            String labelKey = getGraphOption(GraphVizGraphOption.forcelabels) == null ? "label" : "xlabel";
            first = append(writer, labelKey, edge.getLabel(), true, first);
            first = append(writer, "headlabel", edge.getHeadLabel(), true, first);
            first = append(writer, "taillabel", edge.getTailLabel(), true, first);
            first = append(writer, "lhead", edge.getLogicalHead(), false, first);
            first = append(writer, "ltail", edge.getLogicalTail(), false, first);
            for (Map.Entry<? extends String, ? extends String> e : edge.getAttributes().entrySet())
                first = append(writer, e.getKey(), e.getValue(), true, first);
            writer.append("];\n");
        }

        /*
         for (GraphVizCluster cluster : clusters.values()) {
         writer.append("\tsubgraph ").append(cluster.getId()).append(" {\n");
         if (cluster.getLabel() != null)
         writer.append("\t\t").append("label=\"").append(String.valueOf(cluster.getLabel())).append("\";\n");
         for (GraphVizNode.Key key : cluster.getNodeKeys()) {
         GraphVizNode node = node(key.getScope(), key.getObject());
         writer.append("\t\t").append(node.getId()).append(";\n");
         }
         writer.append("\t}\n");
         }
         */
        Map<GraphVizCluster, List<GraphVizCluster>> clusterMap = newClusterMap();
        writeClustersTo(writer, clusterMap, null, 0);
        writer.write("}\n");
        writer.flush();
    }

    public void writeTo(@Nonnull OutputStream out) throws IOException {
        writeTo(new OutputStreamWriter(out, Charsets.UTF_8));
    }

    public void writeTo(@Nonnull File file) throws IOException {
        ByteSink sink = Files.asByteSink(file);
        OutputStream out = sink.openBufferedStream();
        try {
            writeTo(out);
        } finally {
            out.close();
        }
    }

    @Nonnull
    public String writeToString() throws IOException {
        StringWriter writer = new StringWriter();
        writeTo(writer);
        return writer.toString();
    }

    @Override
    public String toString() {
        return "GraphVizGraph(" + nodes.size() + " nodes, " + edges.size() + " edges)";
    }
}
