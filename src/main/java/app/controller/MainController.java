package app.controller;

import app.entity.Student;
import app.entity.StudentGroup;
import app.service.StudentGroupService;
import app.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class.getName());

    private StudentGroupService studentgroupService;
    private StudentService studentService;

    @Autowired
    public MainController(StudentGroupService studentgroupService, StudentService studentService) {
        this.studentgroupService = studentgroupService;
        this.studentService = studentService;

        StudentGroup studentGroup1 = new StudentGroup("1-10");
        studentgroupService.save(studentGroup1);
        StudentGroup studentGroup2 = new StudentGroup("11-20");
        studentgroupService.save(studentGroup2);
        StudentGroup studentGroup3 = new StudentGroup("21-30");
        studentgroupService.save(studentGroup3);
        StudentGroup studentGroup4 = new StudentGroup("31-40");
        studentgroupService.save(studentGroup4);
    }

    @GetMapping
    public String homePage() {
        log.info("Call start page");
        return "home";
    }

    /*
curl -H 'Content-Type:application/json' -d '{"fio" : "Viktoria Gatsulia", "master_group" : 3}' 'localhost:8080/saveStudent'
     */
    @PostMapping(value = "/saveStudent", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveStudent(@RequestBody Student student) {
        log.info("call /saveStudent " + student.toString());
        studentService.save(student);
        return ResponseEntity.ok(student);
    }

    @PostMapping(value = "/saveGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveGroup(@RequestBody StudentGroup studentGroup) {
        log.info("call /saveGroup " + studentGroup.toString());
        studentgroupService.save(studentGroup);
        return ResponseEntity.ok(studentGroup);
    }

    @GetMapping("findGroups/")
    public ResponseEntity findGroups() {
        log.info("call /findGroups");
        return ResponseEntity.ok(studentgroupService.findAll());
    }

    @GetMapping("addStudentsForGroup/id_group={id_group}")
    public ResponseEntity findStudents(@PathVariable Long id_group, @RequestBody Student student) {
        log.info("call /addStudentsForGroup/id_group=" + id_group);
        Optional<StudentGroup> byId = studentgroupService.findById(id_group);
        if (byId.isPresent()) {
            studentService.save(student);
        }
        return !byId.isPresent()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    @GetMapping("/getGroupById/id={id}")
    public String getGroupById(Model model, @PathVariable Long id) {
        log.info("call /getGroupById/id=" + id);
        Optional<StudentGroup> groupById= studentgroupService.findById(id);
        groupById.ifPresent(studentGroup -> model.addAttribute("groupById", studentGroup));
        return (groupById.isPresent())
                ? "index"
                : "not_found";
    }

}
