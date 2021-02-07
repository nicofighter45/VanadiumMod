package fvm.nicofighter45.fr.block.modifiertable;

import fvm.nicofighter45.fr.FVM;
import fvm.nicofighter45.fr.items.ModItems;
import fvm.nicofighter45.fr.items.armor.VanadiumArmorMaterials;
import fvm.nicofighter45.fr.items.enchantment.ModEnchants;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModifiersTableGuiDescription extends SyncedGuiDescription {

    private static final Identifier MESSAGE_ITEM = new Identifier(FVM.MODID, "item");

    public ModifiersTableGuiDescription(int syncId, PlayerInventory playerinv, ScreenHandlerContext context) {

        super(FVM.SCREEN_HANDLER_TYPE, syncId, playerinv, getBlockInventory(context, 4), getBlockPropertyDelegate(context));

        PlayerEntity player = playerinv.player;

        //panel type
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        //panel size
        //root.setSize(0, 0);

        //items slots
        WItemSlot itemSlot1 = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot1, 1, 1);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory, 1);
        root.add(itemSlot2, 3, 1);
        WItemSlot itemSlot3 = WItemSlot.of(blockInventory, 2);
        root.add(itemSlot3, 5, 1);
        WItemSlot itemSlot4 = WItemSlot.of(blockInventory, 3);
        root.add(itemSlot4, 7, 1);

        //button
        WButton button = new WButton(new LiteralText("Modify"));
        button.setOnClick(() -> {
            ItemStack item0 = this.getSlot(0).getStack();
            ItemStack item1 = this.getSlot(1).getStack();
            ItemStack item2 = this.getSlot(2).getStack();
            ItemStack item3 = this.getSlot(3).getStack();
            Item item = null;
            ItemStack item_stack = null;
            if (item0.getItem() == ModItems.VANADIUM_STICK && item1.getItem() == ModItems.VANADIUM_INGOT
                    && item2.getItem() == ModItems.VANADIUM_HEART && item3.getItem() == ModItems.VANADIUM_BLOCK_ITEM) {
                item = ModItems.VANADIUM_SWORD;
            }else if(item0.getItem() == ModItems.TRANSISTOR && item1.getItem() == ModItems.TRANSISTOR
            && item2.getItem() == ModItems.TRANSISTOR && item3.getItem() == ModItems.TRANSISTOR) {
                item = ModItems.PROCESSOR;
            }else if(item0.getItem() == ModItems.VANADIUM_INGOT && item1.getItem() == ModItems.VANADIUM_INGOT
                    && item2.getItem() == ModItems.VANADIUM_INGOT && item3.getItem() == ModItems.VANADIUM_INGOT) {
                item = ModItems.VANADIUM_BLOCK_ITEM;
            }else if(item0.getItem() == ModItems.VANADIUM_NUGGET && item1.getItem() == ModItems.VANADIUM_NUGGET
                        && item2.getItem() == ModItems.VANADIUM_NUGGET && item3.getItem() == ModItems.VANADIUM_NUGGET){
                    item = ModItems.VANADIUM_INGOT;
            }else if(item0.getItem() == ModItems.VANADIUM_BLOCK_ITEM && item1.getItem() == Items.DIAMOND
                    && item2.getItem() == Items.DIAMOND && item3.getItem() == ModItems.VANADIUM_BLOCK_ITEM){
                item = ModItems.VANADIUM_HEART;
            }else if(item0.getItem() == ModItems.VANADIUM_INGOT || item1.getItem() == ModItems.VANADIUM_INGOT
                    || item2.getItem() == ModItems.VANADIUM_INGOT || item3.getItem() == ModItems.VANADIUM_INGOT){
                item = ModItems.VANADIUM_STICK;
            }else if(item0.getItem() instanceof ArmorItem && item1.getItem() != Items.AIR){
                ArmorItem armor = (ArmorItem) item0.getItem();
                if(armor.getMaterial() instanceof VanadiumArmorMaterials){
                    Map<Enchantment, Integer> enchant_item = EnchantmentHelper.get(item0);
                    if(armor == ModItems.VANADIUM_HELMET){
                        if(item1.getItem() == ModItems.HASTE_STONE && enchant_item.get(ModEnchants.HASTER) < 5){
                            item_stack = item0;
                            EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.HASTER, item_stack), item_stack);
                        }else if(item1.getItem() == ModItems.STRENGTH_STONE && enchant_item.get(ModEnchants.HASTER) < 2){
                            item_stack = item0;
                            EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.STRENGHTER, item_stack), item_stack);
                        }
                    }else if(armor == ModItems.VANADIUM_LEGGINGS){
                        if(item1.getItem() == ModItems.RESISTANCE_STONE && enchant_item.get(ModEnchants.HASTER) < 2){
                            item_stack = item0;
                            EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.RESISTANCER, item_stack), item_stack);
                        }else if(item1.getItem() == ModItems.SPEED_STONE && enchant_item.get(ModEnchants.HASTER) < 5){
                            item_stack = item0;
                            EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.FASTER, item_stack), item_stack);
                        }
                    }else if(armor == ModItems.VANADIUM_BOOTS){
                        if(item1.getItem() == ModItems.JUMP_STONE && enchant_item.get(ModEnchants.HASTER) < 5){
                            item_stack = item0;
                            EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.JUMPER, item_stack), item_stack);
                        }else if(item1.getItem() == ModItems.NO_FALL_STONE && enchant_item.get(ModEnchants.HASTER) == 0){
                            item_stack = item0;
                            EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.NO_FALL, item_stack), item_stack);
                        }
                    }
                }
            }
            if(item == null && item_stack == null){
                return;
            }
            if(item != null){
                item_stack = new ItemStack(item);
            }
            ItemStack finalItem_stack = item_stack;
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(MESSAGE_ITEM, buf ->
                    buf.writeItemStack(finalItem_stack)
            );
        });
        root.add(button, 3, 3, 3, 1);

        //add player inventory
        root.add(this.createPlayerInventoryPanel(), 0, 5);
        root.validate(this);

        //receiver give item
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(MESSAGE_ITEM, buf -> {
            ServerPlayerEntity server_player = (ServerPlayerEntity) player;
            getBlockInventory(context).clear();
            server_player.closeHandledScreen();
            server_player.getInventory().insertStack(buf.readItemStack());
        });
    }

    private Map<Enchantment, Integer> setActualEnchants(Map<Enchantment, Integer> enchant_item, Enchantment enchantment, ItemStack item) {
        if(!enchant_item.containsKey(enchantment)){
            enchant_item.put(enchantment, 1);
        }else{
            int value = enchant_item.get(enchantment);
            enchant_item.remove(enchantment);
            enchant_item.put(enchantment, value+1);
        }
        return enchant_item;
    }

}