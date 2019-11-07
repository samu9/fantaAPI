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

    public static void main(String[] args) {

        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        GraphTraversalSource g = graph.traversal();
        final JanusGraphManagement management = graph.openManagement();


        g.addV("user").property("user id", 1).property("username","samu").property("password","xxx").property("email","samu@gmail.com").next();
        g.tx().commit();
//        g.tx().rollback();
//        g.V().has("user id","5").drop().iterate();
//        g.tx().commit();

        List<Vertex> l = g.V().has("user id").toList();
        for(Vertex v : l){
            System.out.println(v.property("user id").value() + " - " + v.property("username").value());

        }
        System.out.println(l.size());
        g.tx().rollback();

        graph.close();
    }
}