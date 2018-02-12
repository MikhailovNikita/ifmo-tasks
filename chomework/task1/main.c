#include <stdio.h>
#include <malloc.h>

int main(int argc, char **argv) {
    int n;
    scanf("%d", &n);
    int **a = (int **) malloc((n + 1) * sizeof(int *));
    int i, j;
    int max = 0;
    int digits = 0;

    for (i = 1; i <= n; i++) {
        a[i] = (int *) malloc((n + 1) * sizeof(int));
    }

    for (i = 1; i <= n; i++) {
        for (j = 1; j <= n; j++) {
            a[i][j] = i * j;
        }
    }

    int x1, x2, y1, y2;

    while (1) {
        scanf("%d", &x1);
        digits = 0;
        if (x1 == 0) {

            for (i = 1; i <= n; i++) {
                free(a[i]);
            }

            free(a);
            return 0;
        }

        scanf("%d%d%d", &y1, &x2, &y2);
        max = x2 * y2;

        while (max > 0) {
            max = max / 10;
            digits++;
        }

        for (i = y1; i <= y2; i++) {
            for (j = x1; j <= x2; j++) {
                printf("%*d ", digits, a[i][j]);
            }
            printf("\n");
        }
    }
}