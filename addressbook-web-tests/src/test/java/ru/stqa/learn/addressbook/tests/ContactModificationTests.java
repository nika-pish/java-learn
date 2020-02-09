package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.goTo().addNewContactPage();
            app.contact().create(new ContactData()
                    .withFirstname("Petr"). withMiddlename("Ivanovich"). withLastname("Ivanov").withNickname("IvIv")
                    .withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                    .withBday("1"). withBmonth("January").withByear("1990"). withGroup("test1"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactModification() {

        List<ContactData> before = app.contact().list();
        int index = before.size() -1;
        ContactData contact = new ContactData()
                .withId(before.get(index).getId()).withFirstname("Petr"). withMiddlename("Ivanovich"). withLastname("Ivanov")
                .withNickname("IvIv").withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                .withBday("1"). withBmonth("January").withByear("1990");
        app.contact().modify(index, contact);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove (index);
        before.add (contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }


}
