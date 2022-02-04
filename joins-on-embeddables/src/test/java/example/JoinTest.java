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

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.junit.Test;

/**
 * @author Oliver Gierke
 */
public class JoinTest {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate");
	EntityManager entityManager = factory.createEntityManager();

	@Test
	public void createsJoinsViaEmbeddableUsingStrings() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery();

		Root<User> root = query.from(User.class);

		root.join("finances").join("selectedAccount");
	}

	@Test
	public void createsJoinsViaEmbeddableUsingMetamodel() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery();

		Root<User> root = query.from(User.class);

		root.join(User_.finances).join(Finances_.selectedAccount);
	}
}
