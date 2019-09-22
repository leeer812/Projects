#include "const.h"
#include "transplant.h"
#include "debug.h"
#include "errno.h"

#ifdef _STRING_H
#error "Do not #include <string.h>. You will get a ZERO."
#endif

#ifdef _STRINGS_H
#error "Do not #include <strings.h>. You will get a ZERO."
#endif

#ifdef _CTYPE_H
#error "Do not #include <ctype.h>. You will get a ZERO."
#endif

# define mag_char_1 0x0c
# define mag_char_2 0x0d
# define mag_char_3 0xed

/*
 * You may modify this file and/or move the functions contained here
 * to other source files (except for main.c) as you wish.
 *
 * IMPORTANT: You MAY NOT use any array brackets (i.e. [ and ]) and
 * you MAY NOT declare any arrays or allocate any storage with malloc().
 * The purpose of this restriction is to force you to use pointers.
 * Variables to hold the pathname of the current file or directory
 * as well as other data have been pre-declared for you in const.h.
 * You must use those variables, rather than declaring your own.
 * IF YOU VIOLATE THIS RESTRICTION, YOU WILL GET A ZERO!
 *
 * IMPORTANT: You MAY NOT use floating point arithmetic or declare
 * any "float" or "double" variables.  IF YOU VIOLATE THIS RESTRICTION,
 * YOU WILL GET A ZERO!
 */

int curMode = 0;
long curSize = 0;
int name_length = 0;



void name_push(char *name) {
    int i = 0;
    while (*(name + i) != '\0') {
        *(name_buf + i) = *(name + i);
        i++;
    }
    *(name_buf + i) = '\0';
}

int readnametobuf(int len) {
    int i = 0;
    while (i < len) {
        errno = 0;
        char curChar = getchar();
        if (ferror(stdin) != 0 || errno != 0) return 0;
        *(name_buf + i) = curChar;
        i++;
        name_length++;
    }
    return 1;
}

struct Record {
    char type;
    int depth;
    long size;
    int mode;
    long fileSize;
    char* name;
    char* fileData;
};

void resetNameBuff() {
    for (int i =0; i< name_length; i++) {
        *(name_buf + i) = '\0';
    }
}

int setdepth(int *depth) {
    unsigned char *p = (unsigned char*)depth;
    char curChar = '\0';
    int i = 3;
    for(i = 3; i >= 0; i--) {
        errno = 0;
        char curChar = getchar();
        if (ferror(stdin) != 0 || errno != 0) return 0;
        *(p + i) = curChar;
    }
    return 1;
}

int readDepth(char depth) {
    int byteDepth = 0;
    unsigned char *p = (unsigned char*)&byteDepth;
    char curChar = '\0';
    int i = 3;
    for(i = 3; i >= 0; i--) {
        errno = 0;
        char curChar = getchar();
        if (ferror(stdin) != 0 || errno != 0) return 0;
        *(p + i) = curChar;
    }
    if (byteDepth == depth) return 1;
    return 0;
}

int setsize(long *size) {
    unsigned char *p = (unsigned char*)size;
    char curChar = '\0';
    int i = 7;
    for(int i = 7; i >= 0; i--) {
        errno = 0;
        char curChar = getchar();
        if (ferror(stdin) != 0 || errno != 0) return 0;
        *(p + i) = curChar;
    }
    return 1;
}

int readMagic() {
    errno = 0;
    int c = getchar();
    if (ferror(stdin) != 0 || errno != 0) return -1;
    if (c != mag_char_1) {
        return 0;
    }
    errno = 0;
    c = getchar();
    if (ferror(stdin) != 0 || errno != 0) return -1;
    if (c != mag_char_2) {
        return 0;
    }
    errno = 0;
    c = getchar();
    if (ferror(stdin) != 0 || errno != 0) return -1;
    if (c != mag_char_3) {
        return 0;
    }
    return 1;
}

char readType(char type) {
    errno = 0;
    char c = getchar();
    if (ferror(stdin) != 0 || errno != 0) return -1;
    if (c != type) {
        return -1;
    }
    return type;
}

char getTypeFromStart() {
    if (readMagic() != 1) return -1;
    errno = 0;
    char type = getchar();
    if (ferror(stdin) != 0 || errno != 0) return -1;

    if (type == 0x2) return 0x2;
    if (type == 0x5) return 0x5;
    return -1;
}



