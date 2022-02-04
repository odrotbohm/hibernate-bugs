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

import example.User.UserId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.UUID;

import org.junit.Test;

/**
 * @author Oliver Drotbohm
 */
public class EmbeddedIdTest {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate");
	EntityManager em = factory.createEntityManager();

	@Test
	public void testname() {

		User user = new User();

		user.id = new UserId(UUID.randomUUID().toString());

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(user);
		em.flush();
		em.clear();

		em.createQuery("select u from User u", User.class).getSingleResult();

		transaction.rollback();
	}
}
