#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
                      /* Item type to be contained in the stack */
typedef struct pair{  /* (x,y) pair defined  w/ a link */
  char string[20];
} Item;

#define MAXSTACK 10    /* Upper bound on stack size */

typedef struct node
{
  Item item;
  struct node * next;
} Node;

typedef struct stack
{
  Node * top;    /* Top of the stack  */
  int items;     /* Items in the stack     */
} Stack;

void InitializeStack(Stack * ps);
int StackItemCount(const Stack * ps);
void EmptyTheStack(Stack * ps);

bool StackIsFull(const Stack * ps);
bool StackIsEmpty(const Stack *ps);

bool push(Item item, Stack * ps);
bool pop(Item *pitem, Stack * ps);

static void ItemIntoNode(Item item, Node * pn);
static void ItemFromNode(Node * pn, Item * pi);

void InitializeStack(Stack * ps)
{
  ps->top = NULL;
  ps->items = 0;
}

bool StackIsFull(const Stack * ps)
{
  return ps->items == MAXSTACK;
}

bool StackIsEmpty(const Stack * ps)
{
  return ps->items == 0;
}

int StackItemCount(const Stack * ps)
{
  return ps->items;
}

bool push(Item item, Stack * ps)
{
  Node * pnew;

  if (StackIsFull(ps))
    return false;
  pnew = (Node *) malloc( sizeof(Node));
  if (pnew == NULL)
  {
    fprintf(stderr,"Unable to allocate memory!\n");
    exit(1);
  }
  ItemIntoNode(item, pnew);
  if (StackIsEmpty(ps)){
    ps->top = pnew;            /* item pushed in front of top */
    pnew->next = NULL;
  }
  else{
    pnew->next = ps->top;      /* top reassigned */
    ps->top = pnew;
  }
  ps->items++;                 /* items count updated */

  return true;
}

bool pop(Item * pitem, Stack * ps)
{
  Node * pt;

  if (StackIsEmpty(ps))
    return false;
  ItemFromNode(ps->top, pitem);
  pt = ps->top;
  ps->top = ps->top->next;
  free(pt);
  ps->items--;

  return true;
}

/* empty the Stack */
void EmptyTheStack(Stack * ps)
{
  Item dummy;
  while (!StackIsEmpty(ps))
    pop(&dummy, ps);
}

/* data transfer functions */
static void ItemIntoNode(Item item, Node * pn)
{
  pn->item = item;
}

static void ItemFromNode(Node * pn, Item * pi)
{
  *pi = pn->item;
}


void stackStatus(Stack *pstack);
void display(Item i);

int main( )
{
  int i;

  Stack st;
  Item p;

  InitializeStack(&st);

  stackStatus(&st);

  for(i=0; i< 10; i++){
    sprintf(p.string, "Testing%d", i);
    push(p, &st);
  }

  stackStatus(&st);

  printf("Popping stack contents:\n");
  while(!StackIsEmpty(&st)){
    pop(&p, &st);

    display(p);
  }

  return 0;
}


void display(Item i)
{
    printf("\t%s\n", i.string);
}

void stackStatus(Stack *pstack)
{
  printf("Current stack status:\n");
  if(StackIsEmpty(pstack))
    printf("\tStack empty.\n");
  else{
    printf("\tStack non-empty.\n");
    printf("\t            size: %d\n", StackItemCount(pstack));
  }
  return;
}
