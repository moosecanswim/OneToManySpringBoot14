package me.moosecanswim.onetomany.Controller;

import me.moosecanswim.onetomany.Model.Director;
import me.moosecanswim.onetomany.Model.Movie;
import me.moosecanswim.onetomany.Repository.DirectorRepository;
import me.moosecanswim.onetomany.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    DirectorRepository directorRepository;

    @RequestMapping("/")
    public String index(Model model){
        //create a director
        Director director = new Director();
        director.setName("Stephen Bullock");
        director.setGenre("Sci Fi");

        //create a movie
        Movie movie=new Movie();
        movie.setTitle("Star Movie");
        movie.setYear(2017);
        movie.setDescription("About Stars...");

        //add the movie to an empty list
        Set<Movie> movies=new HashSet<Movie>();

        movies.add(movie);

        movie=new Movie();
        movie.setTitle("DeathStar Ewoks");
        movie.setYear(2011);
        movie.setDescription("About Ewoks on the DeathStar...");
        movies.add(movie);

        //add the list of movies to the director's movie list
        director.setMovies(movies);

        //save the director to the database
        directorRepository.save(director);

        //Grab all the directors from the database and send them to the template
        model.addAttribute("directors",directorRepository.findAll());
        return "index";
    }

    @RequestMapping(value="/addMovie", method= RequestMethod.GET)
    public String addMovies(Model toSend){
        toSend.addAttribute("aMovie",new Movie());
        return "FormMovie";

    }
    @RequestMapping(value="/addMovie", method= RequestMethod.POST)
    public String processMovies(@Valid Movie aMovie, BindingResult result){
        if(result.hasErrors()){
            return "FormMovie";
        }
        movieRepository.save(aMovie);
        return "index";
    }
    @RequestMapping(value="/addDirector", method= RequestMethod.GET)
    public String addDirector(Model toSend){
        toSend.addAttribute("aDirector",new Director());
        return "FormDirector";

    }
    @RequestMapping(value="/addDirector", method= RequestMethod.POST)
    public String processDirector(@Valid Director aDirector, BindingResult result){
        if(result.hasErrors()){
            return "FormDirector";
        }
        directorRepository.save(aDirector);
        return "index";
    }

    @RequestMapping("/showDirectors")
    public String showDirectors(Model model){
        model.addAttribute("listDirectors",directorRepository.findAll());
        return "showDirectors";
    }
    @RequestMapping("/showMovies")
    public String showMovies(Model model){
        model.addAttribute("listMovies",movieRepository.findAll());
        return "showMovies";
    }

}
