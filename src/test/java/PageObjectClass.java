import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;


public class PageObjectClass {
    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(name = "j_username")
    private WebElement j_username;

    @FindBy(name = "j_password")
    private WebElement j_password;

    @FindBy(tagName = "button")
    private WebElement logIn_button;

    @FindBy(xpath = "/html/body/div[4]/div[1]/div[1]/div[4]/a[2]")
    private WebElement manage_jenkins;

    @FindBy(xpath = "/html/body/div[4]/div[2]/div[15]/a/dl/dt")
    private WebElement manage_users;

    @FindBy(xpath = "/html/body/div[4]/div[1]/div/div[3]/a[2]")
    private WebElement create_user;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement username;

    @FindBy(xpath = "//table/tbody/tr[2]/td[2]/input")
    private WebElement password;

    @FindBy(xpath = "//table/tbody/tr[3]/td[2]/input")
    private WebElement confirm_password;

    @FindBy(xpath = "/html//table/tbody/tr[4]/td[2]/input")
    private WebElement fullname;

    @FindBy(xpath = "/html//table/tbody/tr[5]/td[2]/input")
    private WebElement email;

    @FindBy(id = "yui-gen2-button")
    private WebElement create_user_button;

    @FindBy(xpath = "//a[@href='user/someuser/delete']")
    private WebElement delete_user;

    @FindBy(id = "yui-gen2-button")
    private WebElement delete_confirm_button;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div[2]/div[2]/div[1]/div/div/a")
    private WebElement autoRefresh;


    public PageObjectClass(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
    }

    public String getJUsername() {
        return j_username.getAttribute("value");
    }

    public String getJPassword() {
        return j_password.getAttribute("value");
    }

    public String getUsername() {
        return username.getAttribute("value");
    }

    public String getPassword() {
        return password.getAttribute("value");
    }

    public String getConfirmPassword() {
        return confirm_password.getAttribute("value");
    }

    public String getFullname() {
        return fullname.getAttribute("value");
    }

    public String getEmail() {
        return email.getAttribute("value");
    }

    public String getLogInButtonColor() {
        return logIn_button.getCssValue("background-color");
    }

    public String getCreateUserButtonColor() {
        return create_user_button.getCssValue("background-color");
    }

    public String getDeleteConfirmUserButtonColor() {
        return delete_confirm_button.getCssValue("background-color");
    }


    /**
     * This method sets username for login Jenkins form
     * @param jUsername
     * @return
     */
    public PageObjectClass setJUsername(String jUsername) {
        j_username.clear();
        j_username.sendKeys(jUsername);
        return this;
    }


    /**
     * This method sets password for login Jenkins form
     * @param jPassword
     * @return
     */
    public PageObjectClass setJPassword(String jPassword) {
        j_password.clear();
        j_password.sendKeys(jPassword);
        return this;
    }


    /**
     * This method sets username in Jenkins
     * @param name
     * @return
     */
    public PageObjectClass setUsername(String name) {
        username.clear();
        username.sendKeys(name);
        return this;
    }


    /**
     * This method sets password in Jenkins
     * @param pswd
     * @return
     */
    public PageObjectClass setPassword(String pswd) {
        password.clear();
        password.sendKeys(pswd);
        return this;
    }


    /**
     * This method sets confirm password in Jenkins
     * @param confirmPassword
     * @return
     */
    public PageObjectClass setConfirmPassword(String confirmPassword) {
        confirm_password.clear();
        confirm_password.sendKeys(confirmPassword);
        return this;
    }


    /**
     * This method sets fullname in Jenkins
     * @param fullName
     * @return
     */
    public PageObjectClass setFullName(String fullName) {
        fullname.clear();
        fullname.sendKeys(fullName);
        return this;
    }


    /**
     * This method sets email in Jenkins
     * @param e_mail
     * @return
     */
    public PageObjectClass setEmail(String e_mail) {
        email.clear();
        email.sendKeys(e_mail);
        return this;
    }


    /**
     * This method clicks 'Login' button
     * @return
     */
    public PageObjectClass submitLogIn() {
        logIn_button.click();
        return this;
    }


    /**
     * This method clicks 'Create User' button
     * @return
     */
    public PageObjectClass submitCreateUserButton() {
        create_user_button.click();
        return this;
    }


    /**
     * This method clicks 'Manage Jenkins' link
     * @return
     */
    public PageObjectClass submitManageJenkins() {
        manage_jenkins.click();
        return this;
    }


