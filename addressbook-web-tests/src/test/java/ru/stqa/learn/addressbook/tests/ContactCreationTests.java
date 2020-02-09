package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().goToHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData contact = new ContactData("Petr", "Ivanov", "Ivanovich", "IvIv", "Moscow", "44555", "222443@fake.fake", "1", "January", "1990", "test1");
        app.getNavigationHelper().goToAddNewContactPage();
        app.getContactHelper().createContact(contact);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() +1);

        contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add (contact);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after));
    }
}

