package Fanta;

import java.time.LocalDate;

public class Coach {
    private final long id;
    private final String name;
    private final LocalDate birthdate;
    private final String birthplace;
    private final String nationality;
    private final String team;

    public Coach(long id, String name, LocalDate birthdate, String birthplace, String nationality, String team){
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
        this.nationality = nationality;
        this.team = team;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getNationality() {
        return nationality;
    }

    public String getTeam() {
        return team;
    }
}
