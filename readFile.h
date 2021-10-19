//
// Created by New User on 10/15/2021.
//

#ifndef STOCKMARKITANALYSYS_READFILE_H
#define STOCKMARKITANALYSYS_READFILE_H

#include <iostream>
#include <istream>
#include <fstream>
#include <sys/stat.h>
#include <string>
#include <sstream>
#include <vector>
#include "transactionLog.h"

namespace DS{
    class readFile{
        typedef transactionLog::value_type valueType;
        typedef std::string string;
        typedef double valueT;
        typedef std::ifstream ifstream;
        typedef std::ofstream ofstream;
        typedef std::size_t sizeT;

        const sizeT DATE = 0, OPEN = 1, HIGH = 2, LOW = 3, CLOSE = 4, PRED = 5, EARN = 6;
        public:
            readFile():fileName(""), symbol(""), compName(""),
            openFile(""), data(new string*[0]), row(0), earning(0){}
            readFile(string , valueT inv = 0);
            virtual ~readFile();
            string findCompany();
            void read();
            void append(const string*);
            string predict(const string*);
            bool is_digits(const std::string&);
            bool matches(const string *src);
            void expt();
            void print();
            void calculate(const string*);
            transactionLog getLog() { return log; }
            inline bool exists (const std::string& name){
                ifstream f(name.c_str());
                return f.good();
            }
        private:
            string path = "F:\\NewUser\\Desktop\\StockMarKitAnalySys\\Company\\";
            string fileName, compName, symbol;
            ifstream openFile;
            ofstream writeFile, writeTrans;
            string** data;
            valueT prev[4] = {0.0,0.0,0.0,0.0};
            valueT origInvest, invest, earning;
            sizeT row;
            const sizeT col = 7;
            transactionLog log;
    };

}


#endif //STOCKMARKITANALYSYS_READFILE_H









//    std::cout << "prev: " << prev[0] << ", " << prev[1] << ", " << prev[2] << ", " << prev[3] << std::endl;
//    std::cout << "curr: " << src[OPEN] << ", " << src[HIGH] << ", " << src[LOW] << ", " << src[CLOSE] << std::endl;
//    std::cout << "Greater? " << (std::stod(src[OPEN]) < prev[0] && std::stod(src[HIGH]) > prev[1]
//                                 && std::stod(src[LOW]) < prev[2] && std::stod(src[CLOSE]) > prev[3]) << std::endl;