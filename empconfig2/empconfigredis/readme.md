```
mvn clean package tomee:run
http://localhost:8080/empconfigredis/rest
http://localhost:8080/empconfigredis/rest/msg
```

```
mvn liberty:run
http://localhost:9080/rest/
http://localhost:9080/rest/msg
```

redis

```
hset config.empconfigredis "config.welcome.message" "welcome headshot"
hset config.empconfigredis "config.welcome.message" "updated"
```

higher the Ordinal value higher the priority

if key is not found in highest priority configsource tomee|openliberty's config impl looks for value in lower priority configsources.

tomme and openliberty different behavier

if used jakarta.inject.Provider<String> in tomee it works as dynamic configsource
if used String in tomee it doen't work as dynamic configsource
if used String in openliberty it works as dynamic configsource