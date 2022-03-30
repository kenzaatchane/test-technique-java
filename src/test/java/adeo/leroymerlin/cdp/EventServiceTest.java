package adeo.leroymerlin.cdp;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest(useDefaultFilters = false)
@SpringBootTest(classes = {EventService.class})
@EnableAutoConfiguration
public class EventServiceTest {
    @Autowired
    EventService eventService;


    @Test
    @Sql("/sql-data/data.sql")
    public void should_delete_an_event() {
        assertEquals(5, eventService.getEvents().size());
        eventService.delete(Long.valueOf(1000));
        assertEquals(4, eventService.getEvents().size());
    }

    @Test
    @Sql("/sql-data/data.sql")
    public void should_update_an_event_review() {
        Event event = new Event();
        event.setComment("test comment");
        event.setNbStars(3);
        eventService.updateEvent(Long.valueOf(1000), event);
        Optional<Event> optionalEvent = eventService.getEventById(Long.valueOf(1000));
        assertTrue(optionalEvent.isPresent());
        assertEquals(Integer.valueOf(3), optionalEvent.get().getNbStars());
        assertEquals("test comment", optionalEvent.get().getComment());
    }

    @Test
    public void should_return_a_filtred_list_of_events() {
        assertEquals(5, eventService.getEvents().size());
        List<Event> eventList = eventService.getFilteredEvents("Robbie");
        assertEquals(1, eventList.size());
    }
}