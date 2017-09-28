/* Joseph T. Parsons
 * Nothing too silly here -- reads the file provided to argv[1], prints it to stdout, and quits.
 * Specs did not mention writing a file, and I'm very, very reticent to modify a person's file system unless I'm explicitly told to, especially on the off-chance that doing so would overwrite something. It's kind-of a no-no. */

#include <stdio.h>

int main(int argc, char *argv[])
{
  FILE *fpin;
  char buffer[1024];

  fpin = fopen(argv[1], "r");

  if (fpin == NULL) {
    printf("The file could not be read. It probably doesn't exist. Or you don't have permission to read it. But probably the first thing.");
  }
  else {
    while (fgets(buffer, 1024, fpin)) {
      fprintf(stdout, "%s", buffer); // I like how I already used fputs(stdout, ...) for Exercise33.
    }
  }

  fclose(fpin);

  return 0;
}
