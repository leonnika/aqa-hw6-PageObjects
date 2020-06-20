package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$$;

public class CardsPage {

    public TransferPage listTransferForCard(String numberCardTransfer) {
        SelenideElement card = $$(withText(numberCardTransfer)).first();
        card.$$("button").find(exactText("Пополнить")).click();
        return new TransferPage();
    }

    public int getCurrentBalance(String numberCardTransfer) {
        SelenideElement card = $$(withText(numberCardTransfer)).first();
        String text = card.getText().substring(29);
        text = text.substring(0, text.indexOf(" р."));
        int currentBalance = Integer.valueOf(text);
        return currentBalance;
    }
}

