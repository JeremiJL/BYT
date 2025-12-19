package emptio.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Arrays;

@Value public class Cost {

    @NonNull BigDecimal value;
    @NonNull Currency currency;

    @JsonCreator
    public Cost(@JsonProperty("value") @NonNull BigDecimal value, @JsonProperty("currency") @NonNull Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public static Cost add(Cost... costs) {
        if (Arrays.stream(costs).findFirst().isEmpty())
            throw new CostException("Can't add 0 number of costs together");
        else {
            Currency currency = Arrays.stream(costs).findFirst().get().getCurrency();
            if (Arrays.stream(costs).allMatch(cost -> cost.currency == currency))
                return new Cost(
                        Arrays.stream(costs).map(Cost::getValue).reduce(BigDecimal.ZERO, BigDecimal::add),
                        currency);
            else
                throw new CostException("Can't add costs of different currencies");
        }
    }


}