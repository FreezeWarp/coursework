/* Joseph T. Parsons
 * Structs! The closest thing C has to associative arrays (but, frankly, they work for 90% of what you use an associative array for).
 * Meanwhile, I'm just going to use fprintf/etc. instead of printf from now on, since I think that maybe is kinda how C++ does it. I really have no idea how C++ does it, but it has a funny syntax that always referrences std::cout. */

#include <stdio.h>
#include <stdlib.h>

struct product {
    float price;
    float discount;
    char name[100];
    int count;
    struct product *next;
};


void displayProducts(const struct product *p) {
    while (p != NULL) {
        displayProduct(p);
        p = p->next;
    }
}

void displayProduct(struct product* p) {
    fprintf(stdout, "%s\n", p->name);
    fprintf(stdout, "Normally $%.2f, now only $%.2f!\n", p->price, p->price * (1-p->discount));
    fprintf(stdout, "(Supplies limited, only %d available last we checked.)\n\n", p->count);
}

int main() {
    unsigned int i;
    char name[5];
    float price;

    // Normally I try to write code from memory, but I am largely paroting the book here.
    struct product *head = NULL;
    struct product *prev, *current;


    printf("====Please Enter Values====\n");
    for (i = 0; i < 5; i++) { // Because I can't be arsed to implement control structures right now.
        printf("Name? ");
        scanf("%[^\n]%*c", name); // I have no idea what this does (see http://stackoverflow.com/questions/6282198/reading-string-from-input-with-space-character. Still goes out of buffer, though, so be warned.
        printf("Price? ");
        scanf("%f", &price);
        getchar();

        // Allocate new element.
        current = (struct product*) malloc(sizeof(struct product));

        // Append either to the head (reference pointer) or to the last used struct.
        if (head == NULL) head = current;
        else              prev->next = current;

        // Set Values for Current Struct
        current->next = NULL; // Set the next element pointer to NULL since it is currently uninitialised because C is stupid and doesn't allow default values in structs. (I'm just kidding C, I love you.)
        strcpy(current->name, name);
        current->price = price;

        // Update Previous
        prev = current;
    }


    printf("====Before Sort====\n");
    displayProducts(head);


    return 0;
}
