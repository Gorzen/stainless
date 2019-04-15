# Benchmark

## Big file
(128'457 lines, 1'095'683 words, 81'409 unique words) [big.txt](http://norvig.com/big.txt)

### WordCounting

ConcRope Parallel  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  | 2.819627273 seconds
Parallel fold on ConcRope[WC], min size 32, 4 threads   | 9.320218039 seconds
Parallel fold on ConcRope[WC], min size 64, 4 threads  | 8.719147123 seconds
Parallel fold on ConcRope[WC], min size 32, 8 threads  | 9.25718213 seconds
Parallel fold on ConcRope[WC], min size 64, 8 threads  | 9.829431564 seconds
Parallel fold on ConcRope[WC], min size 32, 16 threads  | 9.905632951 seconds
Parallel fold on ConcRope[WC], min size 64, 16 threads  | 9.753053033 seconds
**Best total time**  | **11.538774396 seconds**

ConcRope Sequential  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  |  2.819627273 seconds
Sequential fold on ConcRope[WC]  | 15.049214472 seconds
**Total time**  | **17.868841744999997 seconds**

List  |  Time
--|--
Sequential foldLeft on List[WC]  |  132812.152160333 seconds

### Sorting

Sorting  |  Time
--|--
MergeSort on output (81'409 elements)  |  0.516807123 seconds
InsertionSort on output (81'409 elements)  |  82.337158522 seconds

## Medium file
(9'319 lines, 81'640 words, 15'535 unique words) [medium.txt](http://www.gutenberg.org/files/49022/49022-0.txt)

### WordCounting

ConcRope Parallel  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  | 0.151320588 seconds
Parallel fold on ConcRope[WC], min size 32, 4 threads   | 1.077401937 seconds
Parallel fold on ConcRope[WC], min size 64, 4 threads  | 1.043923395 seconds
Parallel fold on ConcRope[WC], min size 32, 8 threads  | 1.089925326 seconds
Parallel fold on ConcRope[WC], min size 64, 8 threads  | 1.169115119 seconds
Parallel fold on ConcRope[WC], min size 32, 16 threads  | 1.928867299 seconds
Parallel fold on ConcRope[WC], min size 64, 16 threads  | 1.182387879 seconds
**Best total time**  | **1.195243983 seconds**

ConcRope Sequential  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  |  0.151320588 seconds
Sequential fold on ConcRope[WC]  | 1.378202296 seconds
**Total time**  | **1.529522884 seconds**

List  |  Time
--|--
Sequential foldLeft on List[WC]  |  1489.537584071 seconds

### Sorting

Sorting  |  Time
--|--
MergeSort on output (15'535 elements)  |  0.107238649 seconds
InsertionSort on output (15'535 elements)  |  0.808636619 seconds

## Small file
(1'485 lines, 7'262 words, 2'956 unique words) [small.txt](http://www.gutenberg.org/files/46162/46162-0.txt)

### WordCounting

ConcRope Parallel  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  | 0.0482739 seconds
Parallel fold on ConcRope[WC], min size 32, 4 threads   | 0.372035605 seconds
Parallel fold on ConcRope[WC], min size 64, 4 threads  | 0.267831779 seconds
Parallel fold on ConcRope[WC], min size 32, 8 threads  | 0.357384938 seconds
Parallel fold on ConcRope[WC], min size 64, 8 threads  | 0.348161341 seconds
Parallel fold on ConcRope[WC], min size 32, 16 threads  | 0.361774507 seconds
Parallel fold on ConcRope[WC], min size 64, 16 threads  | 0.287062918 seconds
**Best total time**  | **0.316105679 seconds**

ConcRope Sequential  |  Time
--|--
Converion List[WC] -> ConcRope[WC]  |  0.0482739 seconds
Sequential fold on ConcRope[WC]  | 0.292434402 seconds
**Total time**  | **0.34070830199999996 seconds**

List  |  Time
--|--
Sequential foldLeft on List[WC]  |  9.377466506 seconds

### Sorting

Sorting  |  Time
--|--
MergeSort on output (2'956 elements)  |  0.034106565 seconds
InsertionSort on output (2'956 elements)  |  0.087277808 seconds
