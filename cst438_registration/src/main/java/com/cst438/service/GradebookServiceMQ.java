package com.cst438.service;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cst438.domain.Course;
import com.cst438.domain.CourseDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;



@Service
@ConditionalOnProperty(prefix = "gradebook", name = "service", havingValue = "mq")
public class GradebookServiceMQ implements GradebookService {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	@Autowired
    StudentRepository studentRepository;

	
	Queue gradebookQueue = new Queue("gradebook-queue", true);

	 

	// send message to grade book service about new student enrollment in course
	@Override
	public void enrollStudent(String student_email, String student_name, int course_id ) {
        Student student = studentRepository.findByEmail(student_email);
		EnrollmentDTO enrollDTO = new EnrollmentDTO(student.getStudent_id(), student_email, student_name,course_id);
	    String jsonEnrollment = asJsonString(enrollDTO);
	    this.rabbitTemplate.convertAndSend(gradebookQueue.getName(), jsonEnrollment);
	}

	
	@RabbitListener(queues = "registration-queue")
	@Transactional
	public void receive(String message) {
        CourseDTO courseDTO = fromJsonString(message, CourseDTO.class);
		
		for(CourseDTO.GradeDTO g : courseDTO.grades) {         
			Enrollment tEnrollment = enrollmentRepository.findByEmailAndCourseId(g.student_email, courseDTO.course_id);
			tEnrollment.setCourseGrade(g.grade);
      }
		
	}
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

}