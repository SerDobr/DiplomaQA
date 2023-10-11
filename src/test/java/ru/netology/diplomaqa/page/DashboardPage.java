package ru.netology.diplomaqa.page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private static final SelenideElement formHeading = $x("//*[@id=\"root\"]/div/h3");

    public PagePayment payByDebitCard() {
        buyButton.click();
        formHeading.shouldHave(Condition.visible,Condition.text("Оплата по карте"));
        return new PagePayment();
    }
    public PagePayment payCreditByCard() {
        creditButton.click();
        formHeading.shouldHave(Condition.visible,Condition.text("Кредит по данным карты"));
        return new PagePayment();
    }
}
