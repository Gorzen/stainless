# Benchmark

## Big file (*x* lines, *x* words, *x* unique words)
[big.txt](http://norvig.com/big.txt)

### WordCounting

ConcRope Parallel  |  Time
      --|--
Converion List[WC] -> ConcRope[WC]  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 4 threads   | ? seconds
Parallel fold on ConcRope[WC], min size 32, 4 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 8 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 32, 8 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 16 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 32, 16 threads  | ? seconds
Best total time  | ? seconds

ConcRope Sequential  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  |  ? seconds
Sequential fold on ConcRope[WC]  | ? seconds
Total time  | ? seconds

List  |  Time
--|--
Sequential foldLeft on List[WC]  |  ? seconds

### Sorting

Sorting  |  Time
--|--
MergeSort on output (*x* elements)  |  ? seconds
InsertionSort on output (*x* elements)  |  ? seconds

## Medium file (*x* lines, *x* words, *x* unique words)
[medium.txt](???.com)

### WordCounting

ConcRope Parallel  |  Time
      --|--
Converion List[WC] -> ConcRope[WC]  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 4 threads   | ? seconds
Parallel fold on ConcRope[WC], min size 32, 4 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 8 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 32, 8 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 16 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 32, 16 threads  | ? seconds
Best total time  | ? seconds

ConcRope Sequential  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  |  ? seconds
Sequential fold on ConcRope[WC]  | ? seconds
Total time  | ? seconds

List  |  Time
--|--
Sequential foldLeft on List[WC]  |  ? seconds

### Sorting

Sorting  |  Time
--|--
MergeSort on output (*x* elements)  |  ? seconds
InsertionSort on output (*x* elements)  |  ? seconds

## Small file (*x* lines, *x* words, *x* unique words)
```txt
...
```

### WordCounting

ConcRope Parallel  |  Time
      --|--
Converion List[WC] -> ConcRope[WC]  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 4 threads   | ? seconds
Parallel fold on ConcRope[WC], min size 32, 4 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 8 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 32, 8 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 16, 16 threads  | ? seconds
Parallel fold on ConcRope[WC], min size 32, 16 threads  | ? seconds
Best total time  | ? seconds

ConcRope Sequential  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  |  ? seconds
Sequential fold on ConcRope[WC]  | ? seconds
Total time  | ? seconds

List  |  Time
--|--
Sequential foldLeft on List[WC]  |  ? seconds

### Sorting

Sorting  |  Time
--|--
MergeSort on output (*x* elements)  |  ? seconds
InsertionSort on output (*x* elements)  |  ? seconds
