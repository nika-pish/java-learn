package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

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

    @Test (enabled = false)
    public void testContactDeletion() throws Exception {

        List<ContactData> before = app.contact().list();
        int index = before.size() -1;
        app.contact().delete(index);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() -1);

        before.remove(index);
        Assert.assertEquals(before,after);
    }

}
