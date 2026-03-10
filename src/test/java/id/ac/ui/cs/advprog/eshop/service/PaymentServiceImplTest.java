package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    @InjectMocks
    PaymentServiceImpl paymentService;

    private Order order;
    private Map<String, String> voucherData;
    private Map<String, String> bankData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product p = new Product();
        p.setProductId("p-1");
        p.setProductName("Laptop");
        p.setProductQuantity(1);
        products.add(p);

        order = new Order("order-1", products, 1708560000L, "Omar");

        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "REF123456");
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment result = paymentService.addPayment(order, "VOUCHER", voucherData);
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherRejected() {
        voucherData.put("voucherCode", "INVALID");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment result = paymentService.addPayment(order, "VOUCHER", voucherData);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferSuccess() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", bankData);
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentBankTransferRejected() {
        bankData.put("bankName", "");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", bankData);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment payment = paymentService.addPayment(order, "VOUCHER", voucherData);

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        verify(orderService, times(1)).updateStatus(eq(order.getId()), eq("SUCCESS"));
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        voucherData.put("voucherCode", "INVALID");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment payment = paymentService.addPayment(order, "VOUCHER", voucherData);

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        verify(orderService, times(1)).updateStatus(eq(order.getId()), eq("FAILED"));
    }

    @Test
    void testGetPaymentFound() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment payment = paymentService.addPayment(order, "VOUCHER", voucherData);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentNotFound() {
        when(paymentRepository.findById("invalid-id")).thenReturn(null);
        assertNull(paymentService.getPayment("invalid-id"));
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        paymentService.addPayment(order, "VOUCHER", voucherData);
        paymentService.addPayment(order, "BANK_TRANSFER", bankData);

        List<Payment> all = new ArrayList<>();
        all.add(new Payment("x", "VOUCHER", voucherData));
        all.add(new Payment("y", "BANK_TRANSFER", bankData));
        when(paymentRepository.findAll()).thenReturn(all);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }
}