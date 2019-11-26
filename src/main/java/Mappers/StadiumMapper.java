package Mappers;

import Models.Stadium;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class StadiumMapper extends Mapper {

    public long mapStadiumId(Vertex stadium){ return this.mapLong(stadium,"stadium id"); }

    public Stadium VertexToEntity(Vertex stadium, Vertex team){

        String name = this.mapName(team);
        long id = this.mapStadiumId(stadium);
        String city = this.mapString(stadium, "city");
        long capacity = this.mapLong(stadium, "capacity");
        String img = this.mapImg(stadium);
        String teamName = this.mapName(team);
        long teamId = this.mapLong(team, "team id");

        return new Stadium(id,name,city,capacity,img,teamName,teamId);
    }
}