struct MetaData {
    int mode;
    long size;
};

 /*
  * Custom functions
  */
int myStrCmp(char *str1, char *str2) {
    int i = 0;
    while(*(str1 + i) == *(str2 + i)) {
        if (*(str1 + i) == '\0' || *(str2 + i) == '\0') break;
        i++;
    }
    if (*(str1 + i) == '\0' && *(str2 + i) == '\0') return 1;
    return 0;
}

int reset() {
    global_options = 0;
    return -1;
}

int hsd(char *argv) {
    if (*argv != '-') return -1;
    if (*(argv+1) == 'h' && *(argv+2) == '\0') return 2;
    if (*(argv+1) == 's' && *(argv+2) == '\0') return 3;
    if (*(argv+1) == 'd' && *(argv+2) == '\0') return 1;
    return -1;
}

int cp(char *argv) {
    if (*argv != '-') return -1;
    if (*(argv+1) == 'c' && *(argv+2) == '\0') return 1;
    if (*(argv+1) == 'p' && *(argv+2) == '\0') return 2;
    return -1;
}

int p(char *argv) {
    if (*argv != '-') return -1;
    if (*(argv+1) == 'p' && *(argv+2) == '\0') return 1;
    return -1;
}

int pdir(char *argv) {
    if (*argv != '-' && *argv != '\0') {
        path_init(argv);
        return 1;
    }
    return -1;
}

int c(char *argv) {
    if (*argv != '-') return -1;
    if (*(argv+1) == 'c' && *(argv+2) == '\0') return 1;
    return -1;
}

/*
 * A function that returns printable names for the record types, for use in
 * generating debugging printout.
 */
static char *record_type_name(int i) {
    switch(i) {
    case START_OF_TRANSMISSION:
    return "START_OF_TRANSMISSION";
    case END_OF_TRANSMISSION:
    return "END_OF_TRANSMISSION";
    case START_OF_DIRECTORY:
    return "START_OF_DIRECTORY";
    case END_OF_DIRECTORY:
    return "END_OF_DIRECTORY";
    case DIRECTORY_ENTRY:
    return "DIRECTORY_ENTRY";
    case FILE_DATA:
    return "FILE_DATA";
    default:
    return "UNKNOWN";
    }
}

int checkValLen(char *name) {
    int len = 0;
    int path_buf_size = sizeof(path_buf) / sizeof(char);
    while (*(name + len) != '\0') {
        len++;
    }

    if (len > path_buf_size) {
        return -1;
    } else return len;
}

int checkValPath(char *name) {
    int len = 0;
    while (*(name + len) != '\0') {
        if (*(name + len) == '/') return -1;
        len++;
    }
    return 0;
}

void addPath(char *name) {
    int len = 0;
    while (*(name + len) != '\0') {
        *(path_buf + len + path_length) = *(name + len);
        len++;
    }

    *(path_buf + len + path_length) = '\0';
    path_length += len;
}

/*
 * @brief  Initialize path_buf to a specified base path.
 * @details  This function copies its null-terminated argument string into
 * path_buf, including its terminating null byte.
 * The function fails if the argument string, including the terminating
 * null byte, is longer than the size of path_buf.  The path_length variable
 * is set to the length of the string in path_buf, not including the terminating
 * null byte.
 *
 * @param  Pathname to be copied into path_buf.
 * @return 0 on success, -1 in case of error
 */
int path_init(char *name) {

    if (checkValLen(name) == -1) return -1;
    addPath(name);
    return 0;
}

/*
 * @brief  Append an additional component to the end of the pathname in path_buf.
 * @details  This function assumes that path_buf has been initialized to a valid
 * string.  It appends to the existing string the path separator character '/',
 * followed by the string given as argument, including its terminating null byte.
 * The length of the new string, including the terminating null byte, must be
 * no more than the size of path_buf.  The variable path_length is updated to
 * remain consistent with the length of the string in path_buf.
 *
 * @param  The string to be appended to the path in path_buf.  The string must
 * not contain any occurrences of the path separator character '/'.
 * @return 0 in case of success, -1 otherwise.
 */
int path_push(char *name) {
    if (checkValLen(name) == -1) return -1;
    if (checkValPath(name) == -1) return -1;

    int len = 0;
    *(path_buf + path_length++) = '/';
    addPath(name);

    return 0;
}

