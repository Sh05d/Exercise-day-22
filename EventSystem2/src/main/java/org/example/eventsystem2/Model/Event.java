package org.example.eventsystem2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Event {
    @NotEmpty(message = "ID can't be empty")
    @Size(min = 3, message = "ID should be more than 2 characters")
    private String id;
    @NotEmpty(message = "Description can't be empty")
    @Size(min = 16, message = "Description should be more than 15 letters")
    private String description;
    @NotNull(message = "Capacity can't be null")
    @Min(26)
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message ="You should put future or today date")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message ="You should put future or today date")
    private LocalDate endDate;
}
