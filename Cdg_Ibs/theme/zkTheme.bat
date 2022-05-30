@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem ZK Theme Script 
rem
rem Date : 2008/10/28
rem
rem Author : Ryan Wu
rem 
rem Options
rem     create          Create a ZK default theme folder
rem     jar               Jar a folder into ZK theme jar file
rem 
rem Copyright (c) 2008 Potix Corporation. All rights reserved.
rem ---------------------------------------------------------------------------
set CURRENT_DIR=%cd%
if "%1" == "" goto displayUsage
if "%1" == "create" goto checkCreate
if "%1" == "jar" goto checkJar
rem no arguments
goto displayUsage
:checkCreate
if "%2" == "" goto noProject
set ProjectName=%2
if not exist "%cd%\Data" goto noData
if not exist "%cd%\Data\img.css.dsp" goto noCSS
goto doCreate
:checkJar
if "%2" == "" goto noProject
set ProjectName=%2
if not exist "%ProjectName%" goto noProject
cd %ProjectName%
rem Use JDK coz have to jar file
if not exist "%JAVA_HOME%\jre\bin\server\jvm.dll" goto noJDK
goto doJar
:noJDK
echo ^>     [Error] Cannot found enviroument attribute : JAVA_HOME
echo               Must point at your Java Development Kit installation.
goto end
:noData
echo ^>     [Error] Cannot found resource folder : %cd%\Data
echo               Must extract it from ZKTheme package.
goto end
:noCSS
echo ^>     [Error] Cannot found resource file : %cd%\Data\img.css.dsp
echo               Must extract it from ZKTheme package.
goto end
:noProject
echo ^>     [Error] Cannot found project folder name : %ProjectName%
echo               Must create it first. You can try : ZKTheme create %ProjectName%
goto end
:doCreate
set Resource=Data\img
rem create directory structure
md %ProjectName% >NUL 2>NUL
md %ProjectName%\web >NUL 2>NUL
md %ProjectName%\web\%ProjectName% >NUL 2>NUL
md %ProjectName%\metainfo >NUL 2>NUL
md %ProjectName%\metainfo\zk >NUL 2>NUL
rem lang-addmon
set metainfo=%ProjectName%\metainfo\zk\lang-addon.xml
rem img.css.dsp file
set imgcss=%ProjectName%\web\%ProjectName%\img.css.dsp
xcopy "%Resource%" "%ProjectName%\web\%ProjectName%\img" /s /C /I /Q /Y  >NUL 2>NUL
echo ^<?xml version="1.0" encoding="UTF-8"?^> > %metainfo%
echo ^<language-addon^> >> %metainfo%
echo 	^<addon-name^>%ProjectName%^</addon-name^> >> %metainfo%
echo 	^<depends^>zul,zkex,zkmax^</depends^> >> %metainfo%
echo 	^<language-name^>xul/html^</language-name^> >> %metainfo%
echo 	^<stylesheet href="~./%ProjectName%/img.css.dsp" type="text/css"/^> >> %metainfo%
echo ^</language-addon^> >> %metainfo%
rem generate img.css.dsp
echo ^<%%@ page contentType="text/css;charset=UTF-8" %%^> > %imgcss%
echo ^<%%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %%^> >> %imgcss%
echo ^<c:set var="project" value="%ProjectName%/img"/^> >> %imgcss%
type Data\img.css.dsp >> %imgcss%
rem generate done
echo ^>     [INFO] Finish create project %ProjectName%.
goto end
:doJar
jar cfM ..\%ProjectName%.jar metainfo
jar ufM ..\%ProjectName%.jar web
echo ^>     [INFO] Finish archive project %ProjectName%.
goto end
:displayUsage
echo.
echo USAGE:
echo         zkTheme [OPTION] "project name"
echo DESCRIPTION:
echo         Create theme template and archive folders into jars.
echo OPTIONS:
echo         create         Create a ZK default theme folder
echo         jar            Jar a folder into ZK theme jar file
echo EXAMPLES:
echo         zkTheme create myZKtheme
echo         zkTheme jar myZKtheme
echo REPORTING BUGS
echo         Report bugs to ^<ryanwu@zkoss.org^>
goto end
:end
cd %CURRENT_DIR% 
