package ru.stqa.learn.mantis.appmanager;

import org.openqa.selenium.By;

public class UserHelper extends HelperBase {

	public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Login']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

  public void resetPassword(String userId) {
    wd.get(app.getProperty("web.baseUrl") + "manage_user_edit_page.php?user_id=" + userId);
    click(By.cssSelector("input[value='Reset Password']"));
  }

  public void changeAccount(String confirmationLink, String username) {
    wd.get(confirmationLink);
    type(By.name("realname"), username);
    type(By.name("password"), "new_password");
    type(By.name("password_confirm"), "new_password");
    click(By.cssSelector("button[type='submit']"));
  }





}