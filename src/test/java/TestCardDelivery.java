import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class TestCardDelivery {

    public String setValueDate(int addedDate) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.plusDays(addedDate).format(pattern).toString();
        return formattedDate;
    }

    public void setAllValue(String city, int addedDate, String name, String phone) {
        SelenideElement element = $("[placeholder='Дата встречи']");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        element.setValue(setValueDate(addedDate));
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
        int addedDate = 5;
        Configuration.holdBrowserOpen=true;
        setAllValue("Краснодар", addedDate, "Иванов", "+79884757770");
        clicker();
        SelenideElement date = $("[data-test-id='notification']");
        date.$(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на "
                + setValueDate(addedDate)), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }


}
