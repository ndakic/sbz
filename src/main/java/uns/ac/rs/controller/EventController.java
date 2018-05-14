package uns.ac.rs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.Event;
import uns.ac.rs.security.TokenUtils;
import uns.ac.rs.service.EventService;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LogManager.getLogger(EventController.class);

    @GetMapping("/all")
    public List<Event> getAll() throws Exception {
        return eventService.getAllActiveEvents();
    }

    @PostMapping("/add")
    public ResponseEntity<Event> add(@RequestBody Event event) throws Exception{

        Event e = eventService.addEvent(event);

        String authToken = request.getHeader("authorization");
        String manager = tokenUtils.getUsernameFromToken(authToken);

        logger.info("Manager: " + manager + " has created Event: " + e.toString() );


        return Optional.ofNullable(e).map(result -> new ResponseEntity<>(e, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception{

        eventService.deleteEvent(id);

        String authToken = request.getHeader("authorization");
        String manager = tokenUtils.getUsernameFromToken(authToken);

        logger.info("Manager: " + manager + " has created deleted Event with id: " + id );

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
