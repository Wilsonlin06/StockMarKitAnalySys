
#ifndef PROJ_TRANSACTIONS_DYN_TRANSACTION_LOG_H
#define PROJ_TRANSACTIONS_DYN_TRANSACTION_LOG_H

#include "transaction.h"

namespace DS {
    class transactionLog {
    public:
        typedef transaction value_type;
        typedef value_type::value_type transaction_value_type;
        typedef double add_type;
        typedef size_t size_type;
        static const size_type GROW_FACTOR = 2; //When resizing, multiply capacity by this amount

        //Constructors
        transactionLog() : capacity(0), used(0), log(nullptr), current_index(0) {}
        transactionLog(const transactionLog&);

        //Destructor
        virtual ~transactionLog();

        //Mutators
        transactionLog& operator=(const transactionLog &);
        void reserve(size_type new_capacity);
        void insert(const add_type&);
        void append(const add_type&);
        void insert(const transaction_value_type&);
        void append(const transaction_value_type&);
        transactionLog& operator+=(const transactionLog &);

        //Move the cursor
        void reset() { current_index = 0; }
        void prev();
        void set_current_by_id(size_type target_id);
        void next();

        //Accessors
        bool has_prev() const;
        bool has_next() const;
        bool current_is_valid() const;
        const value_type& current() const { return log[current_index]; }
        bool operator==(const transactionLog &) const;
        bool operator!=(const transactionLog &) const;
        const value_type* transactions() const { return log; }
        size_type size() const {return used; }
        transaction_value_type sum() const;
        const value_type& max() const;



    private:
        size_type capacity; //Current size of the allocated array
        size_type used; //Number of transactions in the array
        size_type current_index; //Cursor
        value_type * log; //The actual transaction log array
    };
}

#endif //PROJ_TRANSACTIONS_DYN_TRANSACTION_LOG_H
