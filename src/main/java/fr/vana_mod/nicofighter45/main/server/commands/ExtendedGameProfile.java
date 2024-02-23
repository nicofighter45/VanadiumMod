package fr.vana_mod.nicofighter45.main.server.commands;

import com.mojang.authlib.GameProfile;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ExtendedGameProfile extends GameProfile {

    private int permissionLevel;

    /**
     * Constructs a new Game Profile with the specified ID and name.
     * <p/>
     * Either ID or name may be null/empty, but at least one must be filled.
     *
     * @param id   Unique ID of the profile
     * @param name Display name of the profile
     * @throws IllegalArgumentException Both ID and name are either null or empty
     */
    public ExtendedGameProfile(UUID id, String name, int permissionLevel) {
        super(id, name);
        this.permissionLevel = permissionLevel;
    }

    public ExtendedGameProfile(@NotNull GameProfile gameProfile, int permissionLevel) {
        this(gameProfile.getId(), gameProfile.getName(), permissionLevel);
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}