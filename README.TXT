##################################################
# Java Program to parse african mobile numbers
# made as a test exercise by Marco Barbaro
# 28/09/2021
# Feel free to modify the program 
##################################################

##################################################
# RELEASE NOTES
##################################################

NetBeans 8.2 has been used as editor.
The program works either by command line and takes as input the full path to a csv file made as the following example:


id,sms_phone
103343262,27123456789
...


Correct syntax for a number is: 27123456789

Longer numbers will be truncated and still accepted with a warning if starting with "27" String

Launching jar file in "dist" folder will offer an executable java swing jar to test the parser, where you could insert 
a number and test if it is written in a correct form.

Junit tests are launchable from NetBean "AfricanMobileTest" class. The tests check 3 situations: a good number, a bad number and a modified one





