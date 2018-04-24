/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Stores the data literally in the buffer; it's escaped later when we know whether
 * this will be a graphviz escString or an HTML label.
 *
 * @author shevek
 */
public class GraphVizLabel implements Appendable, Serializable {

    private static final Escaper gvEscaper = Escapers.builder()
            .addEscape('\\', "\\\\")
            .addEscape('\"', "\\\"")
            .addEscape('{', "\\{")
            .addEscape('}', "\\}")
            .addEscape('<', "&lt;")
            .addEscape('>', "&gt;")
            .addEscape('&', "&amp;")
            .addEscape('\n', "\\l")
            .addEscape('\r', "")
            .build();

    private final StringBuilder buf = new StringBuilder();

    @Nonnegative
    public int length() {
        return buf.length();
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    @Nonnull
    public StringBuilder getBuffer() {
        return buf;
    }

    @Nonnull
    public GraphVizLabel set(@Nonnull CharSequence value) {
        buf.setLength(0);
        return append(value);
    }

    // Optimized case for ToStringBuilder removed as did not filter special characters.
    @Nonnull
    public GraphVizLabel append(@CheckForNull Object object) {
        return append(String.valueOf(object));
    }

    @Nonnull
    @Override
    public GraphVizLabel append(@Nonnull CharSequence csq) {
        buf.append(csq);    // This has a fast-path in Java.
        return this;
    }

    @Nonnull
    @Override
    public GraphVizLabel append(@Nonnull CharSequence csq, @Nonnegative int start, @Nonnegative int end) {
        if (end > csq.length())
            end = csq.length();
        buf.append(csq, start, end);
        return this;
    }

    @Nonnull
    @Override
    public GraphVizLabel append(char c) {
        buf.append(c);
        return this;
    }

    @Nonnull
    @Override
    public String toString() {
        return gvEscaper.escape(buf.toString());
    }
}
