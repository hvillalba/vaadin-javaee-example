package net.hdavid.vaadinjeeexample.samples;

import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.hdavid.vaadinjeeexample.samples.about.AboutView;
import net.hdavid.vaadinjeeexample.samples.authentication.UserHolder;
import net.hdavid.vaadinjeeexample.samples.crud.SampleCrudView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;

import java.util.HashMap;
import java.util.Map;

public class MainScreen extends HorizontalLayout {
    private Menu menu;

    public MainScreen () {
        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        Navigator navigator = new Navigator(UI.getCurrent(), viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);

        menu.addView(SampleCrudView.class, SampleCrudView.VIEW_NAME, SampleCrudView.VIEW_NAME, FontAwesome.EDIT);
        menu.addView(ProuctLazyListView.class, ProuctLazyListView.VIEW_NAME, ProuctLazyListView.VIEW_NAME, FontAwesome.INFO_CIRCLE);
        menu.addView(AboutView.class, AboutView.VIEW_NAME, AboutView.VIEW_NAME, FontAwesome.INFO_CIRCLE);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();

        // notify the view menu about view changes so that it can display which view is currently active
        navigator.addViewChangeListener(new ViewChangeListener() {
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }
            public void afterViewChange(ViewChangeEvent event) {
                menu.styleMenuItemOfActiveView(event.getViewName());
            }
        });
    }

    private static class Menu extends CssLayout {

        private static final String VALO_MENUITEMS = "valo-menuitems";
        private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
        private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
        private Navigator navigator;
        private Map<String, Button> viewButtons = new HashMap<>();

        private CssLayout menuItemsLayout;
        private CssLayout menuPart;

        public Menu(Navigator navigator) {
            this.navigator = navigator;
            setPrimaryStyleName(ValoTheme.MENU_ROOT);
            menuPart = new CssLayout();
            menuPart.addStyleName(ValoTheme.MENU_PART);

            // header of the menu
            final HorizontalLayout top = new HorizontalLayout();
            top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
            top.addStyleName(ValoTheme.MENU_TITLE);
            top.setSpacing(true);
            Label title = new Label("My CRUD");
            title.addStyleName(ValoTheme.LABEL_H3);
            title.setSizeUndefined();
            Image image = new Image(null, new ThemeResource("img/table-logo.png"));
            image.setStyleName("logo");
            top.addComponent(image);
            top.addComponent(title);
            menuPart.addComponent(top);

            // logout menu item
            MenuBar logoutMenu = new MenuBar();
            logoutMenu.addItem("Logout", FontAwesome.SIGN_OUT, i -> {
                for (UI ui: VaadinSession.getCurrent().getUIs())
                    ui.access(() -> ui.getPage().setLocation("/")); // FIXME, has to be the contextPath of the app server

                getSession().close();
                Page.getCurrent().reload();
            });

            logoutMenu.addStyleName("user-menu");
            menuPart.addComponent(logoutMenu);

            // button for toggling the visibility of the menu when on a small screen
            final Button showMenu = new Button("Menu", cl -> {
                if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE))
                    menuPart.removeStyleName(VALO_MENU_VISIBLE);
                else
                    menuPart.addStyleName(VALO_MENU_VISIBLE);
            });

            showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
            showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
            showMenu.addStyleName(VALO_MENU_TOGGLE);
            showMenu.setIcon(FontAwesome.NAVICON);
            menuPart.addComponent(showMenu);

            // container for the navigation buttons, which are added by addView()
            menuItemsLayout = new CssLayout();
            menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
            menuPart.addComponent(menuItemsLayout);

            addComponent(menuPart);
        }

        public void addView(Class<? extends View> viewClass, final String name, String caption, Resource icon) {
            if (UserHolder.viewAccesibleToUser(name)) {
                createViewButton(name, caption, icon);
                navigator.addView(name, viewClass);
            }
        }

        private void createViewButton(final String name, String caption, Resource icon) {
            Button button = new Button(caption, cl->  navigator.navigateTo(name));
            button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
            button.setIcon(icon);
            menuItemsLayout.addComponent(button);
            viewButtons.put(name, button);
        }

        public void styleMenuItemOfActiveView(String viewName) {
            for (Button button : viewButtons.values())
                button.removeStyleName("selected");

            Button selected = viewButtons.get(viewName);
            if (selected != null)
                selected.addStyleName("selected");

            menuPart.removeStyleName(VALO_MENU_VISIBLE);
        }
    }
}
