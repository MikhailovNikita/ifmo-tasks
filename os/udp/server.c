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

#define PORT 48655

char * get_hosts(char *address);

void upper_string(char s[]) {
    int c = 0;

    while (s[c] != '\0') {
        if (s[c] >= 'a' && s[c] <= 'z') {
            s[c] = s[c] - 32;
        }
        c++;
    }
}

int main()
{
    struct sockaddr_in local, client;
    unsigned int client_len;
    // set server's address
    memset(&local, 0, sizeof(local));
    local.sin_family = AF_INET;
    local.sin_port = htons(PORT);
    local.sin_addr.s_addr = htonl(INADDR_ANY);
    // create socket
    int sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock < 0)
    {
        perror("Can't create socket");
        exit(1);
    }

    int enabled = 1;
    setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, (const void *) &enabled, sizeof(int));
    if(bind(sock, (struct sockaddr *) &local, sizeof(local)) < 0)
    {
        perror("Bind error");
        exit(1);
    }

    client_len = sizeof(client);
    char * buf = (char *) malloc(BUFSIZ * sizeof(char));

    //processing incoming messages
    while(1)
    {
        memset(buf, 0, BUFSIZ);
        if(recvfrom(sock, buf, BUFSIZ, 0, (struct sockaddr *)&client, &client_len) < 0)
        {
            perror("recvfrom func error");
            exit(1);
        }

        char client_addr[BUFSIZ];
        inet_ntop(AF_INET, &(client.sin_addr), client_addr, BUFSIZ);
        printf("%s: %s", client_addr, buf);
        // Intro msg
        if(!strcmp(buf, "init\n")){
            strcpy(buf, "Connected!\n");
        }
        else
        {
            upper_string(buf);
        }
        // send answer to client
        if(sendto(sock, buf, BUFSIZ, MSG_NOSIGNAL, (struct sockaddr * ) &client, client_len) < 0)
        {
            perror("Sendto error");
            exit(1);
        }
        printf("Server: %s", buf);
    }


}