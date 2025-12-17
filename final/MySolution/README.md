# Final MySolution


## File Explanations

When running each file, route to .../final/MySolution and then run:

javac -cp ".;.." Final_0#.java
java -cp ".;.." Final_0#

(Replacing # with the number you want to run)



### Final_01

    I implemented Final_01 to read the text file pg2701.txt and build a lazy stream of words using LnStrm and FnList. I defined words as sequences of letters and apostrophes, converting all letters to lowercase. This allows me to iterate through the words one by one without loading the entire file into memory.


### Final_02

    In Final_02, I took the word stream from Final_01 and converted it into an array, then sorted it using my quicksort implementation from Assign06_03. I counted consecutive duplicates to generate word-count pairs, and then I sorted the pairs by frequency using mergesort from Assign05_01. The result is a list of words with their occurrences, sorted first by count and then lexicographically.


### Final_03

    In Final_03, I implemented word counting using a hash map with quadratic probing (from Assign08_02) instead of sorting an array first. I converted the hash map into a list of (word, count) pairs and then used mergesort from Assign05_01 to order the list by decreasing frequency, breaking ties alphabetically. This allowed me to efficiently count words without creating a large intermediate array.


### Final_04

    In Final_04, I implemented a generic reversible binary search tree (MyBST) based on Quiz02_06. I added support for standard BST operations like insertion, search, and traversal, and I also implemented functionality to reverse the tree efficiently. I also incorporated mergesort from Assign05_01 to maintain sorted lists of tree elements according to the guidelines.


### Final_05

    In Final_05, I used FnList, FnTuple, and streams to process data in a functional style. I built the program by combining small functions to transform and compute data, without changing the original values. This lets the program work efficiently and reuse functions for different tasks.


## Important Changes:

    When submitting "Final-lib" on gradescope, there was no "MyFinalLib" folder in my directory, so I thought you wanted the library in final/MyLibrary. Today I copied all of the files from final/MyLibrary into final/MyFinalLib (without changing the library itself).

    In Final_00.java, I changed the "Paths.get("./../Data/pg2701.txt")" to "Paths.get("final/data/pg2701.txt")" so that it would work. Because the original line caused an error:
        "Cannot read field "head" because "this.root" is null"
    I also changed Final_00.java's imports of FnList and LnStrm from Library to MyFinalLib

    I also compiled each of the files in MyFinalLib into .class files

    For the generic binary search tree in Final_04, I changed MyBST (Reversible Binary Search Tree) to be generic so that it followed the guidelines