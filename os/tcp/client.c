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



int main(int argc, char *argv[])
{
    if(argc != 2)
    {
        printf("Usage: %s \"Server IP\"(e.g. 127.0.0.1)", argv[0]);
        exit(1);
    }

    // create socket
    int sck = socket(AF_INET, SOCK_STREAM, 0);
    if(sck < 0)
    {
        perror("Can't create socket");
        exit(1);
    }

    // set address
    struct sockaddr_in address;
    memset(&address, 0, sizeof(address));
    address.sin_family = AF_INET;
    address.sin_port = htons(PORT);
    address.sin_addr.s_addr = inet_addr(argv[1]);

    // connect to server
    if(connect(sck, (struct sockaddr *) &address, sizeof(address)) < 0)
    {
        perror("Can't connect to server");
        exit(1);
    }


    char buf[BUFSIZ];
    while(1)
    {
        memset(buf, 0, BUFSIZ);
        // waiting server's answer
        read(sck, buf, BUFSIZ);
        if(!strncmp(buf, "|DISCONNECT_TOKEN|\n", 18)){
            printf("Disconnecting\n");
            break;
        }
        printf("Server: %s", buf);

        // read from std input, send to server
        memset(buf, 0, BUFSIZ);
        read(STDIN_FILENO, buf, BUFSIZ);
        send(sck, buf, BUFSIZ, MSG_NOSIGNAL);
    }


    if(close(sck) < 0)
    {
        perror("Can't close socket");
    }

}