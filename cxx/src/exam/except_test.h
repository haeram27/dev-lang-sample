#pragma once

template <typename T>
void throwobjfunc(T t);

void throwfunc(const std::string& s);

void normalfunc(const std::string& s);

void noexceptthrowfunc(const std::string& s) noexcept;

void noexcepcatchfunc(const std::string& s);

void exceptexam();