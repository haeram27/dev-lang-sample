#include <chrono>
#include <iomanip>  //process format of string and stream
#include <iostream>

#include "gtest/gtest.h"

#include "util/time.h"
#include "exam/chrono_test.h"

using namespace std::chrono;
// using util::time::operator""_ms;

long fibonacci(unsigned n)
{
    if (n < 2) return n;
    return fibonacci(n-1) + fibonacci(n-2);
}

void GetCurrentTimeString(std::string& current_time, std::string& rfc3339_time) {

    auto tp = system_clock::now();
    auto tt = system_clock::to_time_t(tp);
    auto tm = std::localtime(&tt);
    auto dr_sec = duration_cast<seconds>(tp.time_since_epoch());
    auto dr_msec = duration_cast<milliseconds>(tp.time_since_epoch());

    // current_time = util::time::to_plainp_stringX(*tm);
    // current_time = util::time::to_plain_string(*tm, dr_msec - dr_sec);
    // rfc3339_time = util::time::to_rfc3339_string(*tm, dr_msec - dr_sec);
}

void PrintCurrentTimeString()
{
    auto tt =::time(nullptr);
    auto gmt = *std::gmtime(&tt);
    auto loct = *std::localtime(&tt);
    std::cerr << "gmt= " << util::time::to_rfc3339_string(gmt) << std::endl;
    std::cerr << "local= " << util::time::to_rfc3339_string(loct) << std::endl;
}


void  GetTickCount()
{
    auto tp = steady_clock::now();
    auto du_msec = duration_cast<milliseconds>(tp.time_since_epoch());
    std::cout << du_msec.count() << std::endl;
}


/*
 * Convert string of time_t value to rfc3339string 
 * to_rfc3339_string("1590034950") >> 2020-05-21T13:22:30Z
 */
std::string to_rfc3339_string(const std::string &duration)
{
    std::string rfc3339;

    try {
        time_t tt {std::stol(duration)};
        rfc3339 = util::time::to_rfc3339_string(*::gmtime(&tt));
    } catch (std::exception& e) {
        std::cout << "[Error] exception occurred! msg: " << e.what();
    } catch (...) {
        std::cout << "[Error] exception occurred!";
    }

    return rfc3339;
}

