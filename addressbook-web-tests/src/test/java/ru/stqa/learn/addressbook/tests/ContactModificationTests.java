package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase{
    @Test (enabled = false)
    public void testContactModification() {
        app.getNavigationHelper().goToHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToAddNewContactPage();
            app.getContactHelper().createContact(new ContactData("Ivan", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", "test1"));
            app.getNavigationHelper().goToHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() -1);
        app.getContactHelper().initContactModification();
        ContactData contact = new ContactData(before.get(before.size() -1).getId(), "Ivanushka", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove (before.size() -1);
        before.add (contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }
}
