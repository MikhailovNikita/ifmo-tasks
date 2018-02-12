#include "forest.h"
#include <errno.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>

int fd[2];
forest f;

int run();

void dfs_process(int v);


int run() {
    if (pipe(fd) < 0) {
        perror("Can't create pipe");
        exit(1);
    }
    int count = 0;
    write(fd[1], &count, sizeof(int));

    int_vector roots = f.get_roots(&f);

    for (int i = 0; i < roots.size; i++) {
        printf("Process tree from the root #%d\n", roots.values[i]);

        int pid = fork();
        if (pid < 0) {
            perror("Error while forking");
            exit(1);
        } else if (!pid) {
            dfs_process(roots.values[i]);
            //dfs function will kill child process anyway
            //exit(0)
        }

    }

    //wait for every child process to finish
    //status is ignored
    int pid, status;
    while ((pid = wait(&status))) {
        if (errno == ECHILD || (pid == -1 && errno == ECHILD)) break;
    }

    //read amount of nodes from pipe and return the value
    read(fd[0], &count, sizeof(int));
    return count;
}


// process node "v" and it's children
void dfs_process(int v) {
    printf("Start process %d in the node %d\n", getpid(), v);

    int i;
    for (i = 0; i < f.edges[v].size; i++) {
        int son = f.edges[v].values[i];
        // Start new processes for son
        int pid = fork();

        if (pid < 0) {
            perror("Fork error");
            exit(1);
        } else if (pid == 0) {
            //child process continues dfs
            dfs_process(son);
        }

        //parent process
        continue;

    }

    int status, pid;

    //wait for every child process to finish
    if (f.edges[v].size) {
        while ((pid = wait(&status))) {
            if (status == ECHILD || (pid == -1 && errno == ECHILD)) break;
        }
    }

    // increment counter of vertices
    int count;
    read(fd[0], &count, sizeof(int));
    count++;
    write(fd[1], &count, sizeof(int));

    // finished
    exit(0);
}


int main(int argc, char *argv[]) {

    int size;
    if (argc < 2) {
        printf("Set size:\n");
        scanf("%d", &size);
    } else {
        size = atoi(argv[1]);
    }

    if (size > 1000) {
        printf("Graph's too large, setting size = 20\n");
        size = 20;
    }

    f = generate(size);
    printf("%s", f.forest_to_str(&f));
    int count = run();
    printf("Initial size: %d nodes.Bypassed %d nodes.\n", size, count);
    exit(0);
    return 0;
}