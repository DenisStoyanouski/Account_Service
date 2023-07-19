package account.Payment;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByEmployeeIgnoreCaseAndPeriod(String employee, YearMonth yearMonth);
    List<Payment> findAllByEmployeeIgnoreCase(String username, Sort sort);
}
