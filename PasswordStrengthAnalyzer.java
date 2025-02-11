package com.passwordanalyzer;

import java.util.Scanner;

public class PasswordStrengthAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Password to analyze: ");
        String password = sc.nextLine();
        
        int score = calculatePasswordStrength(password);

        // Ensure score is between 0 and 5
        score = Math.max(0, Math.min(score, 5));

        String strengthMeter = "[ ";
        for (int i = 0; i < score; i++) {
            strengthMeter += "*";
        }
        for (int i = score; i < 5; i++) {
            strengthMeter += " ";
        }
        strengthMeter += "]";

        System.out.println("Password Strength Score: " + score);
        System.out.println("Password Strength Meter: " + strengthMeter);

        if (score == 5) {
            System.out.println("This is a very strong password.");
        } else if (score >= 3) {
            System.out.println("This is a strong password.");
        } else {
            System.out.println("This is a weak password.");
        }

        // Detailed Feedback
        if (password.length() < 8) {
            System.out.println("Your password is too short. It should be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Your password should include at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("Your password should include at least one lowercase letter.");
        }
        if (!password.matches(".*[0-9].*")) {
            System.out.println("Your password should include at least one number.");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            System.out.println("Your password should include at least one special character.");
        }

        // Generate password suggestions dynamically
        if (score < 3) {
            String suggestion = "Consider adding: ";
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
                System.out.println("Your password is too common and easily guessable.");
                return 0;
            }
        }

        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) score++;

        // Avoid Repeated Characters
        if (password.matches(".*(\\w)\\1{2,}.*")) {
            System.out.println("Your password contains repeated characters, which makes it weaker.");
            score--;
        }

        // Check for Common Sequences
        if (password.matches(".*(012|123|234|345|456|567|678|789|890|abc|bcd|cde|def).*")) {
            System.out.println("Your password contains common sequences, which makes it weaker.");
            score--;
        }

        // Ensure score is between 0 and 5
        score = Math.max(0, Math.min(score, 5));

        return score;
    }
}
