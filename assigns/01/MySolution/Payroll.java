
public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    public Payroll() {
        people = new Employee[INITIAL_MAXIMUM_SIZE];
        maximum_size = INITIAL_MAXIMUM_SIZE;
        current_size = 0;
    }
    
    public int size() {
        return current_size;
    }

    public void print() {
        for (int i = 0; i < current_size; i++) {
            System.out.println("Name: " + people[i].name + "ID: " + people[i].ID + "Salary: " + people[i].salary);
        }
    }

    public void add_employee(Employee newbie) {
	    if (current_size < maximum_size) {
            people[current_size] = newbie;
        }
        else {
            Employee[] modifiedPeople = new Employee[current_size + 1];
            for (int i = 0; i < current_size; i++) {
                modifiedPeople[i] = people[i];
            }
            current_size ++;
            modifiedPeople[current_size] = newbie;
            people = modifiedPeople;
        }
    }

    public void remove_employee(int i) throws EmployeeIndexException {
        if (i < 0 || i >= current_size) {
            throw (new EmployeeIndexException());
        }

        people[i] = null;
        for (int j = i; j < current_size; j++) {
            people[j] = people[j + 1];
        }
        current_size --;
    }
    
    public int find_employee(String name) throws EmployeeNotFoundException {
        for (int i = 0; i < current_size; i++) {
            if (people[i].name.equals(name)) {
                return i;
            }
        }
        throw (new EmployeeNotFoundException());
    }

    public void add_payroll(Payroll source) {
        Employee[] modifiedPeople = new Employee[current_size + source.current_size];
        for (int i = 0; i < current_size; i++) {
            modifiedPeople[i] = people[i];
        }
        for (int i = 0; i < source.current_size; i++) {
            modifiedPeople[current_size + i] = source.people[i];
        }
        current_size += source.current_size;
        if (maximum_size < current_size) {
            maximum_size = current_size;
        }
    }

    public void copy_payroll(Payroll source) {
        current_size = 0;
        add_payroll(source);
    }

    private Employee people[];
    private int maximum_size;
    private int current_size;
}