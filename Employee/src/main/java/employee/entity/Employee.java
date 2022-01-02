package employee.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;



@Entity
@Table(name="Employees")
public class Employee {

	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="lastname")
	private String lastname;
	

	@Column(name="position")
	private EnumPosition enumPosition;
	
	@Column(name="salary")
	private double salary;
	
	@Column(name="namePosition")
	private String position;
	
	@Lob
	@Column(name="picture")
	private byte[] picture;

	
	public Employee() {}
	

	public Employee(String name, String lastname,EnumPosition enumPosition){
		
		this.name=name;
		this.lastname=lastname;
		this.enumPosition=enumPosition;
		this.position=enumPosition.getNamePosition();
		this.salary=enumPosition.getSalary();
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public EnumPosition getEnumPosition() {
		return enumPosition;
	}


	public void setEnumPosition(EnumPosition enumPosition) {
		this.enumPosition = enumPosition;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public byte[] getPicture() {
		return picture;
	}


	public void setPicture(byte[] picture) {
		this.picture = picture;
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", lastname=" + lastname + ", salary=" + salary + ", position="
				+ position + ", picture=" + Arrays.toString(picture) + "]";
	}
	
	
}
