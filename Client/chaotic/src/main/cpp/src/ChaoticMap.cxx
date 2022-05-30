//
// Created by Long Kenvy on 28/05/2022.
//

#include "ChaoticMap.hxx"

#include <cmath>

doubles ChaoticMap::Logistic(const doubles& key, size_t iter) {
    auto x = key[0];
    if (x <= 0 || x >= 1) {
        throw std::runtime_error("x must between 0 and 1");
    }
    auto m = 4.0;
    if (key.size() == 2) {
        m = key[1];
    }

    if (m <= 3.7 || m > 4) {
        std::string err = "m is ";
        err += std::to_string(m);
        err += " must between 3.7 and 4";
        throw std::runtime_error(err);
    }

    doubles result;
    for (auto i = 0; i < iter; ++i) {
        x = m * x * (1 - x);
        result.push_back(x);
    }

    return result;
}

doubles ChaoticMap::Sin(const doubles& key, size_t iter) {
    auto x = key[0];
    if (x <= 0 || x >= 1) {
        throw std::runtime_error("x must between 0 and 1");
    }
    auto m = 0.5;
    if (key.size() == 2) {
        m = key[1];
    }
    if (m <= 0 || m >= 1) {
        std::string err = "m is ";
        err += std::to_string(m);
        err += " must between 0 and 1";
        throw std::runtime_error(err);
    }

    doubles result;
    for (auto i = 0; i < iter; ++i) {
        x = m * sin(M_PI * x);
        result.push_back(x);
    }

    return result;
}

doubles ChaoticMap::generateSequence(ChaoticType type, const doubles &key, size_t iter) {
    if (type == SIN)
        return Sin(key, iter);
    return Logistic(key, iter);
}

doubles ChaoticMap::logisticKey(bytes digest) {
    doubles key;
    byte x = digest[0];
    byte m = digest[1];
    for (auto i = 0; i < digest.size(); i += 2) {
        x = x ^ digest[i];
        m = m ^ digest[i + 1];
    }

    // x between 0 and 1
    key.push_back(x / 255.0);
    key.push_back(3.7 + m / 255.0 * 0.3);
    return key;
}

doubles ChaoticMap::sinKey(bytes digest) {
    doubles key;
    byte x = digest[0];
    byte m = digest[1];
    for (auto i = 0; i < digest.size(); i += 2) {
        x = x ^ digest[i];
        m = m ^ digest[i + 1];
    }

    // x between 0 and 1
    key.push_back(x / 255.0);
    key.push_back(m / 255.0);
    return key;
}

doubles ChaoticMap::generateKey(ChaoticType type, const bytes& digest) {
    if (type == SIN) {
        return sinKey(digest);
    }
    return logisticKey(digest);
}

