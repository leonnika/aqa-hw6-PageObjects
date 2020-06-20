package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardsPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV1;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Nested
    class TestingAuthorizationUserOption {

        @Test
        void shouldAuthorizationValidUser() {
            val loginPage = new LoginPageV1();
            val authInfo = DataHelper.getAuthInfo();
            loginPage.validLogin(authInfo);
        }

        @Test
        void shouldNotAuthorizationUserIsEmpty() {
            val loginPage = new LoginPageV1();
            val authInfo = DataHelper.getAuthInfo();
            loginPage.loginEmpty(authInfo);
        }

        @Test
        void shouldNotAuthorizationPasswordIsEmpty() {
            val loginPage = new LoginPageV1();
            val authInfo = DataHelper.getAuthInfo();
            loginPage.passwordEmpty(authInfo);
        }

        @Test
        void shouldAuthorizationInvalidUserValidCode() {
            val loginPage = new LoginPageV1();
            val authInfo = DataHelper.getInvalidAuthInfo();
            loginPage.invalidLogin(authInfo);
        }
    }

    @Nested
    class TestingAuthorizationCodeOption {
        LoginPageV1 loginPage;
        DataHelper.AuthInfo authInfo;
        VerificationPage verificationPage;

        @BeforeEach
        void init() {
            loginPage = new LoginPageV1();
            authInfo = DataHelper.getAuthInfo();
            verificationPage = loginPage.validLogin(authInfo);
        }

        @Test
        void shouldAuthorizationValidUserValidCode() {
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            verificationPage.validCodeVerify(verificationCode);
        }

        @Test
        void shouldAuthorizationValidUserInvalidCode() {
            val verificationCode = DataHelper.getNotVerificationCodeFor(authInfo);
            verificationPage.invalidCodeVerify(verificationCode);
        }

        @Test
        void shouldAuthorizationValidUserCodeIsEmpty() {
            verificationPage.codeVerifyIsEmpty();
        }
    }

    @Nested
    class TestingTransfer {
        String card01 = "0001";
        String card02 = "0002";
        LoginPageV1 loginPage;
        DataHelper.AuthInfo authInfo;
        VerificationPage verificationPage;
        DataHelper.VerificationCode verificationCode;
        DashboardPage dashboardPage;
        CardsPage cardsPage;

        @BeforeEach
        void init() {
            loginPage = new LoginPageV1();
            authInfo = DataHelper.getAuthInfo();
            verificationPage = loginPage.validLogin(authInfo);
            verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            dashboardPage = verificationPage.validCodeVerify(verificationCode);
            cardsPage = dashboardPage.getCardPage();
        }

        @Test
        void shouldTransferFromCard02ValidAmmount() {
            int balance01 = cardsPage.getCurrentBalance(card01);
            int balance02 = cardsPage.getCurrentBalance(card02);
            val transferPage = cardsPage.listTransferForCard(card01);
            int amountTransfer = DataHelper.Amount.getValidAmount(balance02).getAmount();
            val cardPageResult = transferPage.successfulTransferFromCard02AmountValid(amountTransfer);
            int actual = cardPageResult.getCurrentBalance(card01);
            int expected = balance01 + amountTransfer;
            assertEquals(expected, actual);
        }

        @Test
        void shouldTransferFromCard01ValidAmount() {
            int balance02 = cardsPage.getCurrentBalance(card02);
            int balance01 = cardsPage.getCurrentBalance(card01);
            val transferPage = cardsPage.listTransferForCard(card02);
            int amountTransfer = DataHelper.Amount.getValidAmount(balance01).getAmount();
            val cardPageResult = transferPage.successfulTransferFromCard01AmountValid(amountTransfer);
            int actual = cardPageResult.getCurrentBalance(card02);
            int expected = balance02 + amountTransfer;
            assertEquals(expected, actual);
        }

        //ussie1
        @Test
        void shouldNotTransferFromCard02BalanceLessAmount() {
            int balance02 = cardsPage.getCurrentBalance(card02);
            val transferPage = cardsPage.listTransferForCard(card01);
            int amountTransfer = DataHelper.Amount.getInvalidAmount(balance02).getAmount();
            transferPage.failTransferFromCard02AmountInvalid(amountTransfer);
            $(withText("Недостаточно средств на карте 0002 для перевода!")).waitUntil(visible, 15000);

        }

        @Test
        void shouldNotTransferFromCard01BalanceLessAmount() {
            int balance01 = cardsPage.getCurrentBalance(card01);
            val transferPage = cardsPage.listTransferForCard(card02);
            int amountTransfer = DataHelper.Amount.getInvalidAmount(balance01).getAmount();
            transferPage.failTransferFromCard01AmountInvalid(amountTransfer);
            $(withText("Недостаточно средств на карте 0001 для перевода!")).waitUntil(visible, 15000);
        }

        //ussie2
        @Test
        void shouldNotTransferBetweenOneCard01() {
            int balance02 = cardsPage.getCurrentBalance(card02);
            val transferPage = cardsPage.listTransferForCard(card01);
            int amountTransfer = DataHelper.Amount.getValidAmount(balance02).getAmount();
            transferPage.failTransferBetweenOneCard01(amountTransfer);
        }

        @Test
        void shouldNotTransferFromInvalidCard() {
            int balance01 = cardsPage.getCurrentBalance(card01);
            val transferPage = cardsPage.listTransferForCard(card01);
            int amountTransfer = DataHelper.Amount.getValidAmount(balance01).getAmount();
            transferPage.failTransferInvalidCard(amountTransfer);
        }

        //ussie3
        @Test
        void shouldNotTransferAmount0() {
            val transferPage = cardsPage.listTransferForCard(card02);
            transferPage.failTransferAmount0();
        }

        //ussie4
        @Test
        void shouldNotTransferAmountIsEmpty() {
            val transferPage = cardsPage.listTransferForCard(card02);
            transferPage.failTransferAmountEmpty();
        }

        @Test
        void shouldNotTransferCardFromEmpty() {
            int balance01 = cardsPage.getCurrentBalance(card01);
            val transferPage = cardsPage.listTransferForCard(card02);
            int amountTransfer = DataHelper.Amount.getValidAmount(balance01).getAmount();
            transferPage.failTransferCardFromEmpty(amountTransfer);
        }
    }
}
