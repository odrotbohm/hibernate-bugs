# Custom EmbeddedInstantiator not considered for embedded identifier types

## Phenomenon

A custom `EmbeddableInstantiator` explicitly defined on a type that's used as `@EmbeddedId` is not used.

This can be fundamentally observed by defining an embeddable class without a default constructor.
Hibernate the fails with not being able to find it.
It shouldn't actually need to do that as the construction will ultimately be done by the custom `EmbeddableInstantiator` defined.
If you add a default constructor to the class, it works but through the default mechanism of actually setting values on the instance created through the default constructor.
You can ultimately reveal the problem by rather using a Java record as id type as for those the JVM rejects setting field values in them.

## How to reproduce?

```
$ mvn clean install
```

## Current failure

The registered `EmbeddableInstantiator` is not used when reading the object

```
…
Caused by: org.hibernate.InstantiationException: Could not instantiate entity:  : example.User$UserId
	at org.hibernate.metamodel.internal.EmbeddableInstantiatorPojoStandard.instantiate(EmbeddableInstantiatorPojoStandard.java:84)
	at org.hibernate.sql.results.graph.embeddable.AbstractEmbeddableInitializer.createCompositeInstance(AbstractEmbeddableInitializer.java:326)
	at org.hibernate.sql.results.graph.embeddable.AbstractEmbeddableInitializer.prepareCompositeInstance(AbstractEmbeddableInitializer.java:242)
	at org.hibernate.sql.results.graph.embeddable.AbstractEmbeddableInitializer.initializeInstance(AbstractEmbeddableInitializer.java:190)
	at org.hibernate.sql.results.graph.embeddable.internal.EmbeddableAssembler.assemble(EmbeddableAssembler.java:34)
	at org.hibernate.sql.results.graph.entity.AbstractEntityInitializer.initializeIdentifier(AbstractEntityInitializer.java:414)
	at org.hibernate.sql.results.graph.entity.AbstractEntityInitializer.resolveEntityKey(AbstractEntityInitializer.java:376)
	at org.hibernate.sql.results.graph.entity.AbstractEntityInitializer.resolveKey(AbstractEntityInitializer.java:304)
	at org.hibernate.sql.results.internal.StandardRowReader.coordinateInitializers(StandardRowReader.java:127)
	at org.hibernate.sql.results.internal.StandardRowReader.readRow(StandardRowReader.java:98)
	at org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:143)
	at org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:32)
	at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.doExecuteQuery(JdbcSelectExecutorStandardImpl.java:437)
	at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.executeQuery(JdbcSelectExecutorStandardImpl.java:166)
	at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.list(JdbcSelectExecutorStandardImpl.java:91)
	at org.hibernate.sql.exec.spi.JdbcSelectExecutor.list(JdbcSelectExecutor.java:31)
	at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.lambda$new$0(ConcreteSqmSelectQueryPlan.java:102)
	at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.withCacheableSqmInterpretation(ConcreteSqmSelectQueryPlan.java:305)
	at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.performList(ConcreteSqmSelectQueryPlan.java:246)
	at org.hibernate.query.sqm.internal.QuerySqmImpl.doList(QuerySqmImpl.java:536)
	at org.hibernate.query.spi.AbstractSelectionQuery.list(AbstractSelectionQuery.java:363)
	... 30 more
Caused by: org.hibernate.PropertyAccessException: Could not set value of type [java.lang.String] : `example.User$UserId.id` (setter)
	at org.hibernate.property.access.spi.SetterFieldImpl.set(SetterFieldImpl.java:79)
	at org.hibernate.metamodel.mapping.internal.AbstractEmbeddableMapping.lambda$setValues$1(AbstractEmbeddableMapping.java:112)
	at org.hibernate.metamodel.mapping.internal.EmbeddableMappingTypeImpl.forEachAttributeMapping(EmbeddableMappingTypeImpl.java:720)
	at org.hibernate.metamodel.mapping.internal.AbstractEmbeddableMapping.setValues(AbstractEmbeddableMapping.java:111)
	at org.hibernate.metamodel.internal.EmbeddableInstantiatorPojoStandard.instantiate(EmbeddableInstantiatorPojoStandard.java:78)
	... 50 more
Caused by: java.lang.IllegalAccessException: Can not set final java.lang.String field example.User$UserId.id to java.lang.String
	at java.base/jdk.internal.reflect.UnsafeFieldAccessorImpl.throwFinalFieldIllegalAccessException(UnsafeFieldAccessorImpl.java:76)
	at java.base/jdk.internal.reflect.UnsafeFieldAccessorImpl.throwFinalFieldIllegalAccessException(UnsafeFieldAccessorImpl.java:80)
	at java.base/jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl.set(UnsafeQualifiedObjectFieldAccessorImpl.java:79)
	at java.base/java.lang.reflect.Field.set(Field.java:799)
	at org.hibernate.property.access.spi.SetterFieldImpl.set(SetterFieldImpl.java:52)
	... 54 more
```

