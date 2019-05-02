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
## Maps
- `map`
- `flatMap`
- `flatten`
## Folds
- `foldMap`
- `foldLeft`
- `foldRight`
- `scanLeft`
- `scanRight`
## Equality and predicates
- `contains`
- `filter`
- `replace`
- `find`
- `forall`
- `exists`
- `-`
- `--`
- `indexOf`
- `indexWhere`
- `unique`
## List-like methods
- `drop`
- `take`
- `head`
- `last`
- `tail`
## Working with data
- `groupyBy`
- `zip`
## Transformation
- `toSet`
- `toMap`
- `content`
## Reordering elements
- `reverse`
- `isSorted`
- `sort`
## Misc
- `nonEmpty`
- `mkString`
- `split`

# Comparison
List | ConcRope | Extended ConcRope
--|--|--
`&` | :x: | :x:
`++` | Similar function `concat` | :heavy_check_mark:
`-` | :x: | :x:
`--` | :x: | :x:
`:+` | Similar function `append` | Similar function `append`
`::` | Similar function `insertAtIndex` | Similar function `insertAtIndex`
`apply` | Similar function `lookup` | :heavy_check_mark:
`chunks` | :x: | :x:
`contains` | :x: | :x:
`content` | :x: | :x:
`count` | :x: | :x:
`drop` | :x: | :x:
`dropWhile` | :x: | :x:
`evenSplit` | Similar function `split` | Similar function `split`
`exists` | :x: | :heavy_check_mark:
`fill` | :x: | :x:
`filter` | :x: | :x:
`filterNot` | :x: | :x:
`find` | :x: | :x:
`flatMap` | :x: | :heavy_check_mark:
`flatten` | :x: | :heavy_check_mark: as in List, not in class
`foldLeft` | :x: | :heavy_check_mark:
:x: | :x: | `foldMap`
`foldRight` | :x: | :heavy_check_mark:
`forall` | :x: | :heavy_check_mark:
`groupBy` | :x: | :x:
`head` | Similar function `lookup` | :heavy_check_mark:
`headOption` | Similar function `lookup` | :heavy_check_mark:
`indexOf` | :x: | :x:
`indexWhere` | :x: | :x:
`init` | :x: | :x:
`insertAt` | Similar function `insertAtIndex` | Similar function `insertAtIndex`
`isEmpty` | :heavy_check_mark: | :heavy_check_mark:
`isSorted` | :x:
`last` | Similar function `lookup` | Similar function `lookup`
`lastOption` | Similar function `lookup` | Similar function `lookup`
`length` | Similar function `size` | Similar function `size`
`map` | :x: | :heavy_check_mark:
`mkString` | :x: | :x:
`nonEmpty` | :x: | :x:
`padTo` | :x: | :x:
`partition` | :x: | :x:
`range` | :x: | :x:
`replace` | :x: | :x:
`replaceAt` | Similar function `update` | Similar function `update`
`reverse` | :x: | :x:
`rotate` | :x: | :x:
`scanLeft` | :x: | :x:
`scanRight` | :x: | :x:
`size` | :heavy_check_mark: | :heavy_check_mark:
`slice` | :x: | :x:
`sorted` | :x: | :x:
`sortedIns` | :x: | :x:
`split` | :x:  | :x: 
`splitAt` | :x: | :x:
`splitAtIndex` | :heavy_check_mark: `split` | :heavy_check_mark: `split`
`tail` | :x: | :x:
`tailOption` | :x: | :x:
`take` | :x: | :x:
`takeWhile` | :x: | :x:
`toMap` | :x: | :x:
`toSet` | :x: | :x:
`unapply` | :x: | :x:
`unique` | :x: | :x:
`updated` | :heavy_check_mark: `update` | :heavy_check_mark: `update`
`withFilter` | :x: | :x:
`zip` | :x: | :x:
