package app.repository;

import app.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с БД для сущности StudentGroup
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
}
