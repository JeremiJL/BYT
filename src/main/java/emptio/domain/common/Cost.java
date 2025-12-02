package emptio.domain.common;

import java.math.BigDecimal;

public class Cost {

    public final BigDecimal value;
    public final Currency currency;

    public Cost(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }
}
