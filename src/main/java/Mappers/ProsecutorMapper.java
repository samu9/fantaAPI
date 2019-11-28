package Mappers;

import Models.Prosecutor;
import org.apache.tinkerpop.gremlin.structure.Vertex;


public class ProsecutorMapper extends Mapper {

    public long mapProsecutorId(Vertex prosecutor){ return this.mapLong(prosecutor,"prosecutor id"); }

    public Prosecutor VertexToEntity(Vertex prosecutor){
        String name = this.mapName(prosecutor);
        long id = this.mapProsecutorId(prosecutor);

        return new Prosecutor(id,name);
    }
}