/*
 * @brief  Remove the last component from the end of the pathname.
 * @details  This function assumes that path_buf contains a non-empty string.
 * It removes the suffix of this string that starts at the last occurrence
 * of the path separator character '/'.  If there is no such occurrence,
 * then the entire string is removed, leaving an empty string in path_buf.
 * The variable path_length is updated to remain consistent with the length
 * of the string in path_buf.  The function fails if path_buf is originally
 * empty, so that there is no path component to be removed.
 *
 * @return 0 in case of success, -1 otherwise.
 */
int path_pop() {
    if (path_length == 0) return -1;
    int len = path_length;
    while (*(path_buf + len) != '/' && len > 0) {
        *(path_buf + len) = '\0';
        len--;
    }
    *(path_buf + len) = '\0';
    path_length = len;
    return 0;
}

int outMagicSeq() {
    errno = 0;
    putchar(mag_char_1);
    if (errno != 0 || ferror(stdout) != 0) return -1;
    errno = 0;
    putchar(mag_char_2);
    if (errno != 0 || ferror(stdout) != 0) return -1;
    errno = 0;
    putchar(mag_char_3);
    if (errno != 0 || ferror(stdout) != 0) return -1;
    return 3;
}

int outType(int type) {
    errno = 0;
    putchar(type);
    if (errno != 0 || ferror(stdout) != 0) return -1;
    return 1;
}

int outDepth(int depth) {
    unsigned char *p = (unsigned char*)&depth;
    for (int i = 3; i >= 0; i--) {
        errno = 0;
        putchar(*(p + i));
        if (errno != 0 || ferror(stdout) != 0) return -1;
    }
    return 4;
}

int outSize(int size) {
    unsigned long newSize = size + 8;
    unsigned char *p = (unsigned char*)&newSize;
    for (int i = 7; i >= 0; i--) {
        errno = 0;
        putchar(*(p + i));
        if (errno != 0 || ferror(stdout) != 0) return -1;
    }
    return 1;
}

int startTransmission() {
    int curSize = 0;
    int temp = 0;
    temp = outMagicSeq();
    if (temp == -1) return -1;
    curSize += temp;
    temp = outType(0);
    if (temp == -1) return -1;
    curSize += temp;
    temp = outDepth(0);
    if (temp == -1) return -1;
    curSize += temp;
    outSize(curSize);
    return 1;
}

int endTransmission() {
    int curSize = 0;
    int temp = 0;
    temp = outMagicSeq();
    if (temp == -1) return -1;
    curSize += temp;
    temp = outType(1);
    if (temp == -1) return -1;
    curSize += temp;
    temp = outDepth(0);
    if (temp == -1) return -1;
    curSize += temp;
    outSize(curSize);
    return 1;
}

int startDirRec(int depth) {
    int curSize = 0;
    int temp = 0;
    temp = outMagicSeq();
    if (temp == -1) return -1;
    curSize += temp;
    temp = outType(2);
    if (temp == -1) return -1;
    curSize += temp;
    temp = outDepth(depth);
    if (temp == -1) return -1;
    curSize += temp;
    outSize(curSize);
    return 1;
}

int endDirRec(int depth) {
    int curSize = 0;
    int temp = 0;
    temp = outMagicSeq();
    if (temp == -1) return -1;
    curSize += temp;
    temp = outType(3);
    if (temp == -1) return -1;
    curSize += temp;
    temp = outDepth(depth);
    if (temp == -1) return -1;
    curSize += temp;
    outSize(curSize);
    return 1;
}

int outMetaData(mode_t mode, off_t size) {
    unsigned char *p = (unsigned char*)&mode;
    for (int i = 3; i >= 0; i--) {
        errno = 0;
        putchar(*(p + i));
        if(ferror(stdout) != 0 || errno != 0) return -1;
    }
    p = (unsigned char*)&size;
    for (int i = 7; i >= 0; i--) {
        errno = 0;
        putchar(*(p + i));
        if(ferror(stdout) != 0 || errno != 0) return -1;
    }
    return 1;
}

int getFileNameSize(char *name) {
    int curSize = 0;
    while (*(name + curSize) != '\0') {
        curSize++;
    }
    return curSize;
}

int outFileName(char *name) {
    int i = 0;
    while (*(name + i) != '\0') {
        errno = 0;
        putchar(*(name + i));
        if(ferror(stdout) != 0 || errno != 0) return -1;
        i++;
    }
    return 1;
}

