graphviz4j
==========

A Java GraphViz graph builder and parser.

Building GraphViz Graphs
------------------------

This is designed to be a fluent API for constructing GraphViz
graphs.

Multiple subgraphs can be generated into the same GraphVizGraph
over the same nodes, using the concept of "scope", an otherwise
empty interface.

```
GraphVizGraph graph = new GraphVizGraph();
GraphVizScope scope = ...; // any object.
graph.node(scope, a).label("Node A");
graph.node(scope, b).label("Node B");
graph.edge(scope, a, b).label("My edge");
graph.writeTo(new File("out.dot"));
```

For convenience, the interface GraphVizable is provided for domain
objects which wish to advertise that they may be rendered to GraphViz.
The domain object frequently also implements GraphVizScope, or has a
private inner field of type GraphVizScope.

Parsing GraphViz Graphs
-----------------------

While all the grammar is present, a good fluent API has not yet been
written over it.

The parser does not yet handle HTML labels delimited by
angle-brackets. It should handle all other constructs, and test cases
are welcome.

Documentation
-------------

The [JavaDoc API](http://shevek.github.io/graphviz4j/docs/javadoc/)
is available.
