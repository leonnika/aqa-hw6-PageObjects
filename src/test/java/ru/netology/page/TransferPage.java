package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountTransfer = $("[data-test-id=amount] input");
    private SelenideElement cardFrom = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorTransfer = $("[data-test-id=error-notification]");

    public TransferPage() {
        amountTransfer.shouldBe(visible);
    }

    public CardsPage successfulTransferFromCard02AmountValid(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        cardFrom.setValue(DataHelper.card02().getNumberCard());
        transferButton.click();
        errorTransfer.shouldNotBe(visible);
        return new CardsPage();
    }

    public CardsPage successfulTransferFromCard01AmountValid(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        cardFrom.setValue(DataHelper.card01().getNumberCard());
        transferButton.click();
        errorTransfer.shouldNotBe(visible);
        return new CardsPage();
    }

    public CardsPage failTransferFromCard02AmountInvalid(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        cardFrom.setValue(DataHelper.card02().getNumberCard());
        transferButton.click();
        errorTransfer.shouldNotBe(visible);
        return new CardsPage();
    }

    public CardsPage failTransferFromCard01AmountInvalid(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        cardFrom.setValue(DataHelper.card01().getNumberCard());
        transferButton.click();
        errorTransfer.shouldNotBe(visible);
        return new CardsPage();
    }

    public void failTransferBetweenOneCard01(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        cardFrom.setValue(DataHelper.card01().getNumberCard());
        transferButton.click();
        errorTransfer.shouldBe(visible);
    }

    public void failTransferInvalidCard(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        cardFrom.setValue(DataHelper.cardInvalid().getNumberCard());
        transferButton.click();
        errorTransfer.shouldBe(visible);
    }

    public void failTransferCardFromEmpty(int amount) {
        amountTransfer.setValue(Integer.toString(amount));
        transferButton.click();
        errorTransfer.shouldBe(visible);
    }

    public void failTransferAmountEmpty() {
        cardFrom.setValue(DataHelper.card02().getNumberCard());
        transferButton.click();
        errorTransfer.shouldBe(visible);
    }

    public void failTransferAmount0() {
        cardFrom.setValue(DataHelper.card02().getNumberCard());
        amountTransfer.setValue(Integer.toString(0));
        transferButton.click();
        errorTransfer.shouldBe(visible);
    }
}
