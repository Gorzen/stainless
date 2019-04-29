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
`&` | Missing
`++` | Similar function `concat`
`-` | Missing
`--` | Missing
`:+` | Similar function `insert`
`::` | Similar function `insert`
`appendAssoc` | Similar function `insert`
`appendContent` | Similar function `insert`
`appendIndex` | Similar function `insert`
`appendInsert` | Similar function `insert`
`appendTakeDrop` | Similar function `insert`
`appendUpdate` | Similar function `insert`
`apply` | Similar function `lookup`
`applyForAll` | Missing
`associative` | 
`chunks` |  
`consIndex` |
`contains` | Missing
`content` | Missing
`count` |
`drop` | Missing
`dropWhile` | Missing
`evenSplit` | Similar function `split`
`exists` |
`existsAssoc` |
`fill` |
`filter` | Missing
`filterNot` | Missing
`find` |
`flatMap` | Missing
`flatten` | Missing
`flattenPreservesContent` | Missing
`foldLeft` | Missing
`foldRight` | Missing
`folds` | Missing
`forall` | Missing
`forallAssoc` | Missing
`groupBy` | Missing
`head` | Missing
`headOption` | Missing
`headReverseLast` | Missing
`indexOf` | Similar function `lookup`
`indexWhere` |
`init` |
`insertAt` |
`insertAtImpl` |
`isEmpty` | Present
`isSorted` | Missing
`last` | Missing
`lastOption` | Missing
`leftUnitAppend` |
`length` | Similar function `size`
`map` | Missing
`mkString` | Missing
`nonEmpty` | Missing
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
`scanLeft` | Missing
`scanRight` | Missing
`scanVsFoldLeft` | Missing
`scanVsFoldRight` | Missing
`size` | Present
`slice` |
`snocAfterAppend` |
`snocFoldRight` |
`snocIndex` |
`snocIsAppend` |
`snocLast` |
`snocReverse` |
`sorted` | Missing
`sortedIns` | Missing
`split` | Present
`splitAt` | Similar function `split`
`splitAtIndex` | Similar function `split`
`tail` | Missing
`tailOption` | Missing
`take` | Missing
`takeWhile` | Missing
`toMap` | Missing
`toSet` | Missing
`unapply` | Missing
`unique` | Missing
`updated` | Similar function `update`
`withFilter` | Missing
`zip` | Missing
