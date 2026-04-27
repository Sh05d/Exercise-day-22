package org.example.projecttracker2.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Project {
    @NotEmpty(message = "ID can't be empty")
    @Size(min = 3, message = "Length of ID should be more than two characters")
    private String id;
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 9, message ="Title should be more than 8 letters")
    private String title;
    @NotEmpty(message = "Description can't be empty")
    @Size(min = 16, message = "Description should be more than 15 letter")
    private String description;
    @NotEmpty(message = "Status can't be empty")
    @Pattern(regexp = "(?i)Started|In Progress|Completed", message = "Status should be Started, In Progress or Completed")
    private String status;
    @NotEmpty(message = "Company can't be empty")
    @Size(min = 6, message = "Company name should be more than 6 letters")
    private String companyName;
}
