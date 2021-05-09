package fr.poulpogaz.isekai.editor.ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.util.HashMap;

public class Icons {

    private static final HashMap<String, FlatSVGIcon> ICONS = new HashMap<>();

    public static FlatSVGIcon get(String name) {
        FlatSVGIcon icon = ICONS.get(name);

        if (icon == null) {
            icon = new FlatSVGIcon(name);

            ICONS.put(name, icon);
        }

        return icon;
    }
}