/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizPort extends GraphVizObject<GraphVizPort> {

    /* pp */ static class Key extends GraphVizObject.Key {

        @Nonnull
        private final GraphVizNode node;
        @Nonnull
        private final Object object;

        public Key(@Nonnull GraphVizNode node, @Nonnull Object object) {
            super(node.getScope(), object);
            this.node = Preconditions.checkNotNull(node, "Node was null.");
            this.object = Preconditions.checkNotNull(object, "Object was null.");
        }

        @Nonnull
        public GraphVizNode getNode() {
            return node;
        }

        @Override
        public int hashCode() {
            return getNode().hashCode() ^ getObject().hashCode();
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
            return getNode() == k.getNode()
                    && getObject().equals(k.getObject());
        }

        @Override
        protected void toStringHelper(MoreObjects.ToStringHelper helper) {
            GraphVizScope scope = getScope();
            helper.add("scope", scope.getClass().getSimpleName() + "@" + System.identityHashCode(scope));
            helper.add("node", node.getClass().getSimpleName() + "@" + System.identityHashCode(node));
            helper.add("object", object.getClass().getSimpleName() + "@" + System.identityHashCode(object));
        }

        @Override
        public String toString() {
            MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);
            toStringHelper(helper);
            return helper.toString();
        }
    }

    private final String portId;

    /* pp */ GraphVizPort(@Nonnull GraphVizNode node, @Nonnull Key key, int portId) {
        super(node.getGraph(), key, node.getId() + ":p" + portId);
        this.portId = "p" + portId;
    }

    @Nonnull
    public String getPortId() {
        return portId;
    }
}
