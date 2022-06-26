//
// Created by Long Kenvy on 28/05/2022.
//

#ifndef CHAOTIC_UTILS_H
#define CHAOTIC_UTILS_H

#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
#include <execution>

typedef uint_fast8_t byte;
typedef std::vector<byte> bytes;
typedef std::vector<double> doubles;

class Utils {
public:

    template<class T>
    static T max(T a, T b, T c) {
        return std::max(a, std::max(b, c));
    }

    template<class T>
    static T min(T a, T b, T c) {
        return std::min(a, std::min(b, c));
    }

    static std::string byte2hex(const bytes &);

    static bytes hex2byte(const std::string &);

    static std::string bytes2string(const bytes &);

    static bytes string2bytes(const std::string &);

};


#endif //CHAOTIC_UTILS_H
