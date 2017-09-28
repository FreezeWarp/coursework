/**
 * Joseph T. Parsons
 * Demonstrates basic usage of command-line arguments and string arrays. Named command-line arguments are beyond me, and I didn't even try making those work.
 *
 * Fun-fact: there are quite a few ways of translating programs in most languages. Usually I just put all the strings in the database, give them names, and then SELECT them from the database.
 * Of-course, this is slow, and isn't really suitable for anything that requires good scalability unless you use a memory table (which isn't always available). On the other hand, it can be nice to be able to perform database operations on the strings.
 * But if there is no need for database operations, I just dump them into a file not unlike this one.
 * This is perfectly suitable in languages with any kind of hash table or associative array, but in C its tricky. Here, I would prefer to use a database class instead.
 * (Additionally, not all languages support database calls, e.g. Javascript. Here, we just dump the strings into a JSON file and call it a day.)
 */

#include <stdio.h>
#include <string.h>

char strings[20][50] = {
    "My name is",
    "My email address is",
    "My home highschool was",
    "My home city was",
    "My current college is",
    "My current year in college is",
    "My current age is",
    "My current age in octal is",
    "My current age in hex is",
};
const int s_NAME = 0;
const int s_EMAIL = 1;
const int s_HS = 2;
const int s_HOMECITY = 3;
const int s_COLLEGENAME = 4;
const int s_COLLEGEYEAR = 5;
const int s_AGE = 6;
const int s_AGEOCT = 7;
const int s_AGEHEX = 8;

int main(int argc, char *argv[])
{
    unsigned int age = 20; // Unsigned because age can't be signed. On one line (in contrast with the book) because it's cleaner.

    printf("%30s: %s\n",   strings[s_NAME],        (argc > 1 ? argv[1] : "Joseph Parsons"));
    printf("%30s: <%s>\n", strings[s_EMAIL],       (argc > 2 ? argv[2] : "josephtparsons@gmail.com"));
    printf("%30s: %s\n",   strings[s_HS],          (argc > 3 ? argv[3] : "Shakopee Highschool"));
    printf("%30s: %s\n",   strings[s_HOMECITY],    (argc > 4 ? argv[4] : "Shakopee, MN"));
    printf("%30s: %s\n",   strings[s_COLLEGENAME], (argc > 5 ? argv[5] : "Normandale Community College"));
    printf("%30s: %s\n",   strings[s_COLLEGEYEAR], (argc > 6 ? argv[6] : "College Junior"));
    printf("%30s: %d\n",   strings[s_AGE],         (argc > 7 ? (int) strtol(argv[7], (char **)NULL, 10) : age)); // http://stackoverflow.com/questions/7021725/converting-string-to-integer-c
    printf("%30s: %o\n",   strings[s_AGEOCT],      (argc > 7 ? (int) strtol(argv[7], (char **)NULL, 10) : age));
    printf("%30s: %x\n",   strings[s_AGEHEX],      (argc > 7 ? (int) strtol(argv[7], (char **)NULL, 10) : age));

    return 0;
}


/** OUTPUT:

C:\Users\Joseph\OneDrive\Intro to C (2011)>Exercise29.exe "Silly Billy" "silly@billy.com" "Sillybilly High" "Kentucky" "Kentucky College for Clowns" "
Clown-in-Training" 10
                    My name is: Silly Billy
           My email address is: <silly@billy.com>
        My home highschool was: Sillybilly High
              My home city was: Kentucky
         My current college is: Kentucky College for Clowns
 My current year in college is: Clown-in-Training
             My current age is: 10
    My current age in octal is: 12
      My current age in hex is: a

*/
