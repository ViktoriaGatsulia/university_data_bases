package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Сущность, описывающая студента
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Entity
@Table
public class Student {

    /**
     * Переменная для логирования
     */
    private static final Logger log = Logger.getLogger(Student.class.getName());

    /**
     * Строка для форматирования даты
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Группа, которой принадлежит студент
     */
    @JsonIgnoreProperties(value = "student_list", allowSetters = true)
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_group")
    private StudentGroup master_group;

    /**
     * Индивидуальный идентификатор студента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_student;

    /**
     * Дата добавления студента к группе
     */
    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_add")
    private Date date_add;

    /**
     * ФИО студента
     */
    @Column(name = "fio")
    private String fio;

    /**
     * Пустой конструктор
     */
    public Student() {
        date_add = new Date();
        log.info("Use empty constructor");
    }

    /**
     * Конструктор
     *
     * @param fio - ФИО студента
     */
    public Student(String fio) {
        this.fio = fio;
        this.date_add = new Date();
        log.info("Use second constructor");
    }

    /**
     * Конструктор
     *
     * @param master_group - группа, к которой принадлежит конструктор
     * @param fio          - ФИО студента
     */
    public Student(StudentGroup master_group, String fio) {
        this.master_group = master_group;
        this.fio = fio;
        this.date_add = new Date();
        log.info("Use first constructor");
    }

    /**
     * Сеттер
     *
     * @param fio - ФИО студента
     */
    public void setFio(String fio) {
        this.fio = fio;
    }

    /**
     * Геттер
     *
     * @return - ФИО студента
     */
    public String getFio() {
        return fio;
    }

    /**
     * Геттер
     *
     * @return - группа, к которой принадлежит студент
     */
    public StudentGroup getMaster_group() {
        return master_group;
    }

    /**
     * Сеттер
     *
     * @param master_group - группа, к которой будет принадлежать студент
     */
    public void setMaster_group(StudentGroup master_group) {
        this.master_group = master_group;
    }

    /**
     * Геттер
     *
     * @return - идентификатор студента
     */
    public Long getId_student() {
        return id_student;
    }

    /**
     * Сеттер
     *
     * @param id_student - идентификатор студента
     */
    public void setId_student(Long id_student) {
        this.id_student = id_student;
    }

    /**
     * Геттер
     *
     * @return - дата добавления студента к группе
     */
    public Date getDate_add() {
        return date_add;
    }

    /**
     * Сеттер
     * <p>
     * Устанавливает текущую дату, как дату добавления студента к группе
     */
    public void setDate_add() {
        this.date_add = new Date();
    }

    /**
     * Метод для преобразования класса в строку
     *
     * @return - описание студента
     */
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
