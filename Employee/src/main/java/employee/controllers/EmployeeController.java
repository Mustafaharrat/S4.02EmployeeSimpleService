package employee.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




import employee.dao.EmployeeDao;
import employee.entity.Employee;
import employee.entity.EnumPosition;

@RequestMapping("/")
@WebFilter("/*")
@RestController
public class EmployeeController implements Filter {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	    httpServletResponse.setHeader("IT-Academy-Exercise", "Simple Service");
	    chain.doFilter(request, response);	
	}

	@Override
	public void destroy() {  
	}
	
		
	@GetMapping("/createDb")
	public String createDb() {
	ArrayList<Employee> employeesDb =new ArrayList<Employee>();
	
	employeesDb.add(new Employee("Anna", "Tarres Perez",EnumPosition.Director));
	employeesDb.add(new Employee("Oriol", "Tufet Segarra",EnumPosition.Manager));
	employeesDb.add(new Employee("Cristina", "Turo CapVila",EnumPosition.Administrative));
	employeesDb.add(new Employee("Antoni", "Barbera Soplillo",EnumPosition.Accountant));
	employeesDb.add(new Employee("Pere", "Bach Castellnou",EnumPosition.Manager));
	employeesDb.add(new Employee("Elena", "Wayghn Torders",EnumPosition.Administrative));
	employeesDb.add(new Employee("Xhin", "Shon-Pen",EnumPosition.Accountant));
	
	employeeDao.saveAll(employeesDb);
	
	return "Database created";
	}
	
	@GetMapping("getEmployees")
	public ResponseEntity<List<Employee>> getEmployees(){
		
			
		return ResponseEntity.ok(employeeDao.findAll());
	}
	
	
	@GetMapping("getEmployeeId")
	public ResponseEntity<Optional<Employee>> getEmployee(int id){
		
		if (employeeDao.findById(id).isPresent()) {
			
			return ResponseEntity.ok(employeeDao.findById(id));
		}else {
			
			return ResponseEntity.noContent().build();
		}
		
		
	}
	
	@PostMapping("create/{name}/{lastname}/{position}")
	public ResponseEntity<String> createEmployee(@PathVariable(name="name")String name,
												 @PathVariable(name="lastname")String lastname,
												 @PathVariable(name="position") EnumPosition enumPosition){
	
		Employee employee=new Employee(name,lastname,enumPosition);
			
		employeeDao.save(employee);
		return ResponseEntity.ok("Incorported Employee: "+ employee);
	}	
	
	@GetMapping("getPosition/{enumPosition}")
	public ResponseEntity<List<Employee>> getPosition(@PathVariable(name="enumPosition") EnumPosition enumPosition){
		
		List<Employee> employeeList =employeeDao.findAll();
		return ResponseEntity.ok(employeeList.stream()
								.filter(s-> s.getEnumPosition().equals(enumPosition))
								.collect(Collectors.toList()));
	}
	@PutMapping("/update/{id}/{name}/{lastname}/{enumPosition}")
	public ResponseEntity<HttpStatus> updateEmpleado(@PathVariable(name="id") int id,
													 @PathVariable(name= "name") String name,
													 @PathVariable(name= "lastname") String lastname, 
													 @PathVariable(name="enumPosition") EnumPosition enumPosition){
		
	Optional<Employee> employee=employeeDao.findById(id);
	
	if(employee.isPresent()) {
		Employee newEmployee=employee.get();
		newEmployee.setName(name);
		newEmployee.setLastname(lastname);
		newEmployee.setEnumPosition(enumPosition);
		
		employeeDao.save(newEmployee);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
		
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("deleteEmployee/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(name="id") int id){
		Optional<Employee> employee=employeeDao.findById(id);
		
		if(employee.isPresent()) {
			Employee newEmployee=employee.get();
			employeeDao.delete(newEmployee);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@PutMapping("/updatePicture/{id}")
	public ResponseEntity<HttpStatus> updateEmployeePicture(@PathVariable(name = "id") int id) {
	Employee employee1 = null;
	
	  try {
		  Optional<Employee> employee = employeeDao.findById(id);
		 
		  if (employee.isPresent()) {
			  employee1 = employee.get();
			  
				byte[] picture = null;	
				
				String temp=employee1.getPosition()+".jpg";
				
	
				InputStream inputStream = this.getClass()
										  .getClassLoader()
										  .getResourceAsStream(temp);
			
			try {
					picture = IOUtils.toByteArray(inputStream);
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			employee1.setPicture(picture);
				
				employeeDao.save(employee1);
		  }
	      

		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  
	   } catch (Exception e) {
		   return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	   } 
		  
	}
	
	
	
	@GetMapping("/downloadPicture/{id}")
	public ResponseEntity<Resource> getEmployeePhoto(@PathVariable(name = "id") int id) {
	Employee employee1 = null;	

	Optional<Employee > employee  = employeeDao.findById(id);
			 
			  if (employee.isPresent()) {
				  employee1 = employee.get();
			  }
	     


	byte[] pictureByte = employee1.getPicture();
	    InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pictureByte));
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Disposition", String.format("attachment; filename=Employee"));    
	    return ResponseEntity.ok()
				             .headers(headers)
				             .contentLength(pictureByte.length)
				             .contentType(MediaType.IMAGE_JPEG)
				             .body(resource);		  
	}

}
