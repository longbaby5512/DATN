#include "ChaoticCypher.h"

#include <random>

bytes randomBytes(int length) {
    bytes result(length);
    std::random_device rd;
    std::mt19937_64 gen(rd());
    std::uniform_int_distribution<> dis(0, 255);
    std::for_each(std::execution::par, result.begin(), result.end(), [&](byte& b) {
        b = static_cast<byte>(dis(gen));
    });
    return result;
}


int main() {
    std::string key;
    std::cout << "Enter key: ";
    std::getline(std::cin, key);
    std::cin.ignore(0);

    std::string plaintext;
    std::cout << "Enter plaintext: ";
    std::getline(std::cin, plaintext);

    auto cypher = ChaoticCypher::Builder().build();
    cypher.init(ChaoticCypher::Mode::ENCRYPT, key);
    auto ciphertext = cypher.doFinal(plaintext);
    std::cout << "Ciphertext: " << Utils::bytes2string(ciphertext) << std::endl;
    std::cout << "Ciphertext hex: " << Utils::byte2hex(ciphertext) << std::endl;


    cypher.init(ChaoticCypher::Mode::DECRYPT, key);
    auto decrypted = cypher.doFinal(Utils::bytes2string(ciphertext) );

    std::cout << "Decrypted: " << Utils::bytes2string(decrypted) << std::endl;
    return 0;
}
