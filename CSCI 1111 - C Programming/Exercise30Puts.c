#include <stdio.h>
void myPuts(char *str)
{
  while(*str != '\0')
    putchar(*str++);

  return;
}
