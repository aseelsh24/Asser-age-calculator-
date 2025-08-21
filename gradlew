#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle and Java processes.
# For example, to pass '-Xms128m -Xmx512m' to Gradle, you can set GRADLE_OPTS="-Xms128m -Xmx512m".
# For example, to pass '-Duser.language=en' to Java, you can set JAVA_OPTS="-Duser.language=en".
DEFAULT_JVM_OPTS=""

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "ERROR: $*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

# Attempt to set JAVA_HOME if it is not already set.
if [ -z "$JAVA_HOME" ]; then
    if $darwin; then
        [ -x '/usr/libexec/java_home' ] && JAVA_HOME=`/usr/libexec/java_home`
    fi
    if [ -z "$JAVA_HOME" ]; then
        # If we're on Cygwin, try to find a Windows-installed JDK.
        if $cygwin; then
            # Windows path separator must be escaped twice
            IFS=$'\n'
            for JAVA_CANDIDATE in `find /cygdrive -maxdepth 4 -name javac.exe -type f 2>/dev/null`; do
                # Check for read access with the -r flag
                if [ -r "$JAVA_CANDIDATE" ]; then
                    # We have to use a subshell to avoid "permission denied" errors on Windows
                    (
                        WIN_PATH=`cygpath -w "$JAVA_CANDIDATE"`
                        # The Windows path may contain spaces, so we need to quote it
                        JAVA_HOME=`dirname "$WIN_PATH"`
                        JAVA_HOME=`dirname "$JAVA_HOME"`
                        # Stop after the first candidate is found
                        break
                    )
                fi
            done
            unset IFS
        fi
    fi
fi

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum number of open files if necessary.
if ! $cygwin && ! $msys; then
    # Mac OS X and AIX don't support 'ulimit -n'
    if $darwin || $nonstop ; then
        :
    else
        ulimit -n -S > /dev/null 2>&1 && ulimit -n `ulimit -n -H` > /dev/null 2>&1
    fi
fi

# Add the 'bin' subdirectory to the path
if [ -d "$APP_HOME/bin" ]; then
    export PATH="$APP_HOME/bin:$PATH"
fi

# Split up the JVM options passed as GRADLE_OPTS and JAVA_OPTS.
# This runs in a subshell so that the IFS change doesn't impact the rest of the script.
(
    IFS=' '
    set -f
    for opt in $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS; do
        JVM_OPTS_ARRAY=("${JVM_OPTS_ARRAY[@]}" "$opt")
    done
)

# Add -XX:MaxMetaspaceSize= to JVM_OPTS_ARRAY if not already set
if ! echo "${JVM_OPTS_ARRAY[@]}" | grep -q "MaxMetaspaceSize"; then
    JVM_OPTS_ARRAY=("${JVM_OPTS_ARRAY[@]}" "-XX:MaxMetaspaceSize=256m")
fi

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Escape CLASS files for Cygwin
if $cygwin ; then
    CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

# Execute Gradle
exec "$JAVACMD" "${JVM_OPTS_ARRAY[@]}" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
