package com.dizzyhalo.dizzyhalo.common.register;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements StringRepresentable, ArmorMaterial {
    WARLOCK("warlock", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (pConsumer) -> {
        pConsumer.put(ArmorItem.Type.BOOTS, 2);
        pConsumer.put(ArmorItem.Type.LEGGINGS, 5);
        pConsumer.put(ArmorItem.Type.CHESTPLATE, 6);
        pConsumer.put(ArmorItem.Type.HELMET,2);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
        return Ingredient.of(ModItems.WARLOCK_PANTS.get());
    }),
    FUSION("fusion", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), (pConsumer) -> {
        pConsumer.put(ArmorItem.Type.BOOTS, 3);
        pConsumer.put(ArmorItem.Type.LEGGINGS, 6);
        pConsumer.put(ArmorItem.Type.CHESTPLATE, 8);
        pConsumer.put(ArmorItem.Type.HELMET,3);
    }), 10, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> {
        return Ingredient.of(ModItems.WARLOCK_PANTS.get());
    });

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModArmorMaterials(String pName, int pDurabilityMultiplier, EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;//耐久乘数
        this.protectionFunctionForType = pProtectionFunctionForType;// 护甲值
        this.enchantmentValue = pEnchantmentValue;//附魔时可获得稀有附魔的能力
        this.sound = pSound;//穿戴时声音
        this.toughness = pToughness;//韧性
        this.knockbackResistance = pKnockbackResistance;//击退抗性
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);//修复所用材料
    }

    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public String getSerializedName() {
        return this.name;
    }
}
