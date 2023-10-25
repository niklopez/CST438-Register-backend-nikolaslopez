package com.cst438.service;
import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.StudentRepository;


@Service
@ConditionalOnProperty(prefix = "gradebook", name = "service", havingValue = "rest")

@RestController
public class GradebookServiceREST implements GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	private static String gradebook_url;

	public GradebookServiceREST() {
		System.out.println("REST grade book service");
	}
	@Autowired
	private StudentRepository studentRepository;


	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
        Student student = studentRepository.findByEmail(student_email);
		EnrollmentDTO newDTO = new EnrollmentDTO(student.getStudent_id(), student_email, student_name, course_id);
		
		restTemplate.postForEntity(gradebook_url+"/enrollment", newDTO, EnrollmentDTO.class);
		
	}
	
	 @Autowired
	    EnrollmentRepository enrollmentRepository;

	    /*
	     * endpoint for final course grades
	     */
	    @PutMapping("/course/{course_id}")
	    @Transactional
	    public void updateCourseGrades(@RequestBody FinalGradeDTO[] grades, @PathVariable("course_id") int course_id) {
	        System.out.println("Grades received " + grades.length);

	        for (int i = 0; i < grades.length; i++) {
	            FinalGradeDTO gradeDTO = grades[i];
	            Enrollment enrollment = enrollmentRepository.findByEmailAndCourseId(gradeDTO.studentEmail(), course_id);
	            enrollment.setCourseGrade(gradeDTO.grade());
	            enrollmentRepository.save(enrollment); 

	    }
	}
}