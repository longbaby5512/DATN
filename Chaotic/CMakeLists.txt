cmake_minimum_required(VERSION 3.0.0)
project(Chaotic VERSION 0.1.0)

add_library(ChaoticCypher STATIC src/ChaoticCypher.cxx src/ChaoticMap.cxx src/Constants.cxx src/Utils.cxx src/Hash.cxx)

target_include_directories(ChaoticCypher PUBLIC "${PROJECT_SOURCE_DIR}/include")

add_executable(Chaotic main.cxx)

target_include_directories(Chaotic PUBLIC "${PROJECT_SOURCE_DIR}/include")
target_link_libraries(Chaotic ChaoticCypher)

