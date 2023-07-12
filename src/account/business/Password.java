package account.business;

import java.util.List;

public class Password {
    final static private List<String> PASSWORDS = List.of(
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

    final static private int MIN_LENGTH = 12;

    public static boolean isBreached(String password) {
        return PASSWORDS.contains(password);
    }

    public static boolean isEnoughLong(String password) {
        return password.length() >= MIN_LENGTH;
    }
}

