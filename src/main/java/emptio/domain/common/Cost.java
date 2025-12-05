package emptio.domain.common;

import lombok.NonNull;

import java.math.BigDecimal;

public record Cost(@NonNull BigDecimal value, @NonNull Currency currency) {

    public static Cost add(Cost a, Cost b) {
        if (a.currency != b.currency)
            throw new CostException("Can't add costs of different currencies.");
        else return new Cost(a.value.add(b.value), a.currency);
    }
}
