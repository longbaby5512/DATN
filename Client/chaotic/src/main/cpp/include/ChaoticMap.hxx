//
// Created by Long Kenvy on 28/05/2022.
//

#ifndef CHAOTIC_CHAOTICMAP_HXX
#define CHAOTIC_CHAOTICMAP_HXX

#include "Utils.hxx"

enum ChaoticType {
    LOGISTIC = 0,
    SIN = 1
};

class ChaoticMap {
public:

    static doubles generateSequence(ChaoticType type, const doubles &key, size_t iter);
    static doubles generateKey(ChaoticType type, const bytes& digest);

private:
    static doubles Logistic(const doubles&, size_t);
    static doubles Sin(const doubles&, size_t);

    static doubles logisticKey(bytes digest);
    static doubles sinKey(bytes digest);
};

#endif //CHAOTIC_CHAOTICMAP_HXX