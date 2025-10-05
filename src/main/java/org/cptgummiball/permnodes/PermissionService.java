package org.cptgummiball.permnodes;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import net.luckperms.api.query.QueryOptions;
import net.luckperms.api.util.Tristate;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public final class PermissionService {

    private static LuckPerms lpApi;

    private static LuckPerms api() {
        if (lpApi != null) return lpApi;
        try {
            lpApi = LuckPermsProvider.get();
        } catch (Throwable ignored) {
            lpApi = null;
        }
        return lpApi;
    }

    public static boolean has(ServerPlayer player, String node) {
        // Bypass zuerst (direkt, falls gesetzt)
        if (checkNode(player, node)) return true;
        return false;
    }

    /** Prüft einen Permission-Node *exakt*. LuckPerms kümmert sich selbst um Wildcards. */
    public static boolean checkNode(ServerPlayer player, String node) {
        LuckPerms api = api();
        if (api == null || player == null) return false;

        try {
            PlayerAdapter<ServerPlayer> adapter = api.getPlayerAdapter(ServerPlayer.class);
            Tristate t = adapter.getPermissionData(player).checkPermission(node);
            return t.asBoolean();
        } catch (Throwable t) {
            return false;
        }
    }

    /** True, wenn LuckPerms verfügbar ist. */
    public static boolean isLuckPermsPresent() {
        return api() != null;
    }

    /** Optionaler Zugriff auf den LuckPerms-User (nicht erforderlich). */
    public static Optional<User> lpUser(ServerPlayer p) {
        LuckPerms api = api();
        if (api == null) return Optional.empty();
        return Optional.ofNullable(api.getUserManager().getUser(p.getUUID()));
    }
}
