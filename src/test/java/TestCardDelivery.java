import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class TestCardDelivery {

    public void setValueDate(int addedDate) {
        SelenideElement element = $("[placeholder='Дата встречи']");
        element.sendKeys("\b\b\b\b\b\b\b\b");
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.plusDays(addedDate).format(pattern).toString();
        element.setValue(formattedDate);
    }

    public void setAllValue(String city, int addedDate, String name, String phone) {
        $("[placeholder='Город']").setValue(city);
        $("[name='name']").setValue(name);
        setValueDate(addedDate);
        $("[name='phone']").setValue(phone);
    }

    public void clicker() {
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testValid() {
        setAllValue("Краснодар", 5, "Иванов", "+79884757770");
        clicker();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
    }


}
