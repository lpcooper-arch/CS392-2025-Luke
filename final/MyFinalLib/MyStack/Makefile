########################(beg of [Makefile])########################
MyFinalLib=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp $(MyFinalLib) MyStack.java
comp:: ; javac -cp $(MyFinalLib) MyStackList.java
comp:: ; javac -cp $(MyFinalLib) MyStackArray.java
comp:: ; javac -cp $(MyFinalLib) MyStackFullExn.java
comp:: ; javac -cp $(MyFinalLib) MyStackEmptyExn.java

###################################################################

test:: \
MyStackListTest
MyStackListTest: \
MyStackListTest.class; java -cp .::$(MyFinalLib) MyStackListTest
MyStackListTest.class: MyStackListTest.java; javac -cp $(MyFinalLib) MyStackListTest.java

###################################################################

test:: \
MyStackArrayTest
MyStackArrayTest: \
MyStackArrayTest.class; java -cp .::$(MyFinalLib) MyStackArrayTest
MyStackArrayTest.class: MyStackArrayTest.java; javac -cp $(MyFinalLib) MyStackArrayTest.java

########################(end of [Makefile])########################
