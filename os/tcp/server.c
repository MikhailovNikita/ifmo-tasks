#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <pthread.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>
#include <arpa/inet.h>
#include <time.h>

#define PORT 48655
#define CONS 10

void *recv_msg(void *args);

char *get_hosts(char *address);

time_t start;


struct client {
    int sock_desriptor;
    char *sock_addr;
};

int main() {
    // сreate socket and mem the start time
    start = time(0);
    int serv_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (serv_socket < 0) {
        perror("Can't create socket");
        exit(1);
    }
    struct sockaddr_in local;

    memset(&local, 0, sizeof(local));
    local.sin_family = AF_INET;
    local.sin_port = htons(PORT);
    local.sin_addr.s_addr = INADDR_ANY;
    int enable = 1;
    setsockopt(serv_socket, SOL_SOCKET, SO_REUSEADDR, &enable, sizeof(int));

    if (bind(serv_socket, (struct sockaddr *) &local, sizeof(local))) {
        perror("Bind error");
        exit(1);
    }

    // listen socket connections
    listen(serv_socket, CONS);


    while (1) {
        struct sockaddr_in client;
        unsigned int len = sizeof(client);
        // waiting for new connection
        int cl_socket = accept(serv_socket, (struct sockaddr *) &client, &len);

        if (cl_socket < 0) {
            perror("Can't get client connection");
            exit(1);
        }

        char client_address[BUFSIZ];
        // run thread to process client's messages
        inet_ntop(AF_INET, &(client.sin_addr), client_address, BUFSIZ);
        printf("%s Connected\n", client_address);
        send(cl_socket, "Connected!\nTo leave from server type \":q\"\n", 100, MSG_NOSIGNAL);

        pthread_t thread;
        struct client cl_info = {cl_socket, client_address};
        if (pthread_create(&thread, NULL, recv_msg, (void *) &cl_info)) {
            perror("Can't create thread");
            exit(1);
        }
    }
}


// get client's message and send it back in upper case
void *recv_msg(void *args) {

    void upper_string(char s[]) {
        int c = 0;

        while (s[c] != '\0') {
            if (s[c] >= 'a' && s[c] <= 'z') {
                s[c] = s[c] - 32;
            }
            c++;
        }
    }

    struct client *cl = (struct client *) args;

    int cl_socket = cl->sock_desriptor;
    char *address = cl->sock_addr;
    char buf[BUFSIZ];
    while (1) {
        memset(buf, 0, BUFSIZ);
        read(cl_socket, buf, BUFSIZ);
        printf("%s: %s", address, buf);
        if (!strncmp(buf, ":q\n", 3)) {
            time_t asked = time(0);
            printf("Server uptime: %d seconds ", asked - start);
            printf("%s: Disconnected from server\n", address);
            send(cl_socket, "|DISCONNECT_TOKEN|\n", BUFSIZ, MSG_NOSIGNAL);
            break;
        }

        upper_string(buf);
        send(cl_socket, buf, BUFSIZ, MSG_NOSIGNAL);
    }
    return NULL;
}

