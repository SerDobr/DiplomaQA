package ru.netology.diplomaqa.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplomaqa.data.DataHelper;
import ru.netology.diplomaqa.data.SQLHelper;
import ru.netology.diplomaqa.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    DashboardPage pageMain = new DashboardPage();

    @BeforeEach
    void openForTests() {
        open("http://localhost:8080");
    }

    @Test
    @DisplayName("Тест с APPROVED картой и валидными данными")
    void shouldCheckWithAnApprovedCardAndValidData() {
        var payForm = pageMain.payCreditByCard();
        var approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayment = SQLHelper.getCreditStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test
    @DisplayName("Тест DECLINED карты с валидными данными")
    void shouldCheckTheDeclinedCardAndTheValidData() {
        var payForm = pageMain.payCreditByCard();
        var declinedInfo = DataHelper.getDeclinedCardInfo();
        payForm.fillingForm(declinedInfo);
        payForm.checkErrorNotification();
        String dataSQLPayment = SQLHelper.getCreditStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test
    @DisplayName("Тест с валидными данными")
    void shouldBeCheckedWithValidData() {
        var payForm = pageMain.payCreditByCard();
        var approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayAmount = SQLHelper.getPaymentAmount();
        assertEquals("4500  000", dataSQLPayAmount);
    }



    @Test
    @DisplayName("Тест невалидной карты")
    void shouldheckTheInvalidCard() {
        var payForm = pageMain.payCreditByCard();
        var invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payForm.fillingForm(invalidCardNumber);
        payForm.checkErrorNotification();
    }

    @Test
    @DisplayName("Тест невалидного месяца")
    void shouldCheckTheInvalidMonth() {
        var payForm = pageMain.payCreditByCard();
        var invalidMonth = DataHelper.getInvalidMonthInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    @DisplayName("Тест с 00 месяцем ")
    void shouldCheckTheInvalidMonthZero() {
        var payForm = pageMain.payCreditByCard();
        var invalidMonth = DataHelper.getInvalidMonthZeroInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    @DisplayName("Тест с истекшим сроком действия карты")
    void shouldBeCheckedWithAnExpiredExpirationDate() {
        var payForm = pageMain.payCreditByCard();
        var expiredYear = DataHelper.getExpiredYearInfo();
        payForm.fillFormNoSendRequest(expiredYear);
        payForm.checkCardExpired();
    }

    @Test
    @DisplayName("Тест с неверно указаным сроком действия карты")
    void shouldCheckWithTheIncorrectlySpecifiedCardExpirationDate() {
        var payForm = pageMain.payCreditByCard();
        var invalidYear = DataHelper.getInvalidYearInfo();
        payForm.fillFormNoSendRequest(invalidYear);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    @DisplayName("Тест данные владельца карты на киррилице")
    void shouldCheckTheOwnersDataInCyrillic() {
        var payForm = pageMain.payCreditByCard();
        var invalidOwner = DataHelper.getInvalidOwnerInfo();
        payForm.fillFormNoSendRequest(invalidOwner);
        payForm.checkWrongFormat();
    }

    @Test
    @DisplayName("Тест отправка пустой формы")
    void shouldSendAnEmptyForm() {
        var payForm = pageMain.payCreditByCard();
        var emptyFields = DataHelper.getEmptyFields();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkWrongFormat();
        payForm.checkRequiredField();
    }



    @Test
    @DisplayName("Тест с невалидными данными всех полей")
    void shouldBeCheckedWithInvalidDataOfAllFields() {
        var payForm = pageMain.payCreditByCard();
        var invalidValue = DataHelper.getInvalidCardForm();
        payForm.fillFormNoSendRequest(invalidValue);
        payForm.checkInvalidCardNumber();
        payForm.checkInvalidMonth();
        payForm.checkInvalidYear();
        payForm.checkInvalidOwner();
        payForm.checkInvalidCvv();
    }
}
