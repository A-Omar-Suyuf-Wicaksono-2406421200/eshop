package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus(method, paymentData);
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = status;
    }

    private String determineStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER".equals(method)) {
            return validateVoucher(paymentData.get("voucherCode")) ? "SUCCESS" : "REJECTED";
        } else if ("BANK_TRANSFER".equals(method)) {
            return validateBankTransfer(paymentData) ? "SUCCESS" : "REJECTED";
        }
        return "REJECTED";
    }

    private boolean validateVoucher(String code) {
        if (code == null || code.length() != 16) return false;
        if (!code.startsWith("ESHOP")) return false;
        long numCount = code.chars().filter(Character::isDigit).count();
        return numCount == 8;
    }

    private boolean validateBankTransfer(Map<String, String> data) {
        String bankName = data.get("bankName");
        String referenceCode = data.get("referenceCode");
        if (bankName == null || bankName.isEmpty()) return false;
        if (referenceCode == null || referenceCode.isEmpty()) return false;
        return true;
    }
}