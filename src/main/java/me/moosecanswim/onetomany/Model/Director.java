package me.moosecanswim.onetomany.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Director {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private String genre;

    @OneToMany(mappedBy="director", cascade = CascadeType.ALL, fetch=FetchType.EAGER) //looking for a field called director
    private Set<Movie> movies;

    public Director(){
        setMovies(new HashSet<Movie>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie m){
//        m.setDirector(this);
        this.movies.add(m);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
