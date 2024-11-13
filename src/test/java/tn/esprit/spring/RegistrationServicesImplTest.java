package tn.esprit.spring;

import org.mockito.Mockito;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.RegistrationServicesImpl;

import java.time.LocalDate; // Assurez-vous que cette importation est présente
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RegistrationServicesImplTest {
    private IRegistrationRepository registrationRepository;
    private ISkierRepository skierRepository;
    private ICourseRepository courseRepository;
    private RegistrationServicesImpl registrationService;

    public static void main(String[] args) {
        RegistrationServicesImplTest test = new RegistrationServicesImplTest();
        test.setUp();
        test.testAddRegistrationAndAssignToSkier();
        test.testAssignRegistrationToCourse();

    }

    public void setUp() {
        // Create mocks for the repositories
        registrationRepository = Mockito.mock(IRegistrationRepository.class);
        skierRepository = Mockito.mock(ISkierRepository.class);
        courseRepository = Mockito.mock(ICourseRepository.class);

        // Initialize the RegistrationServicesImpl with the mock repositories
        registrationService = new RegistrationServicesImpl(registrationRepository, skierRepository, courseRepository);
    }

    public void testAddRegistrationAndAssignToSkier() {
        Registration registration = new Registration();
        registration.setNumWeek(1);
        registration.setStartDate(LocalDate.now()); // Initialisation de startDate
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        // Mock the behavior of the skier repository
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationService.addRegistrationAndAssignToSkier(registration, 1L);

        // Assert
        assert result != null : "Result should not be null";
        verify(skierRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
        assert result.getSkier() == skier : "Skier should be assigned to registration";
    }

    public void testAssignRegistrationToCourse() {
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registration.setStartDate(LocalDate.now()); // Initialisation de startDate
        Course course = new Course();
        course.setNumCourse(1L);

        // Mock the behavior of the repositories
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationService.assignRegistrationToCourse(1L, 1L);

        // Assert
        assert result != null : "Result should not be null";
        verify(registrationRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
        assert result.getCourse() == course : "Course should be assigned to registration";
    }


}