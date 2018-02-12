#ifndef OSTASKS_MY_VECTOR_H
#define OSTASKS_MY_VECTOR_H

typedef struct int_vector int_vector, *iv;


struct int_vector
{
    // elements
    int * values;

    // amount of elements in array
    int size;

    // Real size of array
    int capacity;


    // Add element to the end of array without of copyingsi
    // If index < 0 || index > size error
    iv (*append)(int_vector * self, int value);

    // Remove value in specific index by copying elements in new array
    // If index < 0 || index >= size error
    iv (*remove_at)(int_vector * self, int index);

    // Remove all elements with specific value from array by copying elements in new array
    // If array doesn't contain valuue that function doesn't effect on array
    iv (*remove_value)(int_vector * self, int value);

    // Check does the array contain specific value.
    // Returns index of value if array contains or -1 otherwise
    int (*contains)(int_vector * self, int value);

    // Return real size of array (count of elements)
    int (*get_size)(int_vector * self);

    // Free memory, set size = 0
    iv (*clear)(int_vector * self);

    // Returns string representation of array
    char * (*toString)(int_vector * self);
};


int_vector init();

#endif //OSTASKS_MY_VECTOR_H
