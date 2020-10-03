package app.repository;

import app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с БД для сущности Student
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
