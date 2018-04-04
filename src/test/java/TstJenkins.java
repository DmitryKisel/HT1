import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TstJenkins {
    String base_url = "http://localhost:8080";
    StringBuffer verificationErrors = new StringBuffer();
    WebDriver driver = null;
    String j_username = "admin";
    String j_password = "4cb38a8fda0d494194cc1cb945c5c60c";
    String userName = "someuser";
    String password = "somepassword";
    String confirmPassword = "somepassword";
    String fullname = "Some Full Name";
    String email = "some@addr.dom";
    String buttonColour = "#4b758b";
    PageObjectClass page;

    /**
     * This method before testing runs webdriver, geckodriver and signs in Jenkins
     * @throws Exception
     */
    @BeforeClass
    public void beforeClass() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D:\\!!Дима\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get(base_url);
        page = PageFactory.initElements(driver, PageObjectClass.class);
        page.setJUsername(j_username);
        page.setJPassword(j_password);
        page.submitLogIn();
        Thread.sleep(5000);
    }

    /**
     * This mesod after testing prints error log file
     */
    @AfterClass
    public void afterClass() {
        System.out.println(verificationErrors.toString());
    }


    /**
     *  Testing point one in specification "После клика по ссылке «Manage Jenkins» на
     *  странице появляется элемент dt с текстом «Manage Users» и элемент dd с текстом
     *  «Create/delete/modify users that can log in to this Jenkins».
     */
    @Test
    public void tstManageJenkins() {
        Assert.assertEquals(page.getErrorOnTextAbsence("Manage Jenkins"), "");
        verificationErrors.append(page.getErrorOnTextAbsence("Manage Jenkins"));
        page.submitManageJenkins();
        Assert.assertEquals(page.getErrorOnTextAbsence("Manage Users"), "");
        verificationErrors.append(page.getErrorOnTextAbsence("Manage Users"));
        Assert.assertEquals(page.getErrorOnTextAbsence(
                "Create/delete/modify users that can log in to this Jenkins"), "");
        verificationErrors.append(page.getErrorOnTextAbsence(
                "Create/delete/modify users that can log in to this Jenkins"));
    }


    /**
     * Testing point three in additional specification "При клике по ссылке с текстом
     * «ENABLE AUTO REFRESH» эта ссылка пропадает, а вместо неё появляется ссылка с текстом
     * «DISABLE AUTO REFRESH». При клике по ссылке с текстом «DISABLE AUTO REFRESH» эта
     * ссылка пропадает, а вместо неё появляется ссылка с текстом «ENABLE AUTO REFRESH».
     * Т.е. эти две ссылки циклически сменяют друг друга." This testing method will be
     * done only after 'tstManageJenkins' is finished
     */
    @Test(dependsOnMethods = "tstManageJenkins", alwaysRun = true)
    public void tstAutoRefresh() {
        Assert.assertTrue(page.isEnableAutoRefreshPresent(),
                "[Link with text \\\"ENABLE AUTO REFRESH\\\" isn't present]");
        Assert.assertFalse(page.isDisableAutoRefreshPresent(),
                "[Link with text \"DISABLE  AUTO REFRESH\" isn present]");
        page.submitAutoRefresh();

        Assert.assertTrue(page.isDisableAutoRefreshPresent(),
                "[Link with text \\\"DISABLE  AUTO REFRESH\\\" isn't present]");
        Assert.assertFalse(page.isEnableAutoRefreshPresent(),
                "[Link with text \\\"ENABLE AUTO REFRESH\\\" isn present]");
        page.submitAutoRefresh();

        Assert.assertTrue(page.isEnableAutoRefreshPresent(),
                "[Link with text \"ENABLE AUTO REFRESH\" isn't present]");
        Assert.assertFalse(page.isDisableAutoRefreshPresent(),
                "[Link with text \"DISABLE  AUTO REFRESH\" isn present]");
    }


    /**
     * Testing point two in specification "2.	После клика по ссылке, внутри которой
     * содержится элемент dt с текстом «Manage Users», становится доступна ссылка
     * «Create User»." This testing method will be done only after 'tstAutoRefresh'
     * is finished
     */
    @Test(dependsOnMethods = "tstAutoRefresh", alwaysRun = true)
    public void tstManageUsers() {
        page.submitManageUsers();
        Assert.assertEquals(page.getErrorOnTextAbsence("Create User"), "");
        verificationErrors.append(page.getErrorOnTextAbsence("Create User"));
    }


    /**
     * Testing point three in specification "3.После клика по ссылке «Create User» появляется
     * форма с тремя полями типа text и двумя полями типа password, причём все поля должны
     * быть пустыми." This testing method will be done only after 'tstManageUsers'
     * is finished
     */
    @Test(dependsOnMethods = "tstManageUsers", alwaysRun = true)
    public void tstCreateUsersForm() {
        page.submitCreateUser();
        Assert.assertTrue(page.isCreateUserFormPresentForReal(), "[No suitable forms found!]");
    }


    /**
     * Testing point two in additional specification "При попытке создать пользователя с
     * пустым (незаполненным) именем на странице появляется текст «"" is prohibited as
     * a full name for security reasons.»" This testing method will be
     * done only after 'tstCreateUsersForm' is finished
     */
    @Test(dependsOnMethods = "tstCreateUsersForm", alwaysRun = true)
    public void createNoNameUser() {
        page.submitCreateUserButton();
        Assert.assertEquals(page.getErrorOnTextAbsence
                ("\"\" is prohibited as a full name for security reasons."), "");
        verificationErrors.append(page.getErrorOnTextAbsence
                ("\"\" is prohibited as a full name for security reasons."));
    }

    /**
     * Testing point four in specification "4.	После заполнения полей формы («Username» =
     * «someuser», «Password» = «somepassword», «Confirm password» = «somepassword»,
     * «Full name» = «Some Full Name», «E-mail address» = «some@addr.dom») и клика по кнопке
     * с надписью «Create User» на странице появляется строка таблицы (элемент tr), в которой
     * есть ячейка (элемент td) с текстом «someuser»." and point one in additional specification
     * "У всех кнопок (элемент типа button), которые нужно кликать в основной части задания, цвет фона = #4b758b."
     * This testing method will be done only  after 'createNoNameUser' is finished
     */
    @Test(dependsOnMethods = "createNoNameUser", alwaysRun = true)
    public void tstCreateUsers() {
        boolean testOK = true;
        try {
            page.setUsername(userName);
            Assert.assertEquals(page.getUsername(), userName,
                    "Unable to fill 'Jenkins Username' field");
        } catch (NoSuchElementException e) {
            verificationErrors.append("[Unable to fill 'Username' form! No suitable form found!]" + e.toString() + "\n");
            testOK = false;
        }

        try {
            page.setPassword(password);
            Assert.assertEquals(page.getPassword(), password,
                    "Unable to fill 'Jenkins Password' field");
        } catch (NoSuchElementException e) {
            verificationErrors.append("[Unable to fill 'Password' form! No suitable form found!]" + e.toString() + "\n");
            testOK = false;
        }

        try {
            page.setConfirmPassword(confirmPassword);
            Assert.assertEquals(page.getConfirmPassword(), confirmPassword,
                    "Unable to fill 'Jenkins Password' field");
        } catch (NoSuchElementException e) {
            verificationErrors.append("[Unable to fill 'Confirm Password' form! No suitable form found!]" + e.toString() + "\n");
            testOK = false;
        }

        try {
            page.setFullName(fullname);
            Assert.assertEquals(page.getFullname(), fullname,
                    "Unable to fill 'Jenkins Password' field");
        } catch (NoSuchElementException e) {
            verificationErrors.append("[Unable to fill 'Fullname' form! No suitable form found!]" + e.toString() + "\n");
            testOK = false;
        }

        try {
            page.setEmail(email);
            Assert.assertEquals(page.getEmail(), email,
                    "Unable to fill 'Jenkins Password' field");
        } catch (NoSuchElementException e) {
            verificationErrors.append("[Unable to fill 'Email' form! No suitable form found!]" + e.toString() + "\n");
            testOK = false;
        }

        String hex = Color.fromString(page.getCreateUserButtonColor()).asHex();
        try {
            Assert.assertTrue(hex.equals(buttonColour));
        } catch (AssertionError e) {
            verificationErrors.append("[Create User Button color does not equal '#4b758b']: " + e.toString() + "\n");
            testOK = false;
        }
        page.submitCreateUserButton();
        Assert.assertEquals(page.getErrorOnTextAbsence("someuser"), "");
        verificationErrors.append(page.getErrorOnTextAbsence("someuser"));

        if (!testOK) {
            Assert.fail("[One or some elements are missing on page]");
        }
    }


    /**
     * Testing point five in specification "5.	После клика по ссылке с атрибутом href равным
     * «user/someuser/delete» появляется текст «Are you sure about deleting the user from
     * Jenkins?»." This testing method will be  done only after 'tstCreateUsers' is finished
     */
    @Test(dependsOnMethods = "tstCreateUsers", alwaysRun = true)
    public void tstDeleteUsers() {
        page.submitDeleteUser();
        Assert.assertEquals(page.getErrorOnTextAbsence(
                "Are you sure about deleting the user from Jenkins?"), "");
        verificationErrors.append(page.getErrorOnTextAbsence(
                "Are you sure about deleting the user from Jenkins?"));
    }


    /**
     * Testing point six in specification "6.	После клика по кнопке с надписью «Yes» на
     * странице отсутствует строка таблицы (элемент tr), с ячейкой (элемент td) с текстом
     * «someuser». На странице отсутствует ссылка с атрибутом href равным «user/someuser/delete»."
     * and point one in additional specification "У всех кнопок (элемент типа button), которые
     * нужно кликать в основной части задания, цвет фона = #4b758b."
     * This testing method will be done only  after 'tstDeleteUsers' is finished
     */
    @Test(dependsOnMethods = "tstDeleteUsers", alwaysRun = true)
    public void tstDeleteUsersConfirm() {
        Assert.assertEquals(page.getErrorOnTextAbsence("Yes"), "");
        verificationErrors.append(page.getErrorOnTextAbsence("Yes"));

        String hex = Color.fromString(page.getDeleteConfirmUserButtonColor()).asHex();
        Assert.assertTrue(hex.equals(buttonColour), "[Delete User Button color does not equal '#4b758b']");
        page.submitDeleteButtonUser();

        Assert.assertFalse(page.isTableLinePresent(), "[Table line with someuser is present]");
        Assert.assertFalse(page.isHrefSomeuserDeletePresent(), "[Href = 'user/someuser/delete' is present]");
    }


    /**
     * Testing point seven in specification "7.	{На той же странице, без выполнения каких бы
     * то ни было действий}. На странице отсутствует ссылка с атрибутом href равным
     * «user/admin/delete»." This testing method will be  done only after 'tstDeleteUsersConfirm'
     * is finished
     */
    @Test(dependsOnMethods = "tstDeleteUsersConfirm", alwaysRun = true)
    public void tstDeleteAdmin() {
        Assert.assertFalse(page.isHrefAdminDeletePresent(), "[Href = 'user/admin/delete' is present]");
    }
}
