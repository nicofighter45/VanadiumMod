package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.MachineInventory;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PurificatorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ClientPlayerTickable, MachineInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private PurificatorScreenHandler handler;

    private final PurificatorProperties propertyDelegate;

    public PurificatorProperties getPropertyDelegate(){
        return this.propertyDelegate;
    }

    public PurificatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PURIFICATOR_BLOCK_ENTITY_TYPE, pos, state);
        propertyDelegate = new PurificatorProperties();
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        handler = new PurificatorScreenHandler(syncId, playerInventory, this.propertyDelegate,
                ScreenHandlerContext.create(player.getEntityWorld(), pos), this.inventory);
        return handler;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void tick() {
        assert world != null && handler != null;
        if(!world.isClient){
            if(this.propertyDelegate.isCrafting){
                if(this.propertyDelegate.getWater() > 1){
                    this.propertyDelegate.removeWater();
                    this.propertyDelegate.addCraftTime();
                    if(this.propertyDelegate.getCraftTime() == 100){
                        handler.crafting();
                    }
                }
            }
            if(this.propertyDelegate.isFilling){
                if(this.propertyDelegate.getWater() < 400){
                    this.propertyDelegate.addWater(1);
                    this.propertyDelegate.addFillingTime();
                    if(this.propertyDelegate.getFillingTime() == 100){
                        this.propertyDelegate.isFilling = false;
                    }
                }
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.propertyDelegate.init(nbt.getInt("water"), nbt.getInt("craftTime"), nbt.getInt("fillTime"));
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("water", this.propertyDelegate.getWater());
        nbt.putInt("craftTime", this.propertyDelegate.getCraftTime());
        nbt.putInt("fillTime", this.propertyDelegate.getCraftTime());
        super.writeNbt(nbt);
    }
}