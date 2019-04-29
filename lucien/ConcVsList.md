# All List methods
- `&`
- `++`
- `-`
- `--`
- `:+`
- `::`
- `appendAssoc`
- `appendContent`
- `appendIndex`
- `appendInsert`
- `appendTakeDrop`
- `appendUpdate`
- `apply`
- `applyForAll`
- `associative`
- `chunks`
- `consIndex`
- `contains`
- `content`
- `count`
- `drop`
- `dropWhile`
- `evenSplit`
- `exists`
- `existsAssoc`
- `fill`
- `filter`
- `filterNot`
- `find`
- `flatMap`
- `flatten`
- `flattenPreservesContent`
- `foldLeft`
- `foldRight`
- `folds`
- `forall`
- `forallAssoc`
- `groupBy`
- `head`
- `headOption`
- `headReverseLast`
- `indexOf`
- `indexWhere`
- `init`
- `insertAt`
- `insertAtImpl`
- `isEmpty`
- `isSorted`
- `last`
- `lastOption`
- `leftUnitAppend`
- `length`
- `map`
- `mkString`
- `nonEmpty`
- `padTo`
- `partition`
- `range`
- `rec`
- `replace`
- `replaceAt`
- `replaceAtImpl`
- `reverse`
- `reverseAppend`
- `reverseIndex`
- `reverseReverse`
- `rightUnitAppend`
- `rotate`
- `scanLeft`
- `scanRight`
- `scanVsFoldLeft`
- `scanVsFoldRight`
- `size`
- `slice`
- `snocAfterAppend`
- `snocFoldRight`
- `snocIndex`
- `snocIsAppend`
- `snocLast`
- `snocReverse`
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
- `appendAssocInst`
- `appendAssocInst2`
- `appendInsertIndex`
- `appendInv`
- `appendPriv`
- `balanced`
- `concInv`
- `concat`
- `concatNonEmpty`
- `concatNormalized`
- `insert`
- `insertAppendAxiomInst`
- `insertAtIndex`
- `instAppendIndexAxiom`
- `instAppendUpdateAxiom`
- `instSplitAxiom`
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
`:+` | Similar function `insert`
`::` | Similar function `insert`
`appendAssoc` | Similar function `insert`
`appendContent` | Similar function `insert`
`appendIndex` | Similar function `insert`
`appendInsert` | Similar function `insert`
`appendTakeDrop` | Similar function `insert`
`appendUpdate` | Similar function `insert`
`apply` | Similar function `lookup`
`applyForAll` | :x:
`associative` | 
`chunks` |  
`consIndex` |
`contains` | :x:
`content` | :x:
`count` |
`drop` | :x:
`dropWhile` | :x:
`evenSplit` | Similar function `split`
`exists` |
`existsAssoc` |
`fill` |
`filter` | :x:
`filterNot` | :x:
`find` |
`flatMap` | :x:
`flatten` | :x:
`flattenPreservesContent` | :x:
`foldLeft` | :x:
`foldRight` | :x:
`folds` | :x:
`forall` | :x:
`forallAssoc` | :x:
`groupBy` | :x:
`head` | :x:
`headOption` | :x:
`headReverseLast` | :x:
`indexOf` | Similar function `lookup`
`indexWhere` |
`init` |
`insertAt` |
`insertAtImpl` |
`isEmpty` | :heavy_check_mark:
`isSorted` | :x:
`last` | :x:
`lastOption` | :x:
`leftUnitAppend` |
`length` | Similar function `size`
`map` | :x:
`mkString` | :x:
`nonEmpty` | :x:
`padTo` |
`partition` |
`range` |
`rec` |
`replace` |
`replaceAt` |
`replaceAtImpl` |
`reverse` |
`reverseAppend` |
`reverseIndex` |
`reverseReverse` |
`rightUnitAppend` |
`rotate` |
`scanLeft` | :x:
`scanRight` | :x:
`scanVsFoldLeft` | :x:
`scanVsFoldRight` | :x:
`size` | :heavy_check_mark:
`slice` |
`snocAfterAppend` |
`snocFoldRight` |
`snocIndex` |
`snocIsAppend` |
`snocLast` |
`snocReverse` |
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
