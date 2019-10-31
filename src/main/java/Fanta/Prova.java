package Fanta;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;

public class Prova {

    public static void createUserSchema(final JanusGraphManagement management){
        management.makeVertexLabel("user").make();
        management.makePropertyKey("id user").dataType(Long.class).make();
        management.makePropertyKey("username").dataType(String.class).make();
        management.makeEdgeLabel("ha la squadra").make();

    }

    public static void main(String[] args) {

        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        GraphTraversalSource g = graph.traversal();
        final JanusGraphManagement management = graph.openManagement();


        createUserSchema(management);

        graph.close();
    }
}

