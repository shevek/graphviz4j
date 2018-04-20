/*
 * To change this template, choose Tools | Templates
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
public abstract class GraphVizObject<T extends GraphVizObject<?>> extends GraphVizElement<T> {

    /* pp */ static class Key {

        @Nonnull
        private final GraphVizScope scope;
        @Nonnull
        private final Object object;

        public Key(@Nonnull GraphVizScope scope, @Nonnull Object object) {
            this.scope = Preconditions.checkNotNull(scope, "Scope was null.");
            this.object = Preconditions.checkNotNull(object, "Object was null.");
        }

        @Nonnull
        public GraphVizScope getScope() {
            return scope;
        }

        @Nonnull
        public Object getObject() {
            return object;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(getScope()) ^ getObject().hashCode();
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
            return getScope() == k.getScope()
                    && getObject().equals(k.getObject());
        }

        protected void toStringHelper(@Nonnull MoreObjects.ToStringHelper helper) {
            helper
                    .add("scope", scope.getClass().getSimpleName() + "@" + System.identityHashCode(scope))
                    .add("object", object.getClass().getSimpleName() + "@" + System.identityHashCode(object));

        }

        @Override
        public String toString() {
            MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);
            toStringHelper(helper);
            return helper.toString();
        }

    }
    @Nonnull
    private final Key key;
    @Nonnull
    private final String id;

    /* pp */ GraphVizObject(@Nonnull GraphVizGraph graph, @Nonnull Key key, @Nonnull String id) {
        super(graph);
        this.key = key;
        this.id = id;
    }

    @Nonnull
    /* pp */ Key getKey() {
        return key;
    }

    @Nonnull
    public GraphVizScope getScope() {
        return getKey().getScope();
    }

    @Nonnull
    /* pp */ String getId() {
        return id;
    }

    protected void toStringHelper(@Nonnull MoreObjects.ToStringHelper helper) {
        helper
                .add("id", getId());
        getKey().toStringHelper(helper);
    }

    @Override
    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);
        toStringHelper(helper);
        return helper.toString();
    }
}
