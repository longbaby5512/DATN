//
// Created by Long Kenvy on 28/05/2022.
//

#ifndef CHAOTIC_CHAOTICCYPHER_H
#define CHAOTIC_CHAOTICCYPHER_H

#include <functional>

#include "Utils.h"
#include "ChaoticMap.h"
#include "Constants.h"

class ChaoticCypher {
public:

    enum Mode {
        ENCRYPT = 0,
        DECRYPT = 1,
    };

    void init(Mode, const bytes&);
    void init(Mode, const std::string&);


    bytes doFinal(const bytes&);
    bytes doFinal(const std::string&);



    class Builder {
    public:
        Builder& setPermutationAlgorithm(ChaoticType);
        Builder& setSubstitutionAlgorithm(ChaoticType);
        Builder& setDiffusionAlgorithm(ChaoticType);

        ChaoticCypher build();

    private:
        ChaoticType permType = ChaoticType::LOGISTIC;
        ChaoticType subType = ChaoticType::LOGISTIC;
        ChaoticType diffType = ChaoticType::LOGISTIC;
    };


    std::string info();

private:
    ChaoticType permType;
    ChaoticType subType;
    ChaoticType diffType;
    doubles key1, key2, key3;
    int mode{};

    explicit ChaoticCypher();
    ChaoticCypher(ChaoticType, ChaoticType, ChaoticType);

    static bytes generateSBox(const doubles&, size_t, bool, ChaoticType);

    static bytes substitution(const bytes&, const doubles&, bool, ChaoticType);
    static bytes permutation(const bytes&, const doubles&, bool, ChaoticType);
    static bytes diffusion(const bytes&, const doubles&, bool, ChaoticType);

    bytes encrypt(const bytes&);
    bytes decrypt(const bytes&);
};


#endif //CHAOTIC_CHAOTICCYPHER_H
