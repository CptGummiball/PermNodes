package org.cptgummiball.permnodes;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;

public final class PortalPermissionHandler {

    /* Traveling through portals */
    @SubscribeEvent
    public void onTravel(EntityTravelToDimensionEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "portal.bypass")) return;

        ResourceKey<Level> to = e.getDimension();
        ResourceLocation id = to.location(); // z.B. minecraft:the_nether, minecraft:the_end, minecraft:overworld
        String ns = id.getNamespace();
        String path = id.getPath();
        String node = "portal." + ns + "." + path; // portal.minecraft.the_nether

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, node) ||
                PermissionService.checkNode(sp, "portal." + ns + ".*") ||
                PermissionService.checkNode(sp, "portal.*") ||
                PermissionService.checkNode(sp, "portal.allow_default")
                : PermissionService.checkNode(sp, "portal.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_PORTAL_DENY, true);
        }
    }
}