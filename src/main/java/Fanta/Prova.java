package Fanta;

import DAOs.BaseDAO;
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

        BaseDAO dao = new BaseDAO();

        GraphTraversal<Vertex, Vertex> x  = dao.getTraversalByProp("player id");

        GraphTraversal<Vertex, Vertex> y = dao.orderBy(x,"name");

        System.out.println(y.next().property("name").value());

        dao.close();
    }
}