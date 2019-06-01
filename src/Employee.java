import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Employee {
    private int id;
    private Map<Integer, Long> workTimeOnProjects;

    public Employee(int id) {
        this.id = id;
        workTimeOnProjects = new HashMap<>();
    }

    public Map<Integer, Long> getWorkTimeOnProjects() {
        return workTimeOnProjects;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() { // Test purposes
        return "Employee{" +
                "id=" + id +
                ", workTimeOnProjects=" + workTimeOnProjects +
                '}';
    }

    // equals and hashCode are needed to add unique employees to the Set in EmployeesManager
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(workTimeOnProjects, employee.workTimeOnProjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workTimeOnProjects);
    }
}
