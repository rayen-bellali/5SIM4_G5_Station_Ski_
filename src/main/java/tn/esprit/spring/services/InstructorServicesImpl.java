package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices{
    private static final Logger logger = LogManager.getLogger(InstructorServicesImpl.class);

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        logger.info("Adding instructor: {}", instructor);
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        logger.info("Retrieving all instructors");
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        logger.info("Updating instructor: {}", instructor);
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        logger.info("Retrieving instructor with id: {}", numInstructor);
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        logger.info("Adding instructor: {} and assigning to course with id: {}", instructor, numCourse);
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (course != null) {
            Set<Course> courseSet = new HashSet<>();
            courseSet.add(course);
            instructor.setCourses(courseSet);
            return instructorRepository.save(instructor);
        }
        return null;
    }
}
