same app as empconfigtry

using
<b>
*@Liveness*, *@Readiness*, *@Startup*
</b>
annotation

```
http://localhost:8080/emphealthtry/health
http://localhost:8080/emphealthtry/health/live
http://localhost:8080/emphealthtry/health/ready
http://localhost:8080/emphealthtry/health/started
```

<h2> Different kinds of Health Checks </h2>

Difference between health check procedures is only semantic.
The nature of the procedure is defined by annotating the *HealthCheck* procedure with a specific annotation.

* Readiness checks defined with *@Readiness* annotation
* Liveness checks defined with *@Liveness* annotation

A *HealthCheck* procedure with none of the above annotations is not an active procedure and should be ignored.

1. <h2> Readiness check </h2>
A Health Check for readiness allows third party services to know if the application is ready to process requests or not.
</br>The *@Readiness* annotation must be applied on a *HealthCheck* implementation to define a readiness check procedure, otherwise, this annotation is ignored.

2. <h2> Liveness check </h2>
A Health Check for liveness allows third party services to determine if the application is running.
This means that if this procedure fails the application can be discarded (terminated, shutdown).
</br>The *@Liveness* annotation must be applied on a *HealthCheck* implementation to define a Liveness check procedure, otherwise, this annotation is ignored.

3. <h2> Startup check </h2>
A Health check for startup allows applications to define startup probes that are used for initial verification of the application before the liveness probe takes over.
</br>The *@Startup* annotation must be applied on a *HealthCheck* implementation to define a startup check procedure, otherwise, this annotation is ignored.
