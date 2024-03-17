package com.example.walletmanagementws.utils;

import java.util.Random;

public class Utils {
    private static String generateVariablePart() {
        Random random = new Random();
        StringBuilder variablePart = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            variablePart.append(random.nextInt(10));
        }
        return variablePart.toString();
    }

    private static int calculateLuhnChecksumDigit(String numberWithoutChecksum) {
        int sum = 0;
        boolean alternate = true;
        for (int i = numberWithoutChecksum.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numberWithoutChecksum.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }

    public static String generateKashelokNumber() {
        String fixedPart = "999";
        String variablePart = generateVariablePart();
        String numberWithoutChecksum = fixedPart + variablePart;
        int checksumDigit = calculateLuhnChecksumDigit(numberWithoutChecksum);
        return numberWithoutChecksum + checksumDigit;
    }


}
