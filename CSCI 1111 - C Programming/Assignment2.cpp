/**
 * Joseph T. Parsons
 *
 * Simple program that prints several strings and an age variable in aligned columns.
 *
 * @credit The guy sitting right of me taught me how to use %ns for fixed-width right-aligned strings.
 */

#include <stdio.h>
int main(void)
{
    unsigned int age = 20; // Unsigned because age can't be signed. On one line (in contrast with the book) because it's cleaner.
	 
    printf("%30s: %s\n",   "My name is", "Joseph Parsons");
    printf("%30s: <%s>\n", "My email address is", "josephtparsons@gmail.com");
    printf("%30s: %s",     "My home highschool was", "Shakopee Highschool\n");
    printf("%30s: %s\n",   "My home city was", "Shakopee, MN");
    printf("%30s: %s\n",   "My current college is", "Normandale Community College");
    printf("%30s: %s\n",   "My current year in college is", "College Junior");
    printf("%30s: %d\n",   "My current age is", age);
    printf("%30s: %o\n",   "My current age in octal", age);
    printf("%30s: %x\n",   "My current age in hexadecimal", age);
    
    return 0;
}
