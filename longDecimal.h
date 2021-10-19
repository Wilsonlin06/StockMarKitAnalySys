
#ifndef PROJ_TRANSACTIONS_DYN_LONG_DECIMAL_H
#define PROJ_TRANSACTIONS_DYN_LONG_DECIMAL_H

#include <cstdint>
#include <string>

namespace DS {

    class longDecimal {
    public:
        typedef uint_least64_t value_type;
        static const unsigned short DEFAULT_PRECISION = 2;

        longDecimal() :
                negative(false), whole_units(0), fractional_units(0), decimal_point_count(DEFAULT_PRECISION) {}

        explicit longDecimal(unsigned short precision) :
                negative(false), whole_units(0), fractional_units(0), decimal_point_count(precision) {}

        explicit longDecimal(double data, unsigned short precision = DEFAULT_PRECISION);

        longDecimal(bool negative, value_type whole, value_type fractional,
                     unsigned short precision = DEFAULT_PRECISION) :
                negative(negative), whole_units(whole), fractional_units(fractional), decimal_point_count(precision) {}

        longDecimal(const longDecimal &data_in, unsigned short precision = DEFAULT_PRECISION) :
                negative(data_in.negative), whole_units(data_in.whole_units),
                fractional_units(data_in.fractional_units),
                decimal_point_count(precision) {}

        bool operator==(const longDecimal &) const;

        bool operator!=(const longDecimal &right) const { return !(*this == right); }

        longDecimal operator+(const longDecimal &) const;

        longDecimal &operator+=(const longDecimal &);

        longDecimal operator-() const; //unary
        longDecimal operator-(const longDecimal &) const;

        bool is_negative() const { return negative; }

        unsigned short precision() const { return decimal_point_count; }

        value_type whole_value() const { return whole_units; }

        value_type fractional_value() const { return fractional_units; }

        std::string to_string() const;

        bool operator<(const longDecimal &rhs) const;

        bool operator>(const longDecimal &rhs) const;

        bool operator<=(const longDecimal &rhs) const;

        bool operator>=(const longDecimal &rhs) const;

    private:
        typedef int_least64_t s_value_type;
        unsigned short decimal_point_count;
        bool negative;
        value_type whole_units;
        value_type fractional_units;
    };

    std::ostream &operator<<(std::ostream &out, const longDecimal &);

    longDecimal::value_type uipow(longDecimal::value_type base, longDecimal::value_type exp);
}
#endif //PROJ_TRANSACTIONS_DYN_LONG_DECIMAL_H
