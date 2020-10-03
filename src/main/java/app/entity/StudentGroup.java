package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.*;

/**
 * Сущность, описывающая группу студентов
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Entity
@Table
public class StudentGroup {

    /**
     * Переменная для логирования
     */
    private static final Logger log = Logger.getLogger(StudentGroup.class.getName());

    /**
     * Идентификатор группы
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_group;

    /**
     * Номер (название) группы
     */
    @Column(name = "num")
    private String num;

    /**
     * Количество студентов в группе
     */
    @Column(name = "count")
    private int count;

    /**
     * Список студентов в группе
     */
    @JsonIgnoreProperties(value = "student_list", allowSetters = true)
    @OneToMany(mappedBy = "master_group", fetch = FetchType.EAGER)
    private List<Student> student_list = new ArrayList<>();

    /**
     * Конструктор
     */
    public StudentGroup() {
    }

    /**
     * Конструктор
     *
     * @param num - наименование группы
     */
    public StudentGroup(String num) {
        this.num = num;
    }

    /**
     * Геттер
     *
     * @return - идентификатор группы
     */
    public Long getId_group() {
        return id_group;
    }

    /**
     * Сеттер
     *
     * @param id_group - идентификатор группы
     */
    public void setId_group(Long id_group) {
        this.id_group = id_group;
    }

    /**
     * Геттер
     *
     * @return - наименование группы
     */
    public String getNum() {
        return num;
    }

    /**
     * Сеттер
     *
     * @param num - наименование группы
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * Геттер
     *
     * @return - список участников группы, отсортированный по алфавиту
     */
    public List<Student> getStudent_list() {
        student_list.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getFio(), o2.getFio());
            }
        });
        return student_list;
    }

    /**
     * Сеттер
     *
     * @param student_list - список участников группы
     */
    public void setStudent_list(List<Student> student_list) {
        this.student_list = student_list;
    }

    /**
     * Геттер
     *
     * @return - колличество студентов в группе
     */
    public int getCount() {
        return reCount();
    }

    /**
     * Пересчёт студентов в группе
     *
     * @return
     */
    public int reCount() {
        this.count = Objects.isNull(student_list) ? 0 : student_list.size();
        return this.count;
    }

    /**
     * Метод добавления студента в группу
     *
     * @param student - добавляемый студент
     */
    public void addStudent(Student student) {
        log.info("Use addStudent, student_list = " + Objects.isNull(student_list) + " student = " + Objects.isNull(student));
        if (Objects.isNull(student_list)) {
            student_list = new ArrayList<>();
        }
        student_list.add(student);
    }

    /**
     * Метод для преобразования класса в строку
     *
     * @return - описания группы студентов
     */
    @Override
    public String toString() {
        return "StudentGroup{" +
                "id_group=" + Optional.ofNullable(id_group) +
                ", num='" + Optional.ofNullable(num) + '\'' +
                ", student_list=" + Optional.ofNullable(student_list) +
                '}';
    }

}
