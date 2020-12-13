public class Employee {
	
	private int eid;
	private String name;
	private String department;
	private String title;
	private double salary;
	
	Employee() {
		
	}
	
	Employee(int id, String n, String dep, String tit, double sal) {
		setEID(id);
		setName(n);
		setDepartment(dep);
		setTitle(tit);
		setSalary(sal);
	}
	
	public final int getEID() {
		return eid;
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getDepartment() {
		return department;
	}
	
	public final String getTitle() {
		return title;
	}
	
	public final double getSalary() {
		return salary;
	}
	
	public void setEID(int id) {
		eid = id;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setDepartment(String depart) {
		department = depart;
	}
	
	public void setTitle(String tit) {
		title = tit;
	}
	
	public boolean setSalary(double sal) {
		if (sal < 0) {
			return false;
		} else {
			salary = sal;
			return true;
		}
	}

}
