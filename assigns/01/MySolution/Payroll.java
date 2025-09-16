
public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    public Payroll() {
        maximum_size = INITIAL_MAXIMUM_SIZE / 2;
        current_size = 0;
        people = new Employee[0];
    }
    
    public int size() {
        return current_size;
    }

    public void print() {
        for (Employee person : people) {
            System.out.println(person.name + " " + person.ID);
        }
    }

    public void add_employee(Employee newbie) {
	/* your code */
    }

    public void remove_employee(int i) throws EmployeeIndexException {
	/* your code */
    }
    
    public int find_employee(String name) throws EmployeeNotFoundException {
        for (int i = 0; i < current_size; i++) {
            if (people[i].name.equals(name)) {
                return i;
            }
        }
    }

    public void add_payroll(Payroll source) {
	/* your code */
    }

    public void copy_payroll(Payroll source) {
        this.maximum_size = source.maximum_size;
        this.current_size = source.current_size;
        for (Employee person : source.people) {
            
        }
    }

    private Employee people[];
    private int maximum_size;
    private int current_size;
}