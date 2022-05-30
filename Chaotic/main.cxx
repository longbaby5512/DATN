#include "ChaoticCypher.hxx"


int main() {
    bytes data = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    auto str = Utils::byte2hex(data);
    std::cout << str << std::endl;
    bytes data2 = Utils::hex2byte(str);
    for (int i = 0; i < data2.size(); i++) {
        std::cout << data2[i] << " ";
    }


    return 0;
}
