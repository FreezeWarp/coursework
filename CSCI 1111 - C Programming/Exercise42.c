/* Joseph T. Parsons
 * Structs! The closest thing C has to associative arrays (but, frankly, they work for 90% of what you use an associative array for).
 * Meanwhile, I'm just going to use fprintf/etc. instead of printf from now on, since I think that maybe is kinda how C++ does it. I really have no idea how C++ does it, but it has a funny syntax that always referrences std::cout. */

#include <stdio.h>
#include <stdlib.h>

struct product {
    const float price;
    float discount;
    char name[100];
    int count;
};


struct product products[6] = {
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

    {
        .price = 5.99,
        .discount = .05,
        .name = "30pk Morley Filtered Danger-Sticks",
        .count = 100
    },

    {
        .price = 15.99,
        .discount = .8,
        .name = "Stale Bread",
        .count = 250
    },

    {
        .price = 3.99,
        .discount = .005,
        .name = "Idunno, Bacon?",
        .count = 1337
    }

};

void displayProduct(struct product* p) {
    fprintf(stdout, "%s\n", p->name);
    fprintf(stdout, "Normally $%.2f, now only $%.2f!\n", p->price, p->price * (1-p->discount));
    fprintf(stdout, "(Supplies limited, only %d available last we checked.)\n\n", p->count);
}


int compare(const void *p1, const void *p2) {
    struct product * product1 = (struct product *)p1;
    struct product * product2 = (struct product *)p2;

    return 100 * (1 - product1->discount) * product1->price
         - 100 * (1 - product2->discount) * product2->price; // It wants integers, and the floating point conversion doesn't round. So we just multiple by 100 and then let it do its coversion.
}


int main() {
    unsigned int i;
    
    printf("====Before Sort====\n");
    for (i = 0; i < sizeof(products) / sizeof(struct product); i++)
        displayProduct(&(products[i]));


    qsort(
          (void *)&products, // Array to Modify
          sizeof(products) / sizeof(struct product), // Elemenets in Array
          sizeof(struct product), // Size of Each Array Entry
          compare // Function to use for Sorting
    );


    printf("\n====After Sort====\n");
    for (i = 0; i < sizeof(products) / sizeof(struct product); i++)
        displayProduct(&(products[i]));


    return 0;
}
