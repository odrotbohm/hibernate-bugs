package example;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.function.Supplier;

import org.hibernate.annotations.EmbeddableInstantiator;
import org.hibernate.engine.spi.SessionFactoryImplementor;

@Entity
public class User {

	@EmbeddedId UserId id;

	@Embeddable
	@EmbeddableInstantiator(UserIdInstantiator.class)
	static record UserId(String id) implements Serializable {

		private static final long serialVersionUID = 874778077780707367L;

		public UserId() {
			this(null);
		}
	}

	static class UserIdInstantiator implements org.hibernate.metamodel.spi.EmbeddableInstantiator {

		/*
		 * (non-Javadoc)
		 * @see org.hibernate.metamodel.spi.EmbeddableInstantiator#instantiate(java.util.function.Supplier, org.hibernate.engine.spi.SessionFactoryImplementor)
		 */
		@Override
		public Object instantiate(Supplier<Object[]> valuesAccess, SessionFactoryImplementor sessionFactory) {
			return new UserId(valuesAccess.get()[0].toString());
		}

		/*
		 * (non-Javadoc)
		 * @see org.hibernate.metamodel.spi.Instantiator#isInstance(java.lang.Object, org.hibernate.engine.spi.SessionFactoryImplementor)
		 */
		@Override
		public boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
			return UserId.class.isInstance(object);
		}

		/*
		 * (non-Javadoc)
		 * @see org.hibernate.metamodel.spi.Instantiator#isSameClass(java.lang.Object, org.hibernate.engine.spi.SessionFactoryImplementor)
		 */
		@Override
		public boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
			return UserId.class.equals(object.getClass());
		}
	}
}
