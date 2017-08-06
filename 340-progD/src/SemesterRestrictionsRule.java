/**
 * Enforces that ICS 490 must be taken in a fall/spring semester, that 492 must be taken in a summer semester, and that 499 must be taken during the 6th (final) semester.
 * !This assumes that the tested course schedule is composed of six semesters.!
 * (Is this a strategy pattern? I'm not even really sure.)
 */
public class SemesterRestrictionsRule implements PrereqRule {
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
        return !(course.getDepartment().getName().equals("ICS") && course.getCode() == 490 && semesterNum % 3 == 0)
                && !(course.getDepartment().getName().equals("ICS") && course.getCode() == 492 && semesterNum % 3 != 0)
                && !(course.getDepartment().getName().equals("ICS") && course.getCode() == 499 && semesterNum != 6);
    }
}
