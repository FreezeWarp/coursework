import java.util.*;

/**
 * Simulation of local search over a set of rules.
 * Note that I never tested with anything other than COURSES_PER_SEMESTER = 3, nor with any different number of courses. While I think all the arithmetic is right, I could have an off-by-one error here-or-there that will manifest with different courses or courses per semester. (Indeed, while it's possible a solution doesn't exist with COURSES_PER_SEMESTER > 3, I haven't been able to find one for =1 or =2 either. So there's probably a bug somewhere.)
 *
 * @author Joseph T. Parsons
 */
public class ProgD {
    /**
     * The number of courses that are allowed in a given semester. We use this for arithmetic operations, mainly.
     */
    static final int COURSES_PER_SEMESTER = 3;

    /**
     * Whether to print verbose or summary.
     */
    private static boolean printVerbose = false;

    /**
     * Passes to ProgBGraphical, which launches file opener.
     */
    public static void main(String[] args) {
        // Ask for print mode.
        String printModePrompt = UserInterfacePrompts.promptLine("Would you like to print in verbose or short mode? ");

        while (true) {
            if (printModePrompt.toLowerCase().startsWith("s")) {
                System.out.println("Using short mode.");
                printVerbose = false;
                break;
            }
            else if (printModePrompt.toLowerCase().startsWith("v")) {
                System.out.println("Using verbose mode.");
                printVerbose = true;
                break;
            }

            System.out.println("I did not understand that. Please enter 'v' for verbose mode, or 's' for short mode.");
            printModePrompt = UserInterfacePrompts.promptLine("Would you like to print in verbose or short mode? ");
        }


        // Create a separate department object for every department.
        Department mathDepartment = new Department("Math");
        Department icsDepartment = new Department("ICS");
        Department lsDepartment = new Department("Liberal Studies");
        Department nullDepartment = new Department(""); // Used for dummy courses.


        // Create list of courses. (A hashmap is used to more easily specify each course by its code.)
        Map<Integer, Course> courses = new TreeMap<>();
        courses.put(120, new Course(mathDepartment, 120));
        courses.put(210, new Course(mathDepartment, 210));
        courses.put(215, new Course(mathDepartment, 215));
        courses.put(140, new Course(icsDepartment, 140));
        courses.put(141, new Course(icsDepartment, 141));
        courses.put(232, new Course(icsDepartment, 232));
        courses.put(240, new Course(icsDepartment, 240));
        courses.put(311, new Course(icsDepartment, 311));
        courses.put(340, new Course(icsDepartment, 340));
        courses.put(365, new Course(icsDepartment, 365));
        courses.put(372, new Course(icsDepartment, 372));
        courses.put(440, new Course(icsDepartment, 440));
        courses.put(460, new Course(icsDepartment, 460));
        courses.put(462, new Course(icsDepartment, 462));
        courses.put(490, new Course(icsDepartment, 490));
        courses.put(492, new Course(icsDepartment, 492));
        courses.put(499, new Course(icsDepartment, 499));
        courses.put(998, new Course(lsDepartment, 998));
        courses.put(999, new Course(lsDepartment, 999));


        // Add two dummy courses which allow solutions to have any semester containing fewer than three courses (instead of defaulting to just the last).
        // Obviously, we're doing this manually right now (because it's easier); but a simple loop could be used to manually fill in the needed number of courses.
        courses.put(0, new Course(nullDepartment, 0)); // An "empty" course -- i.e., no course. Fills out last semester.
        courses.put(1, new Course(nullDepartment, 0)); // Another "empty" course. Fills out last semester.


        // Now add the courses by order of their values to the "sorted" list. The sorted list is only used for printing.
        List<Course> courseListSorted = new ArrayList<>(courses.values());


        // Create prereqs object.
        Prereqs prereqs = new Prereqs();
        prereqs.addPrereq(courses.get(120), courses.get(210));
        prereqs.addPrereq(courses.get(120), courses.get(215));
        prereqs.addPrereqDuring(courses.get(120), courses.get(140));
        prereqs.addPrereqDuring(courses.get(215), courses.get(141));
        prereqs.addPrereq(courses.get(140), courses.get(141));
        prereqs.addPrereq(courses.get(141), courses.get(232));
        prereqs.addPrereq(courses.get(141), courses.get(240));
        prereqs.addPrereq(courses.get(141), courses.get(311));
        prereqs.addPrereq(courses.get(240), courses.get(311));
        prereqs.addPrereq(courses.get(240), courses.get(340));
        prereqs.addPrereq(courses.get(240), courses.get(365));
        prereqs.addPrereq(courses.get(240), courses.get(372));
        prereqs.addPrereq(courses.get(340), courses.get(440));
        prereqs.addPrereq(courses.get(372), courses.get(499));


        // Create our special rules.
        prereqs.addRule(new FreshmanSophomoreBeforeSeniorRule());
        prereqs.addRule(new SemesterRestrictionsRule(COURSES_PER_SEMESTER, ((courseListSorted.size() + (courseListSorted.size() % COURSES_PER_SEMESTER)) / COURSES_PER_SEMESTER) - 1)); // ...I'm tired. There's probably a much simpler expression that equates to this. Basically, calculates the last semester given our number of courses and the number of courses per semester.


        // Randomise the list of courses.
        List<Course> courseList = new ArrayList<>(courses.values());
        Collections.shuffle(courseList);


        // Find Conflicts, Swap Stuff, and Repeat
        if (printVerbose) {
            for (int i = 0; i < courseListSorted.size(); i++) {
                System.out.print(courseListSorted.get(i) + "\t");
            }
            System.out.println();
            for (int i = 0; i < courseListSorted.size(); i++) {
                System.out.print("---\t");
            }
            System.out.println();
        }

        List<Map.Entry<Integer, Integer>> conflicts;
        while ((conflicts = prereqs.findConflicts(courseList, COURSES_PER_SEMESTER)).size() != 0) { // Basically, keep going until no conflicts recorded.
            if (printVerbose) {
                coursePrint(courseListSorted, courseList); // Print the assignment of courses that was tested.
            }

            int randomIndex = (int) (Math.random() * conflicts.size()); // Choose one conflict at random.

            Collections.swap(courseList, conflicts.get(randomIndex).getKey(), conflicts.get(randomIndex).getValue()); // Flip the two courses involved in the random conflict.
        }


        // Once we've exited the loop, and assignment will have been found.
        System.out.println("Assignment found.");


        // Sort each semester individually for printing
        // (As an aside, there's been a lot more arithmetic on this assignment than I'd expected.)
        for (int i = 0; i < courseList.size() / COURSES_PER_SEMESTER; i++) {
            Collections.sort(courseList.subList(i * COURSES_PER_SEMESTER, (((i + 1) * COURSES_PER_SEMESTER) > courseList.size() ? courseList.size() : ((i + 1) * COURSES_PER_SEMESTER))));
        }


        // Print found assignment.
        if (printVerbose) {
            coursePrint(courseListSorted, courseList);
        }
        else {
            printSemesterAssignment(courseList);
        }
    }


