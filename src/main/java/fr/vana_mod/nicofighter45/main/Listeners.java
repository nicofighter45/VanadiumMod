package fr.vana_mod.nicofighter45.main;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.text.MutableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import fr.vana_mod.nicofighter45.items.ModItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.TypedActionResult;
import fr.vana_mod.nicofighter45.items.custom.Excavator;
import fr.vana_mod.nicofighter45.items.custom.Hammer;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Listeners {

    private static final Map<BlockPos, List<BlockPos>> map = new HashMap<>();

    public static void onAttackBlockRegister(){
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if(!world.isClient){
                if(map.containsKey(pos)){
                    List<BlockPos> list = map.get(pos);
                    ItemStack stack = player.getMainHandStack();
                    for(BlockPos pos_final : list){
                        if(world.getBlockState(pos_final).getBlock() == state.getBlock()){
                            if(stack.getDamage() > 0){
                                world.breakBlock(pos_final, true, player);
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
                    if(stack.getItem().isSuitableFor(state)){
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
                    if(stack.getItem().isSuitableFor(state)){
                        if(direction == Direction.UP || direction == Direction.DOWN){
                            registerBlocks(pos, pos.add(1, 0, 1), pos.add(1,0,-1), pos.add(-1,0,1), pos.add(-1,0,-1), pos.add(0, 0, 1), pos.add(0,0,-1), pos.add(1,0,0), pos.add(-1,0,0));
                        }else if(direction == Direction.NORTH || direction == Direction.SOUTH){
                            registerBlocks(pos, pos.add(-1, 0, 0), pos.add(1, 0, 0), pos.add(1, 1, 0), pos.add(-1,1,0), pos.add(1,-1,0), pos.add(-1,-1,0), pos.add(1, 0, 0), pos.add(-1,0,0));
                        }else if (direction == Direction.EAST || direction == Direction.WEST){
                            registerBlocks(pos, pos.add(-1, 0, 0), pos.add(1, 0, 0), pos.add(0, 1, 1), pos.add(0,1,-1), pos.add(0,-1,1), pos.add(0,-1,-1), pos.add(0, 0, 1), pos.add(0,0,-1));
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

    private static final Identifier ITEM_RIGHT_CLICK_ID = new Identifier(VanadiumMod.MODID, "use_item_callback_packet");

    public static void onItemRightClickRegister(){
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ServerPlayNetworking.registerGlobalReceiver(ITEM_RIGHT_CLICK_ID, (server, packet_player, handler, buf, responseSender) -> {
                System.out.println("server");
                ServerPlayerEntity server_player = (ServerPlayerEntity) player;
                ItemStack it = server_player.getMainHandStack();
                Item item_server = it.getItem();
                CustomPlayer data_player = VanadiumModServer.players.get(server_player.getEntityName());
                int heart = data_player.getHeart();
                int regen = data_player.getRegen();
                System.out.println(item_server + " " + data_player.isCraft() + data_player.isEnder_chest() + heart + regen);
                if(item_server == ModItems.SIMPLE_HEALTH_BOOSTER && heart < 10){
                    data_player.setHeart(heart + 1);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "You got " + heart + " heart");
                }else if(item_server == ModItems.BASE_HEALTH_BOOSTER && heart >= 10 && heart < 15){
                    data_player.setHeart(heart + 1);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "You got " + heart + " heart");
                }else if(item_server == ModItems.ADVANCE_HEALTH_BOOSTER && heart >= 15 && heart < 20){
                    data_player.setHeart(heart + 1);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "You got " + heart + " heart");
                }else if(item_server == ModItems.SIMPLE_REGEN_BOOSTER && regen < 4 && (regen+1) <= heart){
                    data_player.setRegen(regen + 1);
                    sendMsg(server_player, "You got " + regen + " regen");
                }else if(item_server == ModItems.BASE_REGEN_BOOSTER && regen >= 4 && regen < 8 && (regen+1) <= heart){
                    data_player.setRegen(regen + 1);
                    sendMsg(server_player, "You got " + regen + " regen");
                }else if(item_server == ModItems.ADVANCE_REGEN_BOOSTER && regen >= 8 && regen < 12 && (regen+1) <= heart){
                    data_player.setRegen(regen + 1);
                    sendMsg(server_player, "You got " + regen + " regen");
                }else if(item_server == ModItems.CRAFTING_STONE && !data_player.isCraft()){
                    data_player.setCraft(true);
                    sendMsg(server_player, "You now can do /craft to access crafting table");
                }else if(item_server == ModItems.ENDER_CHEST_STONE && !data_player.isEnder_chest()){
                    data_player.setEnder_chest(true);
                    sendMsg(server_player, "You no can do /ec to access your ender chest");
                }else{
                    return;
                }
                it.setCount(0);
            });
            System.out.println("ItemRightClickRegister");
            if(world.isClient){
                ClientPlayNetworking.send(ITEM_RIGHT_CLICK_ID, PacketByteBufs.empty());
            }
            return TypedActionResult.pass(player.getMainHandStack());
        });
    }

    private static void sendMsg(@NotNull ServerPlayerEntity player, String text){
        player.sendMessage(MutableText.of(new TranslatableTextContent(text)), false);
    }

    public static void registerAll() {
        onAttackBlockRegister();
        onItemRightClickRegister();
    }
}