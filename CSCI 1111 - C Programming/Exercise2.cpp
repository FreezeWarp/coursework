/**
 * Joseph T. Parsons
 *
 * Simple program that prints the size of common data types.
 */

#include <stdio.h>
int main(void)
{
    printf("%30s: %s\n",   "Integer Size", "Joseph Parsons");
    printf("%30s: <%s>\n", "My email address is", "josephtparsons@gmail.com");
    printf("%30s: %s",     "My home highschool was", "Shakopee Highschool\n");
    printf("%30s: %s\n",   "My home city was", "Shakopee, MN");
    printf("%30s: %s\n",   "My current college is", "Normandale Community College");
    printf("%30s: %s\n",   "My current year in college is", "College Junior");
    printf("%30s: %d\n",   "My current age is", age);
    printf("%30s: %o\n",   "My current age in octal", age);
    printf("%30s: %x\n",   "My current age in hexadecimal", age);
    printf("%30s: %d\n",   "Feet!", feet);
    printf("%30s: %.2f\n",   "You weight: ", weight);
    
    yellow();
    
    return 0;
}

void yellow(void) {
	printf("Yellow Fever!");
}
