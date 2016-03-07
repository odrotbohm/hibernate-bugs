package example;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class Finances {

	@OneToOne(cascade = CascadeType.PERSIST) //
	Account selectedAccount;
}
