	/**
	 * Joseph T. Parsons
	 *
	 * Simple program that calculates the properties of a rectangular prism based on inputted dimensions.
	 */
	
	#include <stdio.h>
	#include <math.h>
	int main(void)
	{
	    unsigned int height, depth, width; // Unsigned because dimensions can't be signed.
	    
	    printf("This program calculates properties of a box. You will be asked to enter its dimensions. These must be whole numbers.\n\n");
	
	
	
	    /* Prompt Dimensions */    
	    printf("Height? ______\b\b\b\b\b\b");
	    scanf("%d", &height);
	    
	    printf("Depth? ______\b\b\b\b\b\b");
	    scanf("%d", &depth);
	    
	    printf("Width? ______\b\b\b\b\b\b");
	    scanf("%d", &width);
		
	
	
	    /* Calculate Values */
	    unsigned int sArea_top = depth * width,
		  sArea_front = width * height,
		  sArea_side = height * depth,
		  sArea_total = 2 * (sArea_top + sArea_front + sArea_side),
		  perimeter_top = 2 * (depth + width),
		  perimeter_front = 2 * (width + height),
		  perimeter_side = 2 * (height + depth),
		  volume = depth * width * height;
		double chord = sqrt(depth * depth + width * width + height * height);
		
			
	
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
	
	    
	    
	    /* Return */
		return 0;
	}