    /**
     * Print the numerical semester assigned to a given course (starting at one),
     * @param courseListSorted
     * @param courseList
     */
    public static void coursePrint(List<Course> courseListSorted, List<Course> courseList) {
        String print = "";

        for (int i = 0; i < courseListSorted.size(); i++) {
            print += ((courseList.indexOf(courseListSorted.get(i)) / COURSES_PER_SEMESTER) + 1) + "\t";
        }

        System.out.println(print);
    }


    public static void printSemesterAssignment(List<Course> coursesList) {
        String print = "";

        for (int i = 0; i < coursesList.size(); i++) { // For every course...
            if (i % COURSES_PER_SEMESTER == 0) { // If this is the first course of the semester...
                switch ((i / COURSES_PER_SEMESTER) % COURSES_PER_SEMESTER) { // Get the semester season
                    case 0: print += "Summer: "; break;
                    case 1: print += "Autumn: "; break;
                    case 2: print += "Spring: "; break;
                }
            }


            if (coursesList.get(i).getCode() != 0) { // Don't print the empty courses.
                print += coursesList.get(i);
            }


            if (i % COURSES_PER_SEMESTER == COURSES_PER_SEMESTER - 1) { // If this is the last course of the semester...
                print += System.getProperty("line.separator");
            }
            else if (coursesList.get(i).getCode() != 0) { // Don't print the empty courses.
                print += ", ";
            }
        }

        System.out.println(print);
    }
}