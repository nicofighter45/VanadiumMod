package fr.vana_mod.nicofighter45.main;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import fr.vana_mod.nicofighter45.bosses.CustomBossConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Command {

    public static void registerAllCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("stat")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    CustomPlayer customPlayer = VanadiumModServer.players.get(player.getUuid());
                    sendMsg(player, "Here is your stats :");
                    sendMsg(player, "    health  : " + customPlayer.getHeart());
                    sendMsg(player, "    regen  : " + customPlayer.getRegen());
                    sendMsg(player, "    craft   : " + customPlayer.isCraft());
                    sendMsg(player, "    ec      : " + customPlayer.isEnder_chest());
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("spawn")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    sendMsg(player, "§8[§6Server§8] §fYou will be teleported to the spawn in 10s");
                    new Timer().schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    BlockPos spawn = Objects.requireNonNull(player.getServer()).getOverworld().getSpawnPos();
                                    player.teleport(Objects.requireNonNull(player.getServer()).getOverworld(), spawn.getX(), spawn.getY(), spawn.getZ(), 180,0);
                                    sendMsg(player, "§8[§6Server§8] §fYou have been teleported to the spawn");
                                    cancel();
                                }
                            }
                    , 10000, 1);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("broadcast")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("Correct usage /broadcast <message>"));
                    return 1;
                })
                .then(argument("message", MessageArgumentType.message()).executes(c -> {
                    String msg = MessageArgumentType.getMessage(c, "message").getString();
                    for(ServerPlayerEntity player : c.getSource().getServer().getPlayerManager().getPlayerList()){
                        sendMsg(player, "§8[§4BROADCAST§8] §f" + msg);
                        player.sendMessage(MutableText.of(new TranslatableTextContent(msg)), true);
                    }
                    return 1;
                }))
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("pvp")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    ServerPlayerEntity server_player = c.getSource().getPlayerOrThrow();
                    Objects.requireNonNull(server_player.getServer()).setPvpEnabled(!server_player.getServer().isPvpEnabled());
                    sendMsg(server_player, "§8[§6Server§8] §fPvp is now " + server_player.getServer().isPvpEnabled());
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("craft")
                .executes(c -> {
                    ServerPlayerEntity server_player = c.getSource().getPlayerOrThrow();
                    server_player.sendMessage(Text.of("§8[§6Server§8] §fThis command is still WIP"));
                    return 1;
//                    if(VanadiumModServer.players.get(server_player.getUuid()).isCraft()){
//                        server_player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player)
//                                -> new CraftingScreenHandler(syncId, inventory, ScreenHandlerContext.create(player.getWorld(),
//                                player.getBlockPos())),Text.translatable("container.crafting")));
//                        server_player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
//                    }else{
//                        sendMsg(server_player, "You haven't unlock this feature");
//                    }
//                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ec")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    if(VanadiumModServer.players.get(player.getUuid()).isEnder_chest()){
                        player.openHandledScreen(new NamedScreenHandlerFactory() {
                            @Override
                            public Text getDisplayName() {
                                return Text.of("Ender Chest");
                            }

                            @Override
                            public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                                return GenericContainerScreenHandler.createGeneric9x3(syncId, inv, player.getEnderChestInventory());
                            }
                        });
                    }else{
                        sendMsg(player, "§8[§6Server§8] §fYou haven't unlock this feature");
                    }
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("hat")
            .executes(c -> {
                ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                ItemStack hand = player.getMainHandStack();
                if(hand.getItem() == Items.AIR){
                    ZombieEntity zb = EntityType.ZOMBIE.create(player.getWorld());
                    assert zb != null;
                    zb.refreshPositionAndAngles(player.getBlockPos(), 0, 0);
                    player.getWorld().spawnEntity(zb);
                    sendMsg(player, "§8[§6Server§8] §fYou have nothing in your hand");
                    return 1;
                }
                if(hand.getCount() != 1){
                    sendMsg(player, "§8[§6Server§8] §fYou can't put many items in your head");
                    return 1;
                }
                ItemStack helmet = player.getInventory().armor.get(3);
                player.getInventory().removeOne(hand);
                if(helmet.getItem() != Items.AIR){
                    player.getInventory().insertStack(helmet);
                }
                player.getInventory().insertStack(39, hand);
                sendMsg(player, "§8[§6Server§8] §fYour hand and your helmet have been exchange");
                return 1;
            })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("boss")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(c -> {
                ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                sendMsg(player, "§8[§6Server§8] §fCorrect usage /boss remove|set");
                sendMsg(player,"Here is the list of all bosses and there locations :");
                for(int key : VanadiumModServer.bosses.keySet()){
                    sendMsg(player, key + " : " + VanadiumModServer.bosses.get(key).getX() + ";" +
                            VanadiumModServer.bosses.get(key).getY() + ";" +  VanadiumModServer.bosses.get(key).getZ());
                }
                return 1;
            })
            .then(literal("remove")
                .executes(c -> {
                    c.getSource().sendError(Text.of("Correct usage /boss remove <key>"));
                    return 1;
                })
                    .then(argument("key", IntegerArgumentType.integer(1,5))
                            .executes(c -> {
                                ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                int key = IntegerArgumentType.getInteger(c,"key");
                                if(VanadiumModServer.bosses.containsKey(key)){
                                    VanadiumModServer.bosses.remove(key);
                                    sendMsg(player, "§8[§6Server§8] §fBoss with key " + key + " has been delete");
                                }else{
                                    sendMsg(player, "§8[§6Server§8] §fThis boss isn't register");
                                }
                                return 1;
                            })
                    )
            )
            .then(literal("set")
                .executes(c -> {
                    c.getSource().sendError(Text.of("Correct usage /boss set <key> (<x>) (<y>) (<z>)"));
                    return 1;
                })

                    .then(argument("key", IntegerArgumentType.integer(1,5))
                            .executes(c -> {
                                ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                int key = IntegerArgumentType.getInteger(c,"key");
                                VanadiumModServer.bosses.remove(key);
                                VanadiumModServer.bosses.put(key, new CustomBossConfig(key, player.getX(), player.getY(), player.getZ()));
                                sendMsg(player, "§8[§6Server§8] §fBoss with key " + key + " set on your location");
                                return 1;
                            })
                            .then(argument("x", DoubleArgumentType.doubleArg())
                                    .executes(c -> {
                                        c.getSource().sendError(Text.of("Correct usage /boss set <key> (<x>) (<y>) (<z>)"));
                                        return 1;
                                    })
                                    .then(argument("y", DoubleArgumentType.doubleArg())
                                            .executes(c -> {
                                                c.getSource().sendError(Text.of("Correct usage /boss set <key> (<x>) (<y>) (<z>)"));
                                                return 1;
                                            })
                                            .then(argument("z", DoubleArgumentType.doubleArg())
                                                    .executes(c -> {
                                                        ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                                        int key = IntegerArgumentType.getInteger(c,"key");
                                                        double x = DoubleArgumentType.getDouble(c, "x");
                                                        double y = DoubleArgumentType.getDouble(c, "y");
                                                        double z = DoubleArgumentType.getDouble(c, "z");
                                                        VanadiumModServer.bosses.remove(key);
                                                        VanadiumModServer.bosses.put(key, new CustomBossConfig(key, x, y, z));
                                                        sendMsg(player, "§8[§6Server§8] §fBoss with key " + key + " set on " + x + ";" + y + ";" + z);
                                                        return 1;
                                                    }
                                                )
                                        )
                                )
                        )
                )
            )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("dim")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("Correct usage /dim <overworld|nether|end>"));
                    return 1;
                })
                .then(literal("overworld")
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerWorld overworld = Objects.requireNonNull(player.getServer()).getOverworld();
                            if(overworld == player.getWorld()) {
                                sendMsg(player, "§8[§6Server§8] §fYou are already in the overworld");
                            }else{
                                player.teleport(overworld, overworld.getSpawnPos().getX(), overworld.getSpawnPos().getY(),
                                        overworld.getSpawnPos().getZ(), 0, 0);
                                sendMsg(player, "§8[§6Server§8] §fYou are now in the overworld");
                            }
                            return 1;
                        })
                )
                .then(literal("nether")
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerWorld nether = Objects.requireNonNull(player.getServer()).getWorld(World.NETHER);
                            assert nether != null;
                            if(nether == player.getWorld()) {
                                sendMsg(player, "§8[§6Server§8] §fYou are already in the nether");
                            }else{
                                player.teleport(nether, nether.getSpawnPos().getX(), nether.getSpawnPos().getY(),
                                        nether.getSpawnPos().getZ(), 0, 0);
                                sendMsg(player, "§8[§6Server§8] §fYou are now in the nether");
                            }
                            return 1;
                        })

                )
                .then(literal("end")
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerWorld end = Objects.requireNonNull(player.getServer()).getWorld(World.END);
                            assert end != null;
                            if(end == player.getWorld()) {
                                sendMsg(player, "§8[§6Server§8] §fYou are already in the end");
                            }else{
                                player.teleport(end, 0, 100, 0, 0, 0);
                                sendMsg(player, "§8[§6Server§8] §fYou are now in the end");
                            }
                            return 1;
                        })

                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("data")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    sendMsg(player, "§8[§6Server§8] §fThis is the list of all register players with their data :");
                    for (UUID uuid : VanadiumModServer.players.keySet()){
                        sendMsg(player, "    " + uuid + " :");
                        CustomPlayer cp = VanadiumModServer.players.get(uuid);
                        sendMsg(player, "        health : " + cp.getHeart());
                        sendMsg(player, "        regen  : " + cp.getRegen());
                        sendMsg(player, "        craft  : " + cp.isCraft());
                        sendMsg(player, "        ec     : " + cp.isEnder_chest());
                    }
                    sendMsg(player, "\nCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>");
                    return 1;
                })
                .then(literal("set")
                        .executes(c -> {
                            c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                            return 1;
                        })
                        .then(argument("player", EntityArgumentType.player())
                                .executes(c -> {
                                    c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                    return 1;
                                })
                                .then(literal("health")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", IntegerArgumentType.integer(0))
                                                .executes(c -> {
                                                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    int value = IntegerArgumentType.getInteger(c, "value");
                                                    VanadiumModServer.players.get(uuid).setHeart(value);
                                                    for(ServerPlayerEntity server_player : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()){
                                                        if(server_player.getUuid() == uuid){
                                                            Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(value);
                                                            sendMsg(server_player, "§8[§6Server§8] §fYou now have " + value/2 + " heart(s)");
                                                        }
                                                    }
                                                    sendMsg(player, "§8[§6Server§8] §fThe number of heart of " + uuid + " is now " + value);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("regen")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", IntegerArgumentType.integer(0))
                                                .executes(c -> {
                                                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    int value = IntegerArgumentType.getInteger(c, "value");
                                                    VanadiumModServer.players.get(uuid).setRegen(value);
                                                    sendMsg(player, "§8[§6Server§8] §fThe number of regen heart of " + uuid + " is now " + value);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("craft")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", BoolArgumentType.bool())
                                                .executes(c -> {
                                                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    boolean value = BoolArgumentType.getBool(c, "value");
                                                    VanadiumModServer.players.get(uuid).setCraft(value);
                                                    sendMsg(player, "§8[§6Server§8] §f" + uuid + " crafting table feature is now set to " + value);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("ender_chest")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", BoolArgumentType.bool())
                                                .executes(c -> {
                                                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    boolean value = BoolArgumentType.getBool(c, "value");
                                                    VanadiumModServer.players.get(uuid).setEnder_chest(value);
                                                    sendMsg(player, "§8[§6Server§8] §f" + uuid + " crafting table feature is now set to " + value);
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(literal("get")
                        .executes(c -> {
                            c.getSource().sendError(Text.of("Correct usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                            return 1;
                        })
                        .then(argument("player", EntityArgumentType.player())
                                .executes(c -> {
                                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                    CustomPlayer cp = VanadiumModServer.players.get(uuid);
                                    sendMsg(player, "§8[§6Server§8] §f" + uuid + " :");
                                    sendMsg(player, "   health : " + cp.getHeart());
                                    sendMsg(player, "   regen  : " + cp.getRegen());
                                    sendMsg(player, "   craft  : " + cp.isCraft());
                                    sendMsg(player, "   ec     : " + cp.isEnder_chest());
                                    return 1;
                                })
                        )
                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("invsee")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("\nCorrect usage /invsee <player>"));
                    return 1;
                })
                .then(argument("player", EntityArgumentType.player())
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerPlayerEntity player_to_see = EntityArgumentType.getPlayer(c, "player");
                            sendMsg(player, "§8[§6Server§8] §fPrinting inventory of " + player_to_see.getEntityName());
                            player.openHandledScreen(new NamedScreenHandlerFactory() {
                                @Override
                                public Text getDisplayName() {
                                    return Text.of("Inventory of " + player_to_see.getEntityName());
                                }

                                @Override
                                public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                                    return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, inv, player_to_see.getInventory(), 4);
                                }
                            });
                            return 1;
                        })
                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ecsee")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("Correct usage /ecsee <player>"));
                    return 1;
                })
                .then(argument("player", EntityArgumentType.player())
                        .executes(c -> {
                            ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                            ServerPlayerEntity player_to_see = EntityArgumentType.getPlayer(c, "player");
                            sendMsg(player, "§8[§6Server§8] §fPrinting inventory of " + player_to_see.getEntityName());
                            player.openHandledScreen(new NamedScreenHandlerFactory() {
                                @Override
                                public Text getDisplayName() {
                                    return Text.of("Ender chest of " + player_to_see.getEntityName());
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

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text){
        player.sendMessage(MutableText.of(new TranslatableTextContent(text)), false);
    }

}