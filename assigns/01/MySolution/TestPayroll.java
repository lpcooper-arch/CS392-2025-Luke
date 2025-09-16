public class TestPayroll {
    public static void main(String[] args) {
        Payroll first = new Payroll();
        Payroll second = new Payroll();

        add_employees(first, 3);
        add_employees(second, 2);


        first.print();
        second.print();

        first.add_payroll(second);
        first.print();


        first.copy_payroll(second);
        first.print();

    }
    public static void add_employees(Payroll payroll, int num) {
        String[] names = {"Emily", "Michael", "Sarah", "David", "Jessica", "James", "Laura", "Robert", "Anna", "Daniel"};
        for (int i = 1; i < num; i++) {
            Employee person = new Employee();
            person.name = names[(int) (Math.random() * 10)];
            person.ID = (int) (Math.random() * 1000);
            person.salary = 50000 + (Math.random() * 2000) - 1000;
            payroll.add_employee(person);
        }
    }
}
