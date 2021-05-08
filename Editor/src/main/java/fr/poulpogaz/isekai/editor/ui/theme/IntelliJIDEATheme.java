package fr.poulpogaz.isekai.editor.ui.theme;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import fr.poulpogaz.isekai.editor.utils.LazyValue;

import java.io.IOException;
import java.io.InputStream;

public final class IntelliJIDEATheme extends Theme {

    private final String fileName;
    private final String name;
    private final boolean dark;
    private final String license;
    private final String sourceCodeUrl;
    private final String sourceCodePath;

    public IntelliJIDEATheme(String fileName,
                             String name,
                             boolean dark,
                             String license,
                             String sourceCodeUrl,
                             String sourceCodePath) {
        this.fileName = fileName;
        this.name = name;
        this.dark = dark;
        this.license = license;
        this.sourceCodeUrl = sourceCodeUrl;
        this.sourceCodePath = sourceCodePath;
    }

    @Override
    protected LazyValue<? extends FlatLaf> createLaf() {
        return new LazyValue<>() {
            @Override
            protected FlatLaf create() throws IOException {
                String path = "/themes/" + fileName;

                InputStream is = IntelliJIDEATheme.class.getResourceAsStream(path);
                if (is == null) {
                    return null;
                }

                return IntelliJTheme.createLaf(is);
            }
        };
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
        }  else if(url.contains("gitlab.com")) {
            url = url.replace("/blob/", "/raw/");
        } else {
            throw new UnsupportedOperationException("Unsupported theme");
        }

        return url.replace(" ", "%20");
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }

    public boolean isDark() {
        return dark;
    }

    public String getLicense() {
        return license;
    }

    public String getSourceCodeUrl() {
        return sourceCodeUrl;
    }

    public String getSourceCodePath() {
        return sourceCodePath;
    }
}