void chrono_exam()
{
	std::cout << "[chrono_exam]" << std::endl;

// Second(1) > milli sec (10^-3 sec) > micro sec (10^-6 sec) > nano sec (10^-9 sec)
// tm = std::localtime(&time_t) = 

// time_point  since C++11 <chrono>
//             Point of time = value of type Duration indicating the time interval from the start of the Clock's epoch.
//             Current time = start of the Clock's Epoch(1.Jan.1970) + Duration(unit of nanoseconds)
// time_t      Seconds since the Epoch(1.Jan.1970). resolve time_t by std::time(nullprt) or to_time_t(time_point)
// tm          TIMER type has format of Calendar, min unit is sec.  std::localtime(&time_t) or std::gmtime

// tm* = local

    std::time_t t = std::time(nullptr);    //time_t time()
    std::tm* ptm = std::localtime(&t);     //tm* std::localtime()

    auto systemclk_tp = system_clock::now();
    auto systemclk_dur = systemclk_tp.time_since_epoch();
    auto systemclk_tt = system_clock::to_time_t(systemclk_tp); // to_time_t(time_point)
    auto systemclk_tm = std::localtime(&systemclk_tt);
    auto steadyclk_tp = steady_clock::now();
    auto highresclk_tp = high_resolution_clock::now();
    auto highresclk_tt = high_resolution_clock::to_time_t(highresclk_tp);

    auto tp_hour = time_point_cast<hours>(systemclk_tp);
    auto tp_min = time_point_cast<minutes>(systemclk_tp);
    auto tp_sec = time_point_cast<seconds>(systemclk_tp);
    auto tp_msec = time_point_cast<milliseconds>(systemclk_tp);
    auto tp_mcsec = time_point_cast<microseconds>(systemclk_tp);
    auto tp_nsec = time_point_cast<nanoseconds>(systemclk_tp);

    auto dr_hour = duration_cast<hours>(systemclk_dur);
    auto dr_min = duration_cast<minutes>(systemclk_dur);
    auto dr_sec = duration_cast<seconds>(systemclk_dur);
    auto dr_msec = duration_cast<milliseconds>(systemclk_dur);
    auto dr_mcsec = duration_cast<microseconds>(systemclk_dur);
    auto dr_nsec = duration_cast<nanoseconds>(systemclk_dur);

    std::cout << "epoch time: "
              << systemclk_dur.count() << " | "  // unit of nanoseconds
              << dr_hour.count() << " | "        // unit of hours = unit of sec / 3600
              << dr_min.count() << " | "         // unit of minutes = unit of sec / 60
              << dr_sec.count() << " | "         // unit of seconds
              << dr_msec.count() << " | "        // unit of milliseconds
              << dr_mcsec.count() << " | "       // unit of microseconds
              << dr_nsec.count() << std::endl;   // unit of nanoseconds

    // count() is member of <duration> type
    std::cout << "value of msec: " << (tp_msec - tp_sec).count() << std::endl;
    std::cout << "value of msec: " << milliseconds{tp_msec - tp_sec}.count() << std::endl;
    std::cout << "value of msec: " << duration_cast<milliseconds>(tp_msec - tp_sec).count() << std::endl;
	
    std::cout << "value of msec: " << (dr_msec - dr_sec).count() << std::endl;
    std::cout << "value of msec: " << milliseconds{dr_msec - dr_sec}.count() << std::endl;
    std::cout << "value of msec: " << duration_cast<milliseconds>(dr_msec - dr_sec).count() << std::endl;

    std::cout << "value of msec: " << (dr_msec%seconds{1}).count() << std::endl; // most recommand one
    std::cout << "value of msec: " << milliseconds{dr_msec%seconds{1}}.count() << std::endl;
    std::cout << "value of msec: " << duration_cast<milliseconds>(dr_msec%seconds{1}).count() << std::endl;


    // std::cout << "Error: " << tp_msec.count() << std::endl;
    std::cout << "Error: " << dr_msec.count() << std::endl;

    // std::cout << "plain: " << util::time::to_plain_string(*systemclk_tm) << std::endl;
    // std::cout << "plain w ms: " << util::time::to_plain_string(*systemclk_tm, dr_msec - dr_sec) << std::endl;
    // std::cout << "rfc3339: " << util::time::to_rfc3339_string(*systemclk_tm) << std::endl;
    // std::cout << "rfc3339 w ms: " << util::time::to_rfc3339_string(*systemclk_tm, dr_msec - dr_sec) << std::endl;
    // std::cout << "timestr: " << util::time::to_string(systemclk_tt) << std::endl;
    // std::cout << "timestr w ms: " << util::time::to_string(systemclk_tt, milliseconds{dr_msec.count() - dr_sec.count()*1000}) << std::endl;

    std::cout << "systemclk epoch nano: " << systemclk_tp.time_since_epoch().count() << std::endl;
    std::cout << "systemclk max: " << system_clock::to_time_t(systemclk_tp.max()) << std::endl;
    std::cout << "systemclk min: " << system_clock::to_time_t(systemclk_tp.min()) << std::endl;
    std::cout << "systemclk time_t: " << systemclk_tt << std::endl;
    std::cout << "systemclk ctime: " << std::ctime(&systemclk_tt);  // cstyle time_t param
    std::cout << "systemclk asctime: " << std::asctime(std::localtime(&t)); // cstyle  tm param
    std::cout << "systemclk UTC: " << std::put_time(std::gmtime(&t), "%c %Z") << std::endl;  //since C++ 11
    std::cout << "systemclk Local: " << std::put_time(std::localtime(&t), "%c %Z")<< std::endl; //since C++ 11
    std::cout << "systemclk Local: " << std::put_time(std::localtime(&t),  "%m/%d/%y")<< std::endl; //since C++ 11

    // POSIX-specific:
    // std::string tz = "TZ=Asia/Korea";
    // putenv(tz.data());  //.data() == .c_str()
    // std::cout << "KST: " << std::put_time(std::localtime(&t), "%c %Z") << std::endl;

    // std::cout << duration_cast<microseconds>(steadyclk_now).count();
    // std::cout << "highres clock: " << highresclk_tt << std::endl;


//  elapsed time by system_clock
    std::cout << std::endl << ">>>> elapsed time by system_clock <<<<" << std::endl;
    auto systemclk_start = system_clock::now();
    std::cout << "f(42) = " << fibonacci(42) << '\n';
    auto systemclk_end = system_clock::now();

    duration<double> elapsed_seconds = systemclk_end-systemclk_start;
    std::time_t end_time = system_clock::to_time_t(systemclk_end);

    std::cout << "finished computation at " << std::ctime(&end_time)
              << "elapsed time: " << elapsed_seconds.count() << "s\n";


//  elapsed time by steady_clock
    std::cout << std::endl << ">>>> elapsed time by steady_clock <<<<" << std::endl;
    steady_clock::time_point steadyclk_start = steady_clock::now();
    std::cout << "f(42) = " << fibonacci(42) << '\n';
    steady_clock::time_point steadyclk_end = steady_clock::now();
    std::cout << "Printing took "
              << duration_cast<microseconds>(steadyclk_end - steadyclk_start).count()
              << "us.\n";


//  time_point to string by put_time :: time_point > time_t > std::localtime(&time_t) > put_time(&tm)
    std::cout << std::endl << ">>>> time_point to string by put_char() <<<<" << std::endl;
    systemclk_tp = system_clock::now();
    systemclk_tt = system_clock::to_time_t(systemclk_tp - hours(24));
    std::cout << "24 hours ago, the time was "
              << std::put_time(std::localtime(&systemclk_tt), "%F %T") << '\n';


//  time_point to string :: time_point > time_t > std::ctime(&time_t)
    std::cout << std::endl << ">>>> time_point to string by std::ctime() <<<<" << std::endl;
    systemclk_tp = system_clock::now();

    systemclk_tt = system_clock::to_time_t(systemclk_tp);
    std::cout << std::ctime(&systemclk_tt);


//  time_point to string with millisec
    std::cout << std::endl << ">>>> time_point to string with millisec <<<<" << std::endl;
    highresclk_tp = high_resolution_clock::now();
    milliseconds ms = duration_cast<milliseconds>(highresclk_tp.time_since_epoch());

    seconds s = duration_cast<seconds>(ms);
    highresclk_tt = s.count();
    std::size_t fractional_seconds = ms.count() % 1000;

    std::cout << std::ctime(&highresclk_tt);
    std::cout << fractional_seconds << std::endl;
}


class Chronotest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Chronotest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Chronotest() {
        // You can do clean-up work that doesn't throw exceptions here.
    }

    virtual void SetUp() override {
        // Code here will be called immediately after each test
        // (right before each test).
    }

    virtual void TearDown() override {
        // Code here will be called immediately after each test
        // (right before the destructor).
        // No exception handling allowed here.
    }
};

TEST_F(Chronotest, chronotest) {
    chrono_exam();
}
