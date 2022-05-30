//
// Created by Long Kenvy on 28/05/2022.
//

#include "ChaoticCypher.hxx"
#include "Hash.hxx"

#include <sstream>


const int ChaoticCypher::ENCRYPT_MODE = 0;
const int ChaoticCypher::DECRYPT_MODE = 1;

ChaoticCypher::ChaoticCypher(ChaoticType permType, ChaoticType subType, ChaoticType diffType)
    : permType(permType), subType(subType), diffType(diffType) {}

bytes ChaoticCypher::permutation(
    const bytes& data,
    const doubles& key,
    bool inverse = false,
    ChaoticType type = ChaoticType::LOGISTIC
) {
    auto chaoticSequence = ChaoticMap::generateSequence(type, key, data.size() + Constants::IGNORE_ELEMENTS);
    chaoticSequence.erase(chaoticSequence.cbegin(),
        chaoticSequence.cbegin() + Constants::IGNORE_ELEMENTS);

    auto indices = Utils::argsort(chaoticSequence);
    bytes result(chaoticSequence.size());
    for (auto i = 0; i < indices.size(); ++i) {
        if (inverse) {
            result[indices[i]] = data[i];
        }
        else {
            result[i] = data[indices[i]];
        }
    }
    return result;
}

bytes ChaoticCypher::generateSBox(
    const doubles& key,
    size_t iter = Constants::ITER_GEN_SBOX_DEFAULT,
    bool inverse = false,
    ChaoticType type = ChaoticType::LOGISTIC
) {
    auto chaoticSequence = ChaoticMap::generateSequence(type, key, iter + Constants::SBOX_SIZE);
    chaoticSequence.erase(chaoticSequence.begin(),
        chaoticSequence.begin() + iter);
    auto indices = Utils::argsort(chaoticSequence);
    bytes sbox(Constants::SBOX_SIZE);
    for (auto i = 0; i < Constants::SBOX_SIZE; ++i) {
        sbox[i] = static_cast<byte>(indices[i]);
    }
    if (!inverse) {
        return sbox;
    }
    bytes invSbox(Constants::SBOX_SIZE);
    for (auto i = 0; i < Constants::SBOX_SIZE; ++i) {
        invSbox[sbox[i]] = i;
    }
    return invSbox;
}

bytes ChaoticCypher::substitution(
    const bytes& data,
    const doubles& key,
    bool inverse = false,
    ChaoticType type = ChaoticType::LOGISTIC
) {
    size_t iter = Constants::ITER_GEN_SBOX_DEFAULT;
    if (data.size() > Constants::ITER_GEN_SBOX_DEFAULT) {
        iter = data.size();
    }
    auto sbox = generateSBox(key, iter, inverse, type);
    bytes result(data.size());
    for (auto i = 0; i < data.size(); ++i) {
        result[i] = sbox[data[i]];
    }
    return result;
}


bytes ChaoticCypher::diffusion(
    const bytes& data,
    const doubles& key,
    bool inverse = false,
    ChaoticType type = ChaoticType::LOGISTIC
) {
    auto chaoticSequence = ChaoticMap::generateSequence(type, key, data.size() + Constants::IGNORE_ELEMENTS);
    chaoticSequence.erase(chaoticSequence.cbegin(),
        chaoticSequence.cbegin() + Constants::IGNORE_ELEMENTS);
    auto maxValue = chaoticSequence[0], minValue = chaoticSequence[0];
    for (size_t i = 0, j = chaoticSequence.size(); i < j; ++i, --j) {
        maxValue = Utils::max(maxValue, chaoticSequence[i], chaoticSequence[j]);
        minValue = Utils::min(minValue, chaoticSequence[i], chaoticSequence[j]);
    }
    bytes xorArray;
    for (int i = 0; i < data.size(); ++i) {
        xorArray.push_back(
            static_cast<byte>(254 * (chaoticSequence[i] - minValue) / (maxValue - minValue) +
                1));
    }

    bytes result;
    result.push_back(data[0] ^ xorArray[0]);
    for (auto i = 1; i < data.size(); ++i) {
        if (inverse) {
            result.push_back(data[i] ^ data[i - 1] ^ xorArray[i]);
        }
        else {
            result.push_back(data[i] ^ result[i - 1] ^ xorArray[i]);
        }
    }
    return result;
}

