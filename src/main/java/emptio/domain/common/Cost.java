package emptio.domain.common;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public record Cost(@NonNull BigDecimal value, @NonNull Currency currency) {


    public static Cost add(Cost... costs) {
        if (Arrays.stream(costs).findFirst().isEmpty())
            throw new CostException("Can't add 0 number of costs together");
        else {
            Currency currency = Arrays.stream(costs).findFirst().get().currency();
            if (Arrays.stream(costs).allMatch(cost -> cost.currency == currency))
                return new Cost(
                        Arrays.stream(costs).map(Cost::value).reduce(BigDecimal.ZERO, BigDecimal::add),
                        currency);
            else
                throw new CostException("Can't add costs of different currencies");
        }
    }
}