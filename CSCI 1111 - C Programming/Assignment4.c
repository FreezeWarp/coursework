/**
 * Joseph T. Parsons
 *
 * Simple program that calculates the properties of a rectangular prism based on inputted dimensions.
 */


#include <stdio.h>
#include <math.h>


/**
 * Calculates the area of a rectangle based on width and height parameters.
 */
int rectangleArea(int width, int height)
{
    return width*height;
}


/**
 * Calculates the peremiter of a rectangle based on width and height parameters.
 */
int rectanglePerimeter(int width, int height)
{
    return 2 * (width + height);
}


/**
 * Calculates the volume of a prism based on width, height, and depth parameters.
 */
int prismVolume(int height, int depth, int width)
{
    return height * depth * width;
}


/**
 * Calculates the chord of a prism based on width, height, and depth parameters.
 */
double prismChord(int height, int depth, int width)
{
    return sqrt((double) (depth * depth + width * width + height * height));
}


/**
 * Displays the program description.
 */
void programDescription()
{
    printf("This program calculates properties of a box. You will be asked to enter its dimensions. These must be whole numbers.\n\n");
}


/**
 * Calculates prism properties from height, depth, and width, and displays calculations.
 */
void programCalculateAndDisplay(int height, int depth, int width)
{
    int sArea_top = rectangleArea(depth, width),
      sArea_front = rectangleArea(width, height),
      sArea_side = rectangleArea(height, depth),
      sArea_total = 2 * (sArea_top + sArea_front + sArea_side),
      perimeter_top = rectanglePerimeter(depth, width),
      perimeter_front = rectanglePerimeter(width, height),
      perimeter_side = rectanglePerimeter(height, depth),
      volume = prismVolume(height, depth, width);
    double chord = prismChord(height, depth, width);


    /* Display Calculations */
    printf("\n\nSurface Area\n");
    printf("%20s: %d\n",   "Top or Bottom",      sArea_top);
    printf("%20s: %d\n",   "Front or Back",      sArea_front);
    printf("%20s: %d\n",   "Left or Right Side", sArea_side);
    printf("%20s: %d\n",   "Everything",         sArea_total);

    printf("\n\nPerimeter\n");
    printf("%20s: %d\n",   "Top or Bottom",      perimeter_top);
    printf("%20s: %d\n",   "Front or Back",      perimeter_front);
    printf("%20s: %d\n",   "Left or Right Side", perimeter_side);

    printf("\n\nOther\n");
    printf("%20s: %d\n",   "Volume",             volume);
    printf("%20s: %.2f\n", "Chord/Diagonal",     chord);
}


/**
 * MAIN
 */
int main(void)
{
    int height, depth, width;


    /* Display Program Description */
    programDescription();


    /* Prompt Dimensions */
    printf("Height? ______\b\b\b\b\b\b");
    scanf("%d", &height);

    printf("Depth? ______\b\b\b\b\b\b");
    scanf("%d", &depth);

    printf("Width? ______\b\b\b\b\b\b");
    scanf("%d", &width);


    /* Calculate Values */
    programCalculateAndDisplay(height, depth, width);


    /* Return */
    return 0;
}
