package app.service;

import app.entity.StudentGroup;
import app.repository.StudentGroupRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс-сервис для работы со StudentGroup репозиторием
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Service
public class StudentGroupService {

    /**
     * Переменная для логирования
     */
    private static final Logger log = Logger.getLogger(StudentGroupService.class.getName());

    /**
     * Репозиторий для сущности StudentGroup
     */
    private final StudentGroupRepository studentgroupRepository;

    /**
     * Конструктор
     *
     * @param studentgroupRepository - репозиторий групп студентов
     */
    @Autowired
    public StudentGroupService(StudentGroupRepository studentgroupRepository) {
        this.studentgroupRepository = studentgroupRepository;
    }

    /**
     * Метод для сохранения новой группы
     *
     * @param studentGroup - сохраняемая группа
     */
    public void save(StudentGroup studentGroup) {
        if (Objects.isNull(studentGroup.getNum())) {
            log.error("try save incorrect userGroup: " + studentGroup.toString());
            return;
        }
        studentgroupRepository.save(studentGroup);
    }

    /**
     * Поиск группы по id
     *
     * @param id - индивидуальный идентификатор искомой группы
     * @return - найденная группа (Optional)
     */
    public Optional<StudentGroup> findById(Long id) {
        return studentgroupRepository.findById(id);
    }

    /**
     * Метод для поиска всех групп
     *
     * @return - список найденных групп
     */
    public List<StudentGroup> findAll() {
        return studentgroupRepository.findAll();
    }

    /**
     * Удаление группы по её индивидуальному идентификатору
     *
     * @param id - идентификатор группы, которую следует удалить
     */
    public void deleteById(Long id) {
        studentgroupRepository.deleteById(id);
    }
}
