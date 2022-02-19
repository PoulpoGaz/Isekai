package fr.poulpogaz.isekai.game.renderer.g2d;

import fr.poulpogaz.isekai.game.renderer.Texture;
import fr.poulpogaz.isekai.game.renderer.utils.Disposable;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

public class ImageFont implements Disposable {

    private static final int PADDING = 2;
    private static final BufferedImage EMPTY = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    private final Font font;
    private final Map<Character, Glyph> charMap;

    private Texture texture;

    private int height;
    private int ascent;
    private int descent;
    private int leading;

    public ImageFont(Font font, Charset charset) {
        this.font = font;

        char[] chars = availableCharsForCharset(charset);

        charMap = new HashMap<>(chars.length);

        int size = computeTextureDimension(chars);
        makeTexture(chars, size);
    }

    public ImageFont(Font font, String charset) {
        this(font, Charset.forName(charset));
    }

    public ImageFont(Font font, char[] chars) {
        this.font = font;

        charMap = new HashMap<>(chars.length);

        int size = computeTextureDimension(chars);
        makeTexture(chars, size);
        System.out.println(charMap);
    }

    private char[] availableCharsForCharset(Charset charset) {
        CharsetEncoder encoder = charset.newEncoder();

        StringBuilder chars = new StringBuilder((int) Character.MAX_VALUE);
        for (char i = 0; i < Character.MAX_VALUE; i++) {
            if (encoder.canEncode(i)) {
                chars.append(i);
            }
        }

        return chars.toString().toCharArray();
    }

    private int computeTextureDimension(char[] chars) {
        java.awt.Graphics2D g2d = EMPTY.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g2d.getFontMetrics(font);
        this.height = fm.getHeight();

        int sqrt = (int) Math.sqrt(chars.length);
        int preferredSize = fm.getHeight() * sqrt;
        int exponent = (int) (Math.log(preferredSize) / Math.log(2)) + 1;
        preferredSize = (int) Math.pow(2, exponent);

        HashMap<Character, Glyph.GlyphBuilder> builders = new HashMap<>(chars.length);

        boolean revalidate = false;
        int x = 0;
        int y = 0;

        // try to put all chars in a texture of size preferredSize, which is a power of two
        int height = 0;
        for (char c : chars) {
            int charWidth = fm.charWidth(c);

            int x2 = x + charWidth + PADDING;

            if (x2 >= preferredSize) {
                x = 0;
                y += this.height;

                // we can't put all chars
                if (y >= preferredSize) {
                    revalidate = true;

                    // store height
                    height = y + this.height;
                }
            }

            Glyph.GlyphBuilder builder = new Glyph.GlyphBuilder();

            builder.setChar(c);
            builder.setX(x);
            builder.setY(y);
            builder.setWidth(charWidth);

            builders.put(c, builder);

            x += builder.getWidth() + PADDING;
        }

        int size;
        if (revalidate) {
            size = height * preferredSize; // preferredSize = width
            int old = preferredSize * preferredSize;

            float ratio = (float) size / old;
            int power = (int) (Math.round(Math.log(ratio) / Math.log(2)));

            size = (int) Math.pow(power * exponent, 2);

            x = 0;
            y = 0;
            for (char c : chars) {
                Glyph.GlyphBuilder builder = builders.get(c);

                int x2 = x + builder.getWidth() + PADDING;

                if (x2 >= preferredSize) {
                    x = 0;
                    y += this.height;
                }

                builder.setX(x);
                builder.setY(y);

                x += builder.getWidth();

                builders.remove(c, builder);
                charMap.put(c, builder.build());
            }
        } else {
            size = preferredSize;

            for (Map.Entry<Character, Glyph.GlyphBuilder> entry : builders.entrySet()) {
                charMap.put(entry.getKey(), entry.getValue().build());
            }
        }

        this.ascent = fm.getAscent();
        this.descent = fm.getDescent();
        this.leading = fm.getLeading();

        g2d.dispose();

        return size;
    }

    private void makeTexture(char[] chars, int textureSize) {
        BufferedImage image = new BufferedImage(textureSize, textureSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);

        for (char c : chars) {
            Glyph glyph = charMap.get(c);

            g2d.drawString("" + c, glyph.getX(), glyph.getY() + ascent);
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] ints = ((DataBufferInt) image.getData().getDataBuffer()).getData();
        ByteBuffer buffer = ByteBuffer.allocateDirect(ints.length * 4);

        for (int i = 0; i < textureSize * textureSize; i++) {
            int color = ints[i];

            // convert argb to rgba
            color = color << 8 | (color >> 24) & 0xFF;

            buffer.putInt(color);
        }

        buffer.flip();

        texture = new Texture(GLFWImage.create().set(textureSize, textureSize, buffer));
    }

    public Glyph getGlyph(char c) {
        return charMap.get(c);
    }

    public Font getFont() {
        return font;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getHeight() {
        return height;
    }

    public int getAscent() {
        return ascent;
    }

    public int getDescent() {
        return descent;
    }

    public int getLeading() {
        return leading;
    }

    public int getTextureSize() {
        return texture.getWidth();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}