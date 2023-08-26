package ru.netology.diplomaqa.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        return "5555 5555 5555  5555";
    }

    public static String getEmptyCardNumberValue() {
        return " ";
    }

    public static String getValidMonth() {
        return "08";
    }

    public static String getInvalidMonth() {
        return "99";
    }

    public static String getInvalidMonthZero() {
        return "00";
    }

    public static String getEmptyMonthValue() {
        return " ";
    }

    public static String getValidYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.plusYears(2);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        return newYear.format(yearFormatter);
    }

    public static String getExpiredYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.minusYears(11);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        return newYear.format(yearFormatter);
    }

    public static String getInvalidYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.plusYears(78);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        return newYear.format(yearFormatter);
    }

    public static String getEmptyYearValue() {
        return " ";
    }

    public static String getValidOwnerCard() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String getInvalidOwnerCard() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getEmptyOwnerValue() {
        return " ";
    }

    public static String getValidCvv() {
        return "123";
    }

    public static String getInvalidCvv() {
        return "12";
    }

    public static String getEmptyCvvValue() {
        return " ";
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getInvalidCardNumberInfo() {
        return new CardInfo(getInvalidCardNumber(), getValidMonth(), getValidYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getInvalidMonthInfo() {
        return new CardInfo(getApprovedCardNumber(), getInvalidMonth(), getValidYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getInvalidMonthZeroInfo() {
        return new CardInfo(getApprovedCardNumber(), getInvalidMonthZero(), getValidYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getExpiredYearInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getExpiredYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getInvalidYearInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getInvalidYear(), getValidOwnerCard(), getValidCvv());
    }

    public static CardInfo getInvalidOwnerInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getInvalidOwnerCard(), getValidCvv());
    }

    public static CardInfo getEmptyFields() {
        return new CardInfo(getEmptyCardNumberValue(), getEmptyMonthValue(), getEmptyYearValue(),
                getEmptyOwnerValue(), getEmptyCvvValue());
    }


    public static CardInfo getInvalidCardForm() {
        return new CardInfo(getInvalidCardNumber(), getInvalidMonth(), getInvalidYear(),
                getInvalidOwnerCard(), getInvalidCvv());
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvv;
    }
}
