package account.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public void addPayment(Payment payment) {
        paymentRepository.save(payment);
    }

}
