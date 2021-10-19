
#ifndef PROJ_TRANSACTIONS_DYN_TRANSACTION_H
#define PROJ_TRANSACTIONS_DYN_TRANSACTION_H

#include <cstdint>  // provides uint_least64_t
#include <iostream>
#include <string>
#include "longDecimal.h"

namespace DS {
    class transaction {
    public:
        typedef longDecimal value_type;
        static const unsigned short PRECISION = 2;
        static size_t next_id;
        static unsigned short id_digits;
        transaction() : transaction_id(0), transaction_value(value_type(PRECISION)) {  }
        explicit transaction(double data_in) :transaction_id(next_id), transaction_value(data_in, PRECISION) {
            update_id_digits();
        }
        explicit transaction(const value_type &data_in) :
                transaction_id(next_id), transaction_value(data_in, PRECISION) {
            update_id_digits();
        }
        size_t id() const { return transaction_id; }
        const value_type& value() const { return transaction_value; }
        bool is_negative() const { return transaction_value.is_negative(); }
        bool operator==(const transaction&) const;
        bool operator!=(const transaction& right) const { return !(*this == right); }
        transaction operator+(const transaction&) const;
        transaction operator-() const; //unary
        transaction operator-(const transaction&) const;
        std::string to_string() const;
    private:
        void update_id_digits() const;      // Internal function, sounds the number of digits in the current id
        size_t transaction_id;              // Every transaction has a unique id
        value_type transaction_value;       // Holds the actual numeric value of this object
    };

    std::ostream& operator<<(std::ostream& out, const transaction&);


} // End of DS namespace

#endif //PROJ_TRANSACTIONS_DYN_TRANSACTION_H
