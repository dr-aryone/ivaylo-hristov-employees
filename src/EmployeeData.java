public class EmployeeData {
    private int employeeID;
    private int projectID;
    private long daysWorked;

    public EmployeeData(int employeeID, int projectID, long daysWorked) {
        this.employeeID = employeeID;
        this.projectID = projectID;
        this.daysWorked = daysWorked;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public int getProjectID() {
        return projectID;
    }

    public long getDaysWorked() {
        return daysWorked;
    }

    // For testing purposes
    @Override
    public String toString() {
        return "EmployeeData{" +
                "employeeID=" + employeeID +
                ", projectID=" + projectID +
                ", daysWorked=" + daysWorked +
                '}';
    }
}
