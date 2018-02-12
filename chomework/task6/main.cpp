//
// Created by nikita on 08.05.16.
//

#include <iostream>
#include "lazy_string.h"

using namespace std;

int main(){
    lazy_string s = lazy_string("Hello, world!");
    cout << s << "\n";

    s[1] = 'K';
    cout << s << "\n";

    lazy_string s2 = s.substr(1,100);
    cout << s2 << "\n";

    lazy_string s3;
    cin >> s3;
    cout << s3;
}