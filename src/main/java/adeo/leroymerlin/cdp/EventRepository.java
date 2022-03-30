package adeo.leroymerlin.cdp;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface EventRepository extends Repository<Event, Long> {

    void delete(Long eventId);

    Event save(Event event);

    Optional<Event> findOne(Long eventId);

    List<Event> findAllBy();
}
