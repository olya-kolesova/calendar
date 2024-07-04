package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Validated
@RestController
public class EventController {



    private List<Event> events = new ArrayList<>();

    @GetMapping(value="/event/today", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEvents() {
        return events;
    }


    @PostMapping("/event")
    public ResponseEntity<Object> postEvent(@Valid @RequestBody Event event) {
        events.add(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
