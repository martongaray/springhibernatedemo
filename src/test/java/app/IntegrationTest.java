package app;

import app.dto.DepartmentDto;
import app.dto.EmployeeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void insertDepartment() {
        DepartmentDto itDepartment = new DepartmentDto("IT");
        ResponseEntity<DepartmentDto> response = restTemplate.postForEntity("/departments", itDepartment, DepartmentDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itDepartment.getDepartmentName(), response.getBody().getDepartmentName());
        assertNotNull(response.getBody().getDepartmentId());
    }

    @Test
    public void insertEmployee() {
        DepartmentDto itDepartment = new DepartmentDto("IT");
        EmployeeDto itEmployee = new EmployeeDto("Alice", "IT");
        restTemplate.postForEntity("/departments", itDepartment, DepartmentDto.class);
        ResponseEntity<EmployeeDto> response = restTemplate.postForEntity("/employees", itEmployee, EmployeeDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itEmployee.getEmployeeName(), response.getBody().getEmployeeName());
        assertEquals(itEmployee.getDepartmentName(), response.getBody().getDepartmentName());
        assertNotNull(response.getBody().getEmployeeId());
        assertNotNull(response.getBody().getDepartmentId());
    }

    @Test
    public void insertEmployeeWithoutDepartment() {
        EmployeeDto itEmployee = new EmployeeDto("Alice", "IT");
        ResponseEntity<String> response = restTemplate.postForEntity("/employees", itEmployee, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Attempting to insert an employee without an existing department.", response.getBody());
    }

    @Test
    public void insertingDuplicateDepartment() {
        DepartmentDto itDepartment = new DepartmentDto("IT");
        ResponseEntity<String> response = restTemplate.postForEntity("/departments", itDepartment, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        response = restTemplate.postForEntity("/departments", itDepartment, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Attempting to insert a duplicate department.", response.getBody());
    }

    @Test
    public void populateDatabaseTest() {
        DepartmentDto itDepartment = new DepartmentDto("IT");
        DepartmentDto hrDepartment = new DepartmentDto("HR");
        EmployeeDto itEmployee1 = new EmployeeDto("Alice", "IT");
        EmployeeDto itEmployee2 = new EmployeeDto("Bob", "IT");
        EmployeeDto itEmployee3 = new EmployeeDto("Carol", "IT");
        EmployeeDto hrEmployee1 = new EmployeeDto("Dave", "HR");
        EmployeeDto hrEmployee2 = new EmployeeDto("Fred", "HR");
        ResponseEntity<DepartmentDto> departmentResponse = restTemplate.postForEntity("/departments", itDepartment, DepartmentDto.class);
        assertEquals(HttpStatus.OK, departmentResponse.getStatusCode());
        departmentResponse = restTemplate.postForEntity("/departments", hrDepartment, DepartmentDto.class);
        assertEquals(HttpStatus.OK, departmentResponse.getStatusCode());

        ResponseEntity<EmployeeDto> employeeResponse = restTemplate.postForEntity("/employees", itEmployee1, EmployeeDto.class);
        assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());
        employeeResponse = restTemplate.postForEntity("/employees", itEmployee2, EmployeeDto.class);
        assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());
        employeeResponse = restTemplate.postForEntity("/employees", itEmployee3, EmployeeDto.class);
        assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());
        employeeResponse = restTemplate.postForEntity("/employees", hrEmployee1, EmployeeDto.class);
        assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());
        employeeResponse = restTemplate.postForEntity("/employees", hrEmployee2, EmployeeDto.class);
        assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());

        ResponseEntity<List<DepartmentDto>> departments  = restTemplate.exchange("/departments/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DepartmentDto>>(){});
        for (DepartmentDto department : departments.getBody()) {
            if (department.getDepartmentName().equals("IT")) {
                assertEquals(3, department.getEmployeeIds().size());
            } else if (department.getDepartmentName().equals("HR")) {
                assertEquals(2, department.getEmployeeIds().size());
            }
        }
    }

}
