package org.mm;

import com.google.gdata.client.contacts.ContactsService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtkx.WTKXSerializer;
import org.mm.contact.ContactModule;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Mustafa Motiwala
 * Date: Apr 17, 2010
 * Time: 5:37:17 PM
 */
@SuppressWarnings("unused")
public class PIMApplication implements Application {
    private static final Log log = LogFactory.getLog(PIMApplication.class);

    private static PIMApplication app;

    @Inject
    @Named("svcContacts")
    private ContactsService service;
    @Inject
    @Named("loginAction")
    private Action loginAction;
    @Inject
    private List<ApplicationTab> applicationTabs = new ArrayList<ApplicationTab>(0);

    private URL serviceUrl;

    private Window window;

    @Override
    public void startup(Display display, Map<String, String> properties) throws Exception {
        WTKXSerializer wtkxSerializer = new WTKXSerializer();
        Action.getNamedActions().put("loginAction",app.loginAction);
        app.loginAction.setEnabled(true);
        log.warn("Preparing to de-serialize window.");
        window = (Window)wtkxSerializer.readObject(app,"PIMApplication.wtkx");
        app.window = window;
        window.open(display);
    }

    @Override
    public boolean shutdown(boolean optional) throws Exception {
        return false;
    }

    @Override
    public void suspend() throws Exception {
    }

    @Override
    public void resume() throws Exception {
    }

    public static void main(String args[]) {
        Injector injector = Guice.createInjector(new ApplicationModule(), new ContactModule());
        app = injector.getInstance(PIMApplication.class);
        DesktopApplicationContext.main(PIMApplication.class,args);
    }

    public void setServiceUrl(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public static PIMApplication getInstance(){
        return app;
    }
}
