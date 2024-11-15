#pragma once

#include <string>

long fibonacci(unsigned n);
void GetCurrentTimeString(std::string& current_time, std::string& rfc3339_time);
void PrintCurrentTimeString();
void GetTickCount();
std::string to_rfc3339_string(const std::string &duration);
void chrono_exam();