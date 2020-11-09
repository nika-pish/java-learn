package ru.stqa.learn.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.learn.mantis.appmanager.HttpSession;
import ru.stqa.learn.mantis.model.MailMessage;
import ru.stqa.learn.mantis.model.UserData;
import ru.stqa.learn.mantis.model.Users;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{
  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

    @Test
    public void TestChangePassword () throws IOException, InterruptedException {
      Users users = app.db().users();
      UserData user = users.iterator().next();
      System.out.println(user.getId());

      app.usersHelper().start("administrator", "root");
      app.usersHelper().resetPassword(Integer.toString(user.getId()));
      List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
      String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
      System.out.println(confirmationLink);
      app.usersHelper().changeAccount(confirmationLink, user.getUsername());

      HttpSession session = app.newSession();
      assertTrue(session.login(user.getUsername(), "new_password"));
      assertTrue(session.isLoggedInAs(user.getUsername()));
    }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
      app.mail().stop();
    }
  }

