package com.gmail.volodymyrdotsenko.javabio.cli.settings;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

/**
 * Created by Volodymyr Dotsenko on 5/15/16.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JavaBioCommandsHistoryProvider extends DefaultHistoryFileNameProvider {
    public String getHistoryFileName() {
        return "javabio-cli.log";
    }

    @Override
    public String getProviderName() {
        return "Java BIO history file name provider";
    }
}