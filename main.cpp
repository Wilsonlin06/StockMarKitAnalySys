#include <iostream>

#include "transactionLog.h"
#include "helpers.h"
#include "readFile.h"

using namespace DS;
int main(int argc, char const *argv[]) {
    std::string symbol = argv[1];
    if(argv[2] == NULL || !(new readFile())->is_digits(argv[2])){
        std::cout << "Usage: StockMarKitAnalySys Stock_Symbol Investment_Amount" << std::endl;
    }else{
        readFile file1(symbol, std::stod(argv[2]));
        file1.read();
        file1.expt();
        file1.print();
        std::cout << "Transactions:" << std::endl << file1.getLog() << std::endl;
    }

    return 0;
}

