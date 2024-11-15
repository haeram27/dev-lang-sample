#include <iostream>
#include "util/logger.h"

int main () {
    util::logger::init();
    SPDLOG_INFO("this is spdlog!");
    return 0;
}
