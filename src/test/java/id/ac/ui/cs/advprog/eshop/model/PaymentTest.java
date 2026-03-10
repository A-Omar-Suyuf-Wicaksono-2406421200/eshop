package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Map<String, String> voucherData;
    private Map<String, String> bankData;

    @BeforeEach
    void setUp() {
        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "REF123456");
    }

    @Test
    void testCreatePaymentVoucherValid() {
        Payment payment = new Payment("pay-1", "VOUCHER", voucherData);
        assertEquals("pay-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(voucherData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherInvalidLength() {
        voucherData.put("voucherCode", "ESHOP123");
        Payment payment = new Payment("pay-1", "VOUCHER", voucherData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidPrefix() {
        voucherData.put("voucherCode", "SHOP12345ABC5678");
        Payment payment = new Payment("pay-1", "VOUCHER", voucherData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidNumCount() {
        voucherData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("pay-1", "VOUCHER", voucherData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferValid() {
        Payment payment = new Payment("pay-2", "BANK_TRANSFER", bankData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferEmptyBankName() {
        bankData.put("bankName", "");
        Payment payment = new Payment("pay-2", "BANK_TRANSFER", bankData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferNullReferenceCode() {
        bankData.put("referenceCode", null);
        Payment payment = new Payment("pay-2", "BANK_TRANSFER", bankData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferEmptyReferenceCode() {
        bankData.put("referenceCode", "");
        Payment payment = new Payment("pay-2", "BANK_TRANSFER", bankData);
        assertEquals("REJECTED", payment.getStatus());
    }
}