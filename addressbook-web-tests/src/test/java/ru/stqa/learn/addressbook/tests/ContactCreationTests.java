package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().homePage();
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData()
                .withFirstname("Petr"). withMiddlename("Ivanovich"). withLastname("Ivanov").withNickname("IvIv")
                .withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                .withBday("1"). withBmonth("January").withByear("1990"). withGroup("test1");
        app.goTo().addNewContactPage();
        app.contact().create(contact);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() +1);

        contact.withId(after.stream().mapToInt((o1) -> o1.getId()).max().getAsInt());
        before.add (contact);
        Assert.assertEquals(before, after);
    }
}

