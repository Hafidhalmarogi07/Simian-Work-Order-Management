package id.simian.oauth2.controller;

import id.simian.oauth2.entity.ProjectOrder;
import id.simian.oauth2.repository.ProjectOrderRepository;
import id.simian.oauth2.responseException.BadRequest;
import id.simian.oauth2.util.PageableSpec;
import id.simian.oauth2.util.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projectorder")
public class ProjectOrderController {

    @Autowired
    private ProjectOrderRepository projectOrderRepository;


    @Secured({"ROLE_MANAGER","ROLE_KONSULTAN","ROLE_DEVELOPMENT"})
    @GetMapping({"","/"})
    public Page<ProjectOrder> getAllProjectOrder(@RequestParam Map<String, String> params){
        PageableSpec<ProjectOrder> pageableSpec = SpecificationUtils.of(params);
        return projectOrderRepository.findAll(pageableSpec.getSpecification(), pageableSpec.getPageable());
    }

    @Secured({"ROLE_MANAGER","ROLE_KONSULTAN","ROLE_DEVELOPMENT"})
    @GetMapping({"/{bId:[\\d]+}", "/{bId:[\\d]+}"})
    public ProjectOrder getByIdProjectOrder(@PathVariable Long bId){
        ProjectOrder projectOrder=projectOrderRepository.findById(bId).orElseThrow(() -> new BadRequest("Project Order white id not found "));
        return  projectOrder;
    }


    @Secured({"ROLE_MANAGER"})
    @PostMapping({"","/"})
    public ProjectOrder postProjectOrder(@RequestBody ProjectOrder newProject){

        if(newProject.getName().isEmpty()) throw new BadRequest("project order  name required");
        if(newProject.getDescription().isEmpty()) throw new BadRequest("Description required");

        return projectOrderRepository.save(newProject);

    }

    @Secured({"ROLE_MANAGER"})
    @PutMapping({"/{bId:[\\d]+}", "/{bId:[\\d]+}"})
    public ProjectOrder putProjectOrder(@PathVariable Long bId, @RequestBody ProjectOrder newProject){
        ProjectOrder projectOrder = projectOrderRepository.findById(bId).orElseThrow(() -> new BadRequest("Project Order not found id"));
        if(newProject.getName() != null) projectOrder.setName(newProject.getName());
        if(newProject.getDescription() != null) projectOrder.setDescription(newProject.getDescription());
        return projectOrderRepository.save(projectOrder);
    }

    @Secured({"ROLE_MANAGER"})
    @DeleteMapping({"/{bId:[\\d]+}", "/{bId:[\\d]+}"})
    public String deleteProjectOrder(@PathVariable Long bId){
        ProjectOrder projectOrder = projectOrderRepository.findById(bId).orElseThrow(() -> new BadRequest("Project Order not found id"));
        projectOrder.setDeleted(new Date());
        projectOrderRepository.save(projectOrder);
        return "{\"success\":true}";
    }
}

