/**
 * Models a single department, e.g. ICS.
 */
public class Department {
    /**
     * The department's name, e.g. ICS.
     */
    String name;

    /**
     * Create a new Department.
     * @param name {@link Department#name}
     */
    public Department(String name) {
        this.name = name;
    }

    /**
     * @return {@link Department#name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return A string representation of the department, composed of its name.
     */
    public String toString() {
        return name;
    }
}
