package me.moosecanswim.onetomany.Controller;

import me.moosecanswim.onetomany.Model.Director;
import me.moosecanswim.onetomany.Model.Movie;
import me.moosecanswim.onetomany.Repository.DirectorRepository;
import me.moosecanswim.onetomany.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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


//        //create a movie
//        Movie movie=new Movie();
//        movie.setTitle("Star Movie");
//        movie.setYear(2017);
//        movie.setDescription("About Stars...");
//        movieRepository.save(movie);
//
//        //add the movie to an empty list
//        Set<Movie> movies=new HashSet<Movie>();
//
//        //create a director
//        Director director = new Director();
//        director.setName("Stephen Spielberg");
//        director.setGenre("Sci Fi");
//        director.addMovie(movie);
//        //save the director to the database
//        directorRepository.save(director);
//
//        movies.add(movie);
//
//        movie=new Movie();
//        movie.setTitle("DeathStar Ewoks");
//        movie.setYear(2011);
//        movie.setDescription("About Ewoks on the DeathStar...");
//        movies.add(movie);
//        movieRepository.save(movie);
//
//        //add the list of movies to the director's movie list
//        director.setMovies(movies);
//
//
//
//
//        //Grab all the directors from the database and send them to the template
//        model.addAttribute("directors",directorRepository.findAll());
        return "index";
    }

    @RequestMapping(value="/addMovie", method= RequestMethod.GET)
    public String addMovies(Model toSend){
        toSend.addAttribute("aMovie",new Movie());
        toSend.addAttribute("listDirectors",directorRepository.findAll());
        return "FormMovie";

    }
    @RequestMapping(value="/addMovie", method= RequestMethod.POST)
    public String processMovies(@Valid Movie aMovie, BindingResult result){
        if(result.hasErrors()){
            return "FormMovie";
        }
        movieRepository.save(aMovie);
        return "redirect:/showMovies";
    }
    @RequestMapping(value="/addDirector", method= RequestMethod.GET)
    public String addDirector(Model toSend){
        toSend.addAttribute("aDirector",new Director());
        toSend.addAttribute("listMovies",movieRepository.findAll());
        return "FormDirector";
    }
    @RequestMapping(value="/addDirector", method= RequestMethod.POST)
    public String processDirector(@Valid Director aDirector, BindingResult result){
        if(result.hasErrors()){
            return "FormDirector";
        }
        directorRepository.save(aDirector);
        return "redirect:/showDirectors";
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
    @RequestMapping("/assignMoviesToDirector/{id}")
    public String assignMoviesToDirector(Model model,@PathVariable("id") long id){
        model.addAttribute("aDirector",directorRepository.findOne(id));
        model.addAttribute("listMovies",movieRepository.findAll());
        return "assignMoviesToDirector";
    }
    @RequestMapping("/detailsDirector/{id}")
    public String directorDetails(Model model,@PathVariable("id") long id){
        Director aDirector=directorRepository.findOne(id);
        model.addAttribute("aDirector",aDirector);
        model.addAttribute("listMovies",movieRepository.findByDirector(aDirector));
        return "detailsDirector";
    }
    @RequestMapping("/detailsMovie/{id}")
    public String movieDetails(Model model,@PathVariable("id") long id){
        model.addAttribute("aMovie", movieRepository.findOne(id));
        return "detailsMovie";
    }
    @RequestMapping("/processMovieAsigment/{directorId}/{movieId}")
    public String processMovieAsigment(@PathVariable("movieId") long movieId,
                                       @PathVariable("directorId") long directorId,
                                       Model model){
        Director aDirector = directorRepository.findOne(directorId);
        Movie aMovie= movieRepository.findOne(movieId);

       // System.out.println("dir id: " + directorId);
       // System.out.println("movie id: " + movieId);

        aMovie.setDirector(aDirector);
        movieRepository.save(aMovie);
        System.out.println("a movies director " + aMovie.getDirector().getName());
        aDirector.addMovie(aMovie);


        System.out.println("finished processing a movie");
        return "redirect:/assignMoviesToDirector/"+directorId;
    }


    @RequestMapping("/delete/{type}/{id}")
    public String delete(@PathVariable("type")String type,@PathVariable("id") long id){
        String whereTo = null;
        switch(type){
            case "director":
                System.out.println("Deleting director: " + directorRepository.findOne(id).toString());
                directorRepository.delete(id);
                whereTo="showDirectors";
                break;
            case "movie":
                System.out.println("Deleting movie: " + movieRepository.findOne(id).toString());
                movieRepository.delete(id);
                whereTo="showMovies";
                break;
        }
    return "redirect:/"+whereTo;
    }
    @RequestMapping("/update/{type}/{id}")
    public String update(@PathVariable("type")String type,@PathVariable("id") long id,Model model){
        String whereTo = null;
        switch(type){
            case "director":
                model.addAttribute("aDirector", directorRepository.findOne(id));
                whereTo="FormDirector";
                break;
            case "movie":
                model.addAttribute("listDirectors",directorRepository.findAll());
                model.addAttribute("aMovie", movieRepository.findOne(id));
                whereTo="FormMovie";
                break;
        }
        return whereTo;
    }
}
