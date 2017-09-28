/* Joseph T. Parsons
 * Structs! The closest thing C has to associative arrays (but, frankly, they work for 90% of what you use an associative array for).
 * Meanwhile, I'm just going to use fprintf/etc. instead of printf from now on, since I think that maybe is kinda how C++ does it. I really have no idea how C++ does it, but it has a funny syntax that always referrences std::cout. */

#include <stdio.h>

struct product {
    const float price;
    float discount;
    char name[100];
    int count;
};


struct product products[10] = {
    {
        .price = 599.99,
        .discount = .1,
        .name = "Sampple 3.75K 5D High-Resolution Oscilatting Television",
        .count = 12
    },
    
    {
        .price = 5.99,
        .discount = .05,
        .name = "30pk Morley Filtered Danger-Sticks",
        .count = 100
    }

};

void displayProduct(struct product p) {
    fprintf(stdout, "%s\n", p.name);
    fprintf(stdout, "Normally $%.2f, now only $%.2f!\n", p.price, p.price * (1-p.discount));
    fprintf(stdout, "(Supplies limited, only %d available last we checked.)\n\n", p.count);
}

int main()
{
    displayProduct(products[0]);
    displayProduct(products[1]);

    return 0;
}
