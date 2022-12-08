package com.brittank88.anything_goes.util.roman_numerals;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents a numeral system.
 * <br />Credits to <a href="https://github.com/auoeke">Auoeke</a> for the original code.
 * <br />Modified to use {@link Component} instead of {@link String} by Brittank88.
 * @see <a href="https://github.com/auoeke/romeral/blob/master/source/net.auoeke.romeral/NumeralSystem.java">Romeral's NumeralSystem.java</a>
 */
public final class NumeralSystem extends AbstractList<Numeral> {

    /** Supports up to 3999. */
    public static final NumeralSystem STANDARD = of(
            Numeral.of("N", 0),
            Numeral.of("I", 1),
            Numeral.of("V", 5),
            Numeral.of("X", 10),
            Numeral.of("L", 50),
            Numeral.of("C", 100),
            Numeral.of("D", 500),
            Numeral.of("M", 1000)
    );

    /** Supports... much more than 3999. */
    public static final NumeralSystem EXTENDED = STANDARD.with(

            // x1000
            Numeral.of(Component.literal("I").withStyle(ChatFormatting.BOLD), 1000),
            Numeral.of(Component.literal("V").withStyle(ChatFormatting.BOLD) , 5000),
            Numeral.of(Component.literal("X").withStyle(ChatFormatting.BOLD) , 10000),
            Numeral.of(Component.literal("L").withStyle(ChatFormatting.BOLD) , 50000),
            Numeral.of(Component.literal("C").withStyle(ChatFormatting.BOLD) , 100000),
            Numeral.of(Component.literal("D").withStyle(ChatFormatting.BOLD) , 500000),
            Numeral.of(Component.literal("M").withStyle(ChatFormatting.BOLD) , 1000000),

            // x1000000
            Numeral.of('\u24BE', 1000000),
            Numeral.of('\u24CB', 5000000),
            Numeral.of('\u24CD', 10000000),
            Numeral.of('\u24C1', 50000000),
            Numeral.of('\u24B8', 100000000),
            Numeral.of('\u24B9', 500000000),
            Numeral.of('\u24C2', 1000000000)
    );

    private final int minIndex;
    private final NumeralElement zero;
    private final NumeralElement[] numerals;
    private final NumeralElement[] fromRomanOrder;

    private NumeralSystem(Numeral[] numerals) {
        if (numerals.length == 0) throw new IllegalArgumentException("numerals is empty");

        Arrays.sort(numerals);
        this.numerals = IntStream.range(0, numerals.length).mapToObj(index -> new NumeralElement(numerals[index], index)).toArray(NumeralElement[]::new);
        NumeralElement first = this.numerals[0];
        this.minIndex = first.value == 0 ? 1 : 0;
        this.zero = first.value == 0 ? first : STANDARD.zero;

        this.fromRomanOrder = this.numerals.clone();
        Arrays.sort(this.fromRomanOrder, Comparator.comparing(Numeral::roman, (a, b) -> {
            String aString = a.getString();
            String bString = b.getString();
            String max = aString.length() >= bString.length() ? aString : bString;
            String maxEq = max.equals(aString) ? bString : aString;

            return max.equals(maxEq)
                    ? 0
                    : max.startsWith(maxEq)
                            ? -1
                            : aString.compareTo(bString);
        }));
    }

    public static NumeralSystem of(Numeral... numerals) {
        return new NumeralSystem(numerals.clone());
    }

    public static NumeralSystem of(Collection<Numeral> numerals) {
        return new NumeralSystem(numerals.toArray(Numeral[]::new));
    }

    public Numeral numeral(long value) {
        for (var numeral : this) {
            if (numeral.value == value) return numeral;
        }

        return null;
    }

    public Numeral numeral(Component roman) {
        for (Numeral numeral : this) {
            if (numeral.roman.equals(roman)) return numeral;
        }

        return null;
    }

    public Numeral numeral(CharSequence roman) {
        for (Numeral numeral : this) {
            if (numeral.roman.getString().contentEquals(roman)) return numeral;
        }

        return null;
    }

