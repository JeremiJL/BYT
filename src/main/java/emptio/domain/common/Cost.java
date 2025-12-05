package emptio.domain.common;

import java.math.BigDecimal;

public class Cost {

    public final BigDecimal value;
    public final Currency currency;

    public Cost(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public static Cost add(Cost a, Cost b) {
        if (a.currency != b.currency)
            throw new CostException("Can't add costs of different currencies.");
        else return new Cost(a.value.add(b.value), a.currency);
    }
}
