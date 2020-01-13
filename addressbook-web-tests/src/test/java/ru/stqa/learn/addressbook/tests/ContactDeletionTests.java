package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        app.goToHomePage();
        app.selectContact();
        app.deleteContact();
        app.acceptDeletion();
        app.goToHomePage();

    }

}
