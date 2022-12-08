package com.brittank88.anything_goes;

import com.brittank88.anything_goes.util.roman_numerals.NumeralSystem;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnythingGoes {

    public static final String MOD_ID = "anything_goes";
    public static ResourceLocation id(String path) { return new ResourceLocation(MOD_ID, path); }

    public static final String MOD_NAME = "Anything Goes";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Initializing...");

        LOGGER.info(NumeralSystem.EXTENDED.toRoman(Integer.MAX_VALUE));
    }
}
