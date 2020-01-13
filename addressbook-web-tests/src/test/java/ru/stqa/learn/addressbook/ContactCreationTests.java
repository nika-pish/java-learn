package ru.stqa.learn.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        goToAddNewContactPage();
        fillContactForm(new ContactData("Ivan", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990"));
        submitContactCreation();
        goToHomePage();

    }

}

