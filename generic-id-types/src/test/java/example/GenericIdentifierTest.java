/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.junit.Test;

/**
 * @author Oliver Gierke
 */
public class GenericIdentifierTest {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate");
	EntityManager entityManager = factory.createEntityManager();

	@Test
	public void exposesGenericIdentifierTypesViaMetamodel() {

		Metamodel metamodel = entityManager.getMetamodel();

		assertIdentifierType(metamodel.entity(ConcreteEntityOne.class), Integer.class);
		assertIdentifierType(metamodel.entity(ConcreteEntityTwo.class), Long.class);
	}

	private static <T> void assertIdentifierType(IdentifiableType<T> entity, Class<?> idType) {

		// Identifier type
		assertThat(entity.getIdType().getJavaType(), is(typeCompatibleWith(idType)));

		// Identifier type by name
		Attribute<? super T, ?> attribute = entity.getAttribute("id");
		assertThat(attribute.getJavaType(), is(typeCompatibleWith(idType)));

		// Identifier type by id attribute
		SingularAttribute<? super T, ?> idAttribute = entity.getId(entity.getIdType().getJavaType());
		assertThat(idAttribute.getJavaType(), is(typeCompatibleWith(idType)));
	}
}
