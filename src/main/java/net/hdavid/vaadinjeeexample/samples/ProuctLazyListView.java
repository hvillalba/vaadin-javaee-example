package net.hdavid.vaadinjeeexample.samples;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import net.hdavid.vaadinjeeexample.ejb.EntityAutoCreation;
import net.hdavid.vaadinjeeexample.ejb.Service;
import net.hdavid.vaadinjeeexample.entity.Product;
import org.vaadin.viritin.SortableLazyList;
import org.vaadin.viritin.grid.MGrid;

import java.util.List;

import static net.hdavid.vaadinjeeexample.L.*;

public class ProuctLazyListView extends CssLayout implements View {

    public static final String VIEW_NAME = "Product";

    Button recreateAndRefresh = new Button("Recreate And Refresh");
    public ProuctLazyListView() {
        // TODO, implementar jpacontainer sin jpa container...  :D




        setSizeFull();
        addStyleName("crud-view");

        int pageSize = 110; // why the hell it's just 2 selects ???????

        SortableLazyList.SortableEntityProvider<Product> sep = new SortableLazyList.SortableEntityProvider<Product>() {
            public int size() {
                return Service.locate().getAllEntitiesCount();
            }
            public List<Product> findEntities(int firstRow, boolean sortAscending, String property) {
                return Service.locate().getAllEntities(firstRow, pageSize, sortAscending, property);
            }
        };

        MGrid<Product> grid = new MGrid<>(sep, sep, pageSize);
        grid.setSizeFull();
//        grid.refreshRows(); // refresh from DB
//        grid.refreshRow(productInstance); // sync to UI changes of one changed bean, does not talk to DB
//        grid.refreshVisibleRows(); // same as refreshRow but for all visible rows


        recreateAndRefresh.addClickListener(cl -> {
            EntityAutoCreation.locate().reloadRequiredEntities();
            grid.refreshRows();
            grid.scrollToStart();

        });


        addComponent(ve("crud-main-layout", _MARGIN, _FULL_SIZE, ho("top-bar", recreateAndRefresh), grid, _EXPAND));
    }

    @Override
    public void enter(ViewChangeEvent event) {

        Notification.show("Hey!");

    }


}
