package org.cptgummiball.permnodes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class BlockPermissionHandler {

    private static ResourceLocation blockId(BlockState state) {
        return state.getBlock().builtInRegistryHolder()
                .unwrapKey().map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.BLOCK.getKey(state.getBlock()));
    }

    private static String dot(ResourceLocation id) {
        return id.getNamespace() + "." + id.getPath();
    }

    private static boolean hasBlockNodes(ServerPlayer sp, String base, ResourceLocation id, Holder<Block> holder) {
        String ns = id.getNamespace();
        String d  = dot(id);

        if (PermissionService.checkNode(sp, "mod.allow." + ns)) return true;
        if (PermissionService.checkNode(sp, base + "." + d)) return true;
        if (PermissionService.checkNode(sp, base + "." + ns + ".*")) return true;
        if (PermissionService.checkNode(sp, base + ".*")) return true;

        // Stream-Check auf irgendeinen passenden Tag
        return holder.tags().anyMatch((TagKey<Block> tag) -> {
            ResourceLocation tid = tag.location();
            return PermissionService.checkNode(sp, base + ".tag." + tid.getNamespace() + "." + tid.getPath());
        });
    }

    /* Breaking */
    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent e) {
        if (!(e.getPlayer() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "blocks.break.bypass")) return;

        BlockState state = e.getState();
        ResourceLocation id = blockId(state);
        Holder<Block> holder = state.getBlock().builtInRegistryHolder();

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? hasBlockNodes(sp, "blocks.break", id, holder) ||
                PermissionService.checkNode(sp, "blocks.break.allow_default")
                : PermissionService.checkNode(sp, "blocks.break.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_BREAK_DENY, true);
        }
    }

    /* Placing */
    @SubscribeEvent
    public void onPlace(BlockEvent.EntityPlaceEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "blocks.place.bypass")) return;

        BlockState state = e.getPlacedBlock();
        ResourceLocation id = blockId(state);
        Holder<Block> holder = state.getBlock().builtInRegistryHolder();

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? hasBlockNodes(sp, "blocks.place", id, holder) ||
                PermissionService.checkNode(sp, "blocks.place.allow_default")
                : PermissionService.checkNode(sp, "blocks.place.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_PLACE_DENY, true);
        }
    }
}