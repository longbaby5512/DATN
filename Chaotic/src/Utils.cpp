//
// Created by Long Kenvy on 28/05/2022.
//

#include "Utils.h"

#include <sstream>
#include <iomanip>

std::string Utils::byte2hex(const bytes& digest) {
    std::stringstream s;
    s << std::setfill('0') << std::hex;

    for (unsigned char i : digest) {
        s << std::setw(2) << (unsigned int)i;
    }
    auto res = s.str();
    std::transform(res.begin(), res.end(), res.begin(), ::toupper);
    return res;
}

bytes Utils::hex2byte(const std::string& hexStr) {
    bytes digest;
    for (size_t i = 0; i < hexStr.size(); i += 2) {
        std::string byteString = hexStr.substr(i, 2);
        digest.push_back(static_cast<byte>(std::stoi(byteString, nullptr, 16)));
    }
    return digest;
}

std::string Utils::bytes2string(const bytes& data) {
    std::string res;
    std::for_each(std::execution::par, data.begin(), data.end(), [&](byte b) {
        res += static_cast<char>(b);
    });
    return res;
}

bytes Utils::string2bytes(const std::string& data) {
    bytes res;
    std::for_each(std::execution::par, data.begin(), data.end(), [&](char c) {
        res.push_back(static_cast<byte>(c));
    });
    return res;
}
