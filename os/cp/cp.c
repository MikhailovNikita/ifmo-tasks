#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

#define BUF_SZ 1024
#define FILENM_SZ 128

int dir_exists(const char *path) {
    DIR *dir = opendir(path);
    int is_dir;

    if (dir) {
        is_dir = 1;
    } else {
        is_dir = 0;
    }
    if (ENOENT == errno) {
        closedir(dir);
    }
    return is_dir;
}

int is_regular_file(char *path) {
    struct stat path_stat;
    stat(path, &path_stat);
    return S_ISREG(path_stat.st_mode);
}

void copy_dir(char *source, char *destination) {

    char *dirn = (char *) malloc(FILENM_SZ);
    DIR *dir = NULL;
    struct dirent *direl;
    char *init_src = (char *) malloc(FILENM_SZ);
    char *init_dst = (char *) malloc(FILENM_SZ);
    char *src = (char *) malloc(FILENM_SZ);
    char *dst = (char *) malloc(FILENM_SZ);
    strcat(strcat(init_src, source), "/");
    strcat(strcat(init_dst, destination), "/");
    strcpy(src, init_src);
    strcpy(dst, init_dst);
    mkdir(destination, 0777);


    if ((dir = opendir(init_src)) == NULL) {
        perror("Can't open dir");
        exit(1);
    }

    while ((direl = readdir(dir))) {
        //printf("!!: %s\n", direntp->d_name);

        //check for directory entry and exits
        if (!strcmp(direl->d_name, ".") || !(strcmp(direl->d_name, ".."))) continue;
        strcat(dst, direl->d_name);
        strcat(src, direl->d_name);
        if (is_regular_file(src)) {
            printf("Copy file %s %s\n", src, dst);
            copy_file(src, dst);
        } else if (dir_exists(src)) {
            copy_dir(src, dst);
        }


        strcpy(src, init_src);
        strcpy(dst, init_dst);
    }
    closedir(dir);

    free(src);
    free(init_src);
    free(dst);
    free(init_dst);
    free(dirn);

}


void copy_file(char *src, char *dest) {
    if (!strcmp(src, dest)) {
        printf("Source and destination are equal \n");
        exit(0);
    }

    int srcf = open(src, O_RDONLY);
    int dstf = open(dest, O_WRONLY | O_CREAT | O_TRUNC, 0777);

    if (srcf < 0) {
        printf("Couldn't read source file \n");
        exit(1);
    }

    //destination path is not leading to regular file
    //check for directory, if not - display error
    if (dstf < 0) {
        if (dir_exists(dest)) {
            char *dir = (char *) malloc(128);
            dstf = open(strcat(strcat(strcat(dir, dest), "/"), src), O_WRONLY | O_CREAT | O_TRUNC, 0700);

            if (dstf < 0) {
                printf("File %s\n", dir);
                perror("Can't create destination file");
                exit(1);
            }

            free(dir);
        } else {
            printf("File %s\n", dest);
            perror("Can't create destination file");
            exit(1);
        }
    }


    char *buf = (char *) malloc(BUF_SZ);
    int buf_read;

    while ((buf_read = read(srcf, buf, BUF_SZ))) {
        if (write(dstf, buf, buf_read) != buf_read) {
            perror("Error while writing file");
            exit(1);
        }
    }

    free(buf);
    if (close(srcf)) perror("Can't close source file");
    if (close(dstf)) perror("Can't close destination file");
    printf("Copying %s succeeded\n", src);
}

void showHelp() {
    printf("Usage:\n");
    printf("Copy one file to another : `cp file1 file2`\n");
    printf("Copy many files to directory : `cp file1 file2 ... fileN dir`\n");
    printf("Recursive directory copying: `cp -r dir1 dir2`\n");
}

int main(int argc, char **argv) {


    if (argc < 3) {
        showHelp();
        exit(0);
    }

    switch (argc) {
        case 3:
            //copying one file to dir
            if (is_regular_file(argv[1])) {
                copy_file(argv[1], argv[2]);
                printf("Complete.\n");
            } else {
                printf("To copy directory use -r flag\nFor example cp -r folder1 folder2");
            }
            break;
        case 4:
            if (!strcmp(argv[1], "-r")) {

                //dir to dir copying
                if (dir_exists(argv[3])) {
                    char *path = (char *) malloc(FILENM_SZ);
                    strcpy(path, argv[3]);
                    strcat(strcat(path, "/"), argv[2]);
                    copy_dir(argv[2], path);
                } else
                    copy_dir(argv[2], argv[3]);
                break;
            }

        default:
            //copying lots of files to dir
        {
            for (int i = 1; i < argc - 1; i++) {
                /* segfault?
                if(!dir_exists(argv[argc - 1])){
                    printf("Directory \"%s\" doesnt' exist");
                    exit(1);
                }
               */
                if (is_regular_file(argv[i])) {
                    copy_file(argv[i], argv[argc - 1]);
                } else {
                    printf("File #%d is not a regular file. Skipping.\n", i);
                }
            }
        }


    }
}
