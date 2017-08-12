/**
 * Enforces that ICS 490 must be taken in a fall/spring semester, that 492 must be taken in a summer semester, and that 499 must be taken during the 6th (final) semester.
 * (Is this a strategy pattern? I'm not even really sure.)
 */
public class SemesterRestrictionsRule implements PrereqRule {
    /**
     * The number of course per semester.
     */
    private int coursesPerSemester;

    /**
     * The last semester, numerically, indexed from 0.
     */
    private int lastSemester;


    /**
     * Create a new semester restriction rule, using info for the number of courses per semester and the last semester index.
     * @param coursesPerSemester {@link SemesterRestrictionsRule#coursesPerSemester}
     * @param lastSemester {@link SemesterRestrictionsRule#lastSemester}
     */
    public SemesterRestrictionsRule(int coursesPerSemester, int lastSemester) {
        this.coursesPerSemester = coursesPerSemester;
        this.lastSemester = lastSemester;
    }


    /**
     * {@link PrereqRule#checkForPrereq(Course, Course)}
     */
    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        return true;
    }

    /**
     * {@link PrereqRule#checkForPrereqDuring(Course, Course)}
     */
    public boolean checkForPrereqDuring(Course firstCourse, Course secondCourse) {
        return true;
    }

    /**
     * {@link PrereqRule#checkForSemester(Course, int)}
     */
    public boolean checkForSemester(Course course, int semesterNum) {
        return !(course.getDepartment().getName().equals("ICS") && course.getCode() == 490 && semesterNum % 3 == 0) // 490 in summer
                && !(course.getDepartment().getName().equals("ICS") && course.getCode() == 492 && semesterNum % 3 != 0) // 492 not in summer
                && !(course.getDepartment().getName().equals("ICS") && course.getCode() == 499 && semesterNum != lastSemester); // 499 last semester
    }
}
