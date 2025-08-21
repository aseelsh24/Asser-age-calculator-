@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle and Java processes.
@rem For example, to pass '-Xms128m -Xmx512m' to Gradle, you can set GRADLE_OPTS="-Xms128m -Xmx512m".
@rem For example, to pass '-Duser.language=en' to Java, you can set JAVA_OPTS="-Duser.language=en".
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to get the absolute path.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz /?
if "%~1" == "/?" goto mainHelp
if "%~1" == "-?" goto mainHelp
if "%~1" == "--help" goto mainHelp
if "%~1" == "-h" goto mainHelp

@rem Setup the command line
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Split up the JVM options passed as GRADLE_OPTS and JAVA_OPTS
@rem This logic is taken from the catalina.bat file in Tomcat.
set JAVA_OPTS=%JAVA_OPTS% %DEFAULT_JVM_OPTS% %GRADLE_OPTS%
set _SKIP=2
if "%JAVA_OPTS:~0,1%" == """" (
  set _SKIP=3
)
if /i "%JAVA_OPTS:~1,4%" == "exit" (
  set _SKIP=2
)
if not "%JAVA_OPTS%" == "" (
  set _JAVA_OPTS=
  set _CARRIED=
  for %%i in (%JAVA_OPTS%) do (
    if not "%%i" == "" (
      call :append "%%i"
    )
  )
)

:run
@rem Execute Gradle
"%JAVA_EXE%" %_java_opts% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code.
if not "" == "%GRADLE_EXIT_CONSOLE%" (
  exit 1
)
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:mainHelp
echo.
echo To see a list of command-line options, try:
echo.
echo %APP_BASE_NAME% --help
echo.
exit /b 0

:append
set _JAVA_OPTS=%_JAVA_OPTS% %1
goto :eof
