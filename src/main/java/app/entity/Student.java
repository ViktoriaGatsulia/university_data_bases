package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table
public class Student {

    private static final Logger log = Logger.getLogger(Student.class.getName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @JsonIgnoreProperties(value = "student_list", allowSetters = true)
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_group")
    private StudentGroup master_group;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_student;

    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_add")
    private Date date_add;

    @Column(name = "fio")
    private String fio;

    public Student() {
        date_add = new Date();
        log.info("Use empty constructor");
    }

    public Student(StudentGroup master_group, String fio) {
        this.master_group = master_group;
        this.fio = fio;
        this.date_add = new Date();
        log.info("Use first constructor");
    }

    public Student(String fio) {
        this.fio = fio;
        this.date_add = new Date();
        log.info("Use second constructor");
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public StudentGroup getMaster_group() {
        log.info("Use getMaster_group");
        return master_group;
    }

    public void setMaster_group(StudentGroup master_group) {
        this.master_group = master_group;
    }

    public Long getId_student() {
        return id_student;
    }

    public void setId_student(Long id_student) {
        this.id_student = id_student;
    }

    public Date getDate_add() {
        log.info("Use getDate_add");
        return date_add;
    }

    public void setDate_add() {
        log.info("Use setDate_add");
        this.date_add = new Date();
    }

    @Override
    public String toString() {
        if (Objects.isNull(master_group))
            return "Student{" +
                    "master_group_id=" + Optional.ofNullable(master_group) +
                    ", id_student=" + Optional.ofNullable(id_student) +
                    ", date_add=" + Optional.ofNullable(date_add) +
                    ", last_name='" + Optional.ofNullable(fio) + '\'' +
                    '}';
        return "Student{" +
                "master_group_id=" + Optional.ofNullable(master_group.getId_group()) +
                ", id_student=" + Optional.ofNullable(id_student) +
                ", date_add=" + Optional.ofNullable(date_add) +
                ", last_name='" + Optional.ofNullable(fio) + '\'' +
                '}';
    }

}
