package by.clevertec.receipt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReceiptApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ReceiptApplication.class, args);
    }

    @Override
    public void run(String... args){
        System.out.println("Gradle project");
    }
}
