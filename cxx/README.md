# cxxsampler
c++ snippets of codes, datastructure, algorithm

Read howtorun.txt in main directory

## compile and run main 
```
main$ make run
or
main$ make -j`nproc` run
or
main$ ./makep run
```

## compile and run gtest
```
main$ make gtest.run <TestSuitName>.<TestName>

main$ make gtest.run VanilaTest.test1
main$ make gtest.run "VanilaTest.*"
main$ make gtest.run VanilaTest.\*
or
main$ make -j`nproc` gtest.run VanilaTest.test1
main$ make -j`nproc` gtest.run "VanilaTest.*"
main$ make -j`nproc` gtest.run VanilaTest.\*
or
main$ ./makep gtest.run VanilaTest.test1
main$ ./makep gtest.run "VanilaTest.*"
main$ ./makep gtest.run VanilaTest.\*
```

## ./makeg is only for gtest
```
main$ ./makeg VanilaTest.test1
main$ ./makeg VanilaTest.*"
main$ ./makeg VanilaTest.\*
```


## clean
```
main$ make clean
```