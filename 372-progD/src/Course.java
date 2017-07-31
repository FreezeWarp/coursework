/**
 * Created by joseph on 31/07/17.
 */
public class Course {
    Department department;
    int code;

    public Course(Department department, int code) {
        this.department = department;
        this.code = code;
    }

    public Department getDepartment() {
        return department;
    }

    public int getCode() {
        return code;
    }

    public String toString() {
        return department + " " + code;
    }
}
