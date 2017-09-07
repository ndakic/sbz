package uns.ac.rs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uns.ac.rs.model.ArticleCategory;
import uns.ac.rs.model.Event;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikola Dakic on 9/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TestEvents {

    @Autowired
    EventService eventService;

    @Autowired
    CategoryService categoryService;

    @Test
    @Transactional
    @Rollback(true)
    public void testAddEvent() throws Exception{

        Event event = new Event();
        event.setStarts(new Date("09/01/2017"));
        event.setEnds(new Date("09/022/2017"));
        event.setTitle("Test");
        event.setDiscount(10.0);

        List<ArticleCategory> articleCategory = categoryService.getArticleCategories();

        event.setCategories(articleCategory);

        Event event1 = eventService.addEvent(event);

        assertThat(event1).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testRemoveEvent() throws Exception{

        List<Event> events = eventService.getAllActiveEvents();

        int before_size = events.size();
        Event event = events.get(0);

        eventService.deleteEvent(event.getId());

        int after_size = eventService.getAllActiveEvents().size();

        assertThat(after_size).isEqualTo(before_size - 1);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    @Rollback(true)
    public void testRemoveNonExistEvent(){
        eventService.deleteEvent(111L);
    }

}
