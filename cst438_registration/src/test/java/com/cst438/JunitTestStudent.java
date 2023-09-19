package com.cst438;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

@SpringBootTest
@AutoConfigureMockMvc
class JunitTestStudent {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAddStudent() throws Exception {
        // Create a new Student that will add to the database
        Student expectedStudent = new Student();
        expectedStudent.setStudent_id(4);
        expectedStudent.setName("Hana Song");
        expectedStudent.setEmail("Hsong@csumb.edu");
        expectedStudent.setStatusCode(0);
        expectedStudent.setStatus(null);

        // Convert the Student object's value to a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(expectedStudent);

        // Use a POST request in order to add a new student
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // check that the status is 200
        assertEquals(200, response.getStatus());

        // Parse the JSON response
        String jsonResponse = response.getContentAsString();
        ObjectMapper responseMapper = new ObjectMapper();
        Student actualStudent = responseMapper.readValue(jsonResponse, Student.class);

        // Use getters to get the student id, name and email
        int actualID = actualStudent.getStudent_id();
        String actualName = actualStudent.getName();
        String actualEmail = actualStudent.getEmail();

        // we verify that the assert values match the actual 
        assertEquals(expectedStudent.getStudent_id(), actualID);
        assertEquals(expectedStudent.getName(), actualName);
        assertEquals(expectedStudent.getEmail(), actualEmail);
        

    }

    @Test
    void testGetStudentById() throws Exception {
        // Create a new Student object
        Student student = new Student();
        student.setStudent_id(5);
        student.setName("Kaya Freedom");
        student.setEmail("kayafree@csumb.edu");
        student.setStatusCode(0);
        student.setStatus(null);

        // Convert the Student object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(student);

        // POST request to add a new student
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that the return status for the POST request is OK (value 200)
        assertEquals(200, response.getStatus());

        // GET request to fetch the student by ID and we add the ID to the URL
        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/students?student_id=" + student.getStudent_id())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that the return status for the GET request is 200
        assertEquals(200, response.getStatus());

    }

    @Test
    void testGetStudentById2() throws Exception {
    	//Add New Test Student
    	Student student = new Student();
        student.setStudent_id(10);
        student.setName("Juan Martinez");
        student.setEmail("JMartinez@csumb.edu");
        student.setStatusCode(0);
        student.setStatus(null);

        // convert the student to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(student);

        // POST request to add a new student
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that the return status for the GET request is 200
        assertEquals(200, response.getStatus());

        // we use a git request to get the student by the ID
        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/students?student_id=" + student.getStudent_id())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that the return status for the GET request is 200
        assertEquals(200, response.getStatus());
    }
    @Test
    void testUpdateStudent() throws Exception {
        // Add a new test student
        Student student = new Student();
        student.setStudent_id(3);
        student.setName("Kylee Song");
        student.setEmail("KyleeSong@csumb.edu");
        student.setStatusCode(0);
        student.setStatus(null);

        // convert the object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(student);

        // use a POST request to update the student
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // check that response status is 200
        assertEquals(200, response.getStatus());

     
        student.setStudent_id(3);
        student.setName("Kylee Smiley");
        student.setEmail("kyleesmiley@csumb.edu");
        student.setStatusCode(0);
        student.setStatus(null);

        String updatedRequestBody = objectMapper.writeValueAsString(student);

        // Use a put request to update the student
        response = mvc.perform(
                MockMvcRequestBuilders
                        .put("/students/{studentId}", student.getStudent_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        // get the JSON response
        String jsonResponse = response.getContentAsString();
        ObjectMapper responseMapper = new ObjectMapper();
        Student updatedStudent = responseMapper.readValue(jsonResponse, Student.class);

//use getter to get the updated name
        String actualName = updatedStudent.getName();

        // Assert that the actual name matches the updated name - "Kylee Smiley"
        assertEquals("Kylee Smiley", actualName);
    }
    @Test
    void testRemoveStudent() throws Exception {
        // Add a new student
        Student student = new Student();
        student.setStudent_id(6);
        student.setName("Clownita Clown");
        student.setEmail("clown@csumb.edu");
        student.setStatusCode(0);
        student.setStatus(null);

        // convert the student to an object 
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(student);

        // use post request
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//verify status 
        assertEquals(200, response.getStatus());

//use delete to remove student        
        response = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/students/{studentId}", student.getStudent_id())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//verify the response status        
        assertEquals(200, response.getStatus());
    }
    @Test
    void testGetAllStudents() throws Exception {
        // adding another test student
        Student student = new Student();
        student.setStudent_id(6);
        student.setName("Clownita Clown");
        student.setEmail("clown@csumb.edu");
        student.setStatusCode(0);
        student.setStatus(null);

        // Convert the Student to a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody1 = objectMapper.writeValueAsString(student);

        // add the another student
        MockHttpServletResponse response1 = mvc.perform(
                MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // verify the response status
        assertEquals(200, response1.getStatus());

     

        // GET- a list of all the student
        MockHttpServletResponse getAllResponse = mvc.perform(
                MockMvcRequestBuilders
                        .get("/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //double checking the reponse status 
        assertEquals(200, getAllResponse.getStatus());

    }







    
}
