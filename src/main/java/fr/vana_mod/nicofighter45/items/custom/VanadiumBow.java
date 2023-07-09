package fr.vana_mod.nicofighter45.items.custom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class VanadiumBow extends BowItem {

    private boolean enderPearl = false;

    public VanadiumBow(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            if (isEnderPearl()) {
                if (playerEntity.getInventory().contains(new ItemStack(Items.ENDER_PEARL))) {
                    playerEntity.getInventory().removeOne(new ItemStack(Items.ENDER_PEARL));
                    System.out.println("client");
                    if (!world.isClient) {
                        System.out.println("server");
                        stack.damage(1, Random.create(), (ServerPlayerEntity) playerEntity);
                        EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, playerEntity);
                        enderPearlEntity.setItem(new ItemStack(Items.ENDER_PEARL, 1));
                        enderPearlEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(),
                                0.2F, getPullProgress(this.getMaxUseTime(stack) - remainingUseTicks) * 3, 0.2F);
                        world.spawnEntity(enderPearlEntity);
                    }
                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                            SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F,
                            0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                    playerEntity.getItemCooldownManager().set(Items.ENDER_PEARL, 200);
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(Items.ENDER_PEARL));
                }
            } else {
                ItemStack itemStack = playerEntity.getProjectileType(stack);
                if (!itemStack.isEmpty() || bl) {
                    if (itemStack.isEmpty()) {
                        itemStack = new ItemStack(Items.ARROW);
                    }

                    int i = this.getMaxUseTime(stack) - remainingUseTicks;
                    float f = getPullProgress(i);
                    if (!((double) f < 0.1)) {
                        boolean bl2 = bl && itemStack.isOf(Items.ARROW);
                        if (!world.isClient) {
                            ArrowItem arrowItem = (ArrowItem) (itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
                            PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, playerEntity);
                            persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.4F, f * 6.0F, 0.2F);
                            if (f == 1.0F) {
                                persistentProjectileEntity.setCritical(true);
                            }

                            int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                            if (j > 0) {
                                persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double) j * 0.5 + 3);
                            }

                            int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                            if (k > 0) {
                                persistentProjectileEntity.setPunch(k);
                            }

                            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                                persistentProjectileEntity.setOnFireFor(100);
                            }
                            stack.damage(1, Random.create(), (ServerPlayerEntity) playerEntity);
                            if (bl2 || playerEntity.getAbilities().creativeMode && (itemStack.isOf(Items.SPECTRAL_ARROW) || itemStack.isOf(Items.TIPPED_ARROW))) {
                                persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            }

                            world.spawnEntity(persistentProjectileEntity);
                        }

                        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                        if (!bl2 && !playerEntity.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                            if (itemStack.isEmpty()) {
                                playerEntity.getInventory().removeOne(itemStack);
                            }
                        }

                        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    }
                }
            }
        }
    }

    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isEnderPearl()) {
            if (!user.getAbilities().creativeMode && !user.getInventory().contains(new ItemStack(Items.ENDER_PEARL))) {
                return TypedActionResult.fail(itemStack);
            } else {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            }
        } else {
            boolean bl = !user.getProjectileType(itemStack).isEmpty();
            if (!user.getAbilities().creativeMode && !bl) {
                return TypedActionResult.fail(itemStack);
            } else {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            }
        }
    }

    public boolean isEnderPearl() {
        return enderPearl;
    }

    public boolean changeEnderPearl() {
        this.enderPearl = !this.enderPearl;
        return this.enderPearl;
    }

}