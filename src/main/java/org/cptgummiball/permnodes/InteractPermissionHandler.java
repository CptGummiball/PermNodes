package org.cptgummiball.permnodes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class InteractPermissionHandler {

    private static ResourceLocation blockId(BlockState s) {
        return s.getBlock().builtInRegistryHolder()
                .unwrapKey().map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.BLOCK.getKey(s.getBlock()));
    }

    private static boolean allows(ServerPlayer sp, String base, ResourceLocation id, Holder<Block> holder) {
        String ns = id.getNamespace();
        String dot = ns + "." + id.getPath();

        if (PermissionService.checkNode(sp, "mod.allow." + ns)) return true;

        if (PermissionService.checkNode(sp, base + "." + dot)) return true;
        if (PermissionService.checkNode(sp, base + "." + ns + ".*")) return true;
        if (PermissionService.checkNode(sp, base + ".*")) return true;

        boolean tagMatch = holder.tags().anyMatch((TagKey<Block> tag) -> {
            ResourceLocation tid = tag.location();
            return PermissionService.checkNode(sp, base + ".tag." + tid.getNamespace() + "." + tid.getPath());
        });

        return tagMatch;
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "interact.bypass")) return;

        BlockState state = e.getLevel().getBlockState(e.getPos());
        ResourceLocation id = blockId(state);
        Holder<Block> holder = state.getBlock().builtInRegistryHolder();

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? allows(sp, "interact", id, holder)
                || PermissionService.checkNode(sp, "interact.allow_default")
                : PermissionService.checkNode(sp, "interact.allow_default");

        if (!allowed) {
            e.setCancellationResult(InteractionResult.FAIL);
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_INTERACT_DENY, true);
        }
    }
}