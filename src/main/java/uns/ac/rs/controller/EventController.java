package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.ArticleCategory;
import uns.ac.rs.model.Event;
import uns.ac.rs.repository.EventRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nikola Dakic on 8/8/17.
 */

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/all")
    public List<Event> getAll() throws Exception {
        return eventRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Event> add(@RequestBody Event event) throws Exception{

        List<ArticleCategory> categories = event.getCategories();

        for(ArticleCategory category: categories){
            System.out.println(category.toString());
        }

        event.setStarts(new Date());
        event.setEnds(new Date());

        Event e = eventRepository.save(event);

        return Optional.ofNullable(e)
                .map(result -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}