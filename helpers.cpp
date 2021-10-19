
#include <sstream>
#include "helpers.h"

namespace DS {

    std::ostream& operator<<(std::ostream &out, const transactionLog& log) {
        //Make a copy of the log so that we can move the cursor
        transactionLog logIterator(log);
        out << logIterator.current();
        while ( logIterator.has_next() ) {
            logIterator.next();
            out << std::endl << logIterator.current();
        }
        return out;
    }

    std::string transaction_id_list(const transactionLog& log) {
        std::ostringstream oss;
        oss << '[';
        if ( log.size() > 0 ) {
            transactionLog logIterator(log);
            oss << logIterator.current().id();
            while ( logIterator.has_next() ) {
                logIterator.next();
                oss << ", " << logIterator.current().id();
            }
        }
        oss << ']';
        return oss.str();
    }

}