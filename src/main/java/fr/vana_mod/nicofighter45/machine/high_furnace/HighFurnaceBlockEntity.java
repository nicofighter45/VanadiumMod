package fr.vana_mod.nicofighter45.machine.high_furnace;

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
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class HighFurnaceBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ClientPlayerTickable, MachineInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    private int syncedInt;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return syncedInt;
        }

        @Override
        public void set(int index, int value) {
            syncedInt = value;
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public HighFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.HIGH_FURNACE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new HighFurnaceScreenHandler(syncId, playerInventory, this.propertyDelegate, player,
                ScreenHandlerContext.create(player.getEntityWorld(), pos));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }
    @Override
    public void tick() {
        assert world != null;
        if(!world.isClient)
            syncedInt++;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        super.writeNbt(nbt);
    }
}