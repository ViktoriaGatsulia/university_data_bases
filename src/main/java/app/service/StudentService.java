package app.service;

import app.entity.Student;
import app.entity.StudentGroup;
import app.repository.StudentGroupRepository;
import app.repository.StudentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс-сервис для работы со Student репозиторием
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Service
public class StudentService {

    /**
     * Переменная для логирования
     */
    private static final Logger log = Logger.getLogger(StudentService.class.getName());

    /**
     * Репозиторий для сущности Student
     */
    private final StudentRepository studentRepository;

    /**
     * Репозитрий для сущности StudentGroup
     */
    private final StudentGroupRepository studentGroupRepository;

    /**
     * Конструктор
     *
     * @param studentRepository      - репозиторий студентов
     * @param studentGroupRepository - репозиторий групп
     */
    @Autowired
    public StudentService(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository) {
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    /**
     * Метод для сохранения нового студента
     *
     * @param student - студент, которого следует сохранить
     */
    public void save(Student student) {
        if (Objects.isNull(student.getFio())) {
            log.error("try save incorrect user: " + student.toString());
            return;
        }
        Long id = student.getMaster_group().getId_group();
        Optional<StudentGroup> optByIdGroup = studentGroupRepository.findById(id);
        if (optByIdGroup.isPresent()) {
            student.setMaster_group(optByIdGroup.get());
            studentRepository.save(student);
        }
    }

    /**
     * Поиск студента по индивидуальному идентификатору
     *
     * @param id - идентификатор искомого студента
     * @return - найденный студент (Optional)
     */
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    /**
     * Метод для поиска всех студентов
     *
     * @return - список найденных студентов
     */
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Метод для удаления студента по его id
     *
     * @param id - идентификатор студента, которого следует удалить
     */
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

}
