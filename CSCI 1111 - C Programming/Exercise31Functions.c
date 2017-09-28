#include <stdio.h>
#include <stdlib.h>

float * selectionSort(float array[ ], int size)
{
  int pass,item,position,temp;

  /* Selection-sort the values read in. */
  for(pass=0; pass<size-1; pass++){
    position = pass;
    for(item=pass+1; item<size; item++)
      if (array[position] < array[item])
        position = item;
    if(pass != position){
      temp = array[pass];
      array[pass] = array[position];
      array[position] = temp;
    }
  }

  return array;
}

float * printArray(float a[ ], int s)
{
  int i;

  for(i=0; i<s; i++)  /* display values in array */
     printf("%f\n", a[i]);

  return a;
}

float * readArray(float a[ ], int s)
{
  int i;
        /* Read score values into array */
  for(i=0; i<s; i++)
  {
    printf("Enter value %d of %d: ", i+1, s);
    scanf("%f", &a[i]);
  }

  return a;
}
