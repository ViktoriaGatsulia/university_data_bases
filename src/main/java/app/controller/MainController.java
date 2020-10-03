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

/**
 * Web-контроллер
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Переменная для логирования
     */
    private static final Logger log = Logger.getLogger(MainController.class.getName());

    /**
     * Сервис для работы с группами студентов
     */
    private final StudentGroupService studentgroupService;

    /**
     * Сервис для работы со студентами
     */
    private final StudentService studentService;

    /**
     * Конструктор
     *
     * @param studentgroupService - сервис для работы с группами студентов
     * @param studentService      - сервис для работы со студентами
     */
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

    /**
     * Get-метод
     * curl 'localhost:8080/'
     *
     * @return - стартовая страница (список групп)
     */
    @GetMapping
    public String homePage() {
        log.info("Call start page");
        return "home";
    }

    /**
     * Post-метод для добавления нового студента
     * curl -H 'Content-Type:application/json' -d '{"fio" : "Viktoria Gatsulia", "master_group" : 3}' 'localhost:8080/saveStudent'
     *
     * @param student - добавляемый студент
     * @return - добавленный студент
     */
    @PostMapping(value = "/saveStudent", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveStudent(@RequestBody Student student) {
        log.info("call /saveStudent " + student.toString());
        studentService.save(student);
        return ResponseEntity.ok(student);
    }

    /**
     * Post-метод для сохранения группы
     * curl -H 'Content-Type:application/json' -d '{"num" : "51-60"}' 'localhost:8080/saveGroup'
     *
     * @param studentGroup - сохраняемая группа
     * @return - сохранённая группа
     */
    @PostMapping(value = "/saveGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveGroup(@RequestBody StudentGroup studentGroup) {
        log.info("call /saveGroup " + studentGroup.toString());
        studentgroupService.save(studentGroup);
        return ResponseEntity.ok(studentGroup);
    }

    /**
     * Get-метод для поиска групп
     * curl 'localhost:8080/findGroups'
     *
     * @return - список найденных групп
     */
    @GetMapping("/findGroups")
    public ResponseEntity findGroups() {
        log.info("call /findGroups");
        return ResponseEntity.ok(studentgroupService.findAll());
    }

    /**
     * Get-метод для поиска студентов определённой группы
     * curl 'localhost:8080/findStudentByGroup/id=1'
     *
     * @param id - идентификатор группы, студентов которой нужно найти
     * @return - список студентов определённой группы
     */
    @GetMapping("/findStudentByGroup/id={id}")
    public ResponseEntity findStudentByGroup(@PathVariable Long id) {
        log.info("call /findStudentByGroup/id=" + id);
        Optional<StudentGroup> byId = studentgroupService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get().getStudent_list());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Post-метод для добавления студентов к определённой группе
     * curl -H 'Content-Type:application/json' -d '{"fio" : "Viktoria Gatsulia"}' 'localhost:8080/addStudentsForGroup/id_group=1'
     *
     * @param id_group - идентификатор группы, к которой добавляется студент
     * @param student  - добавляемый студент
     * @return - группа, к которой был добавлен студент, если эта группа существовала, иначе, ошибка 404
     */
    @PostMapping("/addStudentsForGroup/id_group={id_group}")
    public ResponseEntity addStudentsForGroup(@PathVariable Long id_group, @RequestBody Student student) {
        log.info("call /addStudentsForGroup/id_group=" + id_group);
        Optional<StudentGroup> byId = studentgroupService.findById(id_group);
        if (byId.isPresent()) {
            student.setMaster_group(byId.get());
            studentService.save(student);
        }
        return !byId.isPresent()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    /**
     * Get-метод для поиска группы по её идентификатор
     * curl 'localhost:8080/getGroupById/id=1'
     *
     * @param model - модель - найденная группа
     * @param id    - идентфикатор группы
     * @return - web-страница "index" если группа существует, иначе, web-страница "not_found"
     */
    @GetMapping("/getGroupById/id={id}")
    public String getGroupById(Model model, @PathVariable Long id) {
        log.info("call /getGroupById/id=" + id);
        Optional<StudentGroup> groupById = studentgroupService.findById(id);
        groupById.ifPresent(studentGroup -> model.addAttribute("groupById", studentGroup));
        return (groupById.isPresent())
                ? "index"
                : "not_found";
    }

    /**
     * Delete-метод для удаления студента из группы
     * (при удалении последнего последнего участника группы, группа продолжает существовать)
     * `curl -X DELETE 'localhost:8080/deleteStudent/id=7'
     *
     * @param id - идентификатор удаляемой группы
     * @return - страница noContent
     */
    @DeleteMapping("/deleteStudent/id={id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}






