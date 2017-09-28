/**
 * Joseph T. Parsons
 *
 * Simple program that displays different name formats based on inputs. Debug flag was for testing stuff; figured I'd leave it in in case I want to go back to it.
 *
 * I don't understand C arrays. I _think_ that if more characters are inputted than is normally allowed,
 * the array length is automatically increased (in the same way that using array[] would do that),
 * which seems to defeat the purpose of allocating specific array lengths in the first place.
 * So I must be missing something.
 */

#include <stdio.h>
#include <string.h>
int main(void)
{
    char name_first[21],
      name_middleInitial[2],
      name_last[21],
      name_formatted1[45], // 30 + " " + 1 + ". " 30 + \0
      name_formatted2[46]; // 30 + ", " + 30 + " " + 1 + "." + \0
    const short int DEBUG = 1; // Used for displaying sizeofs.

    printf("This program shows your name based on input parameters. Don't enter more than one character for an initial, because it will break things, and because I can't be bothered to make it work.\n\n");


    /* Prompt Names */
    printf("What's your first name?     ");
    scanf("%s", name_first);

    printf("What's your middle initial? ");
    scanf("%s", name_middleInitial);

    printf("What's your last name?      ");
    scanf("%s", name_last);


    /* First Format */
    strcpy(name_formatted1, name_first);
    strcat(name_formatted1, " ");
    strcat(name_formatted1, name_middleInitial);
    strcat(name_formatted1, ". ");
    strcat(name_formatted1, name_last);


    /* Second Format */
    strcpy(name_formatted2, name_last);
    strcat(name_formatted2, ", ");
    strcat(name_formatted2, name_first);
    strcat(name_formatted2, " ");
    strcat(name_formatted2, name_middleInitial);
    strcat(name_formatted2, ".");


    /* Display Format Results */
    printf("\n%18s: %s",   "First M. Last",  name_formatted1);
    printf("\n%18s: %s", "Last, First M.", name_formatted2);


    /* Debug, for the author's curiosity */
    if (DEBUG == 1) {
        printf("\n\nSizeof:");
        printf("\n%20s: %d", "First Name", sizeof(name_first));
        printf("\n%20s: %d", "Last Name",  sizeof(name_last));
        printf("\n%20s: %d", "F1 Name",    sizeof(name_formatted1));
        printf("\n%20s: %d", "F2 Name",    sizeof(name_formatted2));
    }


    /* Return */
    return 0;
}
