#include <sstream> //For ostringstream
#include <cmath> //For pow
#include <iomanip> //For setfill
#include "longDecimal.h"

namespace DS {
    longDecimal::longDecimal(double value, unsigned short precision) :
            decimal_point_count(precision), whole_units(static_cast<value_type>(value < 0 ? -value : value)),
            fractional_units(
                    static_cast<value_type>((value < 0 ? -value : value) * pow(10, precision)) % uipow(10, precision)),
            negative(value < 0) {
    }

    bool longDecimal::operator==(const longDecimal &right) const {
        return negative == right.negative && fractional_units == right.fractional_units &&
               whole_units == right.whole_units;
    }

    longDecimal &longDecimal::operator+=(const longDecimal &rhs) {
        longDecimal ld = *this + rhs;
        negative = ld.negative;
        whole_units = ld.whole_units;
        fractional_units = ld.fractional_units;
        return *this;
    }

    longDecimal longDecimal::operator+(const longDecimal &rhs) const {

        //Flip around operands if the left is negative and the rhs is positive
        if (negative && !rhs.negative)
            return rhs + *this;

        longDecimal result;

        //bool result.negative = false;
        s_value_type borrowed = 0;
        if (negative && rhs.negative ||
            (rhs.negative &&
             (rhs.whole_units > whole_units ||
              (rhs.whole_units == whole_units && rhs.fractional_units > fractional_units))))
            result.negative = true;

        if (negative == rhs.negative) {
            result.fractional_units = fractional_units + rhs.fractional_units;
            borrowed = static_cast<s_value_type>(result.fractional_units / uipow(10, decimal_point_count));
            result.fractional_units %= uipow(10, decimal_point_count);
        } else {
            s_value_type temp = static_cast<s_value_type>(fractional_units + uipow(10, decimal_point_count)) -
                                static_cast<s_value_type>(rhs.fractional_units + uipow(10, decimal_point_count));
            if (temp < 0) {
                borrowed += result.negative ? 1 : -1;
                temp += static_cast<s_value_type>(uipow(10, decimal_point_count));
            }
            if (result.negative && temp < static_cast<s_value_type>(uipow(10, decimal_point_count))) {
                result.fractional_units = static_cast<value_type>(uipow(10, decimal_point_count) - temp);
                borrowed -= 1;
            } else
                result.fractional_units = static_cast<value_type>(temp) % uipow(10, decimal_point_count);
        }

        if (negative == rhs.negative) {
            result.whole_units = whole_units + rhs.whole_units + borrowed;
        } else {
            s_value_type temp;
            auto opTop = static_cast<s_value_type>(whole_units),
                    opBottom = static_cast<s_value_type>(rhs.whole_units);
            if (result.negative)
                //The larger number is negative and on the rhs
                std::swap(opTop, opBottom);

            temp = (opTop + borrowed) - opBottom;
            result.whole_units = static_cast<value_type>(temp);
        }

        return result;

    }

    longDecimal longDecimal::operator-(const longDecimal &rhs) const {
        return *this + -rhs;
    }

    longDecimal longDecimal::operator-() const {
        return {!negative, whole_units, fractional_units};
    }

    std::string longDecimal::to_string() const {
        std::ostringstream oss;
        oss << *this;
        return oss.str();
    }

    bool longDecimal::operator<(const longDecimal &rhs) const {
        if (negative < rhs.negative)
            return false;
        if (negative > rhs.negative)
            return true;
        if (whole_units < rhs.whole_units)
            return !negative;
        if (rhs.whole_units < whole_units)
            return negative;
        if (negative)
            return rhs.fractional_units < fractional_units;
        else
            return fractional_units < rhs.fractional_units;
    }

    bool longDecimal::operator>(const longDecimal &rhs) const {
        return rhs < *this;
    }

    bool longDecimal::operator<=(const longDecimal &rhs) const {
        return !(rhs < *this);
    }

    bool longDecimal::operator>=(const longDecimal &rhs) const {
        return !(*this < rhs);
    }

    std::ostream &operator<<(std::ostream &out, const longDecimal &ld) {
        out.imbue(std::locale(""));
        out << (ld.is_negative() ? "-" : "");
        out << ld.whole_value();
        out << '.'
            << std::setfill('0') << std::setw(ld.precision()) << ld.fractional_value();
        return out;
    }

    longDecimal::value_type uipow(longDecimal::value_type base, longDecimal::value_type exp) {
        //Special case looking for the default precision, avoid the linear time loop
        if (base == 10 && exp == 2)
            return 100;
        longDecimal::value_type result = 1;
        while (exp > 0) {
            if (exp & 1)
                result *= base;
            exp >>= 1;
            if (exp > 0)
                base *= base;
        }
        return result;
    }

} // End of namespace