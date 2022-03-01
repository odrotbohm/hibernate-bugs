package example;
/*
 * Copyright 2022 the original author or authors.
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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.Test;

/**
 * @author Oliver Drotbohm
 */
public class UuidOnH2Test {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate");
	EntityManager em = factory.createEntityManager();

	@Test
	public void byIdLookupDoesntWork() {

		var tx = em.getTransaction();
		tx.begin();

		MyOrder order = new MyOrder();

		em.persist(order);
		em.flush();
		em.clear();

		assertThat(em.find(MyOrder.class, order.id), is(notNullValue()));
	}

	@Test
	public void findDoesntWork() {

		var tx = em.getTransaction();
		tx.begin();

		MyOrder order = new MyOrder();

		em.persist(order);
		em.flush();

		var query = em.createQuery("select m from MyOrder m where m.id = ?1").setParameter(1, order.id);
		assertThat(query.getSingleResult(), is(notNullValue()));
	}

	@Test
	public void deleteDoesntWork() {

		var tx = em.getTransaction();
		tx.begin();

		MyOrder order = new MyOrder();

		em.persist(order);
		em.flush();

		var query = em.createQuery("delete from MyOrder o where o.id = ?1").setParameter(1, order.id);

		assertThat(query.executeUpdate(), is(1));
	}
}
