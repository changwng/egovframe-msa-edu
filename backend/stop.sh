
setJava8
ps -ewwf | grep build | grep java | awk '{print $2}' | xargs kill -9
#pkill -f build