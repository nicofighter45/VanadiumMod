package fr.vana_mod.nicofighter45.machine.purificator;

import fr.vana_mod.nicofighter45.machine.MachineInventory;
import fr.vana_mod.nicofighter45.machine.ModMachines;
import fr.vana_mod.nicofighter45.machine.purificator.craft.PurificatorRecipe;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PurificatorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, MachineInventory {

    public int water, filling, crafting;

    public final Inventory inventory = new SimpleInventory(3);

    public PurificatorScreenHandler handler;

    private PurificatorRecipe recipe;

    public PurificatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PURIFICATOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    public static void tick(@NotNull World world, BlockPos pos, BlockState state, PurificatorBlockEntity blockEntity) {
        System.out.println("tick");
        assert world != null;
        if(!world.isClient){
            System.out.println("tick server");
            int water = blockEntity.propertyDelegate.get(Properties.WATER.value);
            int crafting = blockEntity.propertyDelegate.get(Properties.CRAFTING.value);
            if(water > 0 && crafting > 0){
                blockEntity.propertyDelegate.set(Properties.WATER.value, water - 1);
                water -= 1;
                blockEntity.propertyDelegate.set(Properties.CRAFTING.value, crafting - 1);
                if(crafting == 1){
                    ItemStack input = blockEntity.inventory.getStack(1);
                    if(input.getCount() > 1){
                        input.decrement(1);
                    }else{
                        blockEntity.inventory.setStack(1, ItemStack.EMPTY);
                    }
                    ItemStack result = blockEntity.inventory.getStack(2);
                    if(result.getItem().equals(blockEntity.recipe.getOutput().getItem())){
                        result.increment(1);
                    }else{
                        blockEntity.inventory.setStack(2, blockEntity.recipe.getOutput().copy());
                    }
                }
            }
            int filling = blockEntity.propertyDelegate.get(Properties.FILLING.value);
            if(filling > 0 && water < 400){
                blockEntity.propertyDelegate.set(Properties.WATER.value, water + 1);
                blockEntity.propertyDelegate.set(Properties.FILLING.value, filling - 1);
            }
        }
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        handler = new PurificatorScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(player.getEntityWorld(), pos), this);
        return handler;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(this.inventory.size());
        for(int i = 0; i < this.inventory.size(); i++){
            list.add(i, this.inventory.getStack(i));
        }
        return list;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        System.out.println("reading nbt");
        Inventories.readNbt(nbt, getItems());
        propertyDelegate.set(Properties.WATER.value, nbt.getInt("water"));
        propertyDelegate.set(Properties.FILLING.value, nbt.getInt("filling"));
        propertyDelegate.set(Properties.CRAFTING.value, nbt.getInt("crafting"));
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        System.out.println("writing nbt");
        Inventories.writeNbt(nbt, getItems());
        nbt.putInt("water", propertyDelegate.get(Properties.WATER.value));
        nbt.putInt("filling", propertyDelegate.get(Properties.FILLING.value));
        nbt.putInt("crafting", propertyDelegate.get(Properties.CRAFTING.value));
        super.writeNbt(nbt);
    }

    public enum Properties{
        WATER(0), FILLING(1), CRAFTING(2);

        public final int value;

        Properties(int value){
            this.value = value;
        }
    }

    public final PropertyDelegate propertyDelegate = new PropertyDelegate() {

        private void updateAPI(){
            fluidStorage.amount = water * 810L;
        }

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> water;
                case 1 -> filling;
                default -> crafting;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case 0:
                    water = value;
                    updateAPI();
                case 1:
                    filling = value;
                default:
                    crafting = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }

    };

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return 4 * FluidConstants.BUCKET;
        }
    };

}