bytes ChaoticCypher::encrypt(const bytes& plainData) {
    auto permData = ChaoticCypher::permutation(plainData, key1, false, permType);
    auto subData = ChaoticCypher::substitution(permData, key2, false, subType);
    return ChaoticCypher::diffusion(subData, key3, false, diffType);
}


bytes ChaoticCypher::decrypt(const bytes& cypherData) {
    auto diffData = diffusion(cypherData, key3, true, diffType);
    auto subData = substitution(diffData, key2, true, subType);
    return permutation(subData, key1, true, permType);
}


ChaoticCypher::ChaoticCypher() {
    mode = ENCRYPT_MODE;
    permType = ChaoticType::LOGISTIC;
    subType = ChaoticType::LOGISTIC;
    diffType = ChaoticType::LOGISTIC;
}



std::string ChaoticCypher::info() {
    std::string str("Perm: ");
    switch (permType) {
    case ChaoticType::LOGISTIC:
        str += "Logistic, ";
        break;
    case ChaoticType::SIN:
        str += "Sin, ";
    }

    str += "Sub: ";
    switch (subType) {
    case ChaoticType::LOGISTIC:
        str += "Logistic, ";
        break;
    case ChaoticType::SIN:
        str += "Sin, ";
    }

    str += "Diff: ";
    switch (diffType) {
    case ChaoticType::LOGISTIC:
        str += "Logistic, ";
        break;
    case ChaoticType::SIN:
        str += "Sin, ";
    }

    return str;
}

void ChaoticCypher::init(int mode, const bytes& key) {
    this->mode = mode;

    Hash::SHA256 hash;
    hash.update(key);
    auto hashKey = hash.digest();

    key1 = ChaoticMap::generateKey(permType, bytes(hashKey.cbegin(), hashKey.cbegin() + hashKey.size() / 2));
    key2 = ChaoticMap::generateKey(subType, bytes(hashKey.cbegin() + hashKey.size() / 2, hashKey.cend()));
    key3 = ChaoticMap::generateKey(diffType, key);
}

void ChaoticCypher::init(int mode, const std::string& key) {
    this->mode = mode;
    Hash::SHA256 hash;
    hash.update(key);
    auto hashKey = hash.digest();

    key1 = ChaoticMap::generateKey(permType, bytes(hashKey.cbegin(), hashKey.cbegin() + hashKey.size() / 2));
    key2 = ChaoticMap::generateKey(subType, bytes(hashKey.cbegin() + hashKey.size() / 2, hashKey.cend()));
    key3 = ChaoticMap::generateKey(diffType, bytes(key.cbegin(), key.cend()));
}

bytes ChaoticCypher::doFinal(const bytes& data) {
    if (mode == ENCRYPT_MODE) {
        return encrypt(data);
    }
    return decrypt(data);
}

bytes ChaoticCypher::doFinal(const std::string& data) {
    if (mode == ENCRYPT_MODE) {
        return encrypt(bytes(data.cbegin(), data.cend()));
    }
    return decrypt(bytes(data.cbegin(), data.cend()));
}

// Builder class
ChaoticCypher::Builder ChaoticCypher::Builder::setPermutationAlgorithm(ChaoticType type) {
    permType = type;
    return *this;
}

ChaoticCypher::Builder ChaoticCypher::Builder::setSubstitutionAlgorithm(ChaoticType type) {
    subType = type;
    return *this;
}

ChaoticCypher::Builder ChaoticCypher::Builder::setDiffusionAlgorithm(ChaoticType type) {
    diffType = type;
    return *this;
}

ChaoticCypher ChaoticCypher::Builder::build() {
    return ChaoticCypher(permType, subType, diffType);
}
