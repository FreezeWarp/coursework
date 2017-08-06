/**
 * This enforces that any 400- level course (of any department) must be taken after *all* sub-300 level courses.
 * (Is this a strategy pattern? I'm not even really sure.)
 */
public class FreshmanSophomoreBeforeSeniorRule implements PrereqRule {
    /**
     * {@link PrereqRule#checkForPrereq(Course, Course)}
     */
    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        return !((courseAfter.getCode() >= 100 && courseAfter.getCode() < 300) && (courseBefore.getCode() >= 400 && courseBefore.getCode() < 500));
    }

    /**
     * {@link PrereqRule#checkForPrereqDuring(Course, Course)}
     */
    public boolean checkForPrereqDuring(Course firstCourse, Course secondCourse) {
        return checkForPrereq(firstCourse, secondCourse) && checkForPrereq(secondCourse, firstCourse);
    }

    /**
     * {@link PrereqRule#checkForSemester(Course, int)}
     */
    public boolean checkForSemester(Course course, int semesterNum) {
        return true;
    }
}
