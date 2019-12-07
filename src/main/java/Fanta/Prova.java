package Fanta;

import DAOs.BaseDAO;
import DAOs.PlayerDAO;
import DAOs.TeamDAO;
import Mappers.PlayerMapper;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.apache.tinkerpop.gremlin.process.traversal.Contains.within;

public class Prova {

    public static void main(String[] args) {

        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        GraphTraversalSource g = graph.traversal();


//        PlayerDAO dao = new PlayerDAO();
        PlayerMapper mapper = new PlayerMapper();


        Path p = g.V().has("team id", 1).as("team").in("plays for").values("birthdate").as("birthdate").path().next();
        Vertex x = p.get("birthdate");



        graph.close();
//        dao.close();
    }
}