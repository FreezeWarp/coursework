/* Joseph T. Parsons
 * Puts a handful of floats into a file, named Exercise34.tmp. */

#include <stdio.h>

int main(int argc, char *argv[])
{
  FILE *fpout = fopen("Exercise34.tmp", "wb");
  float values[7] = {
      .577,
      1.414,
      1.618,
      1.732,
      2.718,
      3.1415,
      9.81,
  };
  char buffer[1024];

  if (fpout == NULL) {
    printf("The file could not be opened for reading. You probably don't have permission to write in the given directory.");
  }
  else {
//    for (int i = 0; i < (sizeof(values) / sizeof(values[0])); i++) {
        fwrite(values, sizeof(values[0]), sizeof(values) / sizeof(values[0]), fpout);
//        printf("%f", values[i]);
//    }
  }

  fclose(fpout);

  return 0;
}
