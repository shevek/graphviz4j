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

    public void toGraphViz(@Nonnull GraphVizGraph graph);
}