int dirEntryRec(int depth, mode_t mode, off_t size, char *name) {
    int curSize = 0;
    int temp = 0;
    temp = outMagicSeq();
    if (temp == -1) return -1;
    curSize += temp;
    temp = outType(4);
    if (temp == -1) return -1;
    curSize += temp;
    temp = outDepth(depth);
    if (temp == -1) return -1;
    curSize += temp;
    curSize += 12;
    curSize += getFileNameSize(name);
    outSize(curSize);
    if(outMetaData(mode, size) == -1) return -1;
    if (outFileName(name) == -1) return -1;
    return 1;
}

int outFileData(off_t size) {
    errno = 0;
    FILE *f = fopen(path_buf, "r");
    if (errno != 0) return -1;
    for(int i = 0; i < size; i++) {
        errno = 0;
        putchar(fgetc(f));
        if (errno != 0) return -1;
    }
    errno = 0;
    fclose(f);
    if (errno != 0) return -1;
    return 1;
}

int fileDataRec(int depth, off_t size) {
    int curSize = 0;
    int temp = 0;
    temp = outMagicSeq();
    if (temp == -1) return -1;
    curSize += temp;
    temp = outType(5);
    if (temp == -1) return -1;
    curSize += temp;
    temp = outDepth(depth);
    if (temp == -1) return -1;
    curSize += temp;
    curSize += size;
    outSize(curSize);
    if(outFileData(size) == -1) return -1;
    return 1;
}

int checkExistingDir() {
    DIR *dir = opendir(path_buf);
    if (dir) {
        return 1;
    } else return 0;
}

/*
 * @brief Deserialize directory contents into an existing directory.
 * @details  This function assumes that path_buf contains the name of an existing
 * directory.  It reads (from the standard input) a sequence of DIRECTORY_ENTRY
 * records bracketed by a START_OF_DIRECTORY and END_OF_DIRECTORY record at the
 * same depth and it recreates the entries, leaving the deserialized files and
 * directories within the directory named by path_buf.
 *
 * @param depth  The value of the depth field that is expected to be found in
 * each of the records processed.
 * @return 0 in case of success, -1 in case of an error.  A variety of errors
 * can occur, including depth fields in the records read that do not match the
 * expected value, the records to be processed to not being with START_OF_DIRECTORY
 * or end with END_OF_DIRECTORY, or an I/O error occurs either while reading
 * the records from the standard input or in creating deserialized files and
 * directories.
 */

int deserialize_directory(int depth) {
    long recordSize = 0;
    long fileSize = 0;
    int mode = curMode;
    //if (readMagic() == 0) return -1;
    //if (readType('2') == -1) return -1;
    if (readDepth(depth) == 0) return -1;
    if (setsize(&recordSize) == 0) return -1;
    struct stat stat_buf;
    if (stat(path_buf, &stat_buf) == 0) {
        if (!(global_options & 0x08)) return -1;
    } else {
        if (name_buf != '\0') {
            if (mkdir(path_buf, 0700) != 0) return -1;
        }
    }
    if (readMagic() == 0) return -1;
    char type = '\0';
    while(readType(0x4) != -1) {
        if(readDepth(depth) == 0) return -1;
        if(setsize(&recordSize) == 0) return -1; // total record header size stored in size
        if(setdepth(&curMode) == 0) return -1; // file mode is stored in curMode
        if(setsize(&fileSize) == 0) return -1; // file size stored in fileSize
        if(readnametobuf(recordSize - 28) == 0) return -1;
        path_push(name_buf);
        curSize = fileSize;
        // end directory Entry -> read for start of dir or file data else error
        if (readMagic() == 0) return -1;
        type = getchar();
        if (type == 0x2) {
            if (deserialize_directory(depth + 1) == -1) return -1;
        } else if (type == 0x5) {
            if (deserialize_file(depth) == -1) return -1;
        } else return -1;
        if (readMagic() == 0) return -1;
    }
    if (type != 0x3) return -1;
    // end of directory
    if(readDepth(depth) == 0) return -1;
    if(setsize(&recordSize) == 0) return -1;
    chmod(path_buf, mode & 0777);
    resetNameBuff();
    path_pop();
    return 0;
}

