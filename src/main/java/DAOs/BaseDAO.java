package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;

import java.util.List;

public class BaseDAO {
    JanusGraph graph;
    GraphTraversalSource g;

    public BaseDAO(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }


    public Vertex getVertexById(String property, long id){
        return this.g.V().has(property, id).next();
    }
    public List<Object> getIdList(String property){
        return getTraversalByProp(property).order().by("name").values(property).toList();
    }


    public GraphTraversal<Vertex, Vertex> getTraversalByProp(String property){
        return this.g.V().has(property);
    }
    public GraphTraversal<Vertex,Vertex> orderBy(GraphTraversal<Vertex,Vertex> traversal, String property){
        return traversal.order().by(property);
    }


    public Vertex getOutVertex(Vertex v, String edge) { return this.g.V(v).out(edge).next();}
    public Vertex getOutVertex(long id, String idProperty, String edge) { return this.g.V().has(idProperty,id).out(edge).next();}

    public List<Vertex> getListOutVertex(Vertex v, String edge){
        return this.g.V(v).out(edge).toList();
    }
    public List<Vertex> getListOutVertex(long id, String idProperty, String edge){
        return this.g.V().has(idProperty,id).out(edge).toList();
    }

    public List<Vertex> getListInVertex(Vertex v, String edge){
        return this.g.V(v).in(edge).toList();
    }
    public List<Vertex> getListInVertex(long id, String idProperty, String edge){
        return this.g.V().has(idProperty,id).in(edge).toList();
    }
    public List<Object> getListOutValues(Vertex v, String edge, String value) { return this.g.V(v).out(edge).values(value).toList(); }
    public List<Object> getListOutValues(long id, String idProperty, String edge, String value) { return this.g.V().has(idProperty,id).out(edge).values(value).toList(); }
    public List<Object> getListInValues(Vertex v, String edge, String value) { return this.g.V(v).out(edge).values(value).toList(); }
    public List<Object> getListInValues(long id, String idProperty, String edge, String value) { return this.g.V().has(idProperty,id).in(edge).values(value).toList(); }

    public long getNewId(String label){ return this.g.V().hasLabel(label).count().next() + 1; }


    public void commit(){ g.tx().commit(); }
    public void close(){
        graph.close();
    }
}
