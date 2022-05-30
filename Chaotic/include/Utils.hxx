//
// Created by Long Kenvy on 28/05/2022.
//

#ifndef CHAOTIC_UTILS_HXX
#define CHAOTIC_UTILS_HXX

#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>


typedef uint_fast8_t byte;
typedef std::vector<byte> bytes;
typedef std::vector<double> doubles;

class Utils {
public:
    template<class T>
    static std::vector<size_t> argsort(const std::vector<T>& array) {
        std::vector<size_t> indices(array.size());
        std::iota(indices.begin(), indices.end(), 0);
        std::sort(indices.begin(), indices.end(),
            [&array](size_t left, size_t right) { return array[left] < array[right]; });
        return indices;
    }

    template<class T>
    static T max(T a, T b, T c) {
        return std::max(a, std::max(b, c));
    }

    template<class T>
    static T min(T a, T b, T c) {
        return std::min(a, std::min(b, c));
    }

    static std::string byte2hex(const bytes& digest);
    static bytes hex2byte(const std::string& hexStr);

};


#endif //CHAOTIC_UTILS_HXX
