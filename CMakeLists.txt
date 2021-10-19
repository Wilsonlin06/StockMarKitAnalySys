cmake_minimum_required(VERSION 3.20)
project(StockMarKitAnalySys)

set(CMAKE_CXX_STANDARD 11)

add_executable(StockMarKitAnalySys main.cpp transactionLog.cpp longDecimal.cpp transaction.cpp helpers.cpp readFile.cpp readFile.h)
