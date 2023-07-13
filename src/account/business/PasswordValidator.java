package account.business;

import account.presentation.PasswordException;

import java.util.List;

public class PasswordValidator {

    final private String password;

    final private List<String> BREACHED_PASSWORDS = List.of(
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

    final private int MIN_LENGTH = 12;

    public PasswordValidator(String password) {
        this.password = password;
    }

    public void check() {
        isBreached();
        isEnoughLong();
    }

    private void isBreached() {
        if (BREACHED_PASSWORDS.contains(password)) {
            throw new PasswordException("The password is in the hacker's database!");
        }
    }

    private void isEnoughLong() {
        if (password.length() < MIN_LENGTH) {
            throw new PasswordException("The password length must be at least 12 chars!");
        }
    }
}

