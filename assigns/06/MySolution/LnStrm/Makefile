########################(beg of [Makefile])########################
MyLibrary=./../..
###################################################################

comp::

###################################################################

clean:: ; rm -f *~

cleanall:: clean
cleanall:: ; rm -f *.class

###################################################################

comp:: ; javac -cp .:$(MyLibrary) LnStrm.java
comp:: ; javac -cp .:$(MyLibrary) LnStcn.java
comp:: ; javac -cp .:$(MyLibrary) LnStrmSUtil.java

########################(end of [Makefile])########################
