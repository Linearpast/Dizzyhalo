package com.dizzyhalo.dizzyhalo.common.register.item;

import com.dizzyhalo.dizzyhalo.common.register.ModArmorMaterials;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class FusionArmorItem extends ArmorItem {
    public FusionArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(
            @NotNull ItemStack stack,
            @NotNull Level level,
            @NotNull Entity entity,
            int slotIndex, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotIndex, isSelected);
        if(entity instanceof Player player) {
            Inventory inv = player.getInventory();
            if(stack.is(ModItems.FUSION_ARMORS.get())){
                RandomSource random = player.getRandom();
                int i = random.nextInt(4);
                Item item = switch (i){
                    case 0 -> ModItems.FUSION_HELMET.get();
                    case 1 -> ModItems.FUSION_CHESTPLATE.get();
                    case 2 -> ModItems.FUSION_LEGGINGS.get();
                    case 3 -> ModItems.FUSION_BOOTS.get();
                    default -> null;
                };
                if(item == null) return;
                ItemStack itemStack = new ItemStack(item);
                itemStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                stack.shrink(1);
                inv.add(itemStack);
                inv.setChanged();
            }

            if(!stack.is(ModItems.FUSION_HELMET.get())) return;
            if (slotIndex < inv.armor.size()) {
                armorTick(player);
            }
        }
    }

    public void armorTick(Player player) {
        if(!player.level().isClientSide()) {
            Inventory inv = player.getInventory();
            if(isAllEquip(inv)){
                addEffect(player);
            }
        }
    }

    private boolean isAllEquip(Inventory inventory){
        ItemStack itemstack = inventory.armor.get(0);
        ItemStack itemstack1 = inventory.armor.get(1);
        ItemStack itemstack2 = inventory.armor.get(2);
        ItemStack itemstack3 = inventory.armor.get(3);
        if(itemstack.isEmpty() || itemstack1.isEmpty() || itemstack2.isEmpty() || itemstack3.isEmpty()) return false;

        ArmorItem armor = (ArmorItem) itemstack.getItem();
        ArmorItem armor1 = (ArmorItem) itemstack1.getItem();
        ArmorItem armor2 = (ArmorItem) itemstack2.getItem();
        ArmorItem armor3 = (ArmorItem) itemstack3.getItem();
        return armor.getMaterial() == ModArmorMaterials.FUSION &&
                armor1.getMaterial() == ModArmorMaterials.FUSION &&
                armor2.getMaterial() == ModArmorMaterials.FUSION &&
                armor3.getMaterial() == ModArmorMaterials.FUSION;
    }

    private void addEffect(Player player){
        Collection<MobEffectInstance> activeEffects = player.getActiveEffects();
        MobEffectInstance effectInstance = activeEffects.stream().filter(mobEffectInstance -> mobEffectInstance.getEffect() == MobEffects.DAMAGE_RESISTANCE).findFirst().orElse(null);
        if(effectInstance != null){
            int duration = effectInstance.getDuration();
            int amplifier = effectInstance.getAmplifier();
            if(amplifier < 1 || duration < 20){
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1, false, false));
            }
        }else {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1, false, false));
        }
    }
}
