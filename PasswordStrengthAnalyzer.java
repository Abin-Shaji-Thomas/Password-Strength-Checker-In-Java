package com.passwordanalyzer;

import java.util.Scanner;

public class PasswordStrengthAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Created by Abin Shaji Thomas");
        System.out.print("Enter the Password to analyze: ");
        String password = sc.nextLine();

        int score = calculatePasswordStrength(password);

        // Ensure score is between 0 and 5
        score = Math.max(0, Math.min(score, 5));

        // Strength Meter Visualization
        String[] meterColors = {
            "\u001B[31m█\u001B[0m", // Red
            "\u001B[31m█\u001B[0m", // Red
            "\u001B[33m█\u001B[0m", // Yellow
            "\u001B[32m█\u001B[0m", // Green
            "\u001B[32m█\u001B[0m"  // Green
        };
        
        String strengthMeter = "";
        for (int i = 0; i < 5; i++) {
            if (i < score) {
                strengthMeter += meterColors[i];
            } else {
                strengthMeter += "⬜"; // Empty block
            }
        }

        System.out.println("Password Strength Score: " + score);
        System.out.println("Password Strength Meter: " + strengthMeter);

        if (score == 5) {
            System.out.println("✅ This is a very strong password!");
        } else if (score >= 3) {
            System.out.println("✅ This is a strong password, but could be improved.");
        } else {
            System.out.println("❌ This is a weak password. Consider making it stronger.");
        }

        // Detailed Feedback for Improvements
        if (password.length() < 8) {
            System.out.println("⚠️ Your password is too short! A secure password should be at least 12-16 characters.");
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("⚠️ Your password should include at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("⚠️ Your password should include at least one lowercase letter.");
        }
        if (!password.matches(".*[0-9].*")) {
            System.out.println("⚠️ Your password should include at least one number.");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            System.out.println("⚠️ Your password should include at least one special character.");
        }

        // Generate password suggestions dynamically
        if (score < 3) {
            String suggestion = "🔹 Consider adding: ";
            if (password.length() < 8) suggestion += "more length, ";
            if (!password.matches(".*[A-Z].*")) suggestion += "uppercase letters, ";
            if (!password.matches(".*[a-z].*")) suggestion += "lowercase letters, ";
            if (!password.matches(".*[0-9].*")) suggestion += "numbers, ";
            if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) suggestion += "special characters, ";

            suggestion = suggestion.substring(0, suggestion.length() - 2) + ".";
            System.out.println(suggestion);
        }

        sc.close();
    }

    public static int calculatePasswordStrength(String password) {
        int score = 0;

        // Common Password Check
        String[] commonPasswords = {"123456", "password", "123456789", "qwerty", "abc123", "12345678", "12345", "admin", "welcome"};
        for (String commonPassword : commonPasswords) {
            if (password.equalsIgnoreCase(commonPassword)) {
                System.out.println("❌ Your password is too common and easily guessable.");
                return 0;
            }
        }

        // Check character types
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        // Count unique character sets
        int charSetSize = 0;
        if (hasUpper) charSetSize += 26;
        if (hasLower) charSetSize += 26;
        if (hasDigit) charSetSize += 10;
        if (hasSpecial) charSetSize += 20; // Approximate special character set size

        // Calculate entropy
        double entropy = password.length() * (Math.log(charSetSize) / Math.log(2));

        // Assign score based on entropy value
        if (entropy >= 80) {
            score = 5;
        } else if (entropy >= 60) {
            score = 4;
        } else if (entropy >= 40) {
            score = 3;
        } else if (entropy >= 20) {
            score = 2;
        } else {
            score = 1;
        }

        System.out.println("🔢 Password Entropy: " + String.format("%.2f", entropy) + " bits");

        // Avoid Repeated Characters (More Strict)
        if (password.matches(".*(.)\\1.*")) {
            System.out.println("⚠️ Your password contains repeated characters, which may make it weaker.");
            score--;
        }

        // Check for Common Sequences
        if (password.matches(".*(012|123|234|345|456|567|678|789|890|abc|bcd|cde|def).*")) {
            System.out.println("⚠️ Your password contains common sequences, which makes it weaker.");
            score--;
        }

        // Ensure score is between 0 and 5
        score = Math.max(0, Math.min(score, 5));

        return score;
    }
}
