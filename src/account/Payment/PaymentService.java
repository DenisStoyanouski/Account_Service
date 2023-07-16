package account.Payment;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public void addPayment(Payment payment) {
        paymentRepository.save(payment);
    }
    @Transactional
    public void addPayments(List<Payment> paymentList) {
        paymentRepository.saveAll(paymentList);
    }

}
