#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <cstring>
#include <iostream>

#include "gtest/gtest.h"
#include "exam/wait_test.h"

#define MAX_PROCESS_NAME 512
#define ESUCCESS 0
#define ENOPROC 1
#define ENONAME 2

using namespace std;


int getProcessNameByPid(int pid, char* name) { 
    int result = ESUCCESS;
    sprintf(name, "/proc/%d/cmdline", pid);
    FILE* fp = fopen(name,"r");

    if(!fp){
        result = ENOPROC;
    } else {
        size_t size;

        size = fread(name, sizeof(char), MAX_PROCESS_NAME , fp);
        if (size > 0) {
            name[size] ='\0';
        } else {
            result = ENONAME;
        }

        fclose(fp);
    }

    return result;
}


void waitexam() {
    pid_t pid;
    int status;

    pid = fork();

    if (pid < 0) {
        // fork failed
        std::cout << "fork error : " << std::endl;

    } else if (pid == 0) {
        // child process
        std::cout << "Child:" << std::endl;
        sleep(2);

    } else {
        // parent process
        int ret = 0;
        char name[MAX_PROCESS_NAME];
        memset(name, 0x00, sizeof(name));

        std::cout << "Parent: wait: pid of child: " << pid << std::endl;
        ret = getProcessNameByPid(pid, name);
        switch (ret) {
            case ESUCCESS:
                std::cout << "The child process name of " << pid << " is " << name << std::endl;
                break;
            case ENOPROC:
                std::cout << "The child process with " << pid << " isn't found." << std::endl;
                break;
            case ENONAME:
                std::cout << "Can't read cmdline of " << pid << std::endl;
                break;
            default:
                std::cout << "Unknown error." << std::endl;
                break;
        }

        pid = waitpid(pid, &status, 0);
        if (pid == -1) {
            //wait failed
        } else {
            if (WIFEXITED(status)) {
                printf("pid(%d) is gracefully terminated with return value (%d).\n", pid, WEXITSTATUS(status));
            } else if (WIFSIGNALED(status)) {
                printf("pid(%d) is forced terminated by signal(%d).\n", pid, WTERMSIG(status));
            } else if (WIFSTOPPED(status)) {
                printf("pid(%d) is stopped by signal(%d).\n", pid, WSTOPSIG(status));
            } else if (WIFCONTINUED(status)) {
                printf("stopped pid(%d) is continued again.\n");
            }
        }
    }
}


class Waittest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Waittest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Waittest() {
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

TEST_F(Waittest, waittest) {
    waitexam();
}