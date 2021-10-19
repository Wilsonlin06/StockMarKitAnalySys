#include <iomanip>
#include <cmath>
#include <sstream>
#include <utility>

#include "transaction.h"

namespace DS {
    size_t transaction::next_id = 1;
    unsigned short transaction::id_digits = 1;

    void transaction::update_id_digits() const {
        ++next_id;
        if (floor(log(static_cast<double>(transaction_id)) / log(10)) + 1 > transaction::id_digits)
            transaction::id_digits = static_cast<unsigned short>(
                                             floor(log(static_cast<double>(transaction_id)) / log(10))) + 1;
    }

    transaction transaction::operator+(const transaction &right) const {
        return transaction(transaction_value + right.transaction_value);
    }

    transaction transaction::operator-(const transaction &right) const {
        return transaction(transaction_value - right.transaction_value);
    }

    transaction transaction::operator-() const {
        return transaction(-transaction_value);
    }

    bool transaction::operator==(const transaction &right) const {
        return transaction_value == right.transaction_value;
    }

    std::string transaction::to_string() const {
        std::ostringstream oss;
        oss << *this;
        return oss.str();
    }

    std::ostream &operator<<(std::ostream &out, const transaction &t) {
        out.imbue(std::locale(""));
        out << "[" << std::setfill('0') << std::setw(transaction::id_digits) << t.id() << "] "
            << (t.is_negative() ? "- $" : "+ $");
        out << t.value().whole_value();
        out << '.'
            << std::setfill('0') << std::setw(transaction::PRECISION) << t.value().fractional_value();
        return out;
    }

} // End of DS namespace