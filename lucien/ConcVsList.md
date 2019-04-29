# All List methods
- `&`
- `++`
- `-`
- `--`
- `:+`
- `::`
- `apply`
- `chunks`
- `contains`
- `content`
- `count`
- `drop`
- `dropWhile`
- `evenSplit`
- `exists`
- `fill`
- `filter`
- `filterNot`
- `find`
- `flatMap`
- `flatten`
- `foldLeft`
- `foldRight`
- `forall`
- `groupBy`
- `head`
- `headOption`
- `indexOf`
- `indexWhere`
- `init`
- `insertAt`
- `isEmpty`
- `isSorted`
- `last`
- `lastOption`
- `length`
- `map`
- `mkString`
- `nonEmpty`
- `padTo`
- `partition`
- `range`
- `replace`
- `replaceAt`
- `reverse`
- `rotate`
- `scanLeft`
- `scanRight`
- `size`
- `slice`
- `sorted`
- `sortedIns`
- `split`
- `splitAt`
- `splitAtIndex`
- `tail`
- `tailOption`
- `take`
- `takeWhile`
- `toMap`
- `toSet`
- `unapply`
- `unique`
- `updated`
- `withFilter`
- `zip`

# All ConcRope methods
- `abs`
- `append`
- `appendInv`
- `appendPriv`
- `balanced`
- `concInv`
- `concat`
- `concatNonEmpty`
- `concatNormalized`
- `insert`
- `insertAtIndex`
- `isEmpty`
- `isLeaf`
- `isNormalized`
- `level`
- `lookup`
- `max`
- `normalize`
- `numTrees`
- `size`
- `split`
- `toList`
- `update`
- `valid`
- `wrap`

# Nicer methods that would be good to have
- `++`
- `:+`
- `::`
- `-`
- `--`

# Methods in List that could be good to have in ConcRope
- `map`
- `flatMap`
- `flatten`
- `fold`
- `foldLeft`
- `foldRight`
- `contains`
- `content`
- `drop`
- `exists`
- `filter`
- `find`
- `forall`
- `groupBy`
- `indexOf`
- `indexWhere`
- `isEmpty`
- `isSorted`
- `head`
- `last`
- `mkString`
- `replace`
- `reverse`
- `scan`
- `sort`
- `split`
- `tail`
- `take`
- `toMap`
- `toSet`
- `unique`
- `zip`
- `-`
- `--`

# Comparison
List | ConcRope
--|--
`&` | :x:
`++` | Similar function `concat`
`-` | :x:
`--` | :x:
`:+` | Similar function `append`
`::` | Similar function `insertAtIndex`
`apply` | Similar function `lookup`
`chunks` | :x:
`contains` | :x:
`content` | :x:
`count` | :x:
`drop` | :x:
`dropWhile` | :x:
`evenSplit` | Similar function `split`
`exists` | :x:
`fill` | :x:
`filter` | :x:
`filterNot` | :x:
`find` | :x:
`flatMap` | :x:
`flatten` | :x:
`foldLeft` | :x:
`foldRight` | :x:
`forall` | :x:
`groupBy` | :x:
`head` | :x:
`headOption` | :x:
`indexOf` | :x:
`indexWhere` | :x:
`init` | :x:
`insertAt` | Similar function `insertAtIndex`
`isEmpty` | :heavy_check_mark:
`isSorted` | :x:
`last` | :x:
`lastOption` | :x:
`length` | Similar function `size`
`map` | :x:
`mkString` | :x:
`nonEmpty` | :x:
`padTo` | :x:
`partition` | :x:
`range` | :x:
`replace` | :x:
`replaceAt` | Similar function `update`
`reverse` | :x:
`rotate` | :x:
`scanLeft` | :x:
`scanRight` | :x:
`size` | :heavy_check_mark:
`slice` | :x:
`sorted` | :x:
`sortedIns` | :x:
`split` | :heavy_check_mark:
`splitAt` | Similar function `split`
`splitAtIndex` | Similar function `split`
`tail` | :x:
`tailOption` | :x:
`take` | :x:
`takeWhile` | :x:
`toMap` | :x:
`toSet` | :x:
`unapply` | :x:
`unique` | :x:
`updated` | Similar function `update`
`withFilter` | :x:
`zip` | :x:
