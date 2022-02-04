package example;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;

@Embeddable
public class Finances {

	@OneToOne(cascade = CascadeType.PERSIST) //
	Account selectedAccount;
}
