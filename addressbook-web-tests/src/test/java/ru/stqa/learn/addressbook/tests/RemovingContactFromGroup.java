package ru.stqa.learn.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.GroupData;
import ru.stqa.learn.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

      if (removingContact.getGroups().size() == 0) {
        app.goTo().groupPage();
        app.contact().addContactToGroup(removingContact,group);
      }

      int contactId = removingContact.getId();
      ContactData newContact = app.db().GetContactDataById(contactId);
      Groups groupsOfContactBefore = newContact.getGroups();
      app.goTo().homePage();
      GroupData groupWithoutContact = newContact.getGroups().iterator().next();
      app.contact().removeContactFromGroup(removingContact, groupWithoutContact);
      Groups groupsOfContactAfter = app.db().GetContactDataById(contactId).getGroups();
      assertThat(groupsOfContactBefore.size() - 1, equalTo(groupsOfContactAfter.size()));
      assertThat(groupsOfContactAfter, equalTo(groupsOfContactBefore.without(groupWithoutContact)));

    }
  }

