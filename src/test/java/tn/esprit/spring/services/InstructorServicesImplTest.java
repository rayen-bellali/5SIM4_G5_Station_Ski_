package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    private Instructor instructor;
    private Course course;

    @BeforeEach
    public void setUp() {
        // Initialize mock data before each test
        instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");

        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(1);
    }

    @Test
    public void testAddInstructor() {
        // Arrange
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        // Assert
        assertNotNull(savedInstructor);
        assertEquals("John", savedInstructor.getFirstName());
        assertEquals("Doe", savedInstructor.getLastName());
        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }

    @Test
    public void testRetrieveInstructor() {
        // Arrange
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        // Act
        Instructor retrievedInstructor = instructorServices.retrieveInstructor(1L);

        // Assert
        assertNotNull(retrievedInstructor);
        assertEquals("John", retrievedInstructor.getFirstName());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveInstructor_NotFound() {
        // Arrange
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Instructor retrievedInstructor = instructorServices.retrieveInstructor(1L);

        // Assert
        assertNull(retrievedInstructor);
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        // Assert
        assertNotNull(savedInstructor);
        assertTrue(savedInstructor.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }

    @Test
    public void testAddInstructorAndAssignToCourse_CourseNotFound() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        // Assert
        assertNull(savedInstructor);
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(0)).save(any(Instructor.class));
    }

    @Test
    public void testUpdateInstructor() {
        // Arrange
        instructor.setFirstName("Jane");
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        // Assert
        assertNotNull(updatedInstructor);
        assertEquals("Jane", updatedInstructor.getFirstName());
        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }
}