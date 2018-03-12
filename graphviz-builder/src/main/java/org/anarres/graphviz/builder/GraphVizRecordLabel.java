/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private final Map<String, String> fields = new LinkedHashMap<String, String>();

    @Nonnull
    public GraphVizRecordLabel title(@CheckForNull String title) {
        this.title = title;
        return this;
    }

    @Nonnull
    public GraphVizRecordLabel field(@Nonnull String port, @Nonnull String text) {
        fields.put(port, text);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<TABLE ALIGN=\"LEFT\" BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">");
        if (title != null)
            buf.append("<TR><TD BALIGN=\"LEFT\">").append(E(title)).append("</TD></TR>");
        for (Map.Entry<String, String> e : fields.entrySet()) {
            String port = e.getKey();
            String text = e.getValue();
            buf.append("<TR><TD BALIGN=\"LEFT\" PORT=\"").append(port).append("\">")
                    .append(E(text)).append("</TD></TR>");
        }
        buf.append("</TABLE>");
        return buf.toString();
    }
}
