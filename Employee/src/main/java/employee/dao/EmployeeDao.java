package employee.dao;



import org.springframework.data.jpa.repository.JpaRepository;



import employee.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee,Integer>{
	
	
}
