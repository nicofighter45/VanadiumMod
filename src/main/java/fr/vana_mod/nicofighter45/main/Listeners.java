package fr.vana_mod.nicofighter45.main;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import fr.vana_mod.nicofighter45.items.ModItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.TypedActionResult;
import fr.vana_mod.nicofighter45.items.custom.Excavator;
import fr.vana_mod.nicofighter45.items.custom.Hammer;

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
                                stack.damage(1, new Random(), (ServerPlayerEntity) player);
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

    private static void registerBlocks(BlockPos... blockPos) {
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

    public static void onItemRightClickRegister(){
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if(!world.isClient) {
                ServerPlayerEntity server_player = (ServerPlayerEntity) player;
                ItemStack it = server_player.getMainHandStack();
                Item item_server = it.getItem();
                CustomPlayer data_player = VanadiumModServer.players.get(server_player.getEntityName());
                PlayerInventory inventory = server_player.getInventory();
                int heart = data_player.getHeart();
                int regen = data_player.getRegen();
                boolean give = false;
                if(item_server == ModItems.SIMPLE_HEALTH_BOOSTER && heart < 20){
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "You got " + (heart + 2)/2 + " heart");
                    give = true;
                }else if(item_server == ModItems.BASE_HEALTH_BOOSTER && heart >= 20 && heart < 30){
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "You got " + (heart + 2)/2 + " heart");
                    give = true;
                }else if(item_server == ModItems.ADVANCE_HEALTH_BOOSTER && heart >= 30 && heart < 40){
                    data_player.setHeart(heart + 2);
                    Objects.requireNonNull(server_player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(data_player.getHeart());
                    sendMsg(server_player, "You got " + (heart + 2)/2 + " heart");
                    give = true;
                }else if(item_server == ModItems.SIMPLE_REGEN_BOOSTER && regen < 8 && (regen+2) <= heart){
                    data_player.setRegen(regen + 2);
                    sendMsg(server_player, "You got " + (regen + 2)/2 + " regen");
                    give = true;
                }else if(item_server == ModItems.BASE_REGEN_BOOSTER && regen >= 8 && regen < 16 && (regen+2) <= heart){
                    data_player.setRegen(regen + 2);
                    sendMsg(server_player, "You got " + (regen + 2)/2 + " regen");
                    give = true;
                }else if(item_server == ModItems.ADVANCE_REGEN_BOOSTER && regen >= 16 && regen < 24 && (regen+2) <= heart){
                    data_player.setRegen(regen + 2);
                    sendMsg(server_player, "You got " + (regen + 2)/2 + " regen");
                    give = true;
                }
                if(give){
                    for(int slot = 0; slot<9; slot++){
                        if(inventory.getStack(slot) == it){
                            inventory.removeStack(slot, 1);
                        }
                    }
                }

            }
            return TypedActionResult.pass(player.getMainHandStack());
        });
    }

    private static void sendMsg(ServerPlayerEntity player, String text){
        player.sendMessage(new TranslatableText(text), false);
    }

    public static void registerAll() {
        onAttackBlockRegister();
        onItemRightClickRegister();
    }
}