    public Component toRoman(long value) {
        if (value == 0) return this.zero.roman;

        MutableComponent builder = Component.empty();

        if (value < 0) {
            if (value == Long.MIN_VALUE) throw new StackOverflowError("value == MIN_VALUE");

            builder.append(Component.literal("-"));
            value = -value;
        }

        for (var index = this.size() - 1; index >= this.minIndex; --index) {
            Numeral numeral = this.get(index);
            long numeralValue = numeral.value;

            while (value >= numeralValue) {
                builder.append(numeral.roman);
                value -= numeralValue;
            }

            var previousIndex = numeral.firstDigit() == 5 ? index - 1 : index - 2;

            if (previousIndex >= this.minIndex) {
                Numeral previousNumeral = this.get(previousIndex);
                long difference = numeralValue - previousNumeral.value;

                if (value >= difference) {
                    builder.append(previousNumeral.roman);
                    builder.append(numeral.roman);
                    value -= difference;
                }
            }
        }

        return builder;
    }

    public long fromRoman(Component roman) {
        if (roman.getString().isEmpty()) throw new IllegalArgumentException("Roman numeral text cannot be empty.");

        long value = 0L;
        NumeralElement nextNumeral = this.numeralPrefix(roman, 0);
        NumeralElement consecutive = nextNumeral;
        int consecutiveCount = 0;

        for (int index = 0; index < roman.getString().length();) {
            if (nextNumeral == null) throw new NumberFormatException("No valid numeral at \"%s\"[%s].".formatted(roman, index));

            NumeralElement numeral = nextNumeral;
            index += numeral.roman().getString().length();

            if (numeral == this.zero && index < roman.getString().length()) throw new NumberFormatException(numeral + " is not the entire string.");

            if (numeral == consecutive) {
                if (++consecutiveCount == 4 && consecutive.index + 1 < this.size()) consecutiveCount = Integer.MIN_VALUE;
            } else {
                consecutive = numeral;
                consecutiveCount = 1;
            }

            a: if (index < roman.getString().length()) {
                nextNumeral = this.numeralPrefix(roman, index);

                if (nextNumeral == null) continue;

                if (numeral.firstDigit() != 5) {
                    var quotient = nextNumeral.value / numeral.value;

                    if (quotient < Integer.MAX_VALUE) {
                        switch ((int) quotient) {
                            case 0, 1, 2 -> { break a; }
                            case 5, 10 -> {
                                value += nextNumeral.value - numeral.value;
                                index += numeral.roman.getString().length();
                                nextNumeral = this.numeralPrefix(roman, index);

                                continue;
                            }
                        }
                    }

                    throw new NumberFormatException("%s (\"%s\"[%s]) found after %s.".formatted(nextNumeral.roman, roman, index, numeral.roman));
                }
            }

            value += numeral.value;
        }

        return value;
    }

    public NumeralSystem with(Numeral... numerals) {
        var combined = Arrays.copyOf(this.numerals, this.size() + numerals.length, Numeral[].class);
        System.arraycopy(numerals, 0, combined, this.size(), numerals.length);

        return new NumeralSystem(combined);
    }

    @Override public int size() {
        return this.numerals.length;
    }

    @Override public @NotNull Iterator<Numeral> iterator() {
        return Arrays.stream(this.numerals).map(n -> (Numeral) n).iterator();
    }

    @Override public Numeral[] toArray() {
        return this.numerals.clone();
    }

    @Override public Numeral get(int index) {
        return this.numerals[index];
    }

    @Override public boolean equals(Object o) {
        return this == o || o instanceof NumeralSystem system && Arrays.equals(this.numerals, system.numerals);
    }

    @Override public int hashCode() {
        return Arrays.hashCode(this.numerals);
    }

    private NumeralElement numeralPrefix(Component roman, int offset) {
        for (NumeralElement numeral : this.fromRomanOrder) {
            if (roman.getString().startsWith(numeral.roman.getString(), offset)) return numeral;
        }

        return null;
    }
}
