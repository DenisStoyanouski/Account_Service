package account.business;

import account.exception.PasswordException;

import java.util.List;

public class PasswordValidator {

    private static String password;

    private static List<String> BREACHED_PASSWORDS = List.of(
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

    private static int MIN_LENGTH = 12;

    public PasswordValidator(String password) {
        this.password = password;
    }

    public static void check(String userPassword) {
        password = userPassword;
        isBreached();
        isEnoughLong();
    }

    private static void isBreached() {
        if (BREACHED_PASSWORDS.contains(password)) {
            throw new PasswordException("The password is in the hacker's database!");
        }
    }

    private static void isEnoughLong() {
        if (password.length() < MIN_LENGTH) {
            throw new PasswordException("Password length must be 12 chars minimum!");
        }
    }
}

