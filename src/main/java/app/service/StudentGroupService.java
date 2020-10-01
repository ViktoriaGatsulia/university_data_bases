package app.service;

import app.entity.StudentGroup;
import app.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentGroupService {

    private StudentGroupRepository studentgroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentgroupRepository) {
        this.studentgroupRepository = studentgroupRepository;
    }

    public StudentGroup save(StudentGroup studentGroup) {
        return studentgroupRepository.save(studentGroup);
    }

    public Optional<StudentGroup> findById(Long id) {
        return studentgroupRepository.findById(id);
    }

    public List<StudentGroup> findAll() {
        return studentgroupRepository.findAll();
    }

    public void deleteById(Long id) {
        studentgroupRepository.deleteById(id);
    }
}
