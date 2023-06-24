## Chapter 5 Challenge

### Challenge #3

Build the solution:

```
make java_chap05
```

Run it:

```
cd build/gen/chap05_representing
alias jlox='java com/craftinginterpreters/lox/Lox'
jlox
```

This will construct the expression `(1 + 2) * (4 - 3)`, then convert it into RPN and print it.

Expected `1 2 + 4 3 - *`
