# Issue with Hibernate 6 when using Querydsl

## Phenomenon

When using a Querydsl "contains" `Predicate` (like `QUser.user.firstname.contains(…)`), the execution of the predicate

```
java.lang.NullPointerException: Cannot invoke "org.hibernate.query.BindableType.getBindableJavaType()" because "this.anticipatedType" is null
	at org.hibernate.query.spi.AbstractQueryParameter.getParameterType(AbstractQueryParameter.java:61)
	at com.querydsl.jpa.impl.JPAUtil.setConstants(JPAUtil.java:53)
	at com.querydsl.jpa.impl.AbstractJPAQuery.createQuery(AbstractJPAQuery.java:133)
	at com.querydsl.jpa.impl.AbstractJPAQuery.createQuery(AbstractJPAQuery.java:125)
	at com.querydsl.jpa.impl.AbstractJPAQuery.fetch(AbstractJPAQuery.java:242)
	…
```

The issue seems to be caused by the `Parameter` implementation's `anticipatedType` being `null` when resolved against the contains predicate.
If the example is changed to an "equals" one, the test case doesn't break.
Also, the test case runs fine on the latest Hibernate 5.

## How to reproduce?

```
$ mvn clean install -Phibernate-6
```
