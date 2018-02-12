//
// Created by mikha on 05.03.2016.
//

#include "rational.h"

int rational::GCD(int a, int b) {
    return b ? GCD(b, a % b) : a;
}

int rational::LCM(int a, int b) {
    return a / GCD(a, b) * b;
}

void rational::simplify() {
    int gcd = GCD(this->num, this->denom);
    this->num /= gcd;
    this->denom /= gcd;
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

rational rational::operator+(rational const &second) const {
    int lcm = LCM(this->denom, second.denom);
    return rational(this->num * (lcm / this->denom) + second.num * (lcm / second.denum), lcm);
}

rational rational::operator-(rational const &second) const {
    int lcm = LCM(this->denom, second.denom);
    return rational(this->num * (lcm / this->denom) - second.num * (lcm / second.denum), lcm);
}

rational rational::operator*(rational const &second) const {
    return rational(this->num * second.num, this->denom * second.denom);
}

rational rational::operator/(rational const &second) const {
    return rational(this->num * second.denom, this->denom * second.num);
}
