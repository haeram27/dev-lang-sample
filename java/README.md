# How to run tests
## test specific tests 
```
$ gradle test --tests '<test-class>.<test-method>'
$ gradle test --tests 'HelloTests.hello'
$ gradle test --tests 'Hello*.hello'
$ gradle test --tests '*.hello'
$ gradle test --tests 'HelloTests.he*'
$ gradle test --tests 'Hello*.hel*'
```
## force rerun test (to prevent gradle using test cache) 
```
$ gradle test --rerun --tests 'HelloTests.hello'
$ gradle test --rerun-tasks --tests 'HelloTests.hello'
```