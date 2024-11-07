package app.service;

import app.component.EmployeeTransformer;
import app.dto.EmployeeDto;
import app.entity.Department;
import app.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeTransformer employeeTransformer;

    public List<EmployeeDto> getEmployees(String departmentName) {
        List<Employee> employees;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d WHERE d.name = :name", Department.class);
            query.setParameter("name", departmentName);
            Department department = query.getSingleResult();
            employees = department.getEmployees();
        } finally {
            entityManager.close();
        }
        return employeeTransformer.transform(employees);
    }

    public EmployeeDto getEmployee(String employeeId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Employee employee;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id = :employeeId", Employee.class);
            query.setParameter("employeeId", employeeId);
            employee = query.getSingleResult();
        } catch (NoResultException e) {
            return new EmployeeDto();
        } finally {
            entityManager.close();
        }
        return employeeTransformer.transform(employee);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
            employees = query.getResultList();
        } finally {
            entityManager.close();
        }
        return employeeTransformer.transform(employees);
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d WHERE d.name = :name", Department.class);
            query.setParameter("name", employeeDto.getDepartmentName());
            Department department = query.getSingleResult();
            Employee employee = new Employee();
            employee.setDepartment(department);
            employee.setName(employeeDto.getEmployeeName());
            Employee employeeResult = employeeRepository.save(employee);
            return employeeTransformer.transform(employeeResult);
        } finally {
            entityManager.close();
        }
    }

}
