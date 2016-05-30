package net.hdavid.vaadinjeeexample.samples.authentication;

import com.vaadin.server.VaadinSession;
import net.hdavid.vaadinjeeexample.entity.User;
import net.hdavid.vaadinjeeexample.samples.ProuctLazyListView;
import net.hdavid.vaadinjeeexample.samples.about.AboutView;
import net.hdavid.vaadinjeeexample.samples.crud.SampleCrudView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHolder {

    public static User get() {
        User user = (User) VaadinSession.getCurrent().getAttribute("UserObj");
        return user;
    }

    public static void setUser(User user) {
        VaadinSession.getCurrent().setAttribute("UserObj", user);
    }

    public static boolean viewAccesibleToUser(String viewName) {
        return get().isInAnyRole(viewNames_roles.get(viewName));
    }

    public static final Map<String, List<String>> viewNames_roles = new HashMap<>();

    static {
        viewNames_roles.put(AboutView.VIEW_NAME, Arrays.asList("admin", "operator"));
        viewNames_roles.put(SampleCrudView.VIEW_NAME, Arrays.asList("admin"));
        viewNames_roles.put(ProuctLazyListView.VIEW_NAME, Arrays.asList("admin"));
    }
//    private void setRolesAllowedInView(String viewName, String ... roles) {
//
//    }
}
