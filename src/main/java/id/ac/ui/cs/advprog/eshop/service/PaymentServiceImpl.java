package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderService orderService;

    private Map<String, String> paymentOrderMap = new HashMap<>();

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, method, paymentData);
        paymentRepository.save(payment);
        paymentOrderMap.put(paymentId, order.getId());
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        String orderId = paymentOrderMap.get(payment.getId());
        if (orderId != null) {
            if ("SUCCESS".equals(status)) {
                orderService.updateStatus(orderId, "SUCCESS");
            } else if ("REJECTED".equals(status)) {
                orderService.updateStatus(orderId, "FAILED");
            }
        }
        Payment updated = new Payment(
                payment.getId(),
                payment.getMethod(),
                payment.getPaymentData(),
                status
        );
        paymentRepository.save(updated);
        return updated;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}