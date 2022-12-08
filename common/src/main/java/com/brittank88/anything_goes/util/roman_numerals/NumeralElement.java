package com.brittank88.anything_goes.util.roman_numerals;

/**
 * Represents a numeral element with an index.
 * <br />Credits to <a href="https://github.com/auoeke">Auoeke</a> for the original code.
 * @see <a href="https://github.com/auoeke/romeral/blob/master/source/net.auoeke.romeral/NumeralElement.java">Romeral's NumeralElement.java</a>
 */
final class NumeralElement extends Numeral {
    final int index;

    NumeralElement(Numeral numeral, int index) {
        super(numeral.roman, numeral.value, numeral.firstDigit);
        this.index = index;
    }
}
