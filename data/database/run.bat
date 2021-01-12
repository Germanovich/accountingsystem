C:
cd C:\Apache\webapps
del accountingsystem.*
rd /s accountingsystem
F:
cd F:\java\final\controller\target
copy *.war C:\Apache\webapps
pause
C:
cd C:\Apache\bin
startup
pause