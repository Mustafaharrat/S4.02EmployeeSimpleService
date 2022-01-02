package employee.entity;

public enum EnumPosition {
	
	Director("Director",6500),Manager("Manager",4000),Administrative("Administrative",1800),Accountant("Accountant",2900);
	
	private String namePosition;
	private double salary;
	
	private EnumPosition() {}
	
	private EnumPosition(String namePosition,double salary) {
		
		this.namePosition=namePosition;
		this.salary=salary;
	}
	
	public String getNamePosition() {
		return namePosition;
	}

	public void setNamePosition(String namePosition) {
		this.namePosition = namePosition;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
	
	
	
	
}


