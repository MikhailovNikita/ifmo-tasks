#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <signal.h>

#define DELIMETERS "\n\r \0"
#define BUFFER_SIZE 32
//

FILE *file;
char *fileName;
int maxID = 0;
int totalLines = 0;
char *line;



int isName(char *string) {
    if (!string) return 0;
    int i;
    for (i = 0; i < strlen(string); i++) {
        if (!isalpha(string[i])) {
            return 0;
        }
    }
    return 1;
}


int isNumber(char *string) {
    if (!string) return 0;
    int i;
    int bracketsCounter = 0;
    int dashCounter = 0;
    for (i = 0; i < strlen(string); i++) {
        if (!(isdigit(string[i]) ||  string[i] == '+' ||  string[i] == '(' ||  string[i] == ')' ||
              string[i] == '-'))
            return 0;

        if (i>0 && string[i]=='+')
            return 0;

        if (string[i]=='-'){
            if(dashCounter){
                return 0;
            }else{
                dashCounter++;
            }
        }else{
            dashCounter = 0;
        }

        if (string[i] == '(') {
            if (bracketsCounter) {
                return 0;
            }
            else {
                bracketsCounter++;
            }
        }
        if (string[i] == ')') {
            if (bracketsCounter==1) {
                bracketsCounter++;
            }
            else {
                return 0;
            }
        }
    }
    return 1;
}


char *fixName(char *str) {
    char *res = (char *) malloc((strlen(str) + 1)*sizeof(char));
    int i;
    for (i = 0; i<strlen(str) + 1; i++) {
        res[i] = (char) tolower(str[i]);
    }
    res[strlen(str) + 1] = '\0';
    return res;
}


char *fixNumber(char *string) {
    char *number = (char *) malloc((strlen(string) + 1)* sizeof(char));
    int i, j = 0;
    for (i = 0; i < strlen(string) + 1; i++) {
        if (isdigit(string[i])) {
            number[j++] = string[i];
        }
    }
    return number;
}


void create() {
    char *name = strtok(NULL, DELIMETERS);
    char *number = strtok(NULL, DELIMETERS);
    if ((isName(name)) && (isNumber(number))) {
        name = fixName(name);
        number = fixNumber(number);
        fprintf(file, "%d %s %s\n", maxID, name, number);
        maxID++;
        totalLines++;
    }
    else {
        return;
    }
}


void readLine(int readFromFile) {
    char *buffer = malloc(BUFFER_SIZE);
    line = calloc(0, 0);
    int length = 0;

    while(1) {
        length += BUFFER_SIZE;
        line = realloc(line, length);
        if (readFromFile) {
            if (!fgets(buffer, BUFFER_SIZE, file))
                break;
        }
        else {
            if (!fgets(buffer, BUFFER_SIZE, stdin))
                break;
        }
        strcat(line, buffer);
        if (buffer[strlen(buffer) - 1] == '\n') {
            break;
        }
    }
    free(buffer);
}


void readFile(char *string, int type) {
    rewind(file);
    while(1) {
        readLine(1);
        char *curID, *curName, *curNumber;
        curID = strtok(line, DELIMETERS);
        curName = strtok(0, DELIMETERS);
        curNumber = strtok(0, DELIMETERS);
        if (!curID){
            return;
        }

        if (type) {
            if (!(strcmp(curNumber, string))) {
                printf("%s %s %s\n", curID, curName, curNumber);
            }
        }
        else {
            if (strstr(curName, string)) {
                printf("%s %s %s\n", curID, curName, curNumber);
            }
        }
        free(line);
    }
    free(string);
}


void find() {
    char *string = strtok(0, DELIMETERS);
    if (isName(string)) {
        string = fixName(string);
        readFile(string, 0);
    }
    if (isNumber(string)) {
        string = fixNumber(string);
        readFile(string, 1);
    }
}


char *getID(char *string) {
    char *copy = (char *) malloc((strlen(string)) * sizeof(char));
    strcpy(copy, string);
    char *curID = strtok(copy, DELIMETERS);
    return curID;
}


