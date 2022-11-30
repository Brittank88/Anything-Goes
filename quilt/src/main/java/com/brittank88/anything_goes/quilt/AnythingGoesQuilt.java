package com.brittank88.anything_goes.quilt;

import com.brittank88.anything_goes.fabriclike.AnythingGoesFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class AnythingGoesQuilt implements ModInitializer {
    @Override public void onInitialize(ModContainer mod) {
        AnythingGoesFabricLike.init();
    }
}
