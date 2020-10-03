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

@Service
public class StudentService {

    private static final Logger log = Logger.getLogger(StudentService.class.getName());

    private StudentRepository studentRepository;
    private StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository) {
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    public Student save(Student student) {
        Long id = student.getMaster_group().getId_group();
        Optional<StudentGroup> optByIdGroup = studentGroupRepository.findById(id);
        if(optByIdGroup.isPresent()) {
            student.setMaster_group(optByIdGroup.get());
            return studentRepository.save(student);
        }
        return student;
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

}