/*
 * @brief Deserialize the contents of a single file.
 * @details  This function assumes that path_buf contains the name of a file
 * to be deserialized.  The file must not already exist, unless the ``clobber''
 * bit is set in the global_options variable.  It reads (from the standard input)
 * a single FILE_DATA record containing the file content and it recreates the file
 * from the content.
 *
 * @param depth  The value of the depth field that is expected to be found in
 * the FILE_DATA record.
 * @return 0 in case of success, -1 in case of an error.  A variety of errors
 * can occur, including a depth field in the FILE_DATA record that does not match
 * the expected value, the record read is not a FILE_DATA record, the file to
 * be created already exists, or an I/O error occurs either while reading
 * the FILE_DATA record from the standard input or while re-creating the
 * deserialized file.
 */

int deserialize_file(int depth) {
    struct stat stat_buf;
    if (stat(path_buf, &stat_buf) == 0) {
        if (!(global_options & 0x08)) return -1;
    }
    errno = 0;
    FILE *f = fopen(path_buf, "w");
    if (f == NULL || errno != 0) return -1;
    if (readDepth(depth) == 0) return -1;
    long fileHeaderSize = 0;
    if (setsize(&fileHeaderSize) == 0) return -1;
    long fileSize = fileHeaderSize - 16;
    // if (fileSize != curSize) return -1;
    for (int i = 0; i < fileSize; i++) {
        errno = 0;
        char c = getchar();
        if (ferror(stdin) != 0 || errno != 0) return -1;
        errno = 0;
        fputc(c, f);
        if (ferror(stdin) != 0 || errno != 0) return -1;
    }
    errno = 0;
    if (fclose(f) != 0 || errno != 0) return -1;
    chmod(path_buf, curMode & 0777);
    resetNameBuff();
    path_pop();
    return 0;
}

/*
 * @brief  Serialize the contents of a directory as a sequence of records written
 * to the standard output.
 * @details  This function assumes that path_buf contains the name of an existing
 * directory to be serialized.  It serializes the contents of that directory as a
 * sequence of records that begins with a START_OF_DIRECTORY record, ends with an
 * END_OF_DIRECTORY record, and with the intervening records all of type DIRECTORY_ENTRY.
 *
 * @param depth  The value of the depth field that is expected to occur in the
 * START_OF_DIRECTORY, DIRECTORY_ENTRY, and END_OF_DIRECTORY records processed.
 * Note that this depth pertains only to the "top-level" records in the sequence:
 * DIRECTORY_ENTRY records may be recursively followed by similar sequence of
 * records describing sub-directories at a greater depth.
 * @return 0 in case of success, -1 otherwise.  A variety of errors can occur,
 * including failure to open files, failure to traverse directories, and I/O errors
 * that occur while reading file content and writing to standard output.
 */

int serialize_directory(int depth) {
    if (startDirRec(depth) == -1) return -1;
    DIR *dir = opendir(path_buf);
    if (dir == NULL) return -1;
    errno = 0;
    struct dirent *de = readdir(dir);
    if (errno != 0) return -1;
    while (de != NULL) {
        if (myStrCmp((*de).d_name, "..") || myStrCmp((*de).d_name, ".")) {
            errno = 0;
            de = readdir(dir);
            if (errno != 0) return -1;
            continue;
        }
        path_push((*de).d_name);
        struct stat stat_buf;
        if (stat(path_buf, &stat_buf) == -1) return -1;
        if (dirEntryRec(depth, stat_buf.st_mode, stat_buf.st_size, (*de).d_name) == -1) return -1;

        if (S_ISDIR(stat_buf.st_mode)) {
            if (serialize_directory(depth+1) == -1) return -1;
        } else if (S_ISREG(stat_buf.st_mode)) {
            if (serialize_file(depth, stat_buf.st_size) == -1) return -1;
        }
        path_pop();
        errno = 0;
        de = readdir(dir);
        if (errno != 0) return -1;
    }
    if (endDirRec(depth) == -1) return -1;
    return 0;
}

/*
 * @brief  Serialize the contents of a file as a single record written to the
 * standard output.
 * @details  This function assumes that path_buf contains the name of an existing
 * file to be serialized.  It serializes the contents of that file as a single
 * FILE_DATA record emitted to the standard output.
 *
 * @param depth  The value to be used in the depth field of the FILE_DATA record.
 * @param size  The number of bytes of data in the file to be serialized.
 * @return 0 in case of success, -1 otherwise.  A variety of errors can occur,
 * including failure to open the file, too many or not enough data bytes read
 * from the file, and I/O errors reading the file data or writing to standard output.
 */
