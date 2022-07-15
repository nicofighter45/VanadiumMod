package fr.vana_mod.nicofighter45.machine.basic;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    private final MachineInventory inventory;

    private final Map<Integer, Integer> properties = new HashMap<>();

    private final MachinePropertyDelegate propertyDelegate;

    public final SingleVariantStorage<FluidVariant> fluidStorage;

    protected AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int propertiesSize) {
        this(type, pos, state, inventorySize, propertiesSize, -1);
    }

    protected AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int propertiesSize, int fluidIndexProperties ) {
        super(type, pos, state);
        this.inventory = MachineInventory.ofSize(this, inventorySize);
        for(int i = 0; i < propertiesSize; i ++){
            this.properties.put(i, 0);
        }
        if(fluidIndexProperties > -1){
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
                    return properties.get(index);
                }

                @Override
                public void set(int index, int value) {
                    properties.put(index, value);
                    if(index == fluidIndexProperties){
                        fluidStorage.amount = value * 810; // in my API, one water bucket equal 100, in fabric it's 81000
                    }
                }

                @Override
                public int size() {
                    return properties.size();
                }
            };
        }else{
            this.fluidStorage = null;
            this.propertyDelegate = new MachinePropertyDelegate() {
                @Override
                public int get(int index) {
                    return properties.get(index);
                }

                @Override
                public void set(int index, int value) {
                    properties.put(index, value);
                }

                @Override
                public int size() {
                    return properties.size();
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
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory.getItems());
        for(int i = 0; i < properties.size(); i ++){
            properties.put(i, nbt.getInt("PropertiesNb" + i));
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory.getItems());
        for(int i = 0; i < properties.size(); i ++){
            nbt.putInt("PropertiesNb" + i, properties.get(i));
        }
    }

    public MachineInventory getInventory(){
        return this.inventory;
    }

    public MachinePropertyDelegate getPropertyDelegate(){
        return this.propertyDelegate;
    }

    @Override
    public abstract @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player);

    protected abstract void slotUpdate(int slot);

}