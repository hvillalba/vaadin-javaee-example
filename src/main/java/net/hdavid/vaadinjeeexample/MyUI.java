package net.hdavid.vaadinjeeexample;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import com.vaadin.server.VaadinServlet;
import net.hdavid.vaadinjeeexample.samples.MainScreen;
import net.hdavid.vaadinjeeexample.samples.authentication.LoginScreen;

import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import net.hdavid.vaadinjeeexample.samples.crud.SampleCrudView;


@Push
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("net.hdavid.vaadinjeeexample.MyAppWidgetset")
public class MyUI extends UI {

    MainScreen mainScreenInstance;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");

        LoginScreen ls = new LoginScreen();
        ls.setLoginListener(MyUI.this::showMainView);
        setContent(ls);
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen());
        getNavigator().navigateTo("".equals(getNavigator().getState()) ? SampleCrudView.VIEW_NAME : getNavigator().getState());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }

}
