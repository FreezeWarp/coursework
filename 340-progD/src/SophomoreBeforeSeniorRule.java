/**
 * This enforces that any 400- level course (of any department) must be taken after *all* sub-300 level courses.
 * (Is this a strategy pattern? I'm not even really sure.)
 */
public class SophomoreBeforeSeniorRule implements PrereqRule {
    /**
     * {@link PrereqRule#checkForPrereq(Course, Course)}
     */
    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        return !((courseAfter.getCode() >= 100 && courseAfter.getCode() < 300) && (courseBefore.getCode() >= 400 && courseBefore.getCode() < 500));
    }

    /**
     * {@link PrereqRule#checkForPrereqOrDuring(Course, Course)}
     */
    public boolean checkForPrereqOrDuring(Course courseBefore, Course courseAfterOrDuring) {
        return checkForPrereq(courseBefore, courseAfterOrDuring);
    }

    /**
     * {@link PrereqRule#checkForSemester(Course, int)}
     */
    public boolean checkForSemester(Course course, int semesterNum) {
        return true;
    }
}
