cmake_minimum_required(VERSION 3.18)
project(nanodiver-native)

add_library(nanodiver-native SHARED native.cpp)

find_library(log-lib log)

target_link_libraries(nanodiver-native ${log-lib})
