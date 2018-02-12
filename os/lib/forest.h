#ifndef OSTASKS_FOREST_H
#define OSTASKS_FOREST_H

#include "my_vector.h"

typedef struct forest forest, * pf;

struct forest
{
    // list of nodes' edges
    int_vector * edges;

    // parent's list (-1 if such doesn't exist)
    int * parent;

    // amount of nodes
    int size;

    // check if adding edge (from -> to) creates cycles
    // or other types of edges which are not allowed for forest
    // If new edge violate structure return 0, otherwise 1
    int (*check_edge)(pf self, int from, int to);

    // add edge from->to
    pf (*add_edge)(pf self, int from, int to);

    // returns the list of forest's roots with degree more than 0
    int_vector (*get_real_roots)(pf self);

    // returns the list of forest's roots
    int_vector (*get_roots)(pf self);

    // free memory
    pf (*clear_forest)(pf self);

    // returns string forest's representation
    char * (*forest_to_str)(pf self);

};

// create forest with specified amount of nodes
forest create(int size);

// generate random forest with specified amount of nodes
// and random edges amount
forest generate(int size);

#endif //OSTASKS_FOREST_H
