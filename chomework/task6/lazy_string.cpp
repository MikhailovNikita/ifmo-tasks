//
// Created by nikita on 08.05.16.
//

#include <iostream>
#include "lazy_string.h"

using namespace std;

size_t lazy_string::size() const {
    return ls_size;
}

size_t lazy_string::length() const {
    return ls_size;
}

lazy_string::operator std::string() {
    return data->substr(shift, ls_size);
}

lazy_string::lazy_string() {
    this->shift = 0;
    this->ls_size = 0;
    this->data = make_shared<string>("");
}

lazy_string::lazy_string(const std::string &str) {
    this->shift = 0;
    this->ls_size = str.size();
    this->data = make_shared<string>(str);
}

lazy_string::lazy_string(size_t shift, size_t ls_size, std::shared_ptr<std::string> data) {
    this->shift = shift;
    this->ls_size = ls_size;
    this->data = data;
}

lazy_string lazy_string::substr(size_t pos, size_t len) {
    if (pos > ls_size)
        throw std::out_of_range("index is out of lazy_string's range");

    if (pos + len > ls_size) {
        return lazy_string(shift + pos, (ls_size - pos), data);
    } else {
        return lazy_string(shift + pos, len, data);
    }
}

char lazy_string::at(size_t index) const {
    if (index > ls_size - 1) throw out_of_range("index is out of lazy_string's range");
    return (*data)[shift + index];
}

char lazy_string::operator[](size_t index) const {
    return (*data)[shift + index];
}

lazy_string::ls_char lazy_string::at(size_t index) {
    if (index > ls_size - 1) throw out_of_range("index is out of lazy_string's range");
    return ls_char(this, index);
}

lazy_string::ls_char lazy_string::operator[](size_t index) {
    return ls_char(this, index);
}

istream &operator>>(istream &input, lazy_string &ls) {
    auto str = make_shared<string>();
    input >> *str;

    ls.data = str;
    ls.shift = 0;
    ls.ls_size = str->length();

    return input;
}

std::ostream &operator<<(std::ostream &os, lazy_string &ls) {
    for (size_t i = 0; i < ls.size(); i++) os << ls[i];
    return os;
}

lazy_string::ls_char::ls_char(lazy_string *lazy_str, size_t index) : lazy_str(lazy_str), index(index) { }

lazy_string::ls_char &lazy_string::ls_char::operator=(char c) {
    if (lazy_str->data.use_count() > 1) {
        lazy_str->shift = 0;
        lazy_str->data = std::make_shared<std::string>(lazy_str->data->substr(lazy_str->shift, lazy_str->ls_size));
    }

    (*lazy_str->data)[lazy_str->shift + index] = c;
    return *this;
}

lazy_string::ls_char::operator char() const {
    return (*lazy_str->data)[lazy_str->shift + index];
}







