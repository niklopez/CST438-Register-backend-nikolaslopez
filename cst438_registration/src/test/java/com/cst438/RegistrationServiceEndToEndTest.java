package com.cst438;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
@SpringBootTest
public class RegistrationServiceEndToEndTest {

    public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_EMAIL = "test@csumb.edu";

	public static final int TEST_COURSE_ID = 40442; 

	public static final String TEST_SEMESTER = "2021 Fall";

	public static final int SLEEP_DURATION = 1000; // 1 second.
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    public void addStudentTest() throws Exception {
        // Set up the WebDriver
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            Student newStudent = new Student();
            newStudent.setStudent_id(5);
            newStudent.setEmail("alex@csumb.edu");
            newStudent.setName("alex test");
            newStudent.setStatusCode(0);
            newStudent.setStatus(null);
            studentRepository.save(newStudent);            
            Student retrievedStudent = studentRepository.findByEmail("alex@csumb.edu");
            assertEquals("alex test", retrievedStudent.getName());
            assertEquals(5, retrievedStudent.getStudent_id());
        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }

    @Test
    public void updateStudentTest() throws Exception {
        // Set up the WebDriver
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            Student newStudent = new Student();
            newStudent.setStudent_id(8);
            newStudent.setEmail("ntest@csumb.edu");
            newStudent.setName("name test");
            newStudent.setStatusCode(0);
            newStudent.setStatus(null);
            studentRepository.save(newStudent);

            // Retrieve the student from the database
            Student retrievedStudent = studentRepository.findByEmail("ntest@csumb.edu");

            // Update the student's details
            retrievedStudent.setName("Paris Tipton");
            retrievedStudent.setStatus("Enrolled");
            studentRepository.save(retrievedStudent);
            //Verify
            Student updatedStudent = studentRepository.findByEmail("test@example.com");
            assertEquals("Paris Tipton", updatedStudent.getName(), "Student's name should be updated.");
            assertEquals("Enrolled", updatedStudent.getStatus(), "Student's status should be updated.");

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }
    @Test
    public void deleteStudentTest() throws Exception {
        // Set up the WebDriver
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
        	 Student newStudent = new Student();
             newStudent.setStudent_id(9);
             newStudent.setEmail("ntest2@csumb.edu");
             newStudent.setName("name test2");
             newStudent.setStatusCode(0);
             newStudent.setStatus(null);
             studentRepository.save(newStudent);



            // Delete the student
            studentRepository.deleteById(newStudent.getStudent_id());

            // Verify that the student is deleted from the database
            Student deletedStudent = studentRepository.findByEmail("ntest2@csumb.edu");
            assertNull(deletedStudent);

            
        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }

}
