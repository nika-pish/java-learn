package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.goToAddNewContactPage();
        app.fillContactForm(new ContactData("Ivan", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990"));
        app.submitContactCreation();
        app.goToHomePage();

    }

}

