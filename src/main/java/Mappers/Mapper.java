package Mappers;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.time.LocalDate;

public class Mapper {
    LocalDate today = LocalDate.now();

    public String mapString(Vertex v, String prop){
        return (String)v.property(prop).value();
    }

    public long mapLong(Vertex v, String prop){
        return Long.parseLong(String.valueOf(v.property(prop).value()));
    }

    public double mapDouble(Vertex v, String prop){
        return Double.parseDouble(String.valueOf(v.property(prop).value()));
    }

    public LocalDate mapLocalDate(Vertex v, String prop){
        return LocalDate.parse((String)v.property(prop).value());
    }

    public String mapName(Vertex v){
        return this.mapString(v,"name");
    }

    public String mapBirthplace(Vertex v){
        return this.mapString(v, "birthplace");
    }

    public LocalDate mapBirthdate(Vertex v){
        return this.mapLocalDate(v,"birthdate");
    }

    public String mapNationality(Vertex v){
        return this.mapString(v, "nationality");
    }

    public String mapImg(Vertex v){
        return this.mapString(v,"img");
    }
}
