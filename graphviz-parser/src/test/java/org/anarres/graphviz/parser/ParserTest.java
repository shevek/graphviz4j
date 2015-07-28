/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.graphviz.parser;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.anarres.graphviz.parser.lexer.Lexer;
import org.anarres.graphviz.parser.lexer.LexerException;
import org.anarres.graphviz.parser.lexer.LexerInterface;
import org.anarres.graphviz.parser.node.Start;
import org.anarres.graphviz.parser.node.Token;
import org.anarres.graphviz.parser.parser.Parser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author shevek
 */
public class ParserTest {

    private static final Logger LOG = LoggerFactory.getLogger(ParserTest.class);
    public static final String DIR = "build/resources/test/parser";

    public void parse(String script) throws Exception {
        StringReader sr = new StringReader(script);
        PushbackReader pr = new PushbackReader(sr, 10);
        final Lexer lx = new Lexer(pr);
        LexerInterface sniffer = new LexerInterface() {

            @Override
            public Token peek() throws LexerException, IOException {
                Token t = lx.peek();
                LOG.info("peek: " + t);
                return t;
            }

            @Override
            public Token next() throws LexerException, IOException {
                Token t = lx.next();
                LOG.info("next: " + t);
                return t;
            }
        };
        Parser ps = new Parser(lx);
        Start ast = ps.parse();
    }

    @Test
    public void testParser() throws Exception {
        File root = new File(DIR);
        LOG.info("Root dir is " + root);

        assertTrue(root.isDirectory());

        for (File file : Files.fileTreeTraverser().preOrderTraversal(root)) {
            if (file.getName().startsWith("."))
                continue;
            if (!file.isFile())
                continue;
            if (!file.getName().endsWith(".gv.txt"))
                continue;
            LOG.info("File is " + file);

            String script = Files.asCharSource(file, StandardCharsets.UTF_8).read();
            parse(script);
        }
    }
}
