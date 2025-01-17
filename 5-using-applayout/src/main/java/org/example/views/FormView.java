package org.example.views;


import java.util.ArrayList;
import java.util.UUID;

import org.example.data.Service;
import org.example.models.Customer;
import org.example.models.Customer.Country;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@Route(value = "customer/:id?", outlet = MainLayout.class)
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
  BindingContext<Customer> context;
  Customer customer = new Customer();
  String customerId = "";
  Div self = getBoundComponent();
  TextField firstName = new TextField("First Name");
  TextField lastName = new TextField("Last Name");
  TextField company = new TextField("Company");
  ChoiceBox country = new ChoiceBox("Country");
  Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> Router.getCurrent().navigate(HomeView.class));

  ColumnsLayout columnsLayout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      cancel, submit);

  public FormView() {
    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));

    fillCountries();
    self.setMaxWidth("600px");
    self.setHeight("100dvh");
    self.addClassName("form");
    self.add(columnsLayout);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
  }

  private void submitCustomer() {
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerId.isEmpty()) {
        Service.getCurrent().addCustomer(customer);
      }
      Router.getCurrent().navigate(HomeView.class);
    }
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
}