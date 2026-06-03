package dataTemplates.login;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import dataTemplatesPojo.login.LoginCredentials;

public class LoginCredentialsTemplate implements TemplateLoader {
    public void load() {
        Fixture.of(LoginCredentials.class)
                .addTemplate(
                        "LoginCredentials",
                        new Rule() {
                            {
                                add("mobileNumber", "8867132477");
                            }
                        });
    }
}
