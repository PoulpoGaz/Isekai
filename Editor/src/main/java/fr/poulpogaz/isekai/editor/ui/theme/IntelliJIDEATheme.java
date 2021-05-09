package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;

import java.io.InputStream;

public record IntelliJIDEATheme(String fileName, String name, boolean dark,
                                String license, String sourceCodeUrl,
                                String sourceCodePath) implements Theme {

    @Override
    public FlatLaf createLaf() throws Exception {
        String path = "/themes/" + fileName;

        InputStream is = IntelliJIDEATheme.class.getResourceAsStream(path);
        if (is == null) {
            return null;
        }

        return IntelliJTheme.createLaf(is);
    }

    @Override
    public boolean isIntellijTheme() {
        return true;
    }

    @Override
    public boolean isCoreTheme() {
        return false;
    }

    String getDownloadUrl() {
        return convert(sourceCodeUrl + "/" + sourceCodePath);
    }

    String convert(String url) {
        if (url.contains("github.com")) {
            url = url + "?raw=true";
        } else if (url.contains("gitlab.com")) {
            url = url.replace("/blob/", "/raw/");
        } else {
            throw new UnsupportedOperationException("Unsupported theme");
        }

        return url.replace(" ", "%20");
    }
}