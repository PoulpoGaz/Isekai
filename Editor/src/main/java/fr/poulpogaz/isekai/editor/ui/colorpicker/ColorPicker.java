package fr.poulpogaz.isekai.editor.ui.colorpicker;

import fr.poulpogaz.isekai.editor.ui.layout.HCOrientation;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalConstraint;
import fr.poulpogaz.isekai.editor.ui.layout.HorizontalLayout;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

import static fr.poulpogaz.isekai.editor.ui.colorpicker.ColorModel.COLOR_PROPERTY;
import static fr.poulpogaz.isekai.editor.ui.colorpicker.ColorNumberDocument.Type.*;

public class ColorPicker extends JComponent {

    public static final int CANCEL_OPTION = 0;
    public static final int CHOOSE_OPTION = 1;
    public static final int ERROR_OPTION = -1;

    private final ColorModel model;

    private SaturationBrightnessChooser chooser;
    private AlphaSlider alphaSlider;
    private HueSlider hueSlider;
    private PreviewComponent previewComponent;

    private JTextField red;
    private JTextField green;
    private JTextField blue;

    private JTextField hue;
    private JTextField saturation;
    private JTextField brightness;

    private JTextField alpha;

    private int returnValue;

    public ColorPicker() {
        model = new ColorModel();

        initComponents();
        initListeners();
    }

    private void initComponents() {
        setLayout(new MigLayout());

        // text field
        red = createField(RGBA, model.getRed(), model::setRed);
        green = createField(RGBA, model.getGreen(), model::setGreen);
        blue = createField(RGBA, model.getBlue(), model::setBlue);

        hue = createField(HUE, model.getHue(), model::setHue);
        saturation = createField(SAT_BRI, model.getSaturation(), model::setSaturation);
        brightness = createField(SAT_BRI, model.getBrightness(), model::setBrightness);

        alpha = createField(RGBA, model.getAlpha(), model::setAlpha);

        // sliders and chooser
        chooser = new SaturationBrightnessChooser();
        chooser.setHue(model.getHue());
        chooser.setPosition(model.getSaturation(), model.getBrightness());
        chooser.setPreferredSize(new Dimension(150, 150));

        alphaSlider = new AlphaSlider(model.getAlpha());
        alphaSlider.setVertical(false);
        alphaSlider.setPreferredSize(new Dimension(150, 25));

        hueSlider = new HueSlider(model.getHue());
        hueSlider.setInvert(true);
        hueSlider.setPreferredSize(new Dimension(25, 150));

        previewComponent = new PreviewComponent();
        previewComponent.setPreferredSize(new Dimension(25, 25));

        add(chooser, "span 1 4");
        add(hueSlider, "span 2 4");

        add(new JLabel("R:"));
        add(red);
        add(new JLabel("H:"));
        add(hue, "wrap");

        add(new JLabel("G:"));
        add(green);
        add(new JLabel("S:"));
        add(saturation, "wrap");

        add(new JLabel("B:"));
        add(blue);
        add(new JLabel("V:"));
        add(brightness, "wrap");

        add(new JLabel("A:"));
        add(alpha, "wrap");

        add(alphaSlider);
        add(previewComponent);
    }

    private void initListeners() {
        chooser.addChangeListener((e) -> {
            Point pos = chooser.getPosition();

            model.setHSB(model.getHue(), pos.x, pos.y);
        });

        alphaSlider.addChangeListener((e) -> {
            model.setAlpha(alphaSlider.getValue());
        });

        hueSlider.addChangeListener((e) -> {
            model.setHue(hueSlider.getValue());
        });

        model.addPropertyChangeListener(COLOR_PROPERTY, this::syncComponents);
    }

