package emptio.domain.utils;

import emptio.domain.user.*;

import java.util.Arrays;

public class AccountTypeUtils {

    public static AccountType getAccountType(User user) {
        return switch (user) {
            case Merchant _ -> AccountType.MERCHANT;
            case Shopper _ -> AccountType.SHOPPER;
            case Advertiser _ -> AccountType.ADVERTISER;
            case null, default -> throw new IllegalStateException("Unexpected value. " +
                    "User has to be one of known types: " + Arrays.toString(AccountType.values()) + ". Given user:" + user);
        };
    }
}
