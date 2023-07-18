package account.Payment;

import account.exception.PaymentExistException;
import account.exception.UserNotExistException;
import account.persistance.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            if (paymentRepository.findByEmployeeAndPeriod(payment.getEmployee(), payment.getPeriod()).isPresent()) {
                throw new PaymentExistException("Payment with this employee and this period exist");
            }
        }
        paymentRepository.saveAll(paymentList);
    }

    @Transactional
    public void updateSalary(Payment payment) {
        if (paymentRepository.findByEmployeeAndPeriod(payment.getEmployee(), payment.getPeriod()).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        } else {
            paymentRepository.findByEmployeeAndPeriod(payment.getEmployee(), payment.getPeriod()).get().setSalary(payment.getSalary());
        }
    }

}
