package account.Payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("api/acct/payments")
    public ResponseEntity<Object> addPayments(@RequestBody List<@Valid Payment> payments) {
        paymentService.addPayments(payments);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Added successfully!");
        return ResponseEntity.ok(response);
    }

    @PutMapping("api/acct/payments")
    public ResponseEntity<Object> updateSalary(@RequestBody @Valid Payment payment) {
        paymentService.updateSalary(payment);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Updated successfully!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> testAuthentication(
            @AuthenticationPrincipal UserDetails details,
            @RequestParam(required = false) String period) {
        System.out.println(period);
        return ResponseEntity.ok().body(paymentService.getPaymentsOfCurrentUser(details.getUsername(), period));
    }
}
