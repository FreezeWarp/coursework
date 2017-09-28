/**
 * Joseph T. Parsons
 *
 * Simple program that prints the size of common data types.
 */

#include <stdio.h>
int main(void)
{
    printf("Integer Sizes:\n");
    printf("%24s: %d\n", "Short Integer Size", sizeof(short int));
    printf("%24s: %d\n", "Integer Size",       sizeof(int));
    printf("%24s: %d\n", "Long Integer Size",  sizeof(long int));
    printf("%24s: %d\n", "Long Long Int Size", sizeof(long long int));
    printf("\n\n");
    
    
    printf("Char Sizes:\n");
    printf("%24s: %d\n", "Char Size",          sizeof(char));
    printf("\n\n");


    printf("Float, Double Sizes:\n");
    printf("%24s: %d\n", "Float Size",         sizeof(float));
    printf("%24s: %d\n", "Double Size",        sizeof(double));
    printf("%24s: %d\n", "Long Double Size",   sizeof(long double));
        
    return 0;
}
