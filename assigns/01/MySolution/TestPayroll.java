public class TestPayroll {

    public static void main(String[] args) {
        Payroll first = new Payroll();
        Payroll second = new Payroll();

        Employee e1 = new Employee();
        e1.name = "Emily";
        e1.ID = 101;
        e1.salary = 60000;

        Employee e2 = new Employee();
        e2.name = "Michael";
        e2.ID = 102;
        e2.salary = 65000;

        Employee e3 = new Employee();
        e3.name = "Sarah";
        e3.ID = 103;
        e3.salary = 70000;

        Employee e4 = new Employee();
        e4.name = "David";
        e4.ID = 104;
        e4.salary = 68000;

        Employee e5 = new Employee();
        e5.name = "Jessica";
        e5.ID = 105;
        e5.salary = 72000;

        first.add_employee(e1);
        first.add_employee(e2);
        first.add_employee(e3);

        second.add_employee(e4);
        second.add_employee(e5);

        first.print();
        System.out.println("Size of first: " + first.size());   // Output: 3
        System.out.println();

        second.print();
        System.out.println("Size of second: " + second.size()); // Output: 2
        System.out.println();

        // Should find the employee e3
        try {
            int index = first.find_employee(e3.name);
            System.out.println("Found at index: " + index); // Output: Found at index: 2
        } catch (EmployeeNotFoundException e) {
            System.out.println("Employee not found");
        }

        // Should catch exception for a nonexistent employee
        try {
            first.find_employee("Nonexistent");
        } catch (EmployeeNotFoundException e) {
            System.out.println("Correctly caught exception for nonexistent employee");
        }

        // Should remove the employee at index 0
        try {
            first.remove_employee(0);
            System.out.println("Removed employee at index 0");
        } catch (EmployeeIndexException e) {
            System.out.println("Failed to remove employee at index 0");
        }

        // Should catch exception for invalid index
        try {
            first.remove_employee(10);
        } catch (EmployeeIndexException e) {
            System.out.println("Correctly caught exception for invalid remove index");
        }

        first.print();
        System.out.println("Size after removal: " + first.size()); // Output: 2
        System.out.println();

        first.add_payroll(second);
        first.print();
        System.out.println("Size after add_payroll: " + first.size()); // Output: 4
        System.out.println();

        first.copy_payroll(second);
        first.print();
        System.out.println("Size after copy_payroll: " + first.size()); // Output: 2
    }
}
