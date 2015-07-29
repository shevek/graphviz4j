/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import java.io.Serializable;

/**
 * A tagging interface for things to be used as graphviz scopes.
 *
 * You can implement this anywhere; it's just to make reflection easier in the IDE.
 *
 * @author shevek
 */
public interface GraphVizScope {

    public static class Impl implements GraphVizScope, Serializable {

        private static final long serialVersionUID = 1L;
    }
}
