//
// Created by Long Kenvy on 28/05/2022.
//

#ifndef CHAOTIC_CHAOTICCYPHER_HXX
#define CHAOTIC_CHAOTICCYPHER_HXX

#include <functional>

#include "Utils.hxx"
#include "ChaoticMap.hxx"
#include "Constants.hxx"

class ChaoticCypher {
public:
    static const int ENCRYPT_MODE;
    static const int DECRYPT_MODE;

    void init(int, const bytes&);
    void init(int, const std::string&);


    bytes doFinal(const bytes&);
    bytes doFinal(const std::string&);



    class Builder {
    public:
        Builder setPermutationAlgorithm(ChaoticType);
        Builder setSubstitutionAlgorithm(ChaoticType);
        Builder setDiffusionAlgorithm(ChaoticType);

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
    int mode;

    explicit ChaoticCypher();
    ChaoticCypher(ChaoticType, ChaoticType, ChaoticType);

private:
    static bytes generateSBox(const doubles&, size_t, bool, ChaoticType);

    static bytes substitution(const bytes&, const doubles&, bool, ChaoticType);
    static bytes permutation(const bytes&, const doubles&, bool, ChaoticType);
    static bytes diffusion(const bytes&, const doubles&, bool, ChaoticType);

    bytes encrypt(const bytes&);
    bytes decrypt(const bytes&);
};


#endif //CHAOTIC_CHAOTICCYPHER_HXX
