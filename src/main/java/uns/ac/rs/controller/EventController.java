package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.Event;
import uns.ac.rs.service.EventService;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nikola Dakic on 8/8/17.
 */

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public List<Event> getAll() throws Exception {
        return eventService.getAllActiveEvents();
    }

    @PostMapping("/add")
    public ResponseEntity<Event> add(@RequestBody Event event) throws Exception{

        Event e = eventService.addEvent(event);

        return Optional.ofNullable(e)
                .map(result -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception{

        eventService.deleteEvent(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