int serialize_file(int depth, off_t size) {
    if (fileDataRec(depth, size) == -1) return -1;
    return 0;
}

/**
 * @brief Serializes a tree of files and directories, writes
 * serialized data to standard output.
 * @details This function assumes path_buf has been initialized with the pathname
 * of a directory whose contents are to be serialized.  It traverses the tree of
 * files and directories contained in this directory (not including the directory
 * itself) and it emits on the standard output a sequence of bytes from which the
 * tree can be reconstructed.  Options that modify the behavior are obtained from
 * the global_options variable.
 *
 * @return 0 if serialization completes without error, -1 if an error occurs.
 */
int serialize() {
    int depth = 0;

    if (opendir(path_buf) == NULL) return -1;
    if (startTransmission() == -1) return -1;
    if (serialize_directory(depth + 1) == -1) return -1;
    if (endTransmission() == -1) return -1;
    return 0;
}

/**
 * @brief Reads serialized data from the standard input and reconstructs from it
 * a tree of files and directories.
 * @details  This function assumes path_buf has been initialized with the pathname
 * of a directory into which a tree of files and directories is to be placed.
 * If the directory does not already exist, it is created.  The function then reads
 * from from the standard input a sequence of bytes that represent a serialized tree
 * of files and directories in the format written by serialize() and it reconstructs
 * the tree within the specified directory.  Options that modify the behavior are
 * obtained from the global_options variable.
 *
 * @return 0 if deserialization completes without error, -1 if an error occurs.
 */
int deserialize() {
    int depth = 0;
    long size = 0;
    if (readMagic() == 0) return -1;
    if (readType(0x0) == -1) return -1;
    if (readDepth(0) == 0) return -1;
    if (setsize(&size) == 0) return -1;
    if (readMagic() == 0) return -1;
    if (readType(0x2) == -1) return -1;
    if (deserialize_directory(depth+1) == -1) return -1;
    if (readMagic() == 0) return -1;
    if (readType(0x1) == -1) return -1;
    if (readDepth(0) == 0) return -1;
    if (setsize(&size) == 0) return -1;
    return 0;
}

/**
 * @brief Validates command line arguments passed to the program.
 * @details This function will validate all the arguments passed to the
 * program, returning 0 if validation succeeds and -1 if validation fails.
 * Upon successful return, the selected program options will be set in the
 * global variable "global_options", where they will be accessible
 * elsewhere in the program.
 *
 * @param argc The number of arguments passed to the program from the CLI.
 * @param argv The argument strings passed to the program from the CLI.
 * @return 0 if validation succeeds and -1 if validation fails.
 * Refer to the homework document for the effects of this function on
 * global variables.
 * @modifies global variable "global_options" to contain a bitmap representing
 * the selected options.
 */
int validargs(int argc, char **argv)
{
    argv++;
    if (argc == 1) return reset();
    if (hsd(*argv) == -1) return reset();
    if (hsd(*argv) == 2) {
            global_options = global_options | 0x1;
        return 0;
    } else {
        // more than 4 args too many
        if (argc > 5) return reset();
        // first arg is s/d
        if (hsd(*argv) == 1) {
            global_options = global_options | 0x4;
            if (argc == 2) return 0;
            argv++;
            // first arg is d
            if (cp(*argv) == -1) return reset();
            if (cp(*argv) == 1) {
                // c, check p
                global_options = global_options | 0x8;
                if (argc == 3) return 0;
                argv++;
                if (p(*argv) == 1) {
                    if (argc == 4) return reset();
                    if (pdir(*(argv+1)) == 1) {
                        return 0;
                    } else return reset();
                }
                return reset();
            } else {
                // p
                argv++;
                // check dir
                if (argc == 3) return reset();
                if (pdir(*argv) == -1) return reset();
                argv++;
                // if 3 args, there is no c
                if (argc == 4) return 0;
                // check if 4th arg is c
                if (c(*argv) == 1) {
                    global_options = global_options | 0x8;
                    return 0;
                } else return reset();
            }
        } else {
            // first arg is s
            global_options = global_options | 0x2;
            if (argc == 2) return 0;
            argv++;
            if (p(*argv) == 1) {
                if (argc == 3) return reset();
                argv++;
                if (pdir(*argv) == -1) return reset();
                global_options = global_options | 0x8;
                return 0;
            } else return reset();
        }
    }
}
