package me.moosecanswim.onetomany.Repository;

import me.moosecanswim.onetomany.Model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Long> {

}
