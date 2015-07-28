/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.base.Preconditions;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizEdge extends GraphVizElement<GraphVizEdge> {

    /* pp */ static final class Key {

        @Nonnull
        private final GraphVizObject.Key sourceKey;
        @Nonnull
        private final GraphVizObject.Key targetKey;

        public Key(@Nonnull GraphVizObject.Key sourceKey, @Nonnull GraphVizObject.Key targetKey) {
            this.sourceKey = Preconditions.checkNotNull(sourceKey, "Source key was null.");
            this.targetKey = Preconditions.checkNotNull(targetKey, "Target key was null.");
        }

        @Nonnull
        public GraphVizObject.Key getSourceKey() {
            return sourceKey;
        }

        @Nonnull
        public GraphVizObject.Key getTargetKey() {
            return targetKey;
        }

        @Override
        public int hashCode() {
            return getSourceKey().hashCode() ^ getTargetKey().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Key k = (Key) obj;
            return getSourceKey().equals(k.getSourceKey()) && getTargetKey().equals(k.getTargetKey());
        }
    }
    @Nonnull
    private final Key key;
    @Nonnull
    private final String sourceId;
    @Nonnull
    private final String targetId;
    @CheckForNull
    private String headShape;
    @CheckForNull
    private String headLabel;
    @CheckForNull
    private String tailShape;
    @CheckForNull
    private String tailLabel;
    @CheckForNull
    private String logicalHead;
    @CheckForNull
    private String logicalTail;

    /* pp */ GraphVizEdge(@Nonnull GraphVizGraph graph, @Nonnull Key key, @Nonnull String sourceId, @Nonnull String targetId) {
        super(graph);
        this.key = key;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    @Nonnull
    /* pp */ Key getKey() {
        return key;
    }

    @Nonnull
    /* pp */ String getSourceId() {
        return sourceId;
    }

    @Nonnull
    /* pp */ String getTargetId() {
        return targetId;
    }

    @CheckForNull
    public String getHeadShape() {
        return headShape;
    }

    @Nonnull
    public GraphVizEdge headShape(@CheckForNull String headShape) {
        this.headShape = headShape;
        return this;
    }

    @CheckForNull
    public String getHeadLabel() {
        return headLabel;
    }

    @Nonnull
    public GraphVizEdge headLabel(@CheckForNull String headLabel) {
        this.headLabel = headLabel;
        return this;
    }

    @CheckForNull
    public String getTailShape() {
        return tailShape;
    }

    @Nonnull
    public GraphVizEdge tailShape(@CheckForNull String tailShape) {
        this.tailShape = tailShape;
        return this;
    }

    @CheckForNull
    public String getTailLabel() {
        return tailLabel;
    }

    @Nonnull
    public GraphVizEdge tailLabel(@CheckForNull String tailLabel) {
        this.tailLabel = tailLabel;
        return this;
    }

    public String getLogicalHead() {
        return logicalHead;
    }

    @Nonnull
    public GraphVizEdge logicalHead(@CheckForNull String logicalHead) {
        this.logicalHead = logicalHead;
        return this;
    }

    public String getLogicalTail() {
        return logicalTail;
    }

    @Nonnull
    public GraphVizEdge logicalTail(@CheckForNull String logicalTail) {
        this.logicalTail = logicalTail;
        return this;
    }
}
