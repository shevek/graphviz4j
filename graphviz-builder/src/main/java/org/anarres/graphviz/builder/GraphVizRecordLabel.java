/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.base.Preconditions;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Honours all HTML attributes set on the node or ports.
 *
 * Attempts some portability between graphviz attributes and HTML attributes.
 *
 * @author shevek
 */
public class GraphVizRecordLabel {

    private static final Escaper ESCAPE_MINIMAL = Escapers.builder()
            .addEscape('\n', "<BR/>")
            .addEscape('\r', "")
            .build();

    private static String E(@Nonnull GraphVizLabel in) {
        return ESCAPE_MINIMAL.escape(in.getBuffer().toString());
    }

    private static final GraphVizHtmlAttribute[] ATTRIBUTE_NAMES = GraphVizHtmlAttribute.values();
    private static final Map<GraphVizHtmlAttribute, GraphVizAttribute> ATTRIBUTE_MAP = new EnumMap<GraphVizHtmlAttribute, GraphVizAttribute>(GraphVizHtmlAttribute.class);

    static {
        for (GraphVizHtmlAttribute a : ATTRIBUTE_NAMES)
            ATTRIBUTE_MAP.put(a, null);
        ATTRIBUTE_MAP.put(GraphVizHtmlAttribute.BGCOLOR, GraphVizAttribute.color);
        ATTRIBUTE_MAP.put(GraphVizHtmlAttribute.BORDER, GraphVizAttribute.penwidth);
        ATTRIBUTE_MAP.put(GraphVizHtmlAttribute.COLOR, GraphVizAttribute.fontcolor);
        ATTRIBUTE_MAP.put(GraphVizHtmlAttribute.HREF, GraphVizAttribute.href);
        ATTRIBUTE_MAP.put(GraphVizHtmlAttribute.TITLE, GraphVizAttribute.tooltip);

    }

    @CheckForNull
    private GraphVizNode node;
    private final List<GraphVizPort> ports;

    @Nonnull
    public GraphVizRecordLabel(@Nonnull GraphVizNode node, @Nonnull Collection<? extends GraphVizPort> ports) {
        this.node = Preconditions.checkNotNull(node, "Node was null.");
        this.ports = new ArrayList<GraphVizPort>(ports);
    }

    private static boolean toStringAttribute(@Nonnull StringBuilder out, @Nonnull GraphVizElement<?> element, @Nonnull GraphVizHtmlAttribute attribute) {
        String value = element.getAttribute(attribute);
        if (value == null) {
            GraphVizAttribute gvAttribute = ATTRIBUTE_MAP.get(attribute);
            if (gvAttribute == null)
                return false;
            value = element.getAttribute(gvAttribute);
            if (value == null)
                return false;
        }
        out.append(" ").append(attribute.getName()).append("=\"").append(value).append("\"");
        return true;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<TABLE");
        for (GraphVizHtmlAttribute attribute : ATTRIBUTE_NAMES) {
            switch (attribute) {
                case HREF:
                case TITLE:
                    continue;
                default:
                    toStringAttribute(buf, node, attribute);
            }
        }
        buf.append(">");
        // CELLSPACING=\"0\"
        NODE:
        {
            GraphVizLabel label = node.getLabel();
            if (label != null) {
                buf.append("<TR><TD BALIGN=\"LEFT\" PORT=\"_title\"");
                toStringAttribute(buf, node, GraphVizHtmlAttribute.HREF);
                toStringAttribute(buf, node, GraphVizHtmlAttribute.TITLE);
                buf.append(">").append(E(label)).append("</TD></TR>");
            }
        }
        PORTS:
        for (GraphVizPort port : ports) {
            GraphVizLabel label = port.label();
            buf.append("<TR><TD BALIGN=\"LEFT\" PORT=\"").append(port.getPortId()).append("\"");
            // port.getAttribute(GraphVizAttribute.color)
            for (GraphVizHtmlAttribute attribute : ATTRIBUTE_NAMES)
                toStringAttribute(buf, port, attribute);
            buf.append(">").append(E(label)).append("</TD></TR>");
        }
        buf.append("</TABLE>");
        return buf.toString();
    }
}
