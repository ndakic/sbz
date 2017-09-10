package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.Event;
import uns.ac.rs.repository.EventRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 9/6/17.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public List<Event> getAllActiveEvents(){

        Date date = new Date();

        List<Event> events = eventRepository.findAll();
        List<Event> objToRemove = new ArrayList<>();

        for(Event event: events) {
            if (event.getEnds().before(date)) {
                System.out.println("ends: " + event.getEnds());
                System.out.println(date);
                objToRemove.add(event);
            }
        }

        events.removeAll(objToRemove);

        return events;
    }

    public Event addEvent(Event event){
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id){
        eventRepository.delete(id);
    }

}
