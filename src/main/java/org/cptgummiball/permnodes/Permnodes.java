package org.cptgummiball.permnodes;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Permnodes.MODID)
public final class Permnodes {
    public static final String MODID = "permnodes";

    public Permnodes() {
        IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(new RecipePermissionHandler());
        NeoForge.EVENT_BUS.register(new BlockPermissionHandler());
        NeoForge.EVENT_BUS.register(new ItemUsePermissionHandler());
        NeoForge.EVENT_BUS.register(new InteractPermissionHandler());
        NeoForge.EVENT_BUS.register(new PortalPermissionHandler());
        NeoForge.EVENT_BUS.register(new IgnitePermissionHandler());
        NeoForge.EVENT_BUS.register(new PvPPermissionHandler());
        NeoForge.EVENT_BUS.register(new AnimalsPermissionHandler());
        NeoForge.EVENT_BUS.register(new CropsPermissionHandler());
        NeoForge.EVENT_BUS.register(new FluidsPermissionHandler());
        NeoForge.EVENT_BUS.register(new MonstersPermissionHandler());
    }

    private void commonSetup(FMLCommonSetupEvent e) {}
}
