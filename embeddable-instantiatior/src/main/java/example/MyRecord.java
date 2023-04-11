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

import example.MyRecord.MyRecordInstantiator;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

import org.hibernate.annotations.EmbeddableInstantiator;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.ValueAccess;

/**
 * @author Oliver Drotbohm
 */
@Embeddable
@EmbeddableInstantiator(MyRecordInstantiator.class)
record MyRecord(int c, String b, LocalDate a) {

	public static class MyRecordInstantiator implements org.hibernate.metamodel.spi.EmbeddableInstantiator {

		@Override
		public boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
			return MyRecord.class.isInstance(object);
		}

		/*
		 * (non-Javadoc)
		 * @see org.hibernate.metamodel.spi.Instantiator#isSameClass(java.lang.Object, org.hibernate.engine.spi.SessionFactoryImplementor)
		 */
		@Override
		public boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
			return MyRecord.class.equals(object.getClass());
		}

		/*
		 * (non-Javadoc)
		 * @see org.hibernate.metamodel.spi.EmbeddableInstantiator#instantiate(org.hibernate.metamodel.spi.ValueAccess, org.hibernate.engine.spi.SessionFactoryImplementor)
		 */
		@Override
		public Object instantiate(ValueAccess valueAccess, SessionFactoryImplementor sessionFactory) {
			return new MyRecord(valueAccess.getValue(2, Integer.class), valueAccess.getValue(1, String.class),
					valueAccess.getValue(0, LocalDate.class));
		}
	}
}
