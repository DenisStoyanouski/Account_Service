package account.Payment;

import account.exception.PaymentExistException;
import account.exception.UserNotExistException;
import account.exception.UsernameNotFoundException;
import account.persistance.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    public void addPayments(Payment payment) {
        paymentRepository.save(payment);
    }
    @Transactional
    public void addPayments(List<Payment> paymentList) {
        for (Payment payment : paymentList) {
            if (userRepository.findByEmailIgnoreCase(payment.getEmployee()).isEmpty()) {
                throw new UserNotExistException("User isn't exist");
            }
            if (!paymentRepository.findByEmployeeAndPeriod(payment.getEmployee(), payment.getPeriod()).isEmpty()) {
                throw new PaymentExistException("Payment with this employee and this period exist");
            }
        }
        paymentRepository.saveAll(paymentList);
    }

}
