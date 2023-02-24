client
has creds

clientapp
if written in html can be deployed with service2
or single page app 

service1
authenticates client
shares encrypted jwt to client
has secret privatekey
shares public key to service2

service2 (service1 from approach 1)
has resource
gets public key from service1
consumes jwt given by client
must decode jwt(using public key)

for some reason if PrivateKey is loaded and kept as ApplicationScopedBean with a method that Produces it.
program throws runtime exception while using key for generating signed token.

server throws 403 if any required field specified in emp-jwt standered is null or missing in token.