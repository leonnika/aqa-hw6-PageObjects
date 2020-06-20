package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.smartcardio.Card;
import java.util.Locale;

@Data
public class DataHelper {

       private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidAuthInfo() {
        Faker faker = new Faker(new Locale("ru"));
        String password = faker.regexify("[0-9]{8}");
        String login =faker.regexify("[a-z]{6}");
        return new AuthInfo(login, password);
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getNotVerificationCodeFor(AuthInfo authInfo) {
        Faker faker = new Faker(new Locale("ru"));
        String code = faker.regexify("[0-9]{10}");
        return new VerificationCode(code);
    }

    public static Cards card01() {
        return new Cards("5559 0000 0000 0001");
    }

    public static Cards card02() {
        return new Cards("5559 0000 0000 0002");
    }

   public static Cards cardInvalid(){
           return new Cards("5559 0000 0000 0000");
   }

    @Value
    public static class Amount {
        private int amount;

        public static Amount getValidAmount(int balance) {
            if (balance>0) {
            int amount = (int) (Math.random() * balance);
            return new Amount(amount);}
            return new Amount(0);
        }

        public static Amount getInvalidAmount(int balance) {
            int amount = (int) (Math.random() *balance) + balance;
            return new Amount(amount);
        }
    


}

}
