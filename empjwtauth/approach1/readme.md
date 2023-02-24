service
has public key
has resource
must decode jwt(using public key)

client
has private key
has encrypted jwt (using private key)

exchange keys on repeated interval
explore about token expire

*maybe bug or intended
when rest endpoints are inside ConfigMain extends Application class
service will not work as expected if deployed in apache tomee 9.0.
however service will work as expected if deployed in openliberty.

but when two seperate classes are created.
1. ConfigMain extends Application (annoted with @ApplicationPath("") and @LoginConfig(authMethod = "MP-JWT"))
2. RestEndpoint (@Path("rest"))

it works in both