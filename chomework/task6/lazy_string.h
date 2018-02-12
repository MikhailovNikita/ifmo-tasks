//
// Created by nikita on 08.05.16.
//

#ifndef CPP_HOMEWORK_LAZY_STRING_H
#define CPP_HOMEWORK_LAZY_STRING_H

#include <string>
#include <iostream>
#include <memory>

class lazy_string {
    struct ls_char {
        friend class lazy_string;
        operator char() const;
        ls_char &operator=(char);

    private:
        ls_char(lazy_string *, size_t);

        const size_t index;
        lazy_string *const lazy_str;
    };


public:

    //Creates std::string copy of current lazy string
    operator std::string();

    //Creates empty lazy string
    lazy_string();

    //Creates lazy string from std::string
    lazy_string(const std::string &str);

    //Size() and length() both returns lazy string's size
    size_t size() const;
    size_t length() const;

    /*
     * Returns substring [start, start + count) substring if (start + count) < size(), otherwise
     * returns [start, lazy_string.size()
     * Throws std::out_of_range exception if start >= size();
     */
    lazy_string substr(size_t start = 0, size_t count = std::string::npos);

    /*
     * Returns requested lazy string's character
     * Throws std::out_of_range exception if index > size()
     */
    ls_char at(size_t index);
    char at(size_t index) const;

    /*
     * Returns requested lazy string's character
     */
    ls_char operator[](size_t index);
    char operator[](size_t index) const;

    //Replaces the current lazy string with string extracted from input stream
    friend std::istream &operator>>(std::istream &is, lazy_string &ls);

    //Sends characters sequence to output stream
    friend std::ostream &operator<<(std::ostream &os, lazy_string &ls);

private:
    size_t shift, ls_size;
    std::shared_ptr<std::string> data;
    lazy_string(size_t shift, size_t ls_size, std::shared_ptr<std::string> data);

};

#endif //CPP_HOMEWORK_LAZY_STRING_H
