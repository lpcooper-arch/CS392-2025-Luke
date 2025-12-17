########################(beg of [Makefile])########################
MyFinalLib=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(MyFinalLib) MyQueue.java
comp:: ; javac -cp $(MyFinalLib) MyQueueList.java
comp:: ; javac -cp $(MyFinalLib) MyQueueArray.java
comp:: ; javac -cp $(MyFinalLib) MyQueueFullExn.java
comp:: ; javac -cp $(MyFinalLib) MyQueueEmptyExn.java

###################################################################

test:: \
MyQueueListTest
MyQueueListTest: \
MyQueueListTest.class; java -cp .::$(MyFinalLib) MyQueueListTest
MyQueueListTest.class: MyQueueListTest.java; javac -cp $(MyFinalLib) MyQueueListTest.java

###################################################################

test:: \
MyQueueArrayTest
MyQueueArrayTest: \
MyQueueArrayTest.class; java -cp .::$(MyFinalLib) MyQueueArrayTest
MyQueueArrayTest.class: MyQueueArrayTest.java; javac -cp $(MyFinalLib) MyQueueArrayTest.java

########################(end of [Makefile])########################
