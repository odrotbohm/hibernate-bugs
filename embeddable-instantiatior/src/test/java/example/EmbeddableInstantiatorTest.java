/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

import org.junit.Test;

/**
 * @author Oliver Drotbohm
 */
public class EmbeddableInstantiatorTest {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate");
	EntityManager em = factory.createEntityManager();

	@Test
	public void writesAndReadsInstantiatedEmbeddable() {

		var wrapper = new Wrapper();
		wrapper.myRecord = new MyRecord(1, "Foo", LocalDate.now());

		var transaction = em.getTransaction();

		transaction.begin();
		em.persist(wrapper);
		transaction.commit();

		em.clear();

		em.find(Wrapper.class, wrapper.id);
	}
}
