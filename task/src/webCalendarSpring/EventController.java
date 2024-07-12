package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Validated
@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping(value="/event/today", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<Event>>> getEventsToday() {

        return new ResponseEntity<>(Optional.of(eventRepository.findAllToday()), HttpStatus.OK);

    }


    @GetMapping("/event/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable Integer id) {
        if (eventRepository.findById(id.longValue()).isPresent()) {
            Event event = eventRepository.findById(id.longValue()).get();
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/event")
    public ResponseEntity<List<Event>> getEventsFromPeriod(@RequestParam(value="start_time", required = false) String startTime, @RequestParam(value="end_time", required = false) String endTime) {
        if ((startTime == null || endTime == null) && !(eventRepository.findAll().isEmpty())) {
            return new ResponseEntity<>(eventRepository.findAll(), HttpStatus.OK);
        } else if (eventRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(eventRepository.findAllFromPeriod(startTime, endTime), HttpStatus.OK);
        }

    }


    @PostMapping("/event")
    public @ResponseBody ResponseEntity<Object> postEvent(@Valid @RequestBody Event event) {
        eventRepository.save(event);
        Map<String, Object> map = new HashMap<>();
        map.put("date", event.getDate());
        map.put("event", event.getEvent());
        map.put("message", "The event has been added!");
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable Integer id) {
        if (eventRepository.findById(id.longValue()).isPresent()) {
            Event event = eventRepository.findById(id.longValue()).get();
            eventRepository.delete(event);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/event")
    public ResponseEntity<List<Object>> deleteAll() {
        eventRepository.deleteAll();
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

}
