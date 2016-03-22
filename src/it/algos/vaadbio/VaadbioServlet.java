package it.algos.vaadbio;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import it.algos.vaad.wiki.WikiLogin;
import it.algos.webbase.web.lib.LibCrypto;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.login.Login;
import it.algos.webbase.web.servlet.AlgosServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet 3.0 introduces a @WebServlet annotation which can be used to replace the traditional web.xml.
 * <p>
 * The straightforward approach to create a Vaadin application using servlet 3.0 annotations,
 * is to simply move whatever is in web.xml to a custom servlet class (extends VaadinServlet)
 * and annotate it using @WebServlet and add @WebInitParams as needed.
 * <p><
 * Vaadin 7.1 introduces two features which makes this a lot easier, @VaadinServletConfiguration
 * and automatic UI finding.
 * VaadinServletConfiguration is a type safe, Vaadin version of @WebInitParam
 * which provides you with the option to select UI by referring the UI class
 * directly toggle productionMode using a boolean and more
 */
@WebServlet(urlPatterns = { "/*" , "/VAADIN/*" }, asyncSupported = true, displayName = "Vaadbio")
@VaadinServletConfiguration(productionMode = false, ui = VaadbioUI.class)
public class VaadbioServlet extends AlgosServlet {

    /**
     * Invoked when a new Vaadin service session is initialized for that service.
     * <p>
     * Because of the way different service instances share the same session, the listener is not necessarily notified immediately
     * when the session is created but only when the first request for that session is handled by a specific service.
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo)
     * prima (PRIMA) di eseguire le regolazioni specifiche <br>
     *
     * @param event the initialization event
     * @throws ServiceException a problem occurs when processing the event
     */
    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        super.sessionInit(event);
        checkCookies();
    }// end of method


    /**
     * Controlla se esistono cookies e tenta di connettersi usando i valori dei cookies
     */
    private boolean checkCookies() {
        Login loginBase;
        WikiLogin loginWiki;
        boolean status = false;
        String nick = "";
        String password = "";
        String clearPass = "";

        // attempt to login from the cookies
        loginBase = Login.getLogin();
        if (loginBase != null) {
            loginBase.loginFromCookies();
            if (loginBase.isLogged()) {
                nick = loginBase.getUser().getNickname();
                password = loginBase.getUser().getPassword();
                clearPass = LibCrypto.decrypt(password);
            }// end of if cycle
        }// end of if cycle

        if (!nick.equals("") && !clearPass.equals("")) {
            loginWiki = new WikiLogin(nick, clearPass);
            status = loginWiki.isValido();
            LibSession.setAttribute("logged", true);
            LibSession.setAttribute(WikiLogin.WIKI_LOGIN_KEY_IN_SESSION, loginWiki);
        }// end of if cycle

        return status;
    }// end of method

}// end of servlet class
