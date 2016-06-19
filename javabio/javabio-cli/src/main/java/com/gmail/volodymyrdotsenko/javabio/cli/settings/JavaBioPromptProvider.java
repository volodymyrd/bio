package com.gmail.volodymyrdotsenko.javabio.cli.settings;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

/**
 * Created by Volodymyr Dotsenko on 5/15/16.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JavaBioPromptProvider extends DefaultPromptProvider {
    @Override
    public String getPrompt() {
        return "javabio-cli>";
    }

    @Override
    public String getProviderName() {
        return "Java BIO CLI prompt provider";
    }
}