package account.Payment;

import account.business.User;

import java.util.function.BiFunction;

public class PaymentDTOMapper implements BiFunction<User, Payment, PaymentDTO> {
    @Override
    public PaymentDTO apply(User user, Payment payment) {
        return new PaymentDTO(
                user.getName(),
                user.getLastname(),
                String.format("%s-%s", payment.getPeriod().getMonth(), payment.getPeriod().getYear()),
                String.format("%d dollar(s) %d cent(s)", payment.getSalary() / 100, payment.getSalary() % 100)

        );
    }
}
