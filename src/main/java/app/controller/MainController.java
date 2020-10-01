package app.controller;

import app.entity.Student;
import app.entity.StudentGroup;
import app.service.StudentGroupService;
import app.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
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

    /*
    curl -H 'Content-Type:application/json' -d '{"first_name" : "Viktoria", "last_name" : "Gatsulia", "master_group" : 3}' 'localhost:8080/saveStudent'
     */
    @PostMapping(value = "/saveStudent", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveStudent(@RequestBody Student student) {
        log.info("call /saveStudent " + student.toString());
        studentService.save(student);
        return ResponseEntity.ok(student);
    }

    @PostMapping(value = "/saveGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveGroup(@RequestBody StudentGroup studentGroup) {
        studentgroupService.save(studentGroup);
        log.info("call /saveGroup " + studentGroup.toString());
        return ResponseEntity.ok(studentGroup);
    }

}
