package ru.netology.diplomaqa.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplomaqa.data.DataHelper;
import ru.netology.diplomaqa.data.SQLHelper;
import ru.netology.diplomaqa.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void openForTests() {
        open("http://localhost:8080");
    }

    DashboardPage pageMain = new DashboardPage();

    @Test
    @DisplayName("Тест загрузки вкладки Купить")
    void shouldCheckTheDownloadOfThePaymentByCard() {
        pageMain.payByDebitCard();
    }

    @Test
    @DisplayName("Тест с APPROVED картой и валидными данными")
    void shouldCheckWithAnApprovedCardAndValidData() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test
    @DisplayName("Тест DECLINED карты с валидными данными")
    void shouldCheckTheDeclinedCardAndTheValidData() {
        var payForm = pageMain.payByDebitCard();
        var declinedInfo = DataHelper.getDeclinedCardInfo();
        payForm.fillingForm(declinedInfo);
        payForm.checkErrorNotification();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test
    @DisplayName("Тест с валидными данными")
    void shouldBeCheckedWithValidData() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayAmount = SQLHelper.getPaymentAmount();
        assertEquals("4500000", dataSQLPayAmount);
    }


    @Test
    @DisplayName("Тест невалидной картой")
    void shouldheckTheInvalidCard() {
        var payForm = pageMain.payByDebitCard();
        var invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payForm.fillingForm(invalidCardNumber);
        payForm.checkErrorNotification();
    }

    @Test
    @DisplayName("Тест невалидного месяца")
    void shouldCheckTheInvalidMonth() {
        var payForm = pageMain.payByDebitCard();
        var invalidMonth = DataHelper.getInvalidMonthInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    @DisplayName("Тест невалидного нулевого месяца")
    void shouldCheckTheInvalidMonthZero() {
        var payForm = pageMain.payByDebitCard();
        var invalidMonth = DataHelper.getInvalidMonthZeroInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    @DisplayName("Тест с истекшим сроком действия карты")
    void shouldBeCheckedWithAnExpiredExpirationDate() {
        var payForm = pageMain.payByDebitCard();
        var expiredYear = DataHelper.getExpiredYearInfo();
        payForm.fillFormNoSendRequest(expiredYear);
        payForm.checkCardExpired();
    }

    @Test
    @DisplayName("Тест с неверно указаным сроком действия карты")
    void shouldCheckWithTheIncorrectlySpecifiedCardExpirationDate() {
        var payForm = pageMain.payByDebitCard();
        var invalidYear = DataHelper.getInvalidYearInfo();
        payForm.fillFormNoSendRequest(invalidYear);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    @DisplayName("Тест данные владельца карты на киррилице")
    void shouldCheckTheOwnersDataInCyrillic() {
        var payForm = pageMain.payByDebitCard();
        var invalidOwner = DataHelper.getInvalidOwnerInfo();
        payForm.fillFormNoSendRequest(invalidOwner);
        payForm.checkIfWrongFormatOfField();
    }

    @Test
    @DisplayName("Тест отправка пустой формы")
    void shouldSendAnEmptyForm() {
        var payForm = pageMain.payByDebitCard();
        var emptyFields = DataHelper.getEmptyFields();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkIfWrongFormatOfField();
        payForm.checkRequiredField();
    }

    @Test
    @DisplayName("Тест отправка с пустым полем Номера карты ")
    void shouldSendAnEmptyFormNumberCards() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getEmptyCardNumber();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();

    }

    @Test
    @DisplayName("Тест отправка с пустым полем Месяц ")
    void shouldSendAnEmptyFormMonth() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getEmptyMonth();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();

    }

    @Test
    @DisplayName("Тест отправка с пустым полем Год ")
    void shouldSendAnEmptyFormYear() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getEmptyYear();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();
    }

    @Test
    @DisplayName("Тест отправка с пустым полем Владелец ")
    void shouldSendAnEmptyFormOwner() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getEmptyOwner();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();
    }

    @Test
    @DisplayName("Тест отправка с пустым полем CVC ")
    void shouldSendAnEmptyFormCVC() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getEmptyCVC();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();
    }

    @Test
    @DisplayName("Тест отправка с неправильным CVC ")
    void shouldSendAnWrongFormCVC() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getWrongCVC();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();
    }

    @Test
    @DisplayName("Тест отправка со спец символами в поле Владелец ")
    void shouldSendAnSpecialSymbolsFormOwner() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getSpecialSymbolsOwner();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();
    }

    @Test
    @DisplayName("Тест отправка с цифрами в поле Владелец ")
    void shouldSendAnNumbersFormOwner() {
        var payForm = pageMain.payByDebitCard();
        var approvedInfo = DataHelper.getNumbersOwner();
        payForm.fillFormNoSendRequest(approvedInfo);
        payForm.checkIfWrongFormatOfField();
    }
}
