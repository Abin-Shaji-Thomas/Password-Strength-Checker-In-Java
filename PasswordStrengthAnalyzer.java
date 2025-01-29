package com.passwordanalyzer;

import java.util.Scanner;

public class PasswordStrengthAnalyzer
{

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Password to analyze: ");
        String password = sc.nextLine();
        
        int score = calculatePasswordStrength(password);
        
        String strengthMeter = "[ ";
        for (int i = 0; i < score; i++) 
        {
            strengthMeter += "*";
        }
        for (int i = score; i < 5; i++) 
        {
            strengthMeter += " ";
        }
        strengthMeter += "]";
        
        System.out.println("Password Strength Score: " + score);
        System.out.println("Password Strength Meter: " + strengthMeter);
        
        if (score == 5) 
        {
            System.out.println("This is a very strong password.");
        } else if (score >= 3)
        {
            System.out.println("This is a strong password.");
        } else 
        {
            System.out.println("This is a weak password.");
        }
        
        // Detailed Feedback
        if (password.length() < 8)
        {
            System.out.println("Your password is too short. It should be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) 
        {
            System.out.println("Your password should include at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) 
        {
            System.out.println("Your password should include at least one lowercase letter.");
        }
        if (!password.matches(".*[0-9].*")) 
        {
            System.out.println("Your password should include at least one number.");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) 
        {
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
    
    // Trim the last comma and space
    suggestion = suggestion.substring(0, suggestion.length() - 2) + ".";
    System.out.println(suggestion);
    }

        
        sc.close();
    }

    public static int calculatePasswordStrength(String password)
    {
        int score = 0;

        // Common Password Check
        String[] commonPasswords = {"123456", "password", "123456789", "qwerty", "abc123", "12345678", "12345", "1234567", "admin", "welcome",
        		"123123", "password1", "1234567", "12345678", "123456", "1234567890", "123456q", "1q2w3e4r5t", "qwertyuiop", "111111", "123321",
        		"qwerty123", "1q2w3e4r", "q1w2e3r4t5y", "password123", "123qwe", "123qwe123", "1q2w3e4r", "123", "123456a", "112233", "123abc", "12345a",
        		"qwerty1", "1234", "1234561", "1234qwer", "1234567a", "qwerty1", "1111111", "abcdef", "password2", "123qwe", "654321", "password12", "121212",
        		"1q2w3e", "abc1234", "123456789", "123456a", "123123", "qwertyuiop", "654321", "1234567890", "12345678", "qwerty123", "1q2w3e4r", "1q2w3e4r5t", 
        		"password1", "password12", "1231234", "qwerty12", "123qwe123"};
        for (String commonPassword : commonPasswords) {
            if (password.equalsIgnoreCase(commonPassword)) {
                System.out.println("Your password is too common and easily guessable.");
                return 0; // Immediately return a score of 0 if the password is too common
            }
        }

        if (password.length() >= 8) {
            score++;
        }
        if (password.matches(".*[A-Z].*")) {  // Contains uppercase
            score++;
        }
        if (password.matches(".*[a-z].*")) {  // Contains lowercase
            score++;
        }
        if (password.matches(".*[0-9].*")) {  // Contains digit
            score++;
        }
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {  // Contains special character
            score++;
        }
        
        // Avoid Repeated Characters
        if (password.matches(".*(\\w)\\1{2,}.*")) 
        {
            System.out.println("Your password contains repeated characters, which makes it weaker.");
            score--;
        }
        
        // Check for Common Sequences
        if (password.matches(".*(012|123|234|345|456|567|678|789|890|abc|bcd|cde|def).*")) {
            System.out.println("Your password contains common sequences, which makes it weaker.");
            score--;
        }
        
        return score;
    }
}
