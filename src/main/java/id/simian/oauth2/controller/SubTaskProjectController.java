package id.simian.oauth2.controller;

import id.simian.oauth2.entity.ProjectOrder;
import id.simian.oauth2.entity.SubTaskProject;
import id.simian.oauth2.repository.ProjectOrderRepository;
import id.simian.oauth2.repository.SubTaskProjectRepository;
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
@RequestMapping("/api/v1/subtastproject")
public class SubTaskProjectController {

    @Autowired
    private ProjectOrderRepository projectOrderRepository;

    @Autowired
    private SubTaskProjectRepository subTaskProjectRepository;

    @Secured({"ROLE_KONSULTAN","ROLE_DEVELOPMENT"})
    @GetMapping({"","/"})
    public Page<SubTaskProject> getAllSubTaskProject(@RequestParam Map<String, String> params){
        PageableSpec<SubTaskProject> pageableSpec = SpecificationUtils.of(params);
        return subTaskProjectRepository.findAll(pageableSpec.getSpecification(), pageableSpec.getPageable());
    }

    @Secured({"ROLE_KONSULTAN","ROLE_DEVELOPMENT"})
    @GetMapping({"/{bId:[\\d]+}", "/{bId:[\\d]+}"})
    public SubTaskProject getByIdSubTaskProject(@PathVariable Long bId){
        SubTaskProject subTaskProject=subTaskProjectRepository.findById(bId).orElseThrow(() -> new BadRequest("Sub Task Project white id not found "));
        return  subTaskProject;
    }


    @Secured({"ROLE_KONSULTAN"})
    @PostMapping({"","/"})
    public SubTaskProject postSubTaskProject(@RequestBody SubTaskProject newSubTask){

        if(newSubTask.getName().isEmpty()) throw new BadRequest("Sub Task Project order  name required");
        if(newSubTask.getDescription().isEmpty()) throw new BadRequest("Description required");
        if(newSubTask.getProjectOrder() == null) throw new BadRequest("Project Order required");
        if(newSubTask.getProjectOrder().getId() == null) throw new BadRequest("Project Order id required");
        ProjectOrder projectOrder = projectOrderRepository.findById(newSubTask.getProjectOrder().getId()).orElseThrow(()-> new BadRequest("Project Order white id not found"));
        newSubTask.setProjectOrder(projectOrder);

        return subTaskProjectRepository.save(newSubTask);

    }

    @Secured({"ROLE_KONSULTAN","ROLE_DEVELOPMENT"})
    @PutMapping({"/{bId:[\\d]+}", "/{bId:[\\d]+}"})
    public SubTaskProject putSubTaskProject(@PathVariable Long bId, @RequestBody SubTaskProject newSubTask){
        SubTaskProject subTaskProject = subTaskProjectRepository.findById(bId).orElseThrow(() -> new BadRequest("Sub Task Project not found id"));
        if(newSubTask.getName() != null) subTaskProject.setName(newSubTask.getName());
        if(newSubTask.getDescription() != null) subTaskProject.setDescription(newSubTask.getDescription());
        if(newSubTask.getProjectOrder().getId() != null){
            ProjectOrder projectOrder = projectOrderRepository.findById(newSubTask.getProjectOrder().getId()).orElseThrow(()-> new BadRequest("Project Order white id not found"));
            subTaskProject.setProjectOrder(projectOrder);
        }
        return subTaskProjectRepository.save(subTaskProject);
    }

    @Secured({"ROLE_KONSULTAN"})
    @DeleteMapping({"/{bId:[\\d]+}", "/{bId:[\\d]+}"})
    public String deleteSubTaskProject(@PathVariable Long bId){
        SubTaskProject subTaskProject = subTaskProjectRepository.findById(bId).orElseThrow(() -> new BadRequest("Sub Task Project not found id"));
        subTaskProject.setDeleted(new Date());
        subTaskProjectRepository.save(subTaskProject);
        return "{\"success\":true}";
    }

}
