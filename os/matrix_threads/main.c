#include <pthread.h>
#include <time.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>


#define ACCESS_RDWR 0666
#define STAT_START 1
#define STAT_GEN   2
#define STAT_EVAL  3
#define STAT_PRINT 4


//lock for processes' stats(those which are above)
pthread_mutex_t lock;

int matrix_cols;
int matrix_rows;
int m_size;
int file_printing;
char ans[1];

// matrixes
int *M1, *M2, *M3;


int status;

// outfile descriptor
int fod = 0;




void signal_handler(int signal_number) {
    switch (signal_number) {
        case SIGINT :
            if (fod && close(fod) < 0) {
                fod = 0;
                perror("Unable to close output file");
            }
            printf("\nLeaving..\n");
            exit(0);
    }
}

void *generator(void *args) {
    time_t t;
    srand((unsigned) time(&t));
    status = STAT_GEN;

    while (1) {
        pthread_mutex_lock(&lock);
        if (status != STAT_GEN) {
            pthread_mutex_unlock(&lock);
            sleep(1);
            continue;
        }


        printf("Generating matrix %dx%d\n", matrix_rows, matrix_cols);

        int i, j;
        for (i = 0; i < matrix_rows; i++) {
            for (j = 0; j < matrix_cols; j++) {
                M1[i * matrix_rows + j] = rand() % 100;
                M2[i * matrix_rows + j] = rand() % 100;
            }
        }
        status = STAT_EVAL;
        pthread_mutex_unlock(&lock);
    }
}

void *evaluator(void *args) {
    while (1) {
        pthread_mutex_lock(&lock);
        if (status != STAT_EVAL) {
            pthread_mutex_unlock(&lock);
            sleep(1);
            continue;
        }

        printf("Evaluating\n");
        int i, j;
        for (i = 0; i < matrix_rows; i++) {
            for (j = 0; j < matrix_cols; j++) {
                M3[i * matrix_rows + j] = M1[i * matrix_rows + j] + M2[i * matrix_rows + j];
            }
        }
        status = STAT_PRINT;
        pthread_mutex_unlock(&lock);
    }
}


void *printer(void *args) {
    fod = open("matrix_threads.out", O_CREAT | O_TRUNC | O_WRONLY, ACCESS_RDWR);
    if (fod < 0) {
        perror("Can't create output file");
        exit(1);
    }

    while (1) {
        pthread_mutex_lock(&lock);
        if (status != STAT_PRINT) {
            pthread_mutex_unlock(&lock);
            sleep(1);
            continue;
        }
        printf("Printing\n");

        char *res = (char *) malloc(matrix_rows * matrix_cols * 32 * sizeof(char));
        res[0] = 0;
        matrix_to_str(res);
        if(file_printing == 1){
            write(fod, res, strlen(res));
        }
        if (matrix_rows <= 8 && matrix_cols <= 8){
            printf("%s", res);
        }else{
            printf("Matrix is too large");
        }
        free(res);

        status = STAT_GEN;
        pthread_mutex_unlock(&lock);
    }
}


void matrix_to_str(char *res) {
    int i;
    for (i = 0; i < matrix_rows; i++) {
        strcat(res, "||");
        print_row(res, M1, i);
        strcat(res, "||");
        if (i == matrix_rows >> 1) {
            strcat(res, "+");
        } else strcat(res, " ");
        strcat(res, "||");
        print_row(res, M2, i);
        strcat(res, "||");
        if (i == matrix_rows >> 1) {
            strcat(res, "=");
        } else strcat(res, " ");
        strcat(res, "||");
        print_row(res, M3, i);
        strcat(res, "||\n");
    }
//    strcat(res, "\n");
//    for(int i = 0; i < matrix_cols * 3; i++){
//        strcat(res, "_");
//    }
//
//    strcat(res,"\n");
}


void print_row(char *buf, int *M, int i) {
    int j;
    for (j = 0; j < matrix_cols; j++) {
        char *el = (char *) malloc(4 * sizeof(char));
        sprintf(el, "%3d ", M[i * matrix_rows + j]);
        strcat(buf, el);
        free(el);
    }

}


int main() {
    printf("Input size of the generated matrix\n");
    printf("Rows: ");
    scanf("%d", &matrix_rows);
    printf("Cols: ");
    scanf("%d", &matrix_cols);

    if (matrix_cols * matrix_rows > 1000) {
        printf("Size's too large\n");
        exit(1);
    }

    m_size = matrix_rows * matrix_cols;

    if(matrix_cols > 8 || matrix_rows > 8){
        printf("Matrices are too large to be displayed\n You can enable file output\n");
    };



    while(1){
        printf("Do you want to enable file output?(Y/N): ");
        scanf("%s", ans);
        if(!strcmp(ans,"Y") || !strcmp(ans,"y")){
            printf("Enabled\n");
            file_printing = 1;
            break;
        }

        if(!strcmp(ans,"N") || !strcmp(ans,"n")){
            printf("Disabled\n");
            file_printing = 0;
            break;
        }
    }

    M1 = (int *) malloc(m_size * sizeof(int));
    M2 = (int *) malloc(m_size * sizeof(int));
    M3 = (int *) malloc(m_size * sizeof(int));

    signal(SIGINT, signal_handler);
    pthread_mutex_init(&lock, NULL);

    pthread_t *threads_arr = (pthread_t *) malloc(3 * sizeof(pthread_t));
    if (pthread_create(&threads_arr[0], NULL, generator, NULL) ||
        pthread_create(&threads_arr[1], NULL, evaluator, NULL) ||
        pthread_create(&threads_arr[2], NULL, printer, NULL)) {
        perror("Can't create thread");
        exit(1);
    }


    int i;
    for (i = 0; i < 3; i++) {
        pthread_join(threads_arr[i], NULL);
    }

    return 0;
}