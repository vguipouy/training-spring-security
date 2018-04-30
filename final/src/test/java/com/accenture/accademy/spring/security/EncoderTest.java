package com.accenture.accademy.spring.security;


import org.junit.Test;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class EncoderTest {
    @Test
    public void exampleTest() {
        String password = "password";

        // No encoder
       /* PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
        String encodedPassword = encoder.encode(password);
        System.out.println("No encoding " + encodedPassword);
        System.out.println("");

        // MD5 encoder
        encoder = new Md4PasswordEncoder();
        encodedPassword = encoder.encode(password);
        System.out.println("MD4 " + encodedPassword);
        System.out.println("");

        // BCrypt encoder
        encoder = new BCryptPasswordEncoder();
        encodedPassword = encoder.encode(password);
        System.out.println("BCrypt " + encodedPassword);
        System.out.println("");

        String providedPassword = "password";
        System.out.println("Is password valid ? " + encoder.matches(providedPassword, encodedPassword));*/
    }
}