    private void syncComponents(PropertyChangeEvent evt) {
        hueSlider.setValue(model.getHue());
        alphaSlider.setValue(model.getAlpha());

        chooser.setHue(model.getHue());
        chooser.setPosition(model.getSaturation(), model.getBrightness());

        previewComponent.repaint();

        setText(red, model.getRed());
        setText(green, model.getGreen());
        setText(blue, model.getBlue());
        setText(alpha, model.getAlpha());
        setText(hue, model.getHue());
        setText(saturation, model.getSaturation());
        setText(brightness, model.getBrightness());
    }

    private void setText(JTextField field, int value) {
        String fieldText = field.getText();
        String text = String.valueOf(value);

        if (fieldText.isEmpty() || Integer.parseInt(fieldText) != Integer.parseInt(text)) {
            field.setText(text);
        }
    }

    private JTextField createField(ColorNumberDocument.Type type, int value, Consumer<Integer> listener) {
        JTextField field = new JTextField();
        field.setDocument(new ColorNumberDocument(type));
        field.setText(String.valueOf(value));

        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                runListener(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                runListener(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                runListener(e.getDocument());
            }

            private void runListener(Document doc) {
                try {
                    String text = doc.getText(0, doc.getLength());

                    if (!text.isEmpty()) {
                        listener.accept(Integer.parseInt(text));
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });

        return field;
    }

    protected JDialog createDialog(Component parent, String title) {
        JDialog dialog;

        if (parent == null) {
            dialog = new JDialog((Frame) null, title, true);
        } else {
            Window window = SwingUtilities.getWindowAncestor(parent);
            if (window instanceof Frame) {
                dialog = new JDialog((Frame) window, title, true);
            } else {
                dialog = new JDialog((Dialog) window, title, true);
            }
        }

        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.setLayout(new BorderLayout());

        content.add(this, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new HorizontalLayout());

        HorizontalConstraint constraint = new HorizontalConstraint();
        constraint.orientation = HCOrientation.RIGHT;
        constraint.leftGap = 5;

        JButton choose = new JButton("Choose");
        choose.addActionListener((l) -> {
            dialog.dispose();
            returnValue = CHOOSE_OPTION;
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener((l) -> {
            dialog.dispose();
            returnValue = CANCEL_OPTION;
        });

        bottom.add(choose, constraint);
        bottom.add(cancel, constraint);

        content.add(bottom, BorderLayout.SOUTH);
        dialog.setContentPane(content);

        if (JDialog.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();

            if (supportsWindowDecorations) {
                dialog.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
            }
        }

        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        return dialog;
    }

    public int showDialog(Component parent, String title) {
        JDialog dialog = createDialog(parent, title);

        returnValue = ERROR_OPTION;

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                returnValue = CANCEL_OPTION;
            }
        });
        dialog.setVisible(true);

        return returnValue;
    }

    public Color getColor() {
        return model.getColor();
    }

    public void setColor(Color color) {
        model.setColor(color);
    }

    public void addColorListener(PropertyChangeListener listener) {
        model.addPropertyChangeListener(COLOR_PROPERTY, listener);
    }

    public void removeColorListener(PropertyChangeListener listener) {
        model.removePropertyChangeListener(COLOR_PROPERTY, listener);
    }

    private class PreviewComponent extends JComponent {

        @Override
        protected void paintComponent(Graphics g) {
            Rectangle bounds = getBounds();
            Insets insets = getInsets();

            RoundRectangle2D.Float rectangle = new RoundRectangle2D.Float();
            rectangle.x = insets.left + 2;
            rectangle.y = insets.top + 2;
            rectangle.width = bounds.width - insets.left - insets.right - 4;
            rectangle.height = bounds.height - insets.top - insets.bottom - 4;
            rectangle.archeight = 3;
            rectangle.arcwidth = 3;

            Graphics2D g2d = (Graphics2D) g;

            Object originalAntialias = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            Stroke originalStroke = g2d.getStroke();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(model.getColor());
            g2d.fill(rectangle);

            g2d.setStroke(new BasicStroke(2f));
            g2d.setColor(new Color(25, 25, 25));
            g2d.draw(rectangle);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, originalAntialias);
            g2d.setStroke(originalStroke);
        }
    }
}