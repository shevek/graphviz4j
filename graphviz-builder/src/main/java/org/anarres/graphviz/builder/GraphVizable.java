/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public interface GraphVizable {

    /** Renders this object into the given GraphVizGraph according to object-defined rules. */
    public void toGraphViz(@Nonnull GraphVizGraph graph);
}
