package webCalendarSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public default List<Event> findAllToday() {
        List<Event> events = findAll();
        List<Event> eventsToday = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().equals(LocalDate.now())) {
                eventsToday.add(event);
            }
        }
        return eventsToday;
    }

    public default List<Event> findAllFromPeriod(String startDate, String endDate) {
        List<Event> eventsFromPeriod = new ArrayList<>();
        LocalDate startDateLocal = LocalDate.parse(startDate);
        LocalDate endDateLocal = LocalDate.parse(endDate);
        for (Event event : this.findAll()) {
            if (!(event.getDate().isBefore(startDateLocal)) && !(event.getDate().isAfter(endDateLocal))) {
                eventsFromPeriod.add(event);
            }
        }
        return eventsFromPeriod;
    }


}
