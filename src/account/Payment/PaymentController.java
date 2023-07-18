package account.Payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {
    final private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @NotEmpty(message = "Shouldn't be empty")
    @PostMapping("api/acct/payments/**")
    public ResponseEntity<Object> addPayments(@RequestBody List<@Valid Payment> payments) {
        paymentService.addPayments(payments);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Added successfully!");
        return ResponseEntity.ok(response);
    }
}
