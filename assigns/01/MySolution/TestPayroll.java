public class TestPayroll {
    public static void main(String[] args) {
        Payroll first = new Payroll();
        Payroll second = new Payroll();

        add_employees(first, 3);
        add_employees(second, 2);


        first.print();
        System.out.println();
        second.print();
        System.out.println();
        System.out.println();

        first.add_payroll(second);
        first.print();
        System.out.println();
        System.out.println();

        first.copy_payroll(second);
        first.print();

    }
    public static void add_employees(Payroll payroll, int num) {
        String[] names = {"Emily", "Michael", "Sarah", "David", "Jessica", "James", "Laura", "Robert", "Anna", "Daniel"};
        for (int i = 1; i <= num; i++) {
            Employee person = new Employee();
            person.name = names[(int) (Math.random() * 10)];
            person.ID = (int) (Math.random() * 1000);
            person.salary = 50000 + ((int) (100 * (Math.random() * 3000) - 1500) / 100.0);
            payroll.add_employee(person);
        }
    }
}
