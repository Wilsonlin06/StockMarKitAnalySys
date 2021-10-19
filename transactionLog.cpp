#include "transactionLog.h"

namespace DS{

    transactionLog::transactionLog( const transactionLog& arr){
        capacity = arr.capacity;
        used = arr.used;
        log = new value_type[capacity];
        std::copy(arr.log, arr.log + used, log);
        current_index = arr.current_index;
    }

    transactionLog::~transactionLog(){delete [] log;}

    transactionLog& transactionLog::operator=(const transactionLog & arr){
        value_type *new_arr;
        if (this == &arr)
            return *this;
        if (used != arr.used){
            new_arr = new value_type[arr.used];
            delete [] log;
            log = new_arr;
            used = arr.used;
        }
        std::copy(arr.log, arr.log + used, new_arr);
        current_index = arr.current_index;
        return *this;
    }


    void transactionLog::reserve(size_type new_capacity){
        value_type *new_arr;
        capacity = new_capacity;
        new_arr = new value_type[capacity];
        std::copy(log, log + used, new_arr);
        delete [] log;
        log = new_arr;
    }

    void transactionLog::insert(const add_type& data_in){
        insert(transaction_value_type(data_in));
    }
    void transactionLog::append(const add_type& data_in){
        append(transaction_value_type(data_in));
    }

    void transactionLog::insert(const transaction_value_type& data_in){
        if(current_index > used || current_index < 0) {
            current_index = 0;
        }
        value_type* n_arr;
        if(capacity == 0){
            reserve(1);
            log[current_index] = value_type(data_in);
        }else{
            if(used >= capacity) reserve(capacity * this->GROW_FACTOR);
            n_arr = new value_type[capacity];
            size_type idx = 0;
            for(size_type i = 0; i < current_index; ++i) n_arr[idx++] = log[i];
            n_arr[idx++] = value_type(data_in);
            for(size_type i = current_index; i < used; ++i) n_arr[idx++] = log[i];
            delete [] log;
            log = n_arr;
        }
        ++used;
    }
    void transactionLog::append(const transaction_value_type& data_in){
        if(current_index > used || current_index < 0) {
            if(used > 0) current_index = used - 1;
            else current_index = used;
        }
        value_type* n_arr;
        if(capacity == 0){
            reserve(1);
            log[current_index] = value_type(data_in);
        }else{
            if(used >= capacity) reserve(capacity * this->GROW_FACTOR);
            n_arr = new value_type[capacity];
            for(size_type i = 0; i <= current_index; ++i)
                n_arr[i] = log[i];
            n_arr[current_index + 1] = value_type(data_in);
            ++current_index;
            for(size_type i = capacity - 1; i > current_index; --i) n_arr[i] = log[i - 1];
            delete [] log;
            log = n_arr;
        }
        ++used;
    }
    transactionLog& transactionLog::operator+=(const transactionLog & logIn){
        size_type cnt = 0;
        for(size_type i = logIn.current_index; i < logIn.used; ++i){
            append(logIn.log[i].value());
            ++cnt;
        }
        if(current_index != 0) current_index -= cnt;
        return *this;
    }
    void transactionLog::prev(){
        if(used >= 1 && current_index >= 0 && current_index < capacity) --current_index;
        else current_index = -1;
    }
    void transactionLog::set_current_by_id(size_type target_id){
//        bool valid = false;
        for(size_type i = 0; i < used; ++i)
            if(log[i].id() == target_id) {
                current_index = i;
//                valid = true;
            }
//        if(!valid) log[current_index] = value_type(NULL);
    }
    void transactionLog::next(){
        if(used >= 1 && current_index >= 0 && current_index < capacity) ++current_index;
        else current_index = -1;
    }

    bool transactionLog::has_prev() const{
        return (current_index > 0 && current_index < used);
    }
    bool transactionLog::has_next() const{
        if(used == 0) return false;
        return (current_index >= 0 && current_index < used - 1);
    }
    bool transactionLog::current_is_valid() const{
        return (current_index >= 0 && current_index < used);
    }
    bool transactionLog::operator==(const transactionLog & data) const{
        if(this == &data) return true;
//			 if(used != data.used) return false;
        for(size_type i = current_index; i < used; ++i)
            if(log[i] != data.log[i]) return false;
        return true;
    }
    bool transactionLog::operator!=(const transactionLog &data) const{
        if(this == &data) return false;
        for(size_type i = current_index; i < used; ++i)
            if(log[i] != data.log[i]) return true;
        return false;
    }

    transactionLog::transaction_value_type transactionLog::sum() const{

        transaction_value_type ttl;
        if (current_index >= 0 && current_index < used){
            for (size_type i = current_index; i < used; ++i ){
                ttl += log[i].value();
            }
        }
        return ttl;
    }
    const transactionLog::value_type& transactionLog::max() const{
        value_type mx;
        size_type maxIdx = current_index;
        if (current_index >= 0 && current_index < used){
            mx = log[current_index];
            for(size_type i = current_index + 1; i < used; ++i){
                if(log[i].value() > mx.value()){
                    mx = log[i];
                    maxIdx = i;
                }
            }
        }
        return log[maxIdx];
    }
}