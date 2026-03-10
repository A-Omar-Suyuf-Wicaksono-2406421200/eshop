package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");
        payment1 = new Payment("pay-1", "VOUCHER", voucherData);

        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "REF123456");
        payment2 = new Payment("pay-2", "BANK_TRANSFER", bankData);
    }

    @Test
    void testSave() {
        Payment result = paymentRepository.save(payment1);
        assertEquals(payment1.getId(), result.getId());
    }

    @Test
    void testGetPaymentByIdFound() {
        paymentRepository.save(payment1);
        Payment result = paymentRepository.findById("pay-1");
        assertEquals("pay-1", result.getId());
    }

    @Test
    void testGetPaymentByIdNotFound() {
        Payment result = paymentRepository.findById("invalid-id");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> result = paymentRepository.findAll();
        assertEquals(2, result.size());
    }
}