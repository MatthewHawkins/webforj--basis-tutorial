package org.example.renderer;

import org.example.models.Customer;

import com.webforj.component.table.renderer.Renderer;

public class CountryRenderer extends Renderer<Customer> {
  @Override
  public String build() {
    return /* html */"""
        <%
        const country = cell.row.getValue('CountryName');
        const code = cell.row.getValue('CountryCode').toLowerCase();
        %>
        <div part='country-renderer'>
          <img
            part='country-img'
            src='https://flagicons.lipis.dev/flags/1x1/<%= encodeURIComponent(code) %>.svg' />
          <div part="country-text">
            <%= country %>
            <div part="country-subtext">
              <%= code.toUpperCase() %>
            </div>
          </div>
        </div>
        """;
  }
}