/* Joseph T. Parsons
 * Singly-linked lists. */

#include <stdio.h>
#include <stdlib.h>

struct product {
    char name[100];
    struct product *next;
};

void displayProduct(const struct product *p) {
    fprintf(stdout, "%s\n", p->name);
}

void displayProducts(const struct product *p) {
    while (p != NULL) {
        displayProduct(p);
        p = p->next;
    }
}

struct product* newProduct(struct product newP) {
    // Allocate
    struct product* p = (struct product*) malloc(sizeof(struct product));
    
    // Set Element to Passed Parameter
    *p = newP;
    p->next = NULL;
    
    // Return New Element
    return p;
}

/* Appends to a single-linked list. Head can be any element in the list. */
struct product* appendProduct(struct product *head, struct product newP) {
    // Allocate
    struct product* p = (struct product*) malloc(sizeof(struct product));

    // Jump to Last Element
    while (head->next != NULL) head = head->next;

    // Set Element to Passed Parameter, Append It
    *p = newP;
    p->next = NULL;
    head->next = p;

    // Return New Elemenet
    return p;
}

/* Prepends to a single-linked list. Requires correct head. */
struct product* prependProduct(struct product *head, struct product newP) {
    // Allocate
    struct product* p = (struct product*) malloc(sizeof(struct product));

    // Set Element to Passed Parameter
    *p = newP;
    p->next = head;
    
    // Return New Element (Is New List Head)
    return p;
}

void deleteProducts(struct product *head) {
    struct product *p, *next;
    
    // From http://stackoverflow.com/questions/7025328/linkedlist-how-to-free-the-memory-allocated-using-malloc
    for (p = head; NULL != p; p = next) {
        next = p->next;
        free(p);
    }
}

int main() {
    struct product *head = newProduct((struct product) {.name = "I Don't Know Why"});
    head = prependProduct(head, (struct product) {.name = "Hello Hello"});
    appendProduct(head, (struct product) {.name = "You Say Goodbye"});
    head = prependProduct(head, (struct product) {.name = "Goodbye Goodbye"});
    appendProduct(head, (struct product) {.name = "I Say HEllo"});

    displayProducts(head);
    deleteProducts(head);

    return 0;
}
