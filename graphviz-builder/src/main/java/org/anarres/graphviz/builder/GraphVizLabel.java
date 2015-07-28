/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.builder;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class GraphVizLabel implements Appendable, Serializable {

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
        return append(csq, 0, csq.length());
    }

    @Nonnull
    @Override
    public GraphVizLabel append(@Nonnull CharSequence csq, @Nonnegative int start, @Nonnegative int end) {
        if (end > csq.length())
            end = csq.length();
        for (int i = start; i < end; i++) {
            append(csq.charAt(i));
        }
        return this;
    }

    @Nonnull
    @Override
    public GraphVizLabel append(char c) {
        switch (c) {
            case '\\':
            case '"':
            case '{':
            case '}':
                buf.append("\\");
                buf.append(c);
                break;
            case '<':
                buf.append("&lt;");
                break;
            case '>':
                buf.append("&gt;");
                break;
            case '&':
                buf.append("&amp;");
                break;
            case '\n':
                buf.append("\\l");
                break;
            case '\r':
                break;
            default:
                buf.append(c);
                break;
        }
        return this;
    }

    @Nonnull
    @Override
    public String toString() {
        return buf.toString();
    }
}
