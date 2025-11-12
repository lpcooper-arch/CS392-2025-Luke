## Assign07_02 Explanation

To solve the Game of 24, we must represent all possible arithmetic expressions as nodes in a general tree (FnGtree).

Every node in the tree is either

    - a TermInt (number)

        or

    - a TermOpr (operation applied to two subterms)


The helper method makeTree recursively combines all possible pairs of terms to generate every valid arithmetic structure.

Each complete expression tree can then be evaluated to check if it equals 24.

We search through the tree using a Breadth-First Search and Depth-First Search (both made in Assign07_01).

Both methods filter results to find all expressions that evaluate to 24.

We then provide code testing in the main method.