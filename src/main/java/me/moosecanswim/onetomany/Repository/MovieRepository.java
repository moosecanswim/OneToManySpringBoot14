package me.moosecanswim.onetomany.Repository;

import me.moosecanswim.onetomany.Model.Director;
import me.moosecanswim.onetomany.Model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    Set<Movie> findByDirector(Director director);
}
