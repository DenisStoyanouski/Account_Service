package account.Payment;

import account.business.User;
import account.exception.PaymentExistException;
import account.exception.PeriodIsNotValidException;
import account.exception.UserNotExistException;
import account.persistance.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public void addPayments(Payment payment) {
        paymentRepository.save(payment);
    }

    @Transactional
    public void addPayments(List<Payment> paymentList) {
        for (Payment payment : paymentList) {
            if (userRepository.findByEmailIgnoreCase(payment.getEmployee()).isEmpty()) {
                throw new UserNotExistException("User isn't exist");
            }
            if (paymentRepository.findByEmployeeIgnoreCaseAndPeriod(payment.getEmployee(), payment.getPeriod()).isPresent()) {
                throw new PaymentExistException("Payment with this employee and this period exist");
            }
        }
        paymentRepository.saveAll(paymentList);
    }

    @Transactional
    public void updateSalary(Payment payment) {
        if (paymentRepository.findByEmployeeIgnoreCaseAndPeriod(payment.getEmployee(), payment.getPeriod()).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        } else {
            paymentRepository.findByEmployeeIgnoreCaseAndPeriod(payment.getEmployee(), payment.getPeriod()).get().setSalary(payment.getSalary());
        }
    }

    public List<PaymentDTO> getPaymentsOfCurrentUser(String username) {
        User user = userRepository.findByEmailIgnoreCase(username).get();
            return paymentRepository.findAllByEmployeeIgnoreCase(username, Sort.by(Sort.Direction.DESC, "id"))
                    .stream()
                    .map(payment -> new PaymentDTOMapper().apply(user, payment))
                    .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentOfCurrentUserForPeriod(String username, String period) {
        User user = userRepository.findByEmailIgnoreCase(username).get();
        if (!period.matches("(0[1-9]|1[0-2])-\\d{4}")) {
            throw new PeriodIsNotValidException("Not valid format");
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-yyyy");

        if (paymentRepository.findByEmployeeIgnoreCaseAndPeriod(username, YearMonth.parse(period, format)).isEmpty()) {
            throw new PaymentExistException("Error!");
        }
        return new PaymentDTOMapper().apply(
                user,
                paymentRepository.findByEmployeeIgnoreCaseAndPeriod(username, YearMonth.parse(period, format)).get()
        );
    }
}
