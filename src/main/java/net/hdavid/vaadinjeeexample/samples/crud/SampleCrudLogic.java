package net.hdavid.vaadinjeeexample.samples.crud;

import net.hdavid.vaadinjeeexample.samples.authentication.UserHolder;
import net.hdavid.vaadinjeeexample.samples.backend.DataService;
import net.hdavid.vaadinjeeexample.samples.backend.data.Product;

import java.io.Serializable;
import java.util.Arrays;

import com.vaadin.server.Page;

public class SampleCrudLogic implements Serializable {

    private SampleCrudView view;

    public SampleCrudLogic(SampleCrudView simpleCrudView) {
        view = simpleCrudView;

    }

    public void init() {
        editProduct(null);
        // Hide and disable if not admin
        if (!UserHolder.get().isInAnyRole(Arrays.asList("admin"))) {
            view.setNewProductEnabled(false);
        }

        view.showProducts(DataService.get().getAllProducts());
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
        view.editProduct(null);
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }


        Page.getCurrent().setUriFragment("!" + SampleCrudView.VIEW_NAME + "/"
                + fragmentParameter, false);
    }

    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(productId);
                    Product product = findProduct(pid);
                    view.selectRow(product);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private Product findProduct(int productId) {
        return DataService.get().getProductById(productId);
    }

    public void saveProduct(Product product) {
        view.showSaveNotification(product.getProductName() + " ("
                + product.getId() + ") updated");
        view.clearSelection();
        view.editProduct(null);
        view.refreshProduct(product);
        setFragmentParameter("");
    }

    public void deleteProduct(Product product) {
        DataService.get().deleteProduct(product.getId());
        view.showSaveNotification(product.getProductName() + " ("
                + product.getId() + ") removed");

        view.clearSelection();
        view.editProduct(null);
        view.removeProduct(product);
        setFragmentParameter("");
    }

    public void editProduct(Product product) {
        if (product == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(product.getId() + "");
        }
        view.editProduct(product);
    }

    public void newProduct() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editProduct(new Product());
    }

    public void rowSelected(Product product) {
        if (UserHolder.get().isInAnyRole(Arrays.asList("admin"))) {
            view.editProduct(product);
        }
    }
}
