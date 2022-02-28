package example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

	@GeneratedValue @Id Long id;

	SomeValue wrapper;
	// UUID uuid;
}
