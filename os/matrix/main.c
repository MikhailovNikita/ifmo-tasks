#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <time.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>

//random keys to shm
#define SHM_STATUS_KEY 23054912
#define SHM_M1_KEY 78452147
#define SHM_M2_KEY 74814365
#define SHM_M3_KEY 54687852
#define ACCESS_RDWR 0666

//constants to current process
#define STAT_START 1
#define STAT_GEN 2
#define STAT_EVAL 3
#define STAT_PRINT 4

//matrix size and bool for printing in file(or not)
int matrix_cols;
int matrix_rows;
int m_size;
int file_printing;
char ans[1];

// ids to shm
int ids[4];

// output file descriptor
int fod = 0;

void signal_handler(int signal_number) {
    switch (signal_number) {
        case SIGINT:
            printf("Leaving.. \n");
            if (fod) close(fod);
            shmctl(ids[0], IPC_RMID, NULL);
            shmctl(ids[1], IPC_RMID, NULL);
            shmctl(ids[2], IPC_RMID, NULL);
            shmctl(ids[3], IPC_RMID, NULL);
            exit(0);
    }
}

void allocate_mem() {
    ids[0] = shmget(SHM_STATUS_KEY, 1 * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[1] = shmget(SHM_M1_KEY, matrix_cols * matrix_rows * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[2] = shmget(SHM_M2_KEY, matrix_cols * matrix_rows * sizeof(int), IPC_CREAT | ACCESS_RDWR);
    ids[3] = shmget(SHM_M3_KEY, matrix_cols * matrix_rows * sizeof(int), IPC_CREAT | ACCESS_RDWR);


    if (ids[0] < 0 || ids[1] < 0 || ids[2] < 0 || ids[3] < 0) {
        perror("Can't allocate shared memory");
        exit(1);
    }

}

void get_mem(int **status, int **M1, int **M2, int **M3) {
    int stat_id = shmget(SHM_STATUS_KEY, 1 * sizeof(int), ACCESS_RDWR);
    int M1_id = shmget(SHM_M1_KEY, matrix_cols * matrix_rows * sizeof(int), ACCESS_RDWR);
    int M2_id = shmget(SHM_M2_KEY, matrix_cols * matrix_rows * sizeof(int), ACCESS_RDWR);
    int M3_id = shmget(SHM_M3_KEY, matrix_cols * matrix_rows * sizeof(int), ACCESS_RDWR);

    if (stat_id < 0 || M1_id < 0 || M2_id < 0 || M3_id < 0) {
        perror("Can't get access to shared memory");
        exit(1);
    }

    *status = (int *) shmat(stat_id, (void *) 0, 0);
    *M1 = (int *) shmat(M1_id, (void *) 0, 0);
    *M2 = (int *) shmat(M2_id, (void *) 0, 0);
    *M3 = (int *) shmat(M3_id, (void *) 0, 0);
}


void generator() {
    int *status, *M1, *M2, *M3;
    get_mem(&status, &M1, &M2, &M3);

    //initial status
    *status = STAT_GEN;

    time_t t;
    srand((unsigned) time(&t));

    while (1) {
        // waiting while it'll be time for generating
        if (*status != STAT_GEN) {
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

        *status = STAT_EVAL;
    }

}

void evaluator() {
    int *status, *M1, *M2, *M3;
    get_mem(&status, &M1, &M2, &M3);

    while (1) {
        // waiting while it'll be a time for evaluating
        if (*status != STAT_EVAL) {
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

        *status = STAT_PRINT;
    }
}


void print_row(char *buf, int *M, int i) {
    int j;
    for (j = 0; j < matrix_cols; j++) {
        //maximum number in matrix is 200 so 4 chars is enough
        char *num = (char *) malloc(4 * sizeof(char));
        sprintf(num, "%3d ", M[i * matrix_rows + j]);
        strcat(buf, num);
        free(num);
    }

}

void matrix_to_str(char *res, int *M1, int *M2, int *M3) {
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

void printer() {
    int *status, *M1, *M2, *M3;
    get_mem(&status, &M1, &M2, &M3);

    fod = open("matrix.out", O_WRONLY | O_CREAT | O_TRUNC, ACCESS_RDWR);
    if (fod < 0) {
        perror("Can't create output file");
        exit(1);
    }

    while (1) {
        // waiting for printing time
        if (*status != STAT_PRINT) {
            sleep(1);
            continue;
        }
        printf("Printing\n");

        char *res = (char *) malloc(matrix_rows * matrix_cols * 32 * sizeof(char));
        res[0] = 0;
        matrix_to_str(res, M1, M2, M3);
        if (file_printing == 1) {
            write(fod, res, strlen(res));
        }
        if (matrix_rows <= 8 && matrix_cols <= 8) printf("%s", res);
        free(res);

        *status = STAT_GEN;
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

    if (matrix_cols > 8 || matrix_rows > 8) {
        printf("Matrices are too large to be displayed\n You can enable file output\n");
    };

    m_size = matrix_rows * matrix_cols;

    while (1) {
        printf("Do you want to enable file output?(Y/N): ");
        scanf("%s", ans);
        if (!strcmp(ans, "Y") || !strcmp(ans, "y")) {
            printf("Enabled\n");
            file_printing = 1;
            break;
        }

        if (!strcmp(ans, "N") || !strcmp(ans, "n")) {
            printf("Disabled\n");
            file_printing = 0;
            break;
        }
    }


    signal(SIGINT, signal_handler);
    allocate_mem();
    int pid1 = fork();

    if (pid1 < 0) {
        perror("Generator fork error");
        exit(1);
    }
    if (!pid1) {
        generator();
        exit(0);
    }

    int pid2 = fork();

    if (pid2 < 0) {
        perror("Evaluator fork error");
        exit(1);
    }
    if (!pid2) {
        evaluator();
        exit(0);
    }

    int pid3 = fork();

    if (pid3 < 0) {
        perror("Printer fork error");
        exit(1);
    }
    if (!pid3) {
        printer();
        exit(0);
    }


    //waiting for interrupt signal
    while (1) {}

}