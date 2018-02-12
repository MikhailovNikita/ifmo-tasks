//
// Created by mikha on 05.03.2016.
//

#ifndef RATIONAL_RATIONAL_H
#define RATIONAL_RATIONAL_H


class rational {
private:
    int num;
    int denom;

    int GCD(int a, int b) const;

    int LCM(int a, int b) const;
public:
    rational(int num);

    rational(int num, int denom);

    int getNum() const;

    int getDenom() const;

    rational const operator+(rational const &) const;

    rational const operator-(rational const &) const;

    rational const operator*(rational const &) const;

    rational const operator/(rational const &) const;
};

#endif //RATIONAL_RATIONAL_H
