import java.io.File;
import java.io.PrintStream;
import java.util.*;

/**
 * Simulation of local search over a set of rules.
 * Note that I never fully tested with anything other than COURSES_PER_SEMESTER = 3, nor with any different number of courses. While I think all the arithmetic is right, I could have an off-by-one error here-or-there that will manifest with different courses or courses per semester.
 * (Also, last minute edits were done outside of my usual development environment. Apologies for occasionally incosistent indentation.)
 *
 * @author Joseph T. Parsons
 */
public class ProgD {
    /**
     * The number of courses that are allowed in a given semester. We use this for arithmetic operations, mainly.
     * For this assignment, I have been able to get values of 2 and 3 working without anything special.
     * Getting 4 and 5-per-semester working requires adding a few more "empty" courses in order to find a satisfying assignment.
     * (I haven't been able to get 1 working with the SemesterRestrictionsRule. I think it's just too random in that case, since 499 must be in a very specific semester.)
     */
    static final int COURSES_PER_SEMESTER = 3;
    
    /**
     * The maximum number of solutions that will be tested in verbose mode.
     * (I don't want to be responsible for any uneccessary harddrive fragmentation if something goes wrong, thank you very much.)
     */
    static final int MAXIMUM_ATTEMPTS = 10000;

    /**
     * Whether to print verbose or summary.
     */
    private static boolean printVerbose = false;

    /**
     * Prompts for output mode and then runs very specific local search simulation.
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
                System.out.println("Using verbose mode. No more than 10,000 attempts to find a solution will be made; if you are running with default parameters, a solution will almost always be found in less than 2,000 attempts. If you are not, we do not want to fill your drive.");
                printVerbose = true;
                break;
            }

            System.out.println("I did not understand that. Please enter 'v' for verbose mode, or 's' for short mode.");
            printModePrompt = UserInterfacePrompts.promptLine("Would you like to print in verbose or short mode? ");
        };


        // Redirect Output to File
        // (Personally, I believe this should be done by the user through use of piping, but I'm just following instructions.)
        try {
            File outFile = new File("ProgD-solution.txt");
            System.out.println("The output will be written to ProgD-solution.txt in your current working directory. Its full path is " + outFile.getAbsolutePath());
            System.setOut(new PrintStream(outFile));
        } catch (Exception e) {
            System.err.println("Unable to open file for writing. Output will be to stdout.");
        }

        // Create a separate department object for every department.
        Department mathDepartment = new Department("Math");
        Department icsDepartment = new Department("ICS");
        Department lsDepartment = new Department("Liberal Studies");
        Department nullDepartment = new Department(""); // Used for dummy courses.


        // Create list of courses. (A hashmap is used to more easily reference each course by its code. Kinda like an associative array in other, typically weaker-typed languages. A tree map is used to get the list of courses as sorted.)
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
        // Note that this could be taken further (for instance, allowing most semesters to have two courses, and only a few to have three), but that gets quite complicated quite quickly.
        for (int i = 0, courseSize = courses.values().size(); i < (COURSES_PER_SEMESTER - courseSize % COURSES_PER_SEMESTER) % COURSES_PER_SEMESTER; i++) {
            courses.put(i, new Course(nullDepartment, 0)); // An "empty" course -- i.e., no course. Fills out last semester.
        }

        // I spoke too soon! Here we take it further, adding more dummy courses (and actually overwriting the previous ones) if we've got large semesters going.
        // This works for COURSES_PER_SEMESTER values of 4 or 5. (I don't believe there exists a solution with six courses at once, so...)
        if (COURSES_PER_SEMESTER > 3) {
            for (int i = 0; i < (COURSES_PER_SEMESTER - 3) * 4; i++) {
                courses.put(i, new Course(nullDepartment, 0)); // An "empty" course -- i.e., no course. Fills out last semester.
            }
        }


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
        prereqs.addRule(new SemesterRestrictionsRule(COURSES_PER_SEMESTER, (int) Math.ceil((double) courseListSorted.size() / COURSES_PER_SEMESTER) - 1)); // Create a semester rule. Second parameter calculates the number of semesters needed to take the current list of courses, assuming COURSES_PER_SEMESTER is the maximum number taken per semester.


        // Randomise the list of courses. (Assignment specified use of Math.random, but Collections.shuffle seems more appropriate here. Also, it's a lot easier.)
        List<Course> courseList = new ArrayList<>(courses.values());
        Collections.shuffle(courseList);


        // Print Course titles as header if in verbose mode
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


        // Find Conflicts, Swap Stuff, and Repeat
        int attempts = 0; // Number of times a solution is tested.
        List<Map.Entry<Integer, Integer>> conflicts; // List of conflicts found on every attempt.
        while ((conflicts = prereqs.findConflicts(courseList, COURSES_PER_SEMESTER)).size() != 0) { // Basically, keep going until no conflicts recorded.
            if (printVerbose && attempts++ > MAXIMUM_ATTEMPTS) { // Stop if too many attempts and we're in verbose mode.
                System.out.println("Maximum attempts reached. In an effort to spare your harddrive, we're going to stop now. No solution has been found.");
                break;
            }
            
            if (printVerbose) {
                coursePrint(courseListSorted, courseList); // Print the assignment of courses that was tested.
            }

            int randomIndex = (int) (Math.random() * conflicts.size()); // Choose one conflict at random.

            Collections.swap(courseList, conflicts.get(randomIndex).getKey(), conflicts.get(randomIndex).getValue()); // Flip the two courses involved in the random conflict. (Under some circumstances, this will be one course involved in a conflict and another chosen at random.)
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
     * Print the semester number assigned to a given course (starting at one, as per assignment example).
     * @param courseListSorted The list of courses in the order that they should be printed.
     * @param courseList The list of courses in semester-assigned order.
     */
    public static void coursePrint(List<Course> courseListSorted, List<Course> courseList) {
        String print = "";

        for (int i = 0; i < courseListSorted.size(); i++) {
            print += ((courseList.indexOf(courseListSorted.get(i)) / COURSES_PER_SEMESTER) + 1) + "\t";
        }

        System.out.println(print);
    }


    /**
     * Print a semester assignment for each course; semesters are line-separator.
     *
     * @param coursesList The list of courses to print. They should be ordered by the semester they are in.
     */
    public static void printSemesterAssignment(List<Course> coursesList) {
        String print = "";


        // Sort each semester individually for printing
        // (As an aside, there's been a lot more arithmetic on this assignment than I'd expected.)
        for (int i = 0; i < coursesList.size() / COURSES_PER_SEMESTER; i++) {
            Collections.sort(coursesList.subList(i * COURSES_PER_SEMESTER, (((i + 1) * COURSES_PER_SEMESTER) > coursesList.size() ? coursesList.size() : ((i + 1) * COURSES_PER_SEMESTER)))); // For instance, this will sort the sublist of [0, 2] elements, then [3, 5], [6, 8], and so-on.
        }


        // Print all courses.
        for (int i = 0; i < coursesList.size(); i++) { // For every course...
            if (i % COURSES_PER_SEMESTER == 0) { // If this is the first course of the semester...
                switch ((i / COURSES_PER_SEMESTER) % 3) { // Get the semester season
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