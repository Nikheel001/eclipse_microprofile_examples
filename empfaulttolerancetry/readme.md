same app as empconfigtry

the Fault Tolerance specification is to focus on the following aspects:
* Timeout: Define a duration for timeout
* Retry: Define a criteria on when to retry
* Fallback: provide an alternative solution for a failed execution.
* CircuitBreaker: offer a way of fail fast by automatically failing execution to prevent the system
overloading and indefinite wait or timeout by the clients.
* Bulkhead: isolate failures in part of the system while the rest part of the system can still function.

This specification introduces the following interceptor bindings:
* Timeout
* Retry
* Fallback
* CircuitBreaker
* Bulkhead
* Asynchronous (using Future,....)

<b> 1.Retry</b>

Microprofile Fault Tolerance has a feature called Retry that can be used to recover an operation from failure, invoking the same operation again until it reaches its stopping criteria.

The Retry policy allows to configure :

* maxRetries: the maximum retries

* delay: delays between each retry

* delayUnit: the delay unit

* maxDuration: maximum duration to perform the retry for.

* durationUnit: duration unit

* jitter: the random vary of retry delays

* jitterDelayUnit: the jitter unit

* <u>retryOn: specify the failures to retry on (by throwing exception)</u>

* abortOn: specify the failures to abort on

To use this feature you can annotate a class and/or a method with the @Retry annotation. Check the specification for more details.