package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions () {
        app.getNavigationHelper().goToHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToAddNewContactPage();
            app.getContactHelper().createContact(new ContactData("Ivan", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", "test1"));
            app.getNavigationHelper().goToHomePage();
        }
    }

    @Test
    public void testContactModification() {

        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() -1;
        ContactData contact = new ContactData(before.get(index).getId(), "Ivanushka", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", null);
        app.getContactHelper().modifyContact(index, contact);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove (index);
        before.add (contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }


}
