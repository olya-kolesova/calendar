package webCalendarSpring;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.beans.PropertyEditor;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Event {
    @NotBlank
    String event;

    @NotNull
    LocalDate date;
    String message = "The event has been added!";

    public Event() {
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date, BindingResult result) throws MethodArgumentNotValidException {
        if (!(date.matches("\\d{4}-\\d{2}-\\d{2}"))) {
            try {
                Parameter parameter = Event.class.getMethod("setDate", String.class).getParameters()[0];
                throw new MethodArgumentNotValidException(MethodParameter.forParameter(parameter), result);
            } catch (NoSuchMethodException e) {
                System.out.println("This method doesn't exist!");
            }

        }
        this.date = LocalDate.parse(date);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
