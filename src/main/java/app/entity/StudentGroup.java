package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table
public class StudentGroup {

    private static final Logger log = Logger.getLogger(StudentGroup.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_group;

    @Column(name = "num")
    private String num;

    @JsonIgnoreProperties(value = "student_list", allowSetters = true)
    @OneToMany(mappedBy="master_group", fetch = FetchType.EAGER)
    private List<Student> student_list = new ArrayList<>();

    public StudentGroup() {}

//    public StudentGroup(Long id_group, String num, List<Student> student_list) {
//        this.id_group = id_group;
//        this.num = num;
//        this.student_list = student_list;
//    }

    public StudentGroup(String num) {
        this.num = num;
    }

    public StudentGroup(Long master_group_id) {
        this.id_group = master_group_id;
    }


    public Long getId_group() {
        return id_group;
    }

    public void setId_group(Long id_group) {
        this.id_group = id_group;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<Student> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(List<Student> student_list) {
        this.student_list = student_list;
    }

    @Override
    public String toString() {
        return "StudentGroup{" +
                "id_group=" + Optional.ofNullable(id_group) +
                ", num='" + Optional.ofNullable(num) + '\'' +
                ", student_list=" + Optional.ofNullable(student_list) +
                '}';
    }

    public void addStudent(Student student) {
        log.info("Use addStudent, student_list = " + Objects.isNull(student_list) + " student = " + Objects.isNull(student));
        student_list.add(student);
    }
}
