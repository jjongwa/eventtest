package test.eventtest.wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer cash;

    public Wallet(final Long id, final String name, final int cash) {
        this.id = id;
        this.name = name;
        this.cash = cash;
    }

    public Wallet plusCash(final int cash) {
        return new Wallet(id, name, this.cash + cash);
    }

    public Wallet minusCash(final int cash) {
        return new Wallet(id, name, this.cash - cash);
    }
}
