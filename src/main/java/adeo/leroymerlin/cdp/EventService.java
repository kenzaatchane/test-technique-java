package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findOne(id);
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

    /**
     * Displayed only the events with at least one band has a member with the name matching the given query.
     *
     * @param query of the given Event.
     * @return List<Event>
     */
    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        if (CollectionUtils.isEmpty(events))
            return events;

        // Filter the events list in pure JAVA here
        return events.stream().filter(event ->
                event.getBands().stream().anyMatch(band ->
                        band.getMembers().stream().anyMatch(member ->
                                member.getName().toLowerCase().contains(query.toLowerCase()))))
                .collect(Collectors.toList());
    }
}
