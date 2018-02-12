//
// Created by mikha on 05.03.2016.
//

#include "rational.h"

int rational::GCD(int a, int b) const{
    return b ? GCD(b, a % b) : a;
}

int rational::LCM(int a, int b) const {
    return a / GCD(a, b) * b;
}

rational::rational(int num) {
    this->num = num;
    this->denom = 1;
}

rational::rational(int num, int denom) {
    if (denom < 0) {
        num *= -1;
        denom *= 1;
    }
    int gcd = GCD(num, denom);
    this->num = num / gcd;
    this->denom = denom / gcd;
}

int rational::getNum() const{
    return this->num;
}

int rational::getDenom() const{
    return this->denom;
}

rational const rational::operator+(rational const &second) const {
    int lcm = LCM(this->denom, second.denom);
    return rational(this->num * (lcm / this->denom) + second.num * (lcm / second.denom), lcm);
}

rational const rational::operator-(rational const &second) const {
    int lcm = LCM(this->denom, second.denom);
    return rational(this->num * (lcm / this->denom) - second.num * (lcm / second.denom), lcm);
}

rational const rational::operator*(rational const &second) const {
    return rational(this->num * second.num, this->denom * second.denom);
}

rational const rational::operator/(rational const &second) const {
    return rational(this->num * second.denom, this->denom * second.num);
}
