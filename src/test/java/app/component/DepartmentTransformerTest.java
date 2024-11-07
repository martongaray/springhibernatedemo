package app.component;

import app.dto.DepartmentDto;
import app.entity.Department;
import app.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartmentTransformerTest {

    @Autowired
    private DepartmentTransformer departmentTransformer;

    @Test
    public void testTransform_ValidSource_ReturnsCorrectTarget() {
        Department department = new Department("IT");
        department.setId(1L);
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setId(1L);
        alice.setDepartment(department);
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setId(2L);
        bob.setDepartment(department);
        department.getEmployees().add(alice);
        department.getEmployees().add(bob);
        DepartmentDto departmentDto = departmentTransformer.transform(department);
        assertTrue(departmentDto.getEmployeeIds().contains(alice.getId()));
        assertTrue(departmentDto.getEmployeeIds().contains(bob.getId()));
    }

}
