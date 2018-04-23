/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public enum GraphVizHtmlAttribute {

    ALIGN,
    BALIGN,
    BGCOLOR,
    BORDER,
    CELLBORDER,
    CELLPADDING,
    CELLSPACING,
    COLOR,
    COLSPAN,
    COLUMNS,
    FACE,
    FIXEDSIZE,
    GRADIENTANGLE,
    HEIGHT,
    HREF,
    ID,
    POINT_SIZE("POINT-SIZE"),
    PORT,
    ROWS,
    ROWSPAN,
    SCALE,
    SIDES,
    SRC,
    STYLE,
    TARGET,
    TITLE,
    TOOLTIP,
    VALIGN,
    WIDTH;

    private final String name;

    private GraphVizHtmlAttribute(String name) {
        this.name = name;
    }

    private GraphVizHtmlAttribute() {
        this.name = name();
    }

    /** Returns the graphviz name of this attribute, which differs from name() in the case that the attribute name was not a legal Java name. */
    @Nonnull
    public String getName() {
        return name;
    }
}
