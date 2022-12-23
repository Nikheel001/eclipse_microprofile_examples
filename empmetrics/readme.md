same app as empconfigtry

explore Application Metrics Programming Model in specs

Annotations
* *@Counted* => M, C, T
* *@ConcurrentGauge* => M, C, T
* *@Gauge* => M
* *@Metered* => M, C, T
* *@Metric* => F, P
* *@Timed* => M, C, T
* *@SimplyTimed* => M, C, T
* *@RegistryType*

(C=Constructor, F=Field, M=Method, P=Parameter, T=Type)

explore usage of field "tags" in annotations for Kubernetes.</br>
explore vendor and base matrics as well </br>
explore matrics formats JSON,OpenMetrics. </br>

```
http://localhost:8080/empmetrics/metrics
http://localhost:8080/empmetrics/metrics/application
http://localhost:8080/empmetrics/metrics/base
http://localhost:8080/empmetrics/metrics/vendor
```

* JSON format - used when the HTTP Accept header best matches application/json.
* OpenMetrics text format - used when the HTTP Accept header best matches text/plain or when
Accept header would equally accept both text/plain and application/json and there is no other
higher precedence format. This format is also returned when no media type is requested (i.e. no
Accept header is provided in the request)