package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{
    @Test
    public void testContactModification() {
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getContactCount();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToAddNewContactPage();
            app.getContactHelper().createContact(new ContactData("Ivan", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", "test1"));
            app.getNavigationHelper().goToHomePage();
        }
        app.getContactHelper().selectContact(before -1);
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Ivanushka", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);
    }
}
