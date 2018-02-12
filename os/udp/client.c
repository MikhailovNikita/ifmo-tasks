#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>

#define PORT 48655

int main(int argc, char *argv[])
{
    if(argc != 2)
    {
        printf("Usage: %s \"Server's IP\" (e.g. 127.0.0.1)\n", argv[0]);
        exit(1);
    }

    struct sockaddr_in  server;
    unsigned int server_len;
    int bytes_read;
    // set server's address
    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(PORT);
    server.sin_addr.s_addr = inet_addr(argv[1]);

    // Create socket
    int sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock < 0)
    {
        perror("Can't create socket");
        exit(1);
    }

    char buf[BUFSIZ];
    // check server connection
    server_len = sizeof(server);
    strcpy(buf, "init\n");
    printf("init\n");
    if(sendto(sock, buf, BUFSIZ, MSG_NOSIGNAL, (struct sockaddr *) &server, server_len) < 0)
    {
        perror("Sendto error");
        exit(1);
    }


    // read data from std input, send it to server and receive the answer
    while(1)
    {
        memset(buf, 0, BUFSIZ);
        if((bytes_read = recvfrom(sock, buf, BUFSIZ, 0, (struct sockaddr *) &server, &server_len)) < 0)
        {
            perror("Receive error");
            exit(1);
        }
        printf("Server: %s", buf);
        memset(buf, 0, BUFSIZ);
        // read from std input
        if((bytes_read = read(STDIN_FILENO, buf, BUFSIZ)) < 0)
        {
            perror("Read from std input error");
            exit(1);
        }
        if(!strncmp(buf, ":q\n", 3)) break;

        // send message to server
        if(sendto(sock, buf, bytes_read, MSG_NOSIGNAL, (struct sockaddr *) &server, server_len) < 0)
        {
            perror("Error while sending message to server");
            exit(1);
        }

    }

    close(sock);
    return 0;
}