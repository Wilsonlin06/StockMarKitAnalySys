//
// Created by New User on 10/15/2021.
//

#include <iomanip>
#include "readFile.h"
using namespace DS;

readFile::readFile(string sym, valueT inv) {
    symbol = sym;
    compName = findCompany();
    fileName = path + symbol + ".csv";
    data = new string*[0];
    row = 0;
    origInvest = invest = inv;
    earning = 0;
    std::cout << "orig: " << origInvest << std::endl;
}

readFile::~readFile() {
    for(sizeT i = 0; i < row; ++i) delete [] data[i];
    delete [] data;
}

readFile::string readFile::findCompany() {
    string fl = path + "00nasdaq_screener.csv";
    if(exists(fl)){
        openFile.open(fl);
    }
    if(openFile.good()){
        string str;
        string src[2];
        bool found = false;
        while(std::getline(openFile, str)){
            string tmp;
            std::stringstream ss(str);
            sizeT cnt = 0;
            while(std::getline(ss, tmp,',')){
                if(cnt >= 2) break;
                src[cnt++] = tmp;
            }
            if(src[0] == symbol) {
                openFile.close();
                return src[1];
            }
        }
    }
    openFile.close();
    std::cout << "Cannot find company with the symbol entered." << std::endl;
    exit(1);
}

void readFile::read() {
    findCompany();
    if(exists(fileName)){
        openFile.open(fileName);
    }
    else
        std::cout << "File doesn't exists" << std::endl;
    if(openFile.good()){
        string s;
        string source[col];
        while(getline(openFile,s)){
            string tmp;
            std::stringstream ss(s);
            sizeT idx = 0;
            while(std::getline(ss, tmp, ',')){
                if(idx == col - 2) break;
                source[idx++] = tmp;
            }
            source[idx++] = predict(source);
            source[idx++] = std::to_string(earning);
            append(source);
        }
    }else std::cout << "Something went wrong while reading the file." << std::endl;
    openFile.close();
    log.reset();
}

void readFile::append(const string* source){
    if(row == 0){
        data = new string*[1];
        data[row] = new string[col];
        for(sizeT i = 0; i < col; ++i) data[row][i] = source[i];
        data[row][col-2] = "Prediction";
        data[row][col-1] = "Earning";
        ++row;
    }else{
        ++row;
        string** n_data = new string*[row];
        for(sizeT i = 0; i < row; ++i) {
            n_data[i] = new string[col];
            if(i == row - 1) break;
            for(sizeT j = 0; j < col; ++j)
                n_data[i][j] = data[i][j];
        }
        for(sizeT i = 0; i < col; ++i) n_data[row - 1][i] = source[i];
        delete [] data;
        data = n_data;
    }
}

readFile::string readFile::predict(const string* src) {
    string result = "N/A\t";
    if(is_digits(src[OPEN])){
        if(matches(src)) {
            result = std::to_string(std::stod(src[HIGH]) - std::stod(src[LOW]));
            calculate(src);
        }
        prev[0] = std::stod(src[OPEN]);
        prev[1] = std::stod(src[HIGH]);
        prev[2] = std::stod(src[LOW]);
        prev[3] = std::stod(src[CLOSE]);
    }
    return result;
}

bool readFile::is_digits(const string& str)
{
    return str.find_first_not_of(".0123456789") == std::string::npos;
}

bool readFile::matches(const string* src) {
    // prev[0]: OPEN, prev[1]: HIGH, prev[2]: LOW, prev[3]: CLOSE
//    if(std::stod(src[OPEN]) < std::stod(src[CLOSE]))
//        if(std::stod(src[HIGH]) >= prev[1] && std::stod(src[LOW]) <= prev[2])
//            if((prev[0] < prev[3] && (std::stod(src[OPEN]) <= prev[0] && std::stod(src[CLOSE]) >= prev[3]))
//            || (prev[0] > prev[3] && (std::stod(src[OPEN]) <= prev[3] && std::stod(src[CLOSE]) >= prev[0])))
//                return true;
//    return false;
   return (std::stod(src[OPEN]) < prev[0] && std::stod(src[HIGH]) > prev[1]
    && std::stod(src[LOW]) < prev[2] && std::stod(src[CLOSE]) > prev[3]);
}

void readFile::expt() {
    string nPath = path + symbol + "\\";
    mkdir(nPath.c_str());
    string delim = ",";
    writeFile.open(nPath+symbol+".csv");
    writeTrans.open(nPath+symbol+"_transactions.csv");
    if(writeFile.good()){
        writeFile << "Company: " << delim << compName << std::endl;
        writeTrans << "Investment amount: " << delim  << std::fixed << std::setprecision(2) << origInvest << std::endl;
        writeTrans << "Date" << delim << "Earning" << std::endl;
        for(sizeT i = 0; i < row; ++i) {
            writeFile << data[i][DATE] << delim << data[i][OPEN] << delim << data[i][HIGH] << delim << data[i][LOW]
            << delim << data[i][CLOSE] << delim << data[i][PRED] << delim << data[i][EARN] << std::endl;
            if(is_digits(data[i][PRED])){
                writeTrans << data[i][DATE] << delim <<  log.current() << std::endl;
                if(log.has_next()) log.next();
            }
        }
        writeTrans << "Invest + Earning" << delim  << std::fixed << std::setprecision(2) << invest << std::endl;
    }
    writeFile.close();
    writeTrans.close();
    log.reset();
}

void readFile::print() {
    string delim = "";
    std::cout << "Company: " << compName << std::endl;
    for(sizeT i = 0; i < row; ++i) {
        if (i == 0) delim = "\t\t";
        else delim = "\t";
        std::cout << data[i][DATE] << delim << data[i][OPEN] << delim << data[i][HIGH] << delim << data[i][LOW]
                  << delim << data[i][CLOSE] << delim << data[i][PRED] << delim << std::fixed << std::setprecision(2)
                  << data[i][EARN] << std::endl;
    }
}

void readFile::calculate(const string* src) {
    valueT stkPurch = invest / std::stod(src[OPEN]);
    earning += stkPurch * std::stod(src[CLOSE]) - invest;
    invest = origInvest + earning;
    log.append(earning);
//    return log.current();
}