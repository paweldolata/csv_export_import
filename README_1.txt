Coding Exercise
===============

Readme
------

ACME has a utility which generates audit reports - lists of files owned by
users. Substitute JAVA_HOME with your JVM installation and you can build & run
the utility like this:

	export JAVA_HOME=/usr/lib/jvm/java-7-oracle
	$JAVA_HOME/bin/javac -sourcepath src src/com/egnyte/utils/auditreporter/Runner.java -d bin
	$JAVA_HOME/bin/java -cp bin com.egnyte.utils.auditreporter.Runner resources/users.csv resources/files.csv 


Example output:

	Audit Report
	============
	## User: jpublic
	* audit.xlsx ==> 1638232 bytes
	* movie.avi ==> 734003200 bytes
	* marketing.txt ==> 150680 bytes
	## User: atester
	* pic.jpg ==> 5372274 bytes
	* holiday.docx ==> 570110 bytes


This util works, but marketing department keeps asking for new
functionality.


Your Task
---------

Your task is to add new features - *CSV output* and *TOP #n files report*.


### CSV Output

When run with `-c` flag print the report in csv instead of plain text:

	jpublic,audit.xlsx,1638232
	jpublic,movie.avi,734003200
	jpublic,marketing.txt,150680
	atester,pic.jpg,5372274
	atester,holiday.docx,570110


### TOP #n files

When run with `--top n` should print n-largest files sorted by size, e.g., `--top 3`:

	Top #3 Report
	=============
    * movie.avi ==> user jpublic, 734003200 bytes
    * pic.jpg ==> user atester, 5372274 bytes
    * audit.xlsx ==> user jpublic, 1638232 bytes

Top #n should have a corresponding csv format:

	movie.avi,jpublic,734003200
    pic.jpg,atester,5372274
    audit.xlsx,jpublic,1638232



Other Considerations
--------------------
Please notice to do:

### Refactoring

Refactor the code as needed, but do not over-do it.


### Tests

Please include a test code.


### External libraries

Include them - they will help you get your job done. You can also
include a simple build script to make it more straightforward to deploy. We
should be able to build & run the util on linux.


### Readme/manual

If you want to differ from the spec, please include a short note what was done
and why. Include a short note how to run your code. If you depend on a
convention (e.g. order of arguments `-c` `--top n`) please document it.

