package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    /**
     * Update the review of the given Event.
     *
     * @param id of the given Event.
     * @param event updated.
     */
    public void updateEvent(Long id, Event event) {
        Optional<Event> maybeUpdatedEvent = eventRepository.findOne(id);
        if (maybeUpdatedEvent.isPresent()) {
             Event updateEvent = maybeUpdatedEvent.get();
            updateEvent.setNbStars(event.getNbStars());
            updateEvent.setComment(event.getComment());
            eventRepository.save(updateEvent);
        }
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here

        return events;
    }
}
