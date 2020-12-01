//package fr.nicofighter45.fvm;
//
//import net.minecraft.enchantment.Enchantment;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EquipmentSlot;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.effect.StatusEffectInstance;
//import net.minecraft.entity.effect.StatusEffects;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.world.World;
//
//import java.util.Map;
//
//class ItemCustom extends Item{
//
//    public ItemCustom(Settings settings) {
//        super(settings);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
//        if(entity instanceof LivingEntity){
//
//            LivingEntity player = (LivingEntity) entity;
//            ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
//            ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
//            ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
//            ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
//
//            //check emerald
//            if(helmet.getItem().equals(ModItems.EMERALD_HELMET)) {
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0, false, false, true));
//            }else if (chestplate.getItem() == ModItems.EMERALD_CHESTPLATE) {
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, false, false, true));
//            }else if (leggings.getItem() == ModItems.EMERALD_LEGGINGS) {
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, 0, false, false, true));
//            }else if (boots.getItem() == ModItems.EMERALD_BOOTS) {
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, false, false, true));
//            }
//
//            //check enchant
//            Map<Enchantment, Integer> enchantHelemet = EnchantmentHelper.get(helmet);
//            Map<Enchantment, Integer> enchantLeggings = EnchantmentHelper.get(leggings);
//            Map<Enchantment, Integer> enchantBoots = EnchantmentHelper.get(boots);
//
//            if(enchantHelemet.containsKey(ModEnchants.HASTER)){
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, enchantHelemet.get(ModEnchants.HASTER) - 1, false, false, true));
//            }
//            if(enchantHelemet.containsKey(ModEnchants.STRENGHTER)){
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60, enchantHelemet.get(ModEnchants.STRENGHTER) - 1, false, false, true));
//            }
//            if(enchantLeggings.containsKey(ModEnchants.FASTER)){
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, enchantLeggings.get(ModEnchants.FASTER) - 1, false, false, true));
//            }
//            if(enchantLeggings.containsKey(ModEnchants.RESISTANCER)){
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, enchantLeggings.get(ModEnchants.RESISTANCER) - 1, false, false, true));
//            }
//            if(enchantBoots.containsKey(ModEnchants.JUMPER)){
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60, enchantBoots.get(ModEnchants.JUMPER) - 1, false, false, true));
//            }
//
//            //check vanadium
//            if(helmet.getItem() == ModItems.VANADIUM_HELMET && leggings.getItem() == ModItems.VANADIUM_LEGGINGS && boots.getItem() == ModItems.VANADIUM_BOOTS) {
//                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, false, false, true));
//            }
//        }
//    }
//
//}