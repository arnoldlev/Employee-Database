import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Phase3 {
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		String file = args[0];
		System.out.println("Welcome to Employee Management System (EMS)");
		
		Map<Integer, Employee> db = load(file);
		
		menu(input, db);
		
		save(file, db);
	}
	
	public static void save(String file, Map<Integer, Employee> db) throws IOException {
		FileWriter wr = new FileWriter(file);
		PrintWriter write = new PrintWriter(wr);
		for (Employee s : db.values()) {
			write.println(s.getEID() + "," + s.getName() + "," + s.getDepartment() + "," + s.getTitle() + "," + s.getSalary());
		}
		write.close();
		wr.close();
	}
	
	public static Map<Integer, Employee> load(String file) throws IOException {
		Map<Integer, Employee> db = new HashMap<>();
		File cvs = new File(file);
		if (cvs.exists()) {
			Scanner scan = new Scanner(cvs);
			while (scan.hasNext()) {
				String[] employee = scan.nextLine().split(",");
				Employee e = new Employee();
				e.setEID(Integer.valueOf(employee[0]));
				e.setName(employee[1]);
				e.setDepartment(employee[2]);
				e.setTitle(employee[3]);
				e.setSalary(Double.valueOf(employee[4]));
				
				db.put(Integer.valueOf(employee[0]), e);
			}
			scan.close();
		} else {
			System.out.println("Database input file " + file + " not found.");
			System.out.println("Creating an empty database.");
		}
		return db;
	}
	
	public static void menu(Scanner scan, Map<Integer, Employee> db) {
		int selection = 0;
		do {
			System.out.println("Main Menu:");
			System.out.println("1.	Add an Employee");
			System.out.println("2.	Find an Employee (By Employee ID)");
			System.out.println("3.	Find an Employee (By Name)");
			System.out.println("4.	Delete an Employee");
			System.out.println("5.	Display Statistics");
			System.out.println("6.	Display All Employees");
			System.out.println("7.	Exit");
			System.out.println("Enter you selection (1..7):");
			selection = scan.nextInt();
			
			scan.nextLine();
			
			if (selection == 1) {
				createEmployee(scan, db);
			} else if (selection == 2) {
				findByEID(scan, db);
			} else if (selection == 3) {
				findByName(scan, db);
			} else if (selection == 4) {
				deleteEmployee(scan, db);
			} else if (selection == 5) {
				displayStatistics(db);
			} else if (selection == 6) {
				displayStats(db);
			} else if (selection == 7) {
				System.out.println("Thank you for using Employee Management System (EMS)");
			} else {
				System.out.println("You entered an invalid selection! Try again");
				selection = scan.nextInt();
			}
			
		} while (selection != 7);
	}
	
	public static boolean createEmployee(Scanner scan, Map<Integer, Employee> db) {
		String ID, name, department, title;
		double salary;
		int eid;
		System.out.println("Enter an employee ID or QUIT to stop:");
		ID = scan.nextLine();
		if ("QUIT".equalsIgnoreCase(ID)) {
			return true;
		}
		
		eid = Integer.parseInt(ID);
		
		while (db.containsKey(eid)) {
			System.out.println("EID already exists! Enter an Employee ID or QUIT to stop:");
			ID = scan.nextLine();
			if ("QUIT".equalsIgnoreCase(ID)) {
				return true;
			}
			eid = Integer.parseInt(ID);
		}
		
		System.out.println("Enter employee name:");
		name = scan.nextLine();
			
		System.out.println("Enter employee department:");
		department = scan.nextLine();
			
		System.out.println("Enter employee title:");
		title = scan.nextLine();
			
		System.out.println("Enter employee salary:");
		salary = scan.nextDouble();
			
		while (salary <= 0) {
			System.out.println("Please enter a positive salary: ");
			salary = scan.nextDouble();	
		}	
		scan.nextLine(); 
			
		db.put(eid, new Employee(eid, name, department, title, salary));
		return true;
	}
	
	public static void findByName(Scanner scan, Map<Integer,Employee> db) {
		DecimalFormat format = new DecimalFormat("#,###,###.00");
		String name;
		System.out.println("Enter an employee name or QUIT to stop:");
		name = scan.nextLine();
		if ("QUIT".equalsIgnoreCase(name)) {
			return;
		}
		
		ArrayList<String> names = new ArrayList<String>();
		for (Employee s : db.values()) {
			if (name.equalsIgnoreCase(s.getName())) {
				names.add(name);
			}
		}
		
		System.out.println("Found " + names.size() + (names.size() == 1 ? " employee " : " employees ") + "with that name.");
		for (Employee s : db.values()) {
			if (names.contains(s.getName())) {
				System.out.println("Employee ID: " + s.getEID());
				System.out.println("\tName: " + s.getName());
				System.out.println("\tDepartment: " + s.getDepartment());
				System.out.println("\tTitle: " + s.getTitle());
				System.out.println("\tSalary: " + format.format(s.getSalary()));
			}
		}
	}
	
	public static void findByEID(Scanner scan, Map<Integer, Employee> db) {
		DecimalFormat format = new DecimalFormat("#,###,###.00");
		String ID;
		int eid;
		System.out.println("Enter an employee ID or QUIT to stop:");
		ID = scan.nextLine();
		
		if ("QUIT".equalsIgnoreCase(ID)) {
			return;
		}
		
		eid = Integer.parseInt(ID);
		
		while (!db.containsKey(eid)) {
			System.out.println("Employee ID: " + eid + " does not exist!");
			System.out.println("Enter an Employee ID or QUIT to stop:");
			ID = scan.nextLine();
			if ("QUIT".equalsIgnoreCase(ID)) {
				return;
			}
			eid = Integer.parseInt(ID);
		}
		
		System.out.println("Employee ID: " + eid);
		System.out.println("\tName: " + db.get(eid).getName());
		System.out.println("\tDepartment: " + db.get(eid).getDepartment());
		System.out.println("\tTitle: " + db.get(eid).getTitle());
		System.out.println("\tSalary: " + format.format(db.get(eid).getSalary()));
	}
	
	public static void displayStatistics(Map<Integer, Employee> db) {
		Map<String, Map<String, Object>> departments = new HashMap<>();
		for (Employee s : db.values()) {
			if (departments.containsKey(s.getDepartment())) {
				Map<String, Object> information = departments.get(s.getDepartment());
				information.put("Count", (Integer) information.get("Count") + 1);
				
				Double max = (Double) information.get("Max"); 
				information.put("Max", (s.getSalary() > max ?  s.getSalary() : max));
				
				Double min = (Double) information.get("Min"); 
				information.put("Min", (min > s.getSalary() ? s.getSalary() : min));
				
				information.put("Average", (Double) information.get("Average") + s.getSalary());
				
				departments.put(s.getDepartment(), information);
			} else {
				Map<String, Object> information = new HashMap<>();
				information.put("Count", 1);
				information.put("Max", s.getSalary());
				information.put("Min", s.getSalary());
				information.put("Average", s.getSalary());
				departments.put(s.getDepartment(), information);
			}
		}
		
		if (departments.isEmpty()) {
			System.out.println("There are no departments in the database.");
			System.out.println("Employee database is empty.");
			return;
		}
		
		List<String> list = new ArrayList<String>(departments.keySet()); 
		Collections.sort(list);
		
		
		System.out.println("Department Statistics:");
		for (String dep : list) {
			int amount = (int) departments.get(dep).get("Count") ;
			System.out.println("\tDepartment: " + dep + " - " + amount + (amount == 1 ? " employee" : " employees"));
			System.out.printf("\t\tMaximum Salary: $ %9.2f\n", departments.get(dep).get("Max"));
			System.out.printf("\t\tMinimum Salary: $ %9.2f\n", departments.get(dep).get("Min"));
			System.out.printf("\t\tAverage Salary: $ %9.2f\n", (double) departments.get(dep).get("Average") / amount);
		}
		
		System.out.println("There " + (list.size() == 1 ? "is " : "are ") + list.size() + (list.size() == 1 ? " department " : " departments ") + "in the database.");
		System.out.println("There " + (db.size() == 1 ? "is " : "are ") + db.size() + (db.size() == 1 ? " employee " : " employees ") + "in the database.");
	}
	
	
	public static void deleteEmployee(Scanner scan, Map<Integer, Employee> db) {
		String ID; 
		char selection;
		int eid;
		System.out.println("Enter an employee ID or QUIT to stop:");
		ID = scan.nextLine();
		if ("QUIT".equalsIgnoreCase(ID)) {
			return;
		}
		eid = Integer.parseInt(ID);
		
		while (!db.containsKey(eid)) {
			System.out.println("Employee ID: " + eid + " does not exist!");
			System.out.println("Enter an Employee ID or QUIT to stop:");
			ID = scan.nextLine();
			if ("QUIT".equalsIgnoreCase(ID)) {
				return;
			}
			eid = Integer.parseInt(ID);
		}
		
		System.out.println("Employee ID: " + eid + " will be deleted from the database. Are you sure (y/n)?");
		selection = scan.nextLine().charAt(0);
		if (Character.toLowerCase(selection) == 'y') {
			db.remove(eid);
			System.out.println("Employee ID: " + eid + " was deleted from the database.");
		} else {
			System.out.println("Employee deletion was cancelled.");
		}
	}
	
	public static void displayStats(Map<Integer, Employee> db) {
		DecimalFormat format = new DecimalFormat("#,###,###.00");
		for (int id : db.keySet()) {
			System.out.println("Employee ID: " + id);
			System.out.println("\tName: " + db.get(id).getName());
			System.out.println("\tDepartment: " + db.get(id).getDepartment());
			System.out.println("\tTitle: " + db.get(id).getTitle());
			System.out.println("\tSalary: " + format.format(db.get(id).getSalary()));
		}
		int size = db.size();
		if (size > 0) {
			System.out.println("There " + (size == 1 ? "is " : "are ") + db.size() + (size == 1 ? " employee " : " employees ") + "in the database.");
		} else {
			System.out.println("Employee database is empty.");
		}
		
	}

}
