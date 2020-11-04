package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.GroupData;
import ru.stqa.learn.addressbook.model.Groups;

public class RemovingContactFromGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    if (app.db().contacts().size() == 0) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Petr").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
              .withAddress("Moscow").withMobilePhone("44555").withEmail("222443@fake.fake")
              .withBday("1").withBmonth("January").withByear("1990").inGroup(groups.iterator().next()));
      app.goTo().homePage();
    }
  }

    @Test
    public void testRemovingContactFromGroup (){
      Groups groups = app.db().groups();
      Contacts before = app.db().contacts();
      app.goTo().homePage();
      ContactData removingContact = before.iterator().next();
      GroupData group = groups.iterator().next();
      app.contact().removeContactFromGroup(removingContact,group);
    }
  }

