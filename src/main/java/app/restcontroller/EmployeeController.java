package app.restcontroller;

import app.dto.EmployeeDto;
import app.service.EmployeeService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartment(@RequestParam("departmentName") String departmentName) {
        return ResponseEntity.ok(employeeService.getEmployees(departmentName));
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("employee_id") String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<Object> postEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            return ResponseEntity.ok(employeeService.saveEmployee(employeeDto));
        } catch (NoResultException e) {
            return new ResponseEntity<>("Attempting to insert an employee without an existing department.",
                    HttpStatus.BAD_REQUEST);
        }
    }

}
