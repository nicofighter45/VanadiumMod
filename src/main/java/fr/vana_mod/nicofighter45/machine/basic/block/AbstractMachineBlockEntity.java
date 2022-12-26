package fr.vana_mod.nicofighter45.machine.basic.block;

import fr.vana_mod.nicofighter45.machine.basic.properties.MachineInventory;
import fr.vana_mod.nicofighter45.machine.basic.properties.MachinePropertyDelegate;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, MachineInventory {

    public final SingleVariantStorage<FluidVariant> fluidStorage;
    private final DefaultedList<ItemStack> inventory;
    private final int[] properties;
    private final MachinePropertyDelegate propertyDelegate;
    protected boolean changingInventory = false;

    protected AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int propertiesSize) {
        this(type, pos, state, inventorySize, propertiesSize, -1);
    }

    protected AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int propertiesSize, int fluidIndexProperties) {
        super(type, pos, state);
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
        this.properties = new int[propertiesSize];
        for (int i = 0; i < propertiesSize; i++) {
            this.properties[i] = 0;
        }
        if (fluidIndexProperties > -1) {
            this.fluidStorage = new SingleVariantStorage<>() {
                @Override
                protected FluidVariant getBlankVariant() {
                    return FluidVariant.blank();
                }

                @Override
                protected long getCapacity(FluidVariant variant) {
                    return 4 * FluidConstants.BUCKET;
                }
            };
            this.propertyDelegate = new MachinePropertyDelegate() {
                @Override
                public int get(int index) {
                    return properties[index];
                }

                @Override
                public void set(int index, int value) {
                    properties[index] = value;
                    if (index == fluidIndexProperties) {
                        fluidStorage.amount = value * 810L; // in my API, one water bucket equal 100, in fabric it's 81000
                    }
                }

                @Override
                public int size() {
                    return properties.length;
                }
            };
        } else {
            this.fluidStorage = null;
            this.propertyDelegate = new MachinePropertyDelegate() {
                @Override
                public int get(int index) {
                    return properties[index];
                }

                @Override
                public void set(int index, int value) {
                    properties[index] = value;
                }

                @Override
                public int size() {
                    return properties.length;
                }
            };
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        for (int i = 0; i < properties.length; i++) {
            properties[i] = nbt.getInt("PropertiesNb" + i);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        for (int i = 0; i < properties.length; i++) {
            nbt.putInt("PropertiesNb" + i, properties[i]);
        }
    }

    public MachinePropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    @Override
    public abstract @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player);

    @Override
    public DefaultedList<ItemStack> getStacks() {
        return this.inventory;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        assert this.world != null;
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double) this.pos.getX() + 0.5, (double) this.pos.getY() + 0.5, (double) this.pos.getZ() + 0.5) <= 64.0;
        }
    }

}