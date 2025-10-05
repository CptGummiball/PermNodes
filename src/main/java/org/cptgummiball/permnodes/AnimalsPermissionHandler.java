package org.cptgummiball.permnodes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class AnimalsPermissionHandler {

    private static boolean isAnimal(Entity e) {
        return e instanceof Animal;
    }

    private static ResourceLocation entityId(Entity e) {
        return e.getType().builtInRegistryHolder()
                .unwrapKey().map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()));
    }

    private static boolean canAttack(ServerPlayer sp, Entity animal) {
        if (PermissionService.checkNode(sp, "animals.bypass")) return true;

        ResourceLocation id = entityId(animal);
        String ns  = id.getNamespace();
        String dot = ns + "." + id.getPath();

        if (!PermissionService.isLuckPermsPresent()) {
            return PermissionService.checkNode(sp, "animals.allow_default");
        }

        if (PermissionService.checkNode(sp, "mod.allow." + ns)) return true;

        return PermissionService.checkNode(sp, "animals.attack." + dot)
                || PermissionService.checkNode(sp, "animals.attack." + ns + ".*")
                || PermissionService.checkNode(sp, "animals.attack.*")
                || PermissionService.checkNode(sp, "animals.allow_default");
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer attacker)) return;
        if (!isAnimal(e.getTarget())) return;

        if (!canAttack(attacker, e.getTarget())) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_ANIMAL_ATTACK_DENY, true);
        }
    }

    @SubscribeEvent
    public void onIncomingDamage(LivingIncomingDamageEvent e) {
        if (!(e.getEntity() instanceof Animal)) return;

        Entity src = e.getSource().getEntity();
        if (!(src instanceof ServerPlayer attacker)) return;

        if (!canAttack(attacker, e.getEntity())) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_ANIMAL_DENY, true);
        }
    }

    @SubscribeEvent
    public void onBreed(BabyEntitySpawnEvent e) {
        if (!(e.getCausedByPlayer() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "animals.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, "animals.breed")
                || PermissionService.checkNode(sp, "animals.*")
                || PermissionService.checkNode(sp, "animals.allow_default")
                : PermissionService.checkNode(sp, "animals.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_ANIMAL_DENY, true);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (!(e.getTarget() instanceof Animal)) return;
        if (PermissionService.checkNode(sp, "animals.bypass")) return;

        var item = e.getItemStack().getItem();
        String needed = null;

        if (item == Items.SHEARS) needed = "animals.shear";
        else if (item == Items.BUCKET && e.getTarget() instanceof Cow) needed = "animals.milk";
        else if (item == Items.LEAD) needed = "animals.lead";
        else if (item == Items.NAME_TAG) needed = "animals.name_tag";

        if (needed != null) {
            boolean allowed = PermissionService.isLuckPermsPresent()
                    ? PermissionService.checkNode(sp, needed)
                    || PermissionService.checkNode(sp, "animals.*")
                    || PermissionService.checkNode(sp, "animals.allow_default")
                    : PermissionService.checkNode(sp, "animals.allow_default");

            if (!allowed) {
                e.setCancellationResult(InteractionResult.FAIL);
                e.setCanceled(true);
                sp.displayClientMessage(PermConfig.MSG_ANIMAL_DENY, true);
            }
        }
    }
}
