/* Joseph Parsons
 * This gave me wayyyy too much trouble. But, on the brightside, I finally got around to installing cygwin.
 * Mmm... Unix terminal... Never did like DOS myself. */

#include <string.h>
#include <stdio.h>

void myPuts(char *str);

int main( )
{
  char name[81];

  strcpy(name,"Joseph");
  strcat(name," Parsons");

  myPuts("You created: ");
  myPuts(name);
  myPuts("\n");

  return 0;
}
