package sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.exception.InvalidArgumentException;

import java.util.Objects;

import static sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Strings.f;

@SuppressWarnings("WeakerAccess")
public class Color {

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0f);

    /**
     * The RGB components of the color.
     */
    private int red;
    private int green;
    private int blue;

    /**
     * The alpha color component.
     */
    private float alpha = 1;

    /**
     * Creates a color with the alpha component.
     */
    public Color(int red, int green, int blue, float alpha) {
        assertValidColorComponents(red, green, blue);
        assertValidAlphaComponent(alpha);

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * Creates a color wihout the alpha component.
     */
    public Color(int red, int green, int blue) {
        assertValidColorComponents(red, green, blue);

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Asserts that the alpha component of the color is valid.
     * @param alpha alpha component value
     */
    private void assertValidAlphaComponent(float alpha) {
        if (alpha < 0 || alpha > 1) {
            throw new InvalidArgumentException(f("The alpha component has an invalid value: %f", alpha));
        }
    }

    /**
     * Asserts that the color components are valid.
     * @param values color components for validation
     */
    private void assertValidColorComponents(int... values) {
        for (int value : values) {
            if (value > 255 || value < 0) {
                throw new InvalidArgumentException(f("The color component has an invalid value: %d", value));
            }
        }
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public String dump() {
        if (Color.WHITE.equals(this)) {
            return "1";
        } else if (Color.BLUE.equals(this)) {
            return "\uD83D\uDC24";
        } else if (Color.GREEN.equals(this)) {
            return "\uD83C\uDF4E";
        } else if (Color.RED.equals(this)) {
            return "\uD83C\uDF35";
        } else if (Color.TRANSPARENT.equals(this)) {
            return " ";
        }
        return "0";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return red == color.red &&
                green == color.green &&
                blue == color.blue &&
                Float.compare(color.alpha, alpha) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(red, green, blue, alpha);
    }
}
