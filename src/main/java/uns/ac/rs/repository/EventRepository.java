package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.Event;

/**
 * Created by Nikola Dakic on 8/8/17.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}
