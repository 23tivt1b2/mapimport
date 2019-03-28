package data;

import java.io.Serializable;

public class Artist implements Serializable {

    private int popularity;
    private String name;

    public Artist(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

