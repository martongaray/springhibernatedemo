package app.service;

import app.component.DepartmentTransformer;
import app.dto.DepartmentDto;
import app.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentTransformer departmentTransformer;

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getDepartmentName());
        Department departmentResult = departmentRepository.save(department);
        return departmentTransformer.transform(departmentResult);

    }

    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d", Department.class);
            departments = query.getResultList();
        } finally {
            entityManager.close();
        }
        return departmentTransformer.transform(departments);
    }

}