    /**
     * This method clicks 'Manage Users' link
     * @return
     */
    public PageObjectClass submitManageUsers() {
        manage_users.click();
        return this;
    }


    /**
     * This method clicks 'Create User' link
     * @return
     */
    public PageObjectClass submitCreateUser() {
        create_user.click();
        return this;
    }


    /**
     * This method clicks 'Delete User' link
     * @return
     */
    public PageObjectClass submitDeleteUser() {
        delete_user.click();
        return this;
    }


    /**
     * This method confirms deleting and clicks 'Yes' button
     * @return
     */
    public PageObjectClass submitDeleteButtonUser() {
        delete_confirm_button.click();
        return this;
    }


    /**
     * This method checks if forms are present in real. If they are returns true, else false
     * @return
     */
    public boolean isCreateUserFormPresentForReal() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collection<WebElement> forms = driver.findElements(By.tagName("td"));
        if (forms.isEmpty()) {
            return false;
        }
        Iterator<WebElement> i = forms.iterator();
        boolean form_found = false;
        WebElement form = null;
        try {
            while (i.hasNext()) {
                form = i.next();
                if ((form.findElement(By.xpath("//*[@id=\"username\"]")).getAttribute("type")
                        .equalsIgnoreCase("text"))
                        && (form.findElement(By.xpath("//table/tbody/tr[2]/td[2]/input"))
                        .getAttribute("type").equalsIgnoreCase("password"))
                        && (form.findElement(By.xpath("//table/tbody/tr[3]/td[2]/input"))
                        .getAttribute("type").equalsIgnoreCase("password"))
                        && (form.findElement(By.xpath("/html//table/tbody/tr[4]/td[2]/input"))
                        .getAttribute("type").equalsIgnoreCase("text"))
                        && (form.findElement(By.xpath("/html//table/tbody/tr[5]/td[2]/input"))
                        .getAttribute("type").equalsIgnoreCase("text"))) {
                    form_found = true;
                    break;
                }
            }
        } catch (NoSuchElementException e){
            form_found = false;
        }
        return form_found;
    }


    /**
     * This method checks if incoming String value is present on page
     * @param search_string
     * @return
     */
    public boolean pageTextContains(String search_string) {
        return body.getText().contains(search_string);
    }


    /**
     * This method returns message  if text isn't on page
     * @param search_string
     * @return
     */
    public String getErrorOnTextAbsence(String search_string) {
        if (!pageTextContains(search_string)) {
            return "[No '" + search_string + "' is found inside page text!]\n";
        } else {
            return "";
        }
    }


    /**
     * This method check if element td with
     * text "someuser" is present on page or not
     * @return
     */
    public boolean isTableLinePresent() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collection<WebElement> forms = driver.findElements(By.tagName("td"));
        if (forms.isEmpty()) {
            return false;
        }
        Iterator<WebElement> i = forms.iterator();
        boolean form_found = false;
        WebElement form = null;
        try {
            while (i.hasNext()) {
                form = i.next();
        if (driver.findElement(By.tagName("td")).getText()
            .equalsIgnoreCase("someuser")){
            form_found = true;
            break;
        }
            }
        } catch (NoSuchElementException e){
            form_found = false;
        }
        return form_found;
    }


    /**
     * This method checks if link "user/someuser/delete" is present on page
     * @return
     */
    public boolean isHrefSomeuserDeletePresent() {
        try {
            driver.findElement(By.linkText("user/someuser/delete"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /**
     * This method checks if link "user/admin/delete" is present on page
     * @return
     */
    public boolean isHrefAdminDeletePresent() {
        try {
            driver.findElement(By.linkText("user/admin/delete"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /**
     * This method checks if element with text "ENABLE AUTO REFRESH" is present on page
     * @return
     */
    public boolean isEnableAutoRefreshPresent() {
        try {
            if (driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div[1]/div/div/a")).getText()
                    .equals("ENABLE AUTO REFRESH")) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /**
     * This method checks if element with text "DISABLE AUTO REFRESH" is present on page
     * @return
     */
    public boolean isDisableAutoRefreshPresent() {
        try {
            if (driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div[1]/div/div/a")).getText()
                    .equals("DISABLE AUTO REFRESH")) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /**
     * This method click element with text "ENABLE AUTO REFRESH" or "DISABLE AUTO REFRESH"
     * @return
     */
    public PageObjectClass submitAutoRefresh() {
        autoRefresh.click();
        return this;
    }

}

