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
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddingContactToGroup  extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
      Groups groups = app.db().groups();
      Contacts contacts = app.db().contacts();
      if (app.db().contacts().size() == 0) {
        app.goTo().addNewContactPage();
        app.contact().create(new ContactData()
                .withFirstname("Petr").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
                .withAddress("Moscow").withMobilePhone("44555").withEmail("222443@fake.fake")
                .withBday("1").withBmonth("January").withByear("1990").inGroup(groups.iterator().next()));
        app.goTo().homePage();
      }
    }
  }

  @Test
  public void testAddingContactToGroup () {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    app.goTo().homePage();
    ContactData addedContact = before.iterator().next();
    if (addedContact.getGroups().size() == groups.size()) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test4"));
    }
    Groups groupsOfContactBefore = addedContact.getGroups();
    GroupData groupWithAddedContact = new GroupData();
    for (GroupData group : app.db().groups()) {
      if (!group.hasContact(addedContact)) {
        app.goTo().homePage();
        app.contact().addContactToGroup(addedContact, group);
        groupWithAddedContact = group;
      } else if (group.hasContact(addedContact)) {
      }
    }

    ContactData newContact = app.db().GetContactDataById(addedContact.getId());
    Groups groupsOfContactAfter = newContact.getGroups();
    assertThat(groupsOfContactBefore.size()+1, equalTo(groupsOfContactAfter.size()));
    assertThat(groupsOfContactAfter, equalTo(groupsOfContactBefore.withAdded(groupWithAddedContact)));
  }
  }