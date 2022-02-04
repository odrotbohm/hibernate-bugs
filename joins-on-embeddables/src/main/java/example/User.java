package example;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

	@Id @GeneratedValue Long id;
	@Embedded Finances finances = new Finances();
}
