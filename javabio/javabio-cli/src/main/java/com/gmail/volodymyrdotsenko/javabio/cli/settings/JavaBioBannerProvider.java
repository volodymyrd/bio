package com.gmail.volodymyrdotsenko.javabio.cli.settings;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Created by Volodymyr Dotsenko on 5/15/16.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JavaBioBannerProvider extends DefaultBannerProvider {
    public String getBanner() {
        StringBuffer buf = new StringBuffer();
        buf.append("=======================================" + OsUtils.LINE_SEPARATOR);
        buf.append("*                                     *" + OsUtils.LINE_SEPARATOR);
        buf.append("*            Java BIO CLI             *" + OsUtils.LINE_SEPARATOR);
        buf.append("*                                     *" + OsUtils.LINE_SEPARATOR);
        buf.append("=======================================" + OsUtils.LINE_SEPARATOR);
        buf.append("Version:" + this.getVersion());
        return buf.toString();
    }

    public String getVersion() {
        try {
            Class clazz = JavaBioBannerProvider.class;
            String className = clazz.getSimpleName() + ".class";
            String classPath = clazz.getResource(className).toString();
            if (classPath.startsWith("jar")) {
                String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) +
                        "/META-INF/MANIFEST.MF";
                Manifest manifest = new Manifest(new URL(manifestPath).openStream());
                Attributes attr = manifest.getMainAttributes();
                return attr.getValue("Implementation-Version");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "debug version";
    }

    public String getWelcomeMessage() {
        return "Welcome to Java BIO CLI";
    }

    @Override
    public String getProviderName() {
        return "Java BIO CLI";
    }
}