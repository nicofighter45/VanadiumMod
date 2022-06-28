package fr.vana_mod.nicofighter45.main.server;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import fr.vana_mod.nicofighter45.gui.CustomCraftingScreenHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
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
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setbase")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    if(player.getWorld() == Objects.requireNonNull(player.getServer()).getOverworld()){
                        VanadiumModServer.players.get(player.getUuid()).setBase(player.getBlockPos());
                        sendMsg(player, "§8[§6Server§8] §fYour base has been reposition to your position");
                    }else{
                        sendMsg(player, "§8[§6Server§8] §fYou need to be in the overworld");
                    }
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("stat")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    CustomPlayer customPlayer = VanadiumModServer.players.get(player.getUuid());
                    sendMsg(player, "§8[§6Server§8] §fHere is your stats :");
                    sendMsg(player, "    health  : " + customPlayer.getHeart());
                    sendMsg(player, "    regen  : " + customPlayer.getRegen());
                    sendMsg(player, "    craft   : " + customPlayer.isCraft());
                    sendMsg(player, "    ec      : " + customPlayer.isEnder_chest());
                    sendMsg(player, "    baseX   : " + customPlayer.getBase().getX());
                    sendMsg(player, "    baseY   : " + customPlayer.getBase().getY());
                    sendMsg(player, "    baseZ   : " + customPlayer.getBase().getZ());
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("base")
                .executes(c -> {
                    ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                    sendMsg(player, "§8[§6Server§8] §fYou will be teleported to your base in 10s against 1 emerald block");
                    new Timer().schedule(
                            new TimerTask() {

                                private int timer = 10;
                                private BlockPos lastPos = player.getBlockPos();
                                private float lastHealth = player.getHealth();

                                @Override
                                public void run() {
                                    timer --;
                                    if(player.getBlockPos() != lastPos || player.getHealth() < lastHealth){
                                        sendMsg(player, "§8[§6Server§8] §fTeleportation cancel : you moved or loosed health");
                                        cancel();
                                    }
                                    lastPos = player.getBlockPos();
                                    lastHealth = player.getHealth();
                                    if(timer == 0){
                                        EnderChestInventory inventory = player.getEnderChestInventory();
                                        if(inventory.removeItem(Items.EMERALD_BLOCK, 1).getCount() == 1){
                                            BlockPos base = VanadiumModServer.players.get(player.getUuid()).getBase();
                                            player.teleport(Objects.requireNonNull(player.getServer()).getOverworld(), base.getX(), base.getY(), base.getZ(), 180,0);
                                            sendMsg(player, "§8[§6Server§8] §fYou have been teleported to your base");
                                        }else{
                                            sendMsg(player, "§8[§6Server§8] §fYou don't have any emerald block in your ender chest");
                                        }
                                        cancel();
                                    }else if(timer == 5){
                                        sendMsg(player, "§8[§6Server§8] §fTeleporting in 5s");
                                    }else if(timer < 4){
                                        sendMsg(player, "§8[§6Server§8] §fTeleporting in " + timer + "s");
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
                    sendMsg(player, "§8[§6Server§8] §fYou will be teleported to the spawn in 10s");
                    new Timer().schedule(
                            new TimerTask() {

                                private int timer = 10;
                                private BlockPos lastPos = player.getBlockPos();
                                private float lastHealth = player.getHealth();

                                @Override
                                public void run() {
                                    timer --;
                                    if(player.getBlockPos() != lastPos || player.getHealth() < lastHealth){
                                        sendMsg(player, "§8[§6Server§8] §fTeleportation cancel : you moved or loosed health");
                                        cancel();
                                    }
                                    lastPos = player.getBlockPos();
                                    lastHealth = player.getHealth();
                                    if(timer == 0){
                                        BlockPos spawn = Objects.requireNonNull(player.getServer()).getOverworld().getSpawnPos();
                                        player.teleport(Objects.requireNonNull(player.getServer()).getOverworld(), spawn.getX(), spawn.getY(), spawn.getZ(), 180,0);
                                        sendMsg(player, "§8[§6Server§8] §fYou have been teleported to the spawn");
                                        cancel();
                                    }else if(timer == 5){
                                        sendMsg(player, "§8[§6Server§8] §fTeleporting in 5s");
                                    }else if(timer < 4){
                                        sendMsg(player, "§8[§6Server§8] §fTeleporting in " + timer + "s");
                                    }
                                }
                            }
                    , 1000, 1000);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("broadcast")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /broadcast <message>"));
                    return 1;
                })
                .then(argument("message", MessageArgumentType.message()).executes(c -> {
                    String msg = MessageArgumentType.getMessage(c, "message").getString();
                    for(ServerPlayerEntity player : c.getSource().getServer().getPlayerManager().getPlayerList()){
                        sendMsg(player, "§8[§4BROADCAST§8] §f" + msg);
                        player.sendMessage(MutableText.of(new TranslatableTextContent(msg)), true);
                    }
                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §fMessage was broadcast " + msg), true);
                    return 1;
                }))
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("jump")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    VanadiumModServer.jump = !VanadiumModServer.jump;
                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §fJump has been set top " + VanadiumModServer.jump), true);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("pvp")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    Objects.requireNonNull(c.getSource().getServer()).setPvpEnabled(!c.getSource().getServer().isPvpEnabled());
                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §fPvp is now " + c.getSource().getServer().isPvpEnabled()), true);
                    return 1;
                })
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("freeze")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /freeze <player>"));
                    return 1;
                })
                .then(argument("player", EntityArgumentType.player())
                        .executes(c -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(c, "player");
                            if(player.hasStatusEffect(StatusEffects.BLINDNESS) && player.hasStatusEffect(StatusEffects.SLOWNESS)){
                                player.removeStatusEffect(StatusEffects.BLINDNESS);
                                player.removeStatusEffect(StatusEffects.SLOWNESS);
                                player.sendMessage(Text.of("§8[§6Server§8] §fYour are now un-freeze"));
                                c.getSource().sendFeedback(Text.of("§8[§6Server§8] §f" + player.getEntityName() + " is un-freeze"), true);
                            }else{
                                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 26, false, false, false));
                                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, Integer.MAX_VALUE, 26, false, false, false));
                                player.sendMessage(Text.of("§8[§6Server§8] §fYour are now freeze"));
                                c.getSource().sendFeedback(Text.of("§8[§6Server§8] §f" + player.getEntityName() + " is freeze"), true);
                            }
                            return 1;
                        })
                )
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("craft")
                .executes(c -> {
                    ServerPlayerEntity server_player = c.getSource().getPlayerOrThrow();
                    if(VanadiumModServer.players.get(server_player.getUuid()).isCraft()){
                        server_player.openHandledScreen(new NamedScreenHandlerFactory() {
                            @Override
                            public Text getDisplayName() {
                                return Text.of("Crafting Table");
                            }
                            @Override
                            public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                                return new CustomCraftingScreenHandler(syncId, inv, ScreenHandlerContext.create(player.getEntityWorld(), player.getBlockPos()));
                            }
                        });
                        server_player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                    }else{
                        sendMsg(server_player, "§8[§6Server§8] §fYou haven't unlock this feature");
                    }
                    return 1;
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
                        player.incrementStat(Stats.OPEN_ENDERCHEST);
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
                c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /boss <int> <x> <y> <z>"));
                return 1;
            })
            .then(argument("number", IntegerArgumentType.integer(1, 5))
                    .executes(c -> {
                        c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /boss <int> <x> <y> <z>"));
                        return 1;
                    })
                    .then(argument("pos", BlockPosArgumentType.blockPos())
                            .executes(c -> {
                                ServerPlayerEntity player = c.getSource().getPlayerOrThrow();
                                BlockPos pos = BlockPosArgumentType.getBlockPos(c, "pos");
                                int number = IntegerArgumentType.getInteger(c, "number");
                                for(ServerPlayerEntity pl : player.getWorld().getPlayers()){
                                    if(isNearTo(pos.getX(), pl.getX(), pos.getY(), pl.getY(), pos.getZ(), pl.getZ())){
                                        pl.setVelocity(calc(pl.getX()-pos.getX()), 0.5, calc(pl.getZ()-pos.getZ()));
                                        //actualize velocity changes
                                        pl.damage(DamageSource.MAGIC, 0.5f);
                                        pl.heal(0.5f);
                                    }
                                }
                                player.getWorld().spawnParticles(ParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 10000, 0, 0, 0, 1);
                                player.getWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                                        SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1f, 1f);
                                BossSpawner.spawnBoss(number, player.getWorld(), pos);
                                return 1;
                            })
                    )
            )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("dim")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /dim <overworld|nether|end>"));
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
                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §fThis is the list of all register players with their data :"), false);
                    for (UUID uuid : VanadiumModServer.players.keySet()){
                        c.getSource().sendFeedback(Text.of("    " + uuid + " :"), false);
                        CustomPlayer cp = VanadiumModServer.players.get(uuid);
                        c.getSource().sendFeedback(Text.of("        health : " + cp.getHeart()), false);
                        c.getSource().sendFeedback(Text.of("        regen  : " + cp.getRegen()), false);
                        c.getSource().sendFeedback(Text.of("        craft  : " + cp.isCraft()), false);
                        c.getSource().sendFeedback(Text.of("        ec     : " + cp.isEnder_chest()), false);
                    }
                    c.getSource().sendFeedback(Text.of("\nCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"), false);
                    return 1;
                })
                .then(literal("set")
                        .executes(c -> {
                            c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                            return 1;
                        })
                        .then(argument("player", EntityArgumentType.player())
                                .executes(c -> {
                                    c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                    return 1;
                                })
                                .then(literal("health")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", IntegerArgumentType.integer(0))
                                                .executes(c -> {
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    int value = IntegerArgumentType.getInteger(c, "value");
                                                    VanadiumModServer.players.get(uuid).setHeart(value);
                                                    for(ServerPlayerEntity server_player : Objects.requireNonNull(c.getSource().getServer()).getPlayerManager().getPlayerList()){
                                                        if(server_player.getUuid() == uuid){
                                                            Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(value);
                                                            sendMsg(server_player, "§8[§6Server§8] §fYou now have " + value/2 + " heart(s)");
                                                        }
                                                    }
                                                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §fThe number of heart of " + uuid + " is now " + value), true);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("regen")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", IntegerArgumentType.integer(0))
                                                .executes(c -> {
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    int value = IntegerArgumentType.getInteger(c, "value");
                                                    VanadiumModServer.players.get(uuid).setRegen(value);
                                                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §fThe number of regen heart of " + uuid + " is now " + value), false);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("craft")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", BoolArgumentType.bool())
                                                .executes(c -> {
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    boolean value = BoolArgumentType.getBool(c, "value");
                                                    VanadiumModServer.players.get(uuid).setCraft(value);
                                                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §f" + uuid + " crafting table feature is now set to " + value), false);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(literal("ender_chest")
                                        .executes(c -> {
                                            c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                                            return 1;
                                        })
                                        .then(argument("value", BoolArgumentType.bool())
                                                .executes(c -> {
                                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                                    boolean value = BoolArgumentType.getBool(c, "value");
                                                    VanadiumModServer.players.get(uuid).setEnder_chest(value);
                                                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §f" + uuid + " crafting table feature is now set to " + value), true);
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(literal("get")
                        .executes(c -> {
                            c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /data <set|get> <player> <health|regen|craft|ender_chest> <value>"));
                            return 1;
                        })
                        .then(argument("player", EntityArgumentType.player())
                                .executes(c -> {
                                    UUID uuid = EntityArgumentType.getPlayer(c, "player").getUuid();
                                    CustomPlayer cp = VanadiumModServer.players.get(uuid);
                                    c.getSource().sendFeedback(Text.of("§8[§6Server§8] §f" + uuid + " :"), false);
                                    c.getSource().sendFeedback(Text.of("   health : " + cp.getHeart()), false);
                                    c.getSource().sendFeedback(Text.of("   regen  : " + cp.getRegen()), false);
                                    c.getSource().sendFeedback(Text.of("   craft  : " + cp.isCraft()), false);
                                    c.getSource().sendFeedback(Text.of("   ec     : " + cp.isEnder_chest()), false);
                                    return 1;
                                })
                        )
                )
        ));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("invsee")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(c -> {
                    c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /invsee <player>"));
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
                    c.getSource().sendError(Text.of("§8[§6Server§8] §fCorrect usage /ecsee <player>"));
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

    private static double calc(double nb){
        if(nb < 0){
            return -5/Math.exp(0.3 * -nb);
        }else if(nb > 0){
            return 5/Math.exp(0.3 * nb);
        }else{
            return 5;
        }
    }

    private static boolean isNearTo(double x1, double x2, double y1, double y2, double z1, double z2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2)) <= 10; }

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text){
        player.sendMessage(MutableText.of(new TranslatableTextContent(text)), false);
    }

}