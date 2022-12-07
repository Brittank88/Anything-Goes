package com.brittank88.anything_goes.forge;

import com.brittank88.anything_goes.AnythingGoes;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.ClassLoaderUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.jar.JarFile;

@Mod(AnythingGoes.MOD_ID)
public class AnythingGoesForge {
    public AnythingGoesForge() {

        // Submit our event bus to let Architectury register our content at the right time.
        EventBuses.registerModEventBus(AnythingGoes.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        AnythingGoes.init();
    }
}
