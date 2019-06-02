import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class EmployeesManager {
    private List<EmployeeData> employeesData = new ArrayList<>();
    private Set<Employee> employees = new HashSet<>();

    public EmployeesManager(){
        loadEmployeesData("test");
    }

    // In case you want to test different files
    public EmployeesManager(String filePath) {
        loadEmployeesData(filePath);
    }


    private void loadEmployeesData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String input;
            while ((input = reader.readLine()) != null) {
                String[] data = input.split(", ");
                int employeeID = Integer.parseInt(data[0]);
                int projectID = Integer.parseInt(data[1]);
                LocalDate dateFrom = LocalDate.parse(data[2]);
                LocalDate dateTo;
                // If dateTo is null get the today's date
                if (data[3].equals("NULL")) {
                    dateTo = LocalDate.now();
                } else {
                    dateTo = LocalDate.parse(data[3]);
                }
                // Add employee id, project id and how many days he/she worked on the project (inclusive)
                employeesData.add(new EmployeeData(employeeID, projectID,
                        ChronoUnit.DAYS.between(dateFrom, dateTo) + 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillEmployeesSet() {
        // Store employees data in Set to get each employee with no duplicates.
        for(EmployeeData employeeData: employeesData) {
            employees.add(new Employee(employeeData.getEmployeeID()));
        }

        // Adding projects and days for each employee "workTimeOnProjects" map.
        for(Employee employee : employees) {
            for(EmployeeData employeeData : employeesData) {
                if(employeeData.getEmployeeID() == employee.getId()) { // If id matches
                    int projectId = employeeData.getProjectID();
                    long workDays;
                    // If project exist sum the days
                    if(employee.getWorkTimeOnProjects().containsKey(projectId)) {
                        workDays = employee.getWorkTimeOnProjects().get(projectId);
                        workDays += employeeData.getDaysWorked();
                        employee.getWorkTimeOnProjects().put(projectId, workDays);
                    } else {    // if project doesn't exist create it
                        workDays = employeeData.getDaysWorked();
                        employee.getWorkTimeOnProjects().put(projectId, workDays);
                    }
                }
            }
        }
    }

    // This class will be used to store the partnerships
    private class Partnership {
        private int employeeOne;
        private int employeeTwo;

        private Partnership(int employeeOne, int employeeTwo) {
            this.employeeOne = employeeOne;
            this.employeeTwo = employeeTwo;
        }

        private int getEmployeeOne() {
            return employeeOne;
        }

        private int getEmployeeTwo() {
            return employeeTwo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Partnership that = (Partnership) o;
            return employeeOne == that.employeeOne &&
                    employeeTwo == that.employeeTwo;
        }

        @Override
        public int hashCode() {
            return Objects.hash(employeeOne, employeeTwo);
        }
    }

    public String getLongestPartnership() {
        fillEmployeesSet();
        List<Employee> employeeList = new ArrayList<>(employees);
        Map<Partnership, Long> partnerships = new HashMap<>();
        // Get all combinations of partnerships
        for(int i = 0; i < employeeList.size(); i++) {
            for(int b = i + 1; b < employeeList.size(); b++) {
                Employee one = employeeList.get(i);
                Employee two = employeeList.get(b);
                // Loop all the projects
                for(Map.Entry<Integer, Long> oneProject : one.getWorkTimeOnProjects().entrySet()) {
                    // If they worked on same project
                    if(two.getWorkTimeOnProjects().containsKey(oneProject.getKey())) {
                        Partnership partnership = new Partnership(one.getId(), two.getId());
                        long totalDays;
                        long oneDays = oneProject.getValue();
                        long twoDays = two.getWorkTimeOnProjects().get(oneProject.getKey());

                        // If partnership is existing add the days to the value
                        // and update the map
                        if(partnerships.containsKey(partnership)) {
                            totalDays = partnerships.get(partnership);
                            totalDays += oneDays > twoDays ? twoDays : oneDays;
                            partnerships.put(partnership, totalDays);
                        } else { // If partnership was not existing
                            totalDays = oneDays > twoDays ? twoDays : oneDays;
                            partnerships.put(partnership, totalDays);
                        }
                    }
                }
            }
        }

        Partnership longestPartnership = null;
        long max = Long.MIN_VALUE;
        // Find the longest partnership
        for(Map.Entry<Partnership, Long> partnership : partnerships.entrySet()) {
            if(partnership.getValue() > max) {
                longestPartnership = partnership.getKey();
                max = partnership.getValue();
            }
        }

        if(longestPartnership != null) {
            return "The longest partnership is between " + longestPartnership.getEmployeeOne()
                    + " and " + longestPartnership.getEmployeeTwo() + " with total : "
                    + max + " days.";
        }

        return null;
    }
}