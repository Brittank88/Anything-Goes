package com.brittank88.anything_goes.fabric;

import com.brittank88.anything_goes.fabriclike.AnythingGoesFabricLike;
import net.fabricmc.api.ModInitializer;

public class AnythingGoesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AnythingGoesFabricLike.init();
    }
}
