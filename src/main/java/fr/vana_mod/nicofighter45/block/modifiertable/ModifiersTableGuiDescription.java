package fr.vana_mod.nicofighter45.block.modifiertable;

import fr.vana_mod.nicofighter45.main.VanadiumMod;
import fr.vana_mod.nicofighter45.items.ModItems;
import fr.vana_mod.nicofighter45.items.enchantment.ModEnchants;
import fr.vana_mod.nicofighter45.items.enchantment.UpgradeItem;
import fr.vana_mod.nicofighter45.items.armor.VanadiumArmorMaterials;
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
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ModifiersTableGuiDescription extends SyncedGuiDescription {

    private static final Identifier MESSAGE_ITEM = new Identifier(VanadiumMod.MODID, "item");

    public ModifiersTableGuiDescription(int syncId, PlayerInventory playerinv, ScreenHandlerContext context) {

        super(VanadiumMod.SCREEN_HANDLER_TYPE, syncId, playerinv, getBlockInventory(context, 4), getBlockPropertyDelegate(context));

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
        button.setOnClick(() -> ScreenNetworking.of(this, NetworkSide.CLIENT).send(MESSAGE_ITEM, buf -> buf.writeBoolean(this.isTitleVisible())));
        root.add(button, 3, 3, 3, 1);

        //add player inventory
        root.add(this.createPlayerInventoryPanel(), 0, 5);
        root.validate(this);

        //receiver give item
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(MESSAGE_ITEM, buf -> {
            ServerPlayerEntity server_player = (ServerPlayerEntity) player;
            boolean vana = false;
            ItemStack vanaItem = null;
            ItemStack upgrade = null;
            for(int i = 64; i >= 0; i--){
                ItemStack item0 = this.getSlot(0).getStack();
                ItemStack item1 = this.getSlot(1).getStack();
                ItemStack item2 = this.getSlot(2).getStack();
                ItemStack item3 = this.getSlot(3).getStack();
                if(item0.getItem() instanceof ArmorItem){
                    if(((ArmorItem) item0.getItem()).getMaterial() instanceof VanadiumArmorMaterials) {
                        upgrade = upgrades(item1, item2, item3);
                        if(upgrade != null){
                            vana = true;
                            vanaItem = item0;
                        }
                        break;
                    }
                }else if(item1.getItem() instanceof ArmorItem) {
                    if (((ArmorItem) item1.getItem()).getMaterial() instanceof VanadiumArmorMaterials) {
                        upgrade = upgrades(item0, item2, item3);
                        if(upgrade != null){
                            vana = true;
                            vanaItem = item1;
                        }
                        break;
                    }
                }else if(item2.getItem() instanceof ArmorItem) {
                    if (((ArmorItem) item2.getItem()).getMaterial() instanceof VanadiumArmorMaterials) {
                        upgrade = upgrades(item0, item1, item3);
                        if(upgrade != null){
                            vana = true;
                            vanaItem = item2;
                        }
                        break;
                    }
                }else if(item3.getItem() instanceof ArmorItem) {
                    if (((ArmorItem) item3.getItem()).getMaterial() instanceof VanadiumArmorMaterials) {
                        upgrade = upgrades(item0, item1, item2);
                        if(upgrade != null){
                            vana = true;
                            vanaItem = item3;
                        }
                        break;
                    }
                }
                ModifierCraft craft = null;
                Item item = null;
                for(ModifierCraft result : VanadiumMod.crafts.keySet()){
                    if(result.getItem0() == item0.getItem() && result.getItem1() == item1.getItem() && result.getItem2() == item2.getItem() && result.getItem3() == item3.getItem()){
                        item = VanadiumMod.crafts.get(result);
                        craft = result;
                        break;
                    }
                }
                if(item == null){
                    i = -1;
                }else{
                    for(int slot : craft.getNeededItem()){
                        getBlockInventory(context).removeStack(slot, 1);
                    }
                    server_player.getInventory().offerOrDrop(new ItemStack(item));
                }
            }
            if(vana){
                boolean doCancell = false;
                Map<Enchantment, Integer> enchant_item = EnchantmentHelper.get(vanaItem);
                if(vanaItem.getItem() == ModItems.VANADIUM_HELMET){
                    if(upgrade.getItem() == ModItems.HASTE_STONE && (enchant_item.get(ModEnchants.HASTER) == null || enchant_item.get(ModEnchants.HASTER) < ModEnchants.HASTER.getMaxLevel())){
                        EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.HASTER), vanaItem);
                    }else if(upgrade.getItem() == ModItems.STRENGTH_STONE && (enchant_item.get(ModEnchants.STRENGHTER) == null || enchant_item.get(ModEnchants.STRENGHTER) < ModEnchants.STRENGHTER.getMaxLevel())){
                        EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.STRENGHTER), vanaItem);
                    }else{
                        doCancell = true;
                    }
                }else if(vanaItem.getItem() == ModItems.VANADIUM_LEGGINGS){
                    if(upgrade.getItem() == ModItems.RESISTANCE_STONE && (enchant_item.get(ModEnchants.RESISTANCER) == null || enchant_item.get(ModEnchants.RESISTANCER) < ModEnchants.RESISTANCER.getMaxLevel())){
                        EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.RESISTANCER), vanaItem);
                    }else if(upgrade.getItem() == ModItems.SPEED_STONE && (enchant_item.get(ModEnchants.FASTER) == null || enchant_item.get(ModEnchants.FASTER) < ModEnchants.FASTER.getMaxLevel())){
                        EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.FASTER), vanaItem);
                    }else{
                        doCancell = true;
                    }
                }else if(vanaItem.getItem() == ModItems.VANADIUM_BOOTS){
                    if(upgrade.getItem() == ModItems.JUMP_STONE &&
                            (enchant_item.get(ModEnchants.JUMPER) == null || enchant_item.get(ModEnchants.JUMPER) < ModEnchants.JUMPER.getMaxLevel())){
                        EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.JUMPER), vanaItem);
                    }else if(upgrade.getItem() == ModItems.NO_FALL_STONE && (enchant_item.get(ModEnchants.NO_FALL) == null || enchant_item.get(ModEnchants.NO_FALL) < ModEnchants.NO_FALL.getMaxLevel())){
                        EnchantmentHelper.set(setActualEnchants(enchant_item, ModEnchants.NO_FALL), vanaItem);
                    }else{
                        doCancell = true;
                    }
                }else{
                    doCancell = true;
                }
                if(!doCancell){
                    getBlockInventory(context).clear();
                    server_player.getInventory().offerOrDrop(vanaItem);
                }
            }
        });

    }

    private @Nullable ItemStack upgrades(@NotNull ItemStack item0, ItemStack item1, ItemStack item2) {
        if(item0.getItem() instanceof UpgradeItem){
            return item0;
        }else if(item1.getItem() instanceof UpgradeItem){
            return item1;
        }else if(item2.getItem() instanceof UpgradeItem){
            return item2;
        }
        return null;
    }

    @Contract("_, _ -> param1")
    private @NotNull Map<Enchantment, Integer> setActualEnchants(@NotNull Map<Enchantment, Integer> enchant_item, Enchantment enchantment) {
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