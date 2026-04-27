package org.example.eventsystem2.Controller;

import jakarta.validation.Valid;
import org.example.eventsystem2.Model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v2/event-system")
public class EventController {

    ArrayList<Event> events =new ArrayList<>();

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody @Valid Event newEvent, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if(newEvent.getEndDate().isBefore(newEvent.getStartDate())){
            return ResponseEntity.status(400).body("End date most be after start date");
        }
        for(Event event: events){
            if(event.getId().equals(newEvent.getId())){
                return ResponseEntity.status(400).body("ID already exist");
            }
        }
        events.add(newEvent);
        return ResponseEntity.status(200).body("Event created successfully");
    }

    @GetMapping("/display")
    public ResponseEntity<?> displayEvents(){
        if(events.isEmpty()){
            return ResponseEntity.status(400).body("There is no events");
        }
        return ResponseEntity.status(200).body(events);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable String id, @RequestBody @Valid Event updatedEvent, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if(events.isEmpty()){
            return ResponseEntity.status(400).body("There is no event to update");
        }
        if(updatedEvent.getEndDate().isBefore(updatedEvent.getStartDate())){
            return ResponseEntity.status(400).body("End date most be after start date");
        }
        for(int i=0; i< events.size(); i++){
            if(events.get(i).getId().equals(id)){
                updatedEvent.setId(id);
                events.set(i,updatedEvent);
                return ResponseEntity.status(200).body("Event updated successfully");
            }
        }
        return ResponseEntity.status(400).body("Event not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable String id){
        if(events.isEmpty()){
            return ResponseEntity.status(400).body("There is no event to delete");
        }
        for(int i=0; i < events.size(); i++){
            if(events.get(i).getId().equals(id)){
                events.remove(i);
                return ResponseEntity.status(200).body("Event deleted successfully");
            }
        }
        return ResponseEntity.status(400).body("Event not found");
    }

    @PutMapping("/change-capacity/{id}/{capacity}")
    public ResponseEntity<?> changeCapacity(@PathVariable String id,@PathVariable int capacity){
        if(capacity < 26){
            return ResponseEntity.status(400).body("Capacity should be more than 25");
        }
        if(events.isEmpty()){
            return ResponseEntity.status(400).body("There is no event to change its capacity");
        }
        for(Event event: events){
            if(event.getId().equals(id)){
                event.setCapacity(capacity);
                return ResponseEntity.status(200).body("Capacity changed successfully");
            }
        }
        return ResponseEntity.status(400).body("Event not found");
    }

    @GetMapping("search-event/{id}")
    public ResponseEntity<?> searchEvent(@PathVariable String id){
        if(events.isEmpty()){
            return ResponseEntity.status(400).body("There is no event to search");
        }
        for(Event event: events){
            if(event.getId().equals(id)){
                return ResponseEntity.status(200).body(event);
            }
        }
        return ResponseEntity.status(400).body("Event not found");
    }

}
