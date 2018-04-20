/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizRecordLabel {

    private static final Escaper ESCAPE_MINIMAL = Escapers.builder()
            .addEscape('\n', "<BR/>")
            .addEscape('\r', "")
            .build();

    private static String E(@Nonnull String in) {
        return ESCAPE_MINIMAL.escape(in);
    }

    @CheckForNull
    private String title;
    private final List<GraphVizPort> ports = new ArrayList<GraphVizPort>();

    @Nonnull
    public GraphVizRecordLabel title(@CheckForNull String title) {
        this.title = title;
        return this;
    }

    @Nonnull
    public GraphVizRecordLabel field(@Nonnull GraphVizPort port) {
        ports.add(port);
        return this;
    }

    private void toStringAttribute(@Nonnull StringBuilder out, @Nonnull GraphVizPort port, @Nonnull GraphVizAttribute attribute, @Nonnull String key) {
        String value = port.getAttribute(attribute);
        if (value == null)
            return ;
        out.append(" ").append(key).append("=\"").append(value).append("\"");
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<TABLE ALIGN=\"LEFT\" BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">");
        if (title != null)
            buf.append("<TR><TD BALIGN=\"LEFT\" PORT=\"_title\">").append(E(title)).append("</TD></TR>");
        for (GraphVizPort port : ports) {
            String label = port.label().getBuffer().toString();
            buf.append("<TR><TD BALIGN=\"LEFT\" PORT=\"").append(port.getPortId()).append("\"");
            // port.getAttribute(GraphVizAttribute.color)
            toStringAttribute(buf, port, GraphVizAttribute.color, "BGCOLOR");
            toStringAttribute(buf, port, GraphVizAttribute.penwidth, "BORDER");
            toStringAttribute(buf, port, GraphVizAttribute.fontcolor, "COLOR");
            toStringAttribute(buf, port, GraphVizAttribute.href, "HREF");
            toStringAttribute(buf, port, GraphVizAttribute.tooltip, "TITLE");
            buf.append(">").append(E(label)).append("</TD></TR>");
        }
        buf.append("</TABLE>");
        return buf.toString();
    }
}
