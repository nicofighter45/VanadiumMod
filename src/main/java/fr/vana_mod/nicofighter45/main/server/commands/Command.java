package fr.vana_mod.nicofighter45.main.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.vana_mod.nicofighter45.main.gui.CustomCraftingScreenHandler;
import fr.vana_mod.nicofighter45.main.gui.CustomPlayerManagementScreenHandler;
import fr.vana_mod.nicofighter45.main.server.BossSpawner;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import fr.vana_mod.nicofighter45.main.server.ServerInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Command {

    private static final String LANG_COMMAND_PREFIX = "commands.vana-mod.";

    public static void registerAllCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setbase")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    if (player.getServerWorld() == Objects.requireNonNull(player.getServer()).getOverworld()) {
                        ServerInitializer.setPlayerBase(player, player.getBlockPos());
                        sendMsg(player, "setbase.done");
                    } else {
                        sendMsg(player, "setbase.error");
                    }
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("stat")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    CustomPlayer customPlayer = ServerInitializer.getCustomPlayer(player.getUuid());
                    sendMsg(player, "stat.title");
                    sendMsg(player, "stat.health", Integer.toString(customPlayer.getHeart()), false);
                    sendMsg(player, "stat.regen", Integer.toString(customPlayer.getRegen()), false);
                    sendMsg(player, "stat.baseX", Integer.toString(customPlayer.getBase().getX()), false);
                    sendMsg(player, "stat.baseY", Integer.toString(customPlayer.getBase().getY()), false);
                    sendMsg(player, "stat.baseZ", Integer.toString(customPlayer.getBase().getZ()), false);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("base")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    sendMsg(player, "base.pretp_msg");
                    new Timer().schedule(
                            new TimerTask() {

                                private int timer = 10;
                                private BlockPos lastPos = player.getBlockPos();
                                private float lastHealth = player.getHealth();

                                @Override
                                public void run() {
                                    timer--;
                                    if (player.getBlockPos() != lastPos || player.getHealth() < lastHealth) {
                                        sendMsg(player, "teleportation.cancel");
                                        cancel();
                                    }
                                    lastPos = player.getBlockPos();
                                    lastHealth = player.getHealth();
                                    if (timer == 0) {
                                        BlockPos base = ServerInitializer.getCustomPlayer(player.getUuid()).getBase();
                                        player.teleport(Objects.requireNonNull(player.getServer()).getOverworld(), base.getX(), base.getY(), base.getZ(), 180, 0);
                                        sendMsg(player, "base.tp_done");
                                        cancel();
                                    } else if (timer == 5 || timer < 4) {
                                        sendMsg(player, "teleportation.pre_msg", Integer.toString(timer));
                                    }
                                }
                            }
                            , 1000, 1000);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("spawn")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    sendMsg(player, "spawn.pretp_msg");
                    new Timer().schedule(
                            new TimerTask() {

                                private int timer = 10;
                                private BlockPos lastPos = player.getBlockPos();
                                private float lastHealth = player.getHealth();

                                @Override
                                public void run() {
                                    timer--;
                                    if (player.getBlockPos() != lastPos || player.getHealth() < lastHealth) {
                                        sendMsg(player, "teleportation.cancel");
                                        cancel();
                                    }
                                    lastPos = player.getBlockPos();
                                    lastHealth = player.getHealth();
                                    if (timer == 0) {
                                        BlockPos spawn = Objects.requireNonNull(player.getServer()).getOverworld().getSpawnPos();
                                        player.teleport(Objects.requireNonNull(player.getServer()).getOverworld(), spawn.getX(), spawn.getY(), spawn.getZ(), 180, 0);
                                        sendMsg(player, "spawn.tp_done");
                                        cancel();
                                    } else if (timer == 5 || timer < 4) {
                                        sendMsg(player, "teleportation.pre_msg", Integer.toString(timer));
                                    }
                                }
                            }
                            , 1000, 1000);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("broadcast")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendOpErrorMsg(c, "broadcast.error");
                    return 1;
                })
                .then(argument("message", MessageArgumentType.message()).executes(c -> {
                    String msg = MessageArgumentType.getMessage(c, "message").getString().replace("{value}",
                            MessageArgumentType.getMessage(c, "message").getString());
                    for (ServerPlayerEntity player : c.getSource().getServer().getPlayerManager().getPlayerList()) {
                        player.sendMessage(Text.literal(ServerInitializer.BROADCAST_MSG_PREFIX + msg));
                        player.sendMessage(Text.literal(ServerInitializer.BROADCAST_MSG_PREFIX + msg), true);
                    }
                    sendOpFeedbackMsg(c, "broadcast.done",
                            MessageArgumentType.getMessage(c, "message").getString());
                    return 1;
                }))
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("jump")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    ServerInitializer.jump = !ServerInitializer.jump;
                    sendOpFeedbackMsg(c, "jump.done", Boolean.toString(ServerInitializer.jump));
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("pvp")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    Objects.requireNonNull(c.getSource().getServer()).setPvpEnabled(!c.getSource().getServer().isPvpEnabled());
                    sendOpFeedbackMsg(c, "pvp.done", Boolean.toString(c.getSource().getServer().isPvpEnabled()));
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("freeze")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    sendOpErrorMsg(c, "freeze.error");
                    return 1;
                })
                .then(argument("targets", GameProfileArgumentType.gameProfile())
                        .executes(c -> {
                            Collection<GameProfile> targets = GameProfileArgumentType.getProfileArgument(c, "targets");
                            for (GameProfile gameProfile : targets) {
                                UUID uuid = gameProfile.getId();
                                ServerPlayerEntity player = c.getSource().getServer().getPlayerManager().getPlayer(uuid);
                                if(player == null){
                                    sendOpFeedbackMsg(c, "op.failed", gameProfile.getName(), false);
                                    continue;
                                }
                                if(ServerInitializer.frozenPlayer.contains(uuid)){
                                    sendMsg(player, "freeze.deactivate");
                                    ServerInitializer.frozenPlayer.remove(uuid);
                                    sendMsg(player, "freeze.deactivate");
                                    sendOpFeedbackMsg(c, "freeze.deactivate_op_msg", player.getEntityName());
                                }else {
                                    ServerInitializer.frozenPlayer.add(uuid);
                                    sendMsg(player, "freeze.activate");
                                    sendOpFeedbackMsg(c, "freeze.activate_op_msg", player.getEntityName());
                                }
                                ServerInitializer.sendInfoToClient(player);
                            }
                            return 1;
                        })
                )
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("op")
                        .requires(source -> source.hasPermissionLevel(3))
                        .then(argument("targets", GameProfileArgumentType.gameProfile())
                            .then(argument("level", IntegerArgumentType.integer(0, 4))
                                .executes(c -> {
                                    Collection<GameProfile> targets = GameProfileArgumentType.getProfileArgument(c, "targets");
                                    int level = IntegerArgumentType.getInteger(c, "level");
                                    PlayerManager playerManager = c.getSource().getServer().getPlayerManager();
                                    for (GameProfile gameProfile : targets) {
                                        ServerPlayerEntity serverPlayerEntity = playerManager.getPlayer(gameProfile.getId());
                                        if(serverPlayerEntity == null){
                                            sendOpFeedbackMsg(c, "op.failed", gameProfile.getName(), false);
                                            continue;
                                        }
                                        if (playerManager.isOperator(gameProfile)){
                                            playerManager.removeFromOperators(gameProfile);
                                        }
                                        playerManager.addToOperators(new ExtendedGameProfile(gameProfile, level));
                                        ServerInitializer.sendInfoToClient(gameProfile.getId(), c.getSource().getServer());
                                        sendOpFeedbackMsg(c, "op.feedback", gameProfile.getName(), String.valueOf(level));
                                        sendMsg(serverPlayerEntity, "op.update", String.valueOf(level));
                                    }
                                    return 1;
                                })
                            )
                        )
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("craft")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(c -> {
                    ServerPlayerEntity server_player = c.getSource().getPlayerOrThrow();
                    server_player.openHandledScreen(new NamedScreenHandlerFactory() {
                        @Override
                        public Text getDisplayName() {
                            return Blocks.CRAFTING_TABLE.getName();
                        }

                        @Override
                        public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                            return new CustomCraftingScreenHandler(syncId, inv, ScreenHandlerContext.create(player.getEntityWorld(), player.getBlockPos()));
                        }
                    });
                    server_player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ec")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(c -> {
                    ServerPlayerEntity server_player = c.getSource().getPlayerOrThrow();
                    server_player.openHandledScreen(new NamedScreenHandlerFactory() {
                        @Override
                        public Text getDisplayName() {
                            return Blocks.ENDER_CHEST.getName();
                        }

                        @Override
                        public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                            return GenericContainerScreenHandler.createGeneric9x3(syncId, inv, player.getEnderChestInventory());
                        }
                    });
                    server_player.incrementStat(Stats.OPEN_ENDERCHEST);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("hat")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    ItemStack hand = player.getMainHandStack();
                    if (hand.getItem() == Items.AIR) {
                        sendMsg(player, "hat.empty_hand_error");
                        return 1;
                    }
                    if (hand.getCount() != 1) {
                        sendMsg(player, "hat.more_than_1_item_error");
                        return 1;
                    }
                    ItemStack helmet = player.getInventory().getArmorStack(3);
                    player.getInventory().removeOne(hand);
                    if (helmet.getItem() != Items.AIR) {
                        player.getInventory().insertStack(helmet);
                    }
                    player.getInventory().removeStack(39);
                    player.getInventory().insertStack(39, hand);
                    sendMsg(player, "hat.done");
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("boss")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendOpErrorMsg(c, "boss.error");
                    return 1;
                })
                .then(argument("number", IntegerArgumentType.integer(1, 5))
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            BlockPos pos = player.getBlockPos();
                            int number = IntegerArgumentType.getInteger(c, "number");
                            for (ServerPlayerEntity pl : player.getServerWorld().getPlayers()) {
                                if (isNearTo(pos.getX(), pl.getX(), pos.getY(), pl.getY(), pos.getZ(), pl.getZ())) {
                                    pl.setVelocity(calc(pl.getX() - pos.getX()), 0.5, calc(pl.getZ() - pos.getZ()));
                                    //actualize velocity changes
                                    pl.damage(new DamageSource(RegistryEntry.of(new DamageType("", 0))), 0.5f);
                                    pl.heal(0.5f);
                                }
                            }
                            player.getServerWorld().spawnParticles(ParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 10000, 0, 0, 0, 1);
                            player.getServerWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                                    SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1f, 1f);
                            BossSpawner.spawnBoss(number, player.getServerWorld(), pos);
                            return 1;
                        })
                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("dim")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    sendOpErrorMsg(c, "dim.error");
                    return 1;
                })
                .then(literal("overworld")
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerWorld overworld = Objects.requireNonNull(player.getServer()).getOverworld();
                            if (overworld == player.getServerWorld()) {
                                sendMsg(player, "error_overworld");
                            } else {
                                player.teleport(overworld, overworld.getSpawnPos().getX(), overworld.getSpawnPos().getY(),
                                        overworld.getSpawnPos().getZ(), 0, 0);
                                sendMsg(player, "done_overworld");
                            }
                            return 1;
                        })
                )
                .then(literal("nether")
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerWorld nether = Objects.requireNonNull(player.getServer()).getWorld(World.NETHER);
                            assert nether != null;
                            if (nether == player.getServerWorld()) {
                                sendMsg(player, "error_nether");
                            } else {
                                player.teleport(nether, nether.getSpawnPos().getX(), nether.getSpawnPos().getY(),
                                        nether.getSpawnPos().getZ(), 0, 0);
                                sendMsg(player, "done_nether");
                            }
                            return 1;
                        })

                )
                .then(literal("end")
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerWorld end = Objects.requireNonNull(player.getServer()).getWorld(World.END);
                            assert end != null;
                            if (end == player.getServerWorld()) {
                                sendMsg(player, "error_end");
                            } else {
                                player.teleport(end, 0, 100, 0, 0, 0);
                                sendMsg(player, "done_end");
                            }
                            return 1;
                        })

                )
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("data")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendOpFeedbackMsg(c, "data.title", false);
                    for (UUID uuid : ServerInitializer.getPlayersUUID()) {
                        CustomPlayer cp = ServerInitializer.getCustomPlayer(uuid);
                        sendOpFeedbackMsg(c, "data.uuid", Objects.requireNonNull(c.getSource().getServer().getPlayerManager()
                                .getPlayer(uuid)).getEntityName(), false);
                        sendOpFeedbackMsg(c, "data.health", Integer.toString(cp.getHeart()), false);
                        sendOpFeedbackMsg(c, "data.regen", Integer.toString(cp.getRegen()), false);
                    }
                    return 1;
                })
                .then(literal("set")
                        .executes(c -> {
                            sendOpErrorMsg(c, "data.error");
                            return 1;
                        })
                        .then(argument("player", EntityArgumentType.player())
                                .executes(c -> {
                                    sendOpErrorMsg(c, "data.error");
                                    return 1;
                                })
                                .then(literal("health")
                                        .executes(c -> {
                                            sendOpErrorMsg(c, "data.error");
                                            return 1;
                                        })
                                        .then(argument("value", IntegerArgumentType.integer(0))
                                                .executes(c -> {
                                                    ServerPlayerEntity player = EntityArgumentType.getPlayer(c, "player");
                                                    UUID uuid = player.getUuid();
                                                    int value = IntegerArgumentType.getInteger(c, "value");
                                                    ServerInitializer.setPlayerHearts(player, value);
                                                    for (ServerPlayerEntity server_player : Objects.requireNonNull(c.getSource().getServer()).getPlayerManager().getPlayerList()) {
                                                        if (server_player.getUuid() == uuid) {
                                                            Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(value);
                                                            sendMsg(server_player, "data.health_change", Integer.toString(value / 2));
                                                        }
                                                    }
                                                    sendOpFeedbackMsg(c, "data.health_change_op", uuid.toString(), String.valueOf(value));
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("regen")
                                        .executes(c -> {
                                            sendOpErrorMsg(c, "data.error");
                                            return 1;
                                        })
                                        .then(argument("value", IntegerArgumentType.integer(0))
                                                .executes(c -> {
                                                    ServerPlayerEntity player = EntityArgumentType.getPlayer(c, "player");
                                                    int value = IntegerArgumentType.getInteger(c, "value");
                                                    ServerInitializer.setPlayerRegen(player, value);
                                                    sendMsg(player, "data.regen_change", Integer.toString(value / 2));
                                                    sendOpFeedbackMsg(c, "data.regen_change_op", player.getEntityName(), Integer.toString(value / 2));
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(literal("get")
                        .executes(c -> {
                            sendOpErrorMsg(c, "data.error");
                            return 1;
                        })
                        .then(argument("player", EntityArgumentType.player())
                                .executes(c -> {
                                    ServerPlayerEntity player = EntityArgumentType.getPlayer(c, "player");
                                    CustomPlayer cp = ServerInitializer.getCustomPlayer(player.getUuid());
                                    sendOpFeedbackMsg(c, "data.uuid", player.getEntityName(), false);
                                    sendOpFeedbackMsg(c, "data.health", Integer.toString(cp.getHeart()), false);
                                    sendOpFeedbackMsg(c, "data.regen", Integer.toString(cp.getRegen()), false);
                                    return 1;
                                })
                        )
                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("invsee")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    sendOpErrorMsg(c, "invsee.error");
                    return 1;
                })
                .then(argument("player", EntityArgumentType.player())
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerPlayerEntity player_to_see = EntityArgumentType.getPlayer(c, "player");
                            sendMsg(player, "invsee.done", player_to_see.getEntityName());
                            player.openHandledScreen(new NamedScreenHandlerFactory() {
                                @Override
                                public Text getDisplayName() {
                                    return Text.of(player_to_see.getEntityName());
                                }

                                @Override
                                public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                                    return new CustomPlayerManagementScreenHandler(syncId, player.getInventory(), player_to_see.getInventory());
                                }
                            });
                            return 1;
                        })
                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ecsee")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    sendOpErrorMsg(c, "ecsee.error");
                    return 1;
                })
                .then(argument("player", EntityArgumentType.player())
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerPlayerEntity player_to_see = EntityArgumentType.getPlayer(c, "player");
                            sendMsg(player, "ecsee.done", player_to_see.getEntityName());
                            player.openHandledScreen(new NamedScreenHandlerFactory() {
                                @Override
                                public Text getDisplayName() {
                                    return Text.literal(Text.translatable(LANG_COMMAND_PREFIX + "ecsse.name").toString().replace("{value}", player_to_see.getEntityName()));
                                }

                                @Override
                                public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                                    return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, inv, player_to_see.getEnderChestInventory(), 3);
                                }
                            });
                            return 1;
                        })
                )
        ));
    }

    private static double calc(double nb) {
        if (nb < 0) {
            return -5 / Math.exp(0.3 * -nb);
        } else if (nb > 0) {
            return 5 / Math.exp(0.3 * nb);
        } else {
            return 5;
        }
    }

    private static boolean isNearTo(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2)) <= 10;
    }

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text) {
        player.sendMessage(getMsgFrom(text), false);
    }

    @Contract(value = "_ -> new", pure = true)
    private static @NotNull Text getMsgFrom(String text) {
        return Text.translatable(LANG_COMMAND_PREFIX + text);
    }

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text, String replace) {
        sendMsg(player, text, replace, true);
    }

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text, String replace, boolean prefix) {
        if (prefix) {
            player.sendMessage(Text.of(Text.translatable(LANG_COMMAND_PREFIX + text).getString().replace("{value}", replace))
                    , false);
        } else {
            player.sendMessage(Text.of(Text.translatable(LANG_COMMAND_PREFIX + text).getString().replace("{value}", replace))
                    , false);
        }
    }

    private static void sendOpFeedbackMsg(@NotNull CommandContext<ServerCommandSource> c, String text, boolean broadcast) {
        c.getSource().sendFeedback(() -> Text.translatable(LANG_COMMAND_PREFIX + text), broadcast);
    }

    private static void sendOpFeedbackMsg(@NotNull CommandContext<ServerCommandSource> c, String text, String replace) {
        sendOpFeedbackMsg(c, text, replace, true);
    }

    private static void sendOpFeedbackMsg(@NotNull CommandContext<ServerCommandSource> c, String text, String replace1, String replace2) {
        c.getSource().sendFeedback(() -> Text.literal(Text.translatable(LANG_COMMAND_PREFIX + text)
                .getString().replace("{value1}", replace1).replace("{value2}", replace2)), true);
    }

    private static void sendOpFeedbackMsg(@NotNull CommandContext<ServerCommandSource> c, String text, String replace, boolean broadcast) {
        c.getSource().sendFeedback(() -> Text.literal(Text.translatable(LANG_COMMAND_PREFIX + text)
                .getString().replace("{value}", replace)), broadcast);
    }

    private static void sendOpErrorMsg(@NotNull CommandContext<ServerCommandSource> c, String text) {
        c.getSource().sendError(Text.literal(LANG_COMMAND_PREFIX + text));
    }

}