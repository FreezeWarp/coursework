/* Joseph T. Parsons
 * Shortened and blockened version of Exercise42.c */

#include <stdio.h>
#include <stdlib.h>


struct product {
    const float price;
    float discount;
    char name[100];
    int count;
};


struct product products[3] = {
    {
        .price = 57.99,
        .discount = .3,
        .name = "I'm Too Lazy to Make Funny Product Names",
        .count = 1000
    },

    {
        .price = 8.99,
        .discount = .4,
        .name = "Justin Bieber Something Something",
        .count = 11
    },

    {
        .price = 599.99,
        .discount = .1,
        .name = "Sampple 3.75K 5D High-Resolution Oscilatting Television",
        .count = 120
    },

};

void displayProduct(struct product* p) {
    fprintf(stdout, "%s\n", p->name);
    fprintf(stdout, "Normally $%.2f, now only $%.2f!\n", p->price, p->price * (1-p->discount));
    fprintf(stdout, "(Supplies limited, only %d available last we checked.)\n\n", p->count);
}


int compare(const struct product *elem1, const struct product *elem2)
{
   return 100 * (1 - elem1->discount) * elem1->price - 100 * (1 - elem2->discount) * elem2->price; // It wants integers, and the floating point conversion doesn't round. So we just multiple by 100 and then let it do its coversion.
}


int main()
{
    printf("Built on: %s %s\n\n", __DATE__, __TIME__);
    
    printf("====Before Sort====\n");
    for (int i = 0; i < sizeof(products) / sizeof(struct product); i++)
        displayProduct(&(products[i]));


    #if defined(DOSORT) // Or you could just do if false.
    qsort(
          (void *)&products, // Array to Modify
          sizeof(products) / sizeof(struct product), // Elemenets in Array
          sizeof(struct product), // Size of Each Array Entry
          compare // Function to use for Sorting
    );


    printf("\n====After Sort====\n");
    for (int i = 0; i < sizeof(products) / sizeof(struct product); i++)
        displayProduct(&(products[i]));
    #endif


    return 0;
}