void delete() {
    char *ID = strtok(NULL, DELIMETERS);
    if (!ID) {
        return;
    }

    if (atoi(ID) < 0) {
        return;
    }
    int i, j;
    char **fileArray = (char **) malloc(totalLines * sizeof(char *));
    rewind(file);
    for (i = 0; i < totalLines; i++) {
        readLine(1);
        int length = strlen(line) + 1;
        fileArray[i] = (char *) malloc(length * sizeof(char));
        for (j = 0;j < length;j++) {
            fileArray[i][j] = line[j];
        }
        free(line);
    }
    fclose(file);

    file = fopen(fileName, "w+");
    for (i = 0;i < totalLines;i++) {
        char *curID = getID(fileArray[i]);
        if (!(strcmp(curID, ID))) {
            continue;
        }
        fprintf(file,"%s", fileArray[i]);
    }
    for (i = 0;i < totalLines;i++) {
        free(fileArray[i]);
    }
    free(fileArray);
    totalLines--;
}


void change() {
    char *ID = strtok(0, DELIMETERS);
    char *command = strtok(0, DELIMETERS);
    char *string = strtok(0, DELIMETERS);
    if ( !ID || !command || !string) {
        return;
    }
    if (atoi(ID) < 0) {
        return;
    }
    int changingName, i, j;

    if (!(strcmp(command, "name"))) {
        changingName = 1;
    }else{
        if (!(strcmp(command, "number"))) {
            changingName = 0;
        }
        else
        {
            return;
        }
    }

    if (changingName) {
        if (isName(string)) {
            string = fixName(string);
        }
        else {
            return;
        }
    }
    else {
        if (isNumber(string)) {
            string = fixNumber(string);
        }
        else {
            return;
        }

    }
    char **fileArray = (char **) malloc(totalLines * sizeof(char *));
    rewind(file);
    for (i = 0; i < totalLines; i++) {
        readLine(1);
        int length = strlen(line) + 1;
        fileArray[i] = (char *) malloc(length * sizeof(char));
        for (j = 0; j < length; j++) {
            fileArray[i][j] = line[j];
        }
        free(line);
    }
    fclose(file);
    file = fopen(fileName, "w+");
    for (i = 0; i < totalLines; i++) {
        char *curID = getID(fileArray[i]);
        if (!(strcmp(curID, ID))) {
            char *curID = strtok(fileArray[i], DELIMETERS);
            char *curName = strtok(0, DELIMETERS);
            char *curNumber = strtok(0, DELIMETERS);
            if (changingName) {
                fprintf(file, "%s %s %s\n", curID, string, curNumber);
            }
            else {
                fprintf(file, "%s %s %s\n", curID, curName, string);
            }
        }
        else {
            fprintf(file, "%s", fileArray[i]);
        }
    }


    for (i = 0; i < totalLines; i++) {
        free(fileArray[i]);
    }
    free(fileArray);
}

void close() {
    fclose(file);
    exit(0);
}

void getCommand() {
    if (line[0] == '\n') {
        return;
    }
    char *command = strtok(line, DELIMETERS);
    if (!(strcmp(command, "create"))) {
        create();
    }
    if (!(strcmp(command, "find"))) {
        find();
    }
    if (!(strcmp(command, "change"))) {
        change();
    }
    if (!(strcmp(command, "delete"))) {
        delete();
    }
    if (!(strcmp(command, "exit"))) {
        close();
    }
}

int main(int argc, const char **argv) {
    signal(SIGINT, close);
    if (argc!=2) {
        return 1;
    }

    file = fopen(argv[1], "r+");
    if (!file) {
        file = fopen(argv[1], "w+");
        if (!file) {
            exit(1);
        }
    }

    fileName = (char *) malloc((strlen(argv[1]) + 1) * sizeof(char));
    strcpy(fileName, argv[1]);
    fileName[strlen(argv[1])] = '\0';
    while(1){
        readLine(0);
        getCommand();
        fflush(stdout);
        free(line);
    }

}

