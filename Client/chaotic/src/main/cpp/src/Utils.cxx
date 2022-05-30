//
// Created by Long Kenvy on 28/05/2022.
//

#include "Utils.hxx"

#include <sstream>
#include <iomanip>
#include <numeric>

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
