package org.example.projecttracker2.Controller;

import jakarta.validation.Valid;
import org.example.projecttracker2.Model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v2/project-tracker")
public class ProjectController {

    ArrayList<Project> projects = new ArrayList<>();

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody @Valid Project newProject, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for(Project project: projects){
            if(project.getId().equals(newProject.getId())){
               return ResponseEntity.status(400).body("ID already exist");
            }
        }
        projects.add(newProject);
        return ResponseEntity.status(200).body("Project created successfully");
    }

    @GetMapping("/display")
    public ResponseEntity<?> displayProjects(){
        if(projects.isEmpty()){
            return ResponseEntity.status(400).body("Projects list is empty");
        }
        return ResponseEntity.status(200).body(projects);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody @Valid Project updatedProject, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for(int i=0; i< projects.size(); i++){
            if(projects.get(i).getId().equals(id)){
                updatedProject.setId(id); //so id not change
                projects.set(i,updatedProject);
                return ResponseEntity.status(200).body("Project updated successfully");
            }
        }
        return ResponseEntity.status(400).body("Project not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id){
        for(int i=0; i < projects.size(); i++){
            if(projects.get(i).getId().equals(id)){
                projects.remove(i);
                return ResponseEntity.status(200).body("Project deleted successfully");
            }
        }
        return ResponseEntity.status(400).body("Project not found");
    }

    @PutMapping("/change-status/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable String id, @PathVariable String status){
        if(!status.equalsIgnoreCase("started") && !status.equalsIgnoreCase("in progress") &&!status.equalsIgnoreCase("completed")){
            return ResponseEntity.status(400).body("Status should be Started, In Progress or Completed");
        }
        for(Project project: projects){
            if(project.getId().equals(id)){
                project.setStatus(status);
                return ResponseEntity.status(200).body("Status changed successfully");
            }
        }
        return ResponseEntity.status(400).body("Project not found");
    }

    @GetMapping("search-by-title/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title){
        ArrayList<Project> projectByTitle = new ArrayList<>();
        for(Project project: projects){
            if(project.getTitle().equals(title)){
                projectByTitle.add(project);
            }
        }
        if(projectByTitle.isEmpty()){
            return ResponseEntity.status(400).body("There not project with this title");
        }
        return ResponseEntity.status(200).body(projectByTitle);
    }

    @GetMapping("/company-projects/{companyName}")
    public ResponseEntity<?> companyProjects(@PathVariable String companyName){
        ArrayList<Project> companyProjects = new ArrayList<>();
        for(Project project: projects){
            if(project.getCompanyName().equals(companyName)){
                companyProjects.add(project);
            }
        }
        if(companyProjects.isEmpty()){
            return ResponseEntity.status(400).body("There is no project for this company");
        }
        return ResponseEntity.status(200).body(companyProjects);
    }

}
