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
- `fold`
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
`head` | Similar function `lookup`
`headOption` | Similar function `lookup`
`indexOf` | :x:
`indexWhere` | :x:
`init` | :x:
`insertAt` | Similar function `insertAtIndex`
`isEmpty` | :heavy_check_mark:
`isSorted` | :x:
`last` | Similar function `lookup`
`lastOption` | Similar function `lookup`
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
`split` | :x: 
`splitAt` | :x:
`splitAtIndex` | :heavy_check_mark: `split`
`tail` | :x:
`tailOption` | :x:
`take` | :x:
`takeWhile` | :x:
`toMap` | :x:
`toSet` | :x:
`unapply` | :x:
`unique` | :x:
`updated` | :heavy_check_mark: `update`
`withFilter` | :x:
`zip` | :x:
