
#ifndef PROJ_TRANSACTIONS_DYN_HELPERS_H
#define PROJ_TRANSACTIONS_DYN_HELPERS_H

#include <iostream>
#include <string>
#include "transactionLog.h"

namespace DS {
    std::ostream& operator<<(std::ostream &out, const transactionLog&);
    std::string transaction_id_list(const transactionLog&);

}

#endif //PROJ_TRANSACTIONS_DYN_HELPERS_H
