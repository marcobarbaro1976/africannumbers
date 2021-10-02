##################################################
# Java Program to parse african mobile numbers
# made as a test exercise by Marco Barbaro
# email: marco.barbaro@gmail.com
# 28/09/2021
# Feel free to modify the program 
##################################################

##################################################
# RELEASE NOTES
##################################################

NetBeans 8.2 has been used as editor.
The program works either by command line or test gui taking as main:

java -cp dist/AfricanMobilePhones.jar africanmobilephones.MainAfricanNumbers

or

java -cp dist/AfricanMobilePhones.jar africanmobilephones.SingleNumberTester

the first program will produce an output file into an html file, taking as parameter the full path to a csv file made as the following example:

id,sms_phone
103343262,27123456789
...


Correct syntax for a number is: 27123456789

Longer numbers will be truncated and still accepted with a warning if starting with "27" String

Bad shaped numbers will only be listed.

A csv example file is available between the file project files.

Consider that you could change the html file path inside props.properties file to modify the html output path.
Please note also that <id> parameter is not used in this version. A second release of the program could take it as a id to insert the data into a db table instead of producing an html file.


About instead the second main class africanmobilephones.Tester it is a swing gui created to test the parser, where you could insert 
a number and see if it is written in a correct form.


Junit tests are launchable from NetBean "AfricanMobileTest" class. The tests check 3 situations: a good number, a bad number and a modified one





