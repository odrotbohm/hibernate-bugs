package example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class MyOrder {

	@Id UUID id;

	public MyOrder() {
		this.id = UUID.randomUUID();
	}
}
