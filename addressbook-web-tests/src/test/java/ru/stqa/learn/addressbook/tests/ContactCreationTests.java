package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getContactCount();
        app.getNavigationHelper().goToAddNewContactPage();
        app.getContactHelper().createContact(new ContactData("Ivan", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", "test1"));
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before +1);
    }

}

