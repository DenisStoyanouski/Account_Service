package account.business;

import java.util.List;

public class BreachedPassword {
    final private List<String> PASSWORDS = List.of(
            "PasswordForJanuary",
            "PasswordForFebruary",
            "PasswordForMarch",
            "PasswordForApril",
            "PasswordForMay",
            "PasswordForJune",
            "PasswordForJuly",
            "PasswordForAugust",
            "PasswordForSeptember",
            "PasswordForOctober",
            "PasswordForNovember",
            "PasswordForDecember");

    public boolean isBreached(String password) {
        return PASSWORDS.contains(password);
    }
}
