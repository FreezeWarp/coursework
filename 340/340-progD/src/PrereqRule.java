/**
 * A rule that can be used with {@link Prereqs} to specify that a given course cannot be taken before another, or during a given semester.
 */
public interface PrereqRule {
    /**
     * This should be written such that it returns true when courseBefore is allowed to come before courseAfter. It should return false when courseBefore cannot come before courseAfter.
     *
     * @param courseBefore A course that is being taken before courseAfter.
     * @param courseAfter A course that is being taken after courseBefore.
     *
     * @return True if allowed, false otherwise.
     */
    public boolean checkForPrereq(Course courseBefore, Course courseAfter);

    /**
     * This should be written such that it returns true when firstCourse is allowed to come before (or during) secondCourse. It should return false when firstCourse cannot come before secondCourse.
     *
     * @param firstCourse A course that is being taken before secondCourse.
     * @param secondCourse A course that is being taken after (or alongside) firstCourse.
     *
     * @return True if allowed, false otherwise.
     */
    public boolean checkForPrereqDuring(Course firstCourse, Course secondCourse);

    /**
     * This should be written such that it returns true when course is allowed to be taken during semesterNum. It should return false when it is not allowed.
     *
     * @param course The course being taken.
     * @param semesterNum The semester it is being taken.
     *
     * @return True if allowed, false otherwise.
     */
    public boolean checkForSemester(Course course, int semesterNum);
}
