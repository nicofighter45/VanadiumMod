package fr.vana_mod.nicofighter45.main;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import fr.vana_mod.nicofighter45.items.custom.*;
import fr.vana_mod.nicofighter45.main.server.CustomPlayer;
import fr.vana_mod.nicofighter45.main.server.VanadiumModServer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import fr.vana_mod.nicofighter45.items.ModItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Listeners {

    private static final Map<BlockPos, List<BlockPos>> map = new HashMap<>();

    private static void onAttackBlockRegister(){
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if(!world.isClient){
                if(map.containsKey(pos)){
                    List<BlockPos> list = map.get(pos);
                    ItemStack stack = player.getMainHandStack();
                    for(BlockPos pos_final : list){
                        if(world.getBlockState(pos_final).getBlock() == state.getBlock() ||
                                (world.getBlockState(pos_final).getBlock() == Blocks.GRASS_BLOCK && state.getBlock() == Blocks.DIRT) ||
                                (world.getBlockState(pos_final).getBlock() == Blocks.DIRT && state.getBlock() == Blocks.GRASS_BLOCK)){
                            if(stack.getDamage() > 0){
                                ((ServerPlayerEntity) player).interactionManager.tryBreakBlock(pos_final);
                                Block.dropStacks(world.getBlockState(pos_final), world, pos_final, null, player, stack);
                                world.breakBlock(pos_final, false);
                                ((ServerPlayerEntity) player).networkHandler.sendPacket(new BlockUpdateS2CPacket(pos_final, world.getBlockState(pos_final)));
                                stack.damage(1, Random.create(), (ServerPlayerEntity) player);
                            }
                        }
                    }
                    map.remove(pos);
                }
            }
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->{
            if(!world.isClient){
                ItemStack stack = player.getMainHandStack();
                BlockState state = world.getBlockState(pos);
                if(stack.getItem() instanceof Hammer){
                    if(stack.getItem().isSuitableFor(state) && ((Hammer) stack.getItem()).isActive()){
                        int mining_range = ((Hammer) stack.getItem()).getMiningRange();
                        Direction looking = player.getMovementDirection();
                        if(mining_range >= 3 && (direction == Direction.UP || direction == Direction.DOWN)){
                            registerBlocks(pos, pos.add(0, 0, 1), pos.add(0,0,-1), pos.add(1,0,0), pos.add(-1,0,0));
                            if(mining_range >= 4){
                                registerBlocks(pos, pos.add(1, 0, 1), pos.add(1,0,-1), pos.add(-1,0,1), pos.add(-1,0,-1));
                            }
                            return ActionResult.PASS;
                        }
                        if(mining_range >= 1){
                            copperAndTinHammer(1, direction, looking, pos);
                        }
                        if(mining_range >= 2){
                            copperAndTinHammer(-1, direction, looking, pos);
                        }
                        if(mining_range >= 3){
                            if(direction == Direction.NORTH || direction == Direction.SOUTH){
                                registerBlocks(pos, pos.add(1, 0, 0), pos.add(-1,0,0));
                            }else {
                                registerBlocks(pos, pos.add(0, 0, 1), pos.add(0,0,-1));
                            }
                        }
                        if(mining_range >= 4){
                            if(direction == Direction.NORTH || direction == Direction.SOUTH){
                                registerBlocks(pos, pos.add(1, 1, 0), pos.add(-1,1,0), pos.add(1,-1,0), pos.add(-1,-1,0));
                            }else if (direction == Direction.EAST || direction == Direction.WEST){
                                registerBlocks(pos, pos.add(0, 1, 1), pos.add(0,1,-1), pos.add(0,-1,1), pos.add(0,-1,-1));
                            }
                        }
                    }
                }else if(stack.getItem() instanceof Excavator){
                    if(stack.getItem().isSuitableFor(state) && ((Excavator) stack.getItem()).isActive()){
                        if(direction == Direction.UP || direction == Direction.DOWN){
                            registerBlocks(pos, pos.add(1, 0, 1), pos.add(1,0,-1), pos.add(-1,0,1), pos.add(-1,0,-1), pos.add(0, 0, 1), pos.add(0,0,-1), pos.add(1,0,0), pos.add(-1,0,0));
                        }else if(direction == Direction.NORTH || direction == Direction.SOUTH){
                            registerBlocks(pos, pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(1, 0, 0), pos.add(-1,0,0), pos.add(1,1,0), pos.add(-1,1,0), pos.add(1, -1, 0), pos.add(-1,-1,0));
                        }else if (direction == Direction.EAST || direction == Direction.WEST){
                            registerBlocks(pos, pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0,0,-1), pos.add(0,1,1), pos.add(0,1,-1), pos.add(0, -1, 1), pos.add(0,-1,-1));
                        }
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    private static void registerBlocks(BlockPos @NotNull ... blockPos) {
        int i = 0;
        BlockPos first = null;
        List<BlockPos> list = new ArrayList<>();
        for(BlockPos pos : blockPos){
            if(i == 0){
                first = pos;
            }else{
                list.add(pos);
            }
            i++;
        }
        if(first != null && map.containsKey(first)){
            list.addAll(map.get(first));
            map.remove(first);
        }
        map.put(first, list);
    }

    private static void copperAndTinHammer(int neg, Direction direction, Direction looking, BlockPos pos){
        if(direction == Direction.UP || direction == Direction.DOWN) {
            if(looking == Direction.NORTH){
                registerBlocks(pos, pos.add(0, 0, -neg));
            }else if(looking == Direction.WEST){
                registerBlocks(pos, pos.add(-neg, 0, 0));
            }else if(looking == Direction.EAST){
                registerBlocks(pos, pos.add(neg, 0, 0));
            }else if(looking == Direction.SOUTH){
                registerBlocks(pos, pos.add(0, 0, neg));
            }
        }else{
            registerBlocks(pos, pos.add(0, -neg, 0));
        }
    }

    private static void onItemRightClickRegister(){
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if(!world.isClient){
                ServerPlayerEntity server_player = (ServerPlayerEntity) player;
                ItemStack it = server_player.getMainHandStack();
                Item item_server = it.getItem();
                CustomPlayer data_player = VanadiumModServer.players.get(server_player.getUuid());
                int heart = data_player.getHeart();
                int regen = data_player.getRegen();
                if (item_server == ModItems.SIMPLE_HEALTH_BOOSTER && heart < 20) {
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "§8[§6Server§8] §fYou got " + (heart + 2)/2 + " heart");
                } else if (item_server == ModItems.BASE_HEALTH_BOOSTER && heart >= 20 && heart < 30) {
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "§8[§6Server§8] §fYou got " + (heart + 2)/2 + " heart");
                } else if (item_server == ModItems.ADVANCE_HEALTH_BOOSTER && heart >= 30 && heart < 40) {
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "§8[§6Server§8] §fYou got " + (heart + 2)/2 + " heart");
                } else if (item_server == ModItems.SIMPLE_REGEN_BOOSTER && regen < 8 && (regen + 2) <= heart) {
                    data_player.setRegen(regen + 2);
                    sendMsg(server_player, "§8[§6Server§8] §fYou got " + (regen + 2)/2 + " regen");
                } else if (item_server == ModItems.BASE_REGEN_BOOSTER && regen >= 8 && regen < 16 && (regen + 2) <= heart) {
                    data_player.setRegen(regen + 2);
                    sendMsg(server_player, "§8[§6Server§8] §fYou got " + (regen + 2)/2 + " regen");
                } else if (item_server == ModItems.ADVANCE_REGEN_BOOSTER && regen >= 16 && regen < 24 && (regen + 2) <= heart) {
                    data_player.setRegen(regen + 2);
                    sendMsg(server_player, "§8[§6Server§8] §fYou got " + (regen + 2)/2 + " regen");
                } else if (item_server == ModItems.CRAFTING_STONE && !data_player.isCraft()) {
                    data_player.setCraft(true);
                    sendMsg(server_player, "§8[§6Server§8] §fYou now can do /craft to access crafting table");
                } else if (item_server == ModItems.ENDER_CHEST_STONE && !data_player.isEnder_chest()) {
                    data_player.setEnder_chest(true);
                    sendMsg(server_player, "§8[§6Server§8] §fYou now can do /ec to access your ender chest");
                } else {
                    if(it.getItem() instanceof Hammer){
                        if(((Hammer) it.getItem()).changeActivity()){
                            server_player.sendMessage(Text.of("§6Hammer mode §4activate"), true);
                        }else{
                            server_player.sendMessage(Text.of("§6Hammer mode §4deactivate"), true);
                        }
                    } else if(it.getItem() instanceof Excavator){
                        if(((Excavator) it.getItem()).changeActivity()){
                            server_player.sendMessage(Text.of("§6Excavator mode §4activate"), true);
                        }else{
                            server_player.sendMessage(Text.of("§6Excavator mode §4deactivate"), true);
                        }
                    } else if(it.getItem() instanceof MegaAxe){
                        if(((MegaAxe) it.getItem()).changeActivity()){
                            server_player.sendMessage(Text.of("§6MegaAxe mode §4activate"), true);
                        }else{
                            server_player.sendMessage(Text.of("§6MegaAxe mode §4deactivate"), true);
                        }
                    }
                    return TypedActionResult.pass(player.getMainHandStack());
                }
                it.setCount(it.getCount() - 1);
            }
            return TypedActionResult.pass(player.getMainHandStack());
        });
    }

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text){
        player.sendMessage(MutableText.of(new TranslatableTextContent(text)), false);
    }

    private static void onEntityAttack(){
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(player.getMainHandStack().getItem() instanceof SuperHoe){
                entity.setVelocity(1/(1+(entity.getX() - player.getX())),  1/(1 + (entity.getY() - player.getY())),
                        1/(1 + (entity.getZ() - player.getZ())));
            }
            return ActionResult.PASS;
        });
    }

    private final static Identifier BOW_SWITCH_MODE_PACKET = new Identifier(VanadiumMod.MODID, "bow_switch_mode_packet");

    private static void onLeftClick() {
        ServerPlayNetworking.registerGlobalReceiver(BOW_SWITCH_MODE_PACKET, (server, player, handler, buf, responseSender) -> {
            if(((VanadiumBow) player.getMainHandStack().getItem()).changeEnderPearl()){
                player.sendMessage(Text.of("§2Ender Pearl mode §4activate"), true);
            }else{
                player.sendMessage(Text.of("§2Ender Pearl mode §4deactivate"), true);
            }
        });
        InteractionEvent.LEFT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            if(bowCheck(player)){
                return EventResult.interrupt(true);
            }
            return EventResult.pass();
        });
        InteractionEvent.CLIENT_LEFT_CLICK_AIR.register((player, hand) -> bowCheck(player));
    }

    private static boolean bowCheck(@NotNull PlayerEntity player){
        if(player.getMainHandStack().getItem() instanceof VanadiumBow){
            if(player.getWorld().isClient()){
                System.out.println("Client side");
                if(ClientPlayNetworking.canSend(BOW_SWITCH_MODE_PACKET)){
                    ClientPlayNetworking.send(BOW_SWITCH_MODE_PACKET, PacketByteBufs.empty());
                }
            }else{
                System.out.println("Server side");
                if(((VanadiumBow) player.getMainHandStack().getItem()).changeEnderPearl()){
                    player.sendMessage(Text.of("§2Ender Pearl mode §4activate"), true);
                }else{
                    player.sendMessage(Text.of("§2Ender Pearl mode §4deactivate"), true);
                }
            }
            return true;
        }
        return false;
    }

    public static void registerAll() {
        onAttackBlockRegister();
        onItemRightClickRegister();
        onEntityAttack();
        onLeftClick();
    }

}