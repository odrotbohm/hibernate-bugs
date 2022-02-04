# Custom EmbeddedInstantiator not considered for embedded identifier types

## Phenomenon

A custom EmbeddedInstantiator explicitly defined on a type that's used as `@EmbeddedId` is not used.

This can be fundamentally observed by defining an embeddable class without a default constructor.
Hibernate the fails with not being able to find it.
It shouldn't actually need to do that as the construction will ultimately be done by the custom `EmbeddableInstantiator` defined.
If you add a default constructor to the class, it works but through the default mechanism of actually setting values on the instance created through the default constructor.
You can ultimately reveal the problem by rather using a Java record as id type as for those the JVM rejects setting field values in them.

## How to reproduce?

```
$ mvn clean install
```
