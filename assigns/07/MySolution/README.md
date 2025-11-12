# Assignment 7 MySolution README

## Game of 24 Solution Using BFS and DFS

## Algorithm Overview
The Game of 24 solver works in four steps:

1) Generate all expression trees: The makeTree() method recursively combines pairs of numbers using +, -, *, / operations until all numbers are used, creating all possible arithmetic expressions.

2) Wrap as a tree structure: The wrapAsTree() method places all generated expressions as children under a dummy root node, creating a general tree suitable for traversal.

3) Enumerate using BFS and DFS:

Both methods enumerate all expressions, leading to the same output.

4) Filter for solutions: The stream is filtered to remove the dummy root, then filtered again to find expressions that evaluate to 24.

## Changes to Assign07_01
No changes were made to the MyDequeList implementation from Assignment 4.