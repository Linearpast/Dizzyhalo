package com.dizzyhalo.dizzyhalo.common.register;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Dizzyhalo.MODID);

    public static final RegistryObject<ArmorItem> WARLOCK_PANTS = REGISTER.register(
            "warlock_pants", () ->
                    new ArmorItem(
                            ModArmorMaterials.WARLOCK,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties().stacksTo(1)
                    ));
    public static final RegistryObject<SwordItem> BLOODLUST = REGISTER.register(
            "bloodlust", () ->
                    new SwordItem(
                            Tiers.DIAMOND,
                            3,
                            -2.4F,
                            new Item.Properties().stacksTo(1)
                    ));

    public static final RegistryObject<Item> GOLDEN_HEAD = REGISTER.register(
            "golden_head", () ->
                    new Item(new Item.Properties().stacksTo(1))
    );
    public static final RegistryObject<Item> REVIVAL_STAR = REGISTER.register(
            "revival_star", () ->
                    new Item(new Item.Properties().stacksTo(1))
    );
    public static final RegistryObject<Item> DAREDEVIL = REGISTER.register(
            "daredevil", () ->
                    new DaredevilItem(new Item.Properties().stacksTo(1))
    );

    //fusion armor
    public static final RegistryObject<ArmorItem> FUSION_HELMET = REGISTER.register(
            "fusion_helmet", () ->
                    new FusionArmor(
                            ModArmorMaterials.FUSION,
                            ArmorItem.Type.HELMET,
                            new Item.Properties().stacksTo(1)
                    )
    );
    public static final RegistryObject<ArmorItem> FUSION_CHESTPLATE = REGISTER.register(
            "fusion_chestplate", () ->
                    new FusionArmor(
                            ModArmorMaterials.FUSION,
                            ArmorItem.Type.CHESTPLATE,
                            new Item.Properties().stacksTo(1)
                    )
    );
    public static final RegistryObject<ArmorItem> FUSION_LEGGINGS = REGISTER.register(
            "fusion_leggings", () ->
                    new FusionArmor(
                            ModArmorMaterials.FUSION,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties().stacksTo(1)
                    )
    );
    public static final RegistryObject<ArmorItem> FUSION_BOOTS = REGISTER.register(
            "fusion_boots", () ->
                    new FusionArmor(
                            ModArmorMaterials.FUSION,
                            ArmorItem.Type.BOOTS,
                            new Item.Properties().stacksTo(1)
                    )
    );
    public static final RegistryObject<Item> FUSION_ARMORS = REGISTER.register(
            "fusion_armors", () ->
                    new FusionArmor(
                            ModArmorMaterials.FUSION,
                            ArmorItem.Type.HELMET,
                            new Item.Properties().stacksTo(1)
                    )
    );

    public static final RegistryObject<CrossbowItem> BLAZING_CROSSBOW = REGISTER.register(
            "blazing_crossbow", () ->
                    new FireCrossBow(new Item.Properties().stacksTo(1).durability(131))
    );

    public static final RegistryObject<Item> WITHERING_AXE = REGISTER.register(
            "withering_axe", () ->
                    new WitheringAxe(
                            Tiers.DIAMOND,
                            5.0f,
                            -3.0f,
                            new Item.Properties().stacksTo(1)
                    )
    );
    public static final RegistryObject<CrossbowItem> CORRUPTED_CROSSBOW = REGISTER.register(
            "corrupted_crossbow", () ->
                    new PoisonCrossbow(new Item.Properties().stacksTo(1).durability(131))
    );
    public static final RegistryObject<SwordItem> EMERALD_BLADE = REGISTER.register(
            "emerald_blade", () ->
                    new EmeraldBlade(
                            Tiers.DIAMOND,
                            3,
                            -2.4f,
                            new Item.Properties().stacksTo(1),
                            32
                    )
    );
    public static final RegistryObject<Item> AIGLOS = REGISTER.register(
            "aiglos", () ->
                    new AiglosTrident(new Item.Properties().stacksTo(1).durability(270))
    );
    public static final RegistryObject<Item> EXPERT_SEAL = REGISTER.register(
            "expert_seal", () ->
                    new Item(new Item.Properties().stacksTo(16))
    );
}