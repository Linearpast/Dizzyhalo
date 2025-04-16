package com.dizzyhalo.dizzyhalo.datagen.provider;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;

public class ModLangGen extends LanguageProvider {

    private final Lang lang;

    public ModLangGen(PackOutput output, Lang lang) {
        super(output, Dizzyhalo.MODID, lang.getLangName());
        this.lang = lang;
    }

    @Override
    protected void addTranslations() {
        switch (lang){
            case EN_US -> enUsLang.forEach(this::addTranslation);
            case ZH_CN -> zhCnLang.forEach(this::addTranslation);
        }
    }

    private void addTranslation(Object o, String string) {
        if(o instanceof Item){
            add((Item) o,string);
        }else if(o instanceof Block){
            add((Block) o,string);
        }else if(o instanceof String){
            add((String) o,string);
        }else if(o instanceof ItemStack){
            add((ItemStack) o,string);
        }else if(o instanceof Enchantment){
            add((Enchantment) o,string);
        }else if(o instanceof MobEffect){
            add((MobEffect) o,string);
        }else if(o instanceof EntityType<?>){
            add((EntityType<?>) o,string);
        }else {
            throw new RuntimeException("Unknown object type: " + o.getClass());
        }
    }

    public enum Lang{
        
        ZH_CN("zh_cn"), EN_US("en_us");

        private final String langName;
        Lang(String langName) {
            this.langName = langName;
        }
        public String getLangName() {
            return langName;
        }
    }

    public static final HashMap<Object, String> zhCnLang = new HashMap<>();
    public static final HashMap<Object, String> enUsLang = new HashMap<>();
    static {
        zhCnLang.put(ModItems.WARLOCK_PANTS.get(), "§4术士长裤");
        zhCnLang.put(ModItems.BLOODLUST.get(), "§4夺魂之刃");
        zhCnLang.put(ModItems.GOLDEN_HEAD.get(), "§e金头颅");
        zhCnLang.put(ModItems.REVIVAL_STAR.get(), "§e新星");
        zhCnLang.put(ModItems.DAREDEVIL.get(), "§4恐惧战马");
        zhCnLang.put(ModItems.FUSION_HELMET.get(), "§5聚变头盔");
        zhCnLang.put(ModItems.FUSION_CHESTPLATE.get(), "§5聚变胸甲");
        zhCnLang.put(ModItems.FUSION_LEGGINGS.get(), "§5聚变护腿");
        zhCnLang.put(ModItems.FUSION_BOOTS.get(), "§5聚变战靴");
        zhCnLang.put(ModItems.FUSION_ARMORS.get(), "§5聚变之甲");
        zhCnLang.put(ModItems.BLAZING_CROSSBOW.get(), "§c烈焰弩");
        zhCnLang.put(ModItems.WITHERING_AXE.get(), "§0凋零之斧");
        zhCnLang.put(ModItems.CORRUPTED_CROSSBOW.get(), "§2剧毒之弩");
        zhCnLang.put(ModItems.EMERALD_BLADE.get(), "§a绿宝石剑");
        zhCnLang.put(ModItems.AIGLOS.get(), "§9艾格洛斯长矛");
        zhCnLang.put(ModItems.EXPERT_SEAL.get(), "§d大师密卷");
        
        enUsLang.put(ModItems.WARLOCK_PANTS.get(), "§4warlocck pants");
        enUsLang.put(ModItems.BLOODLUST.get(), "§4blood lust");
        enUsLang.put(ModItems.GOLDEN_HEAD.get(), "§egolden head");
        enUsLang.put(ModItems.REVIVAL_STAR.get(), "§erevival star");
        enUsLang.put(ModItems.DAREDEVIL.get(), "§4daredevil");
        enUsLang.put(ModItems.FUSION_HELMET.get(), "§5fusion helmet");
        enUsLang.put(ModItems.FUSION_CHESTPLATE.get(), "§5fusion chestplate");
        enUsLang.put(ModItems.FUSION_LEGGINGS.get(), "§5fusion leggings");
        enUsLang.put(ModItems.FUSION_BOOTS.get(), "§5fusion boots");
        enUsLang.put(ModItems.FUSION_ARMORS.get(), "§5fusion armor");
        enUsLang.put(ModItems.BLAZING_CROSSBOW.get(), "§cblazing crossbow");
        enUsLang.put(ModItems.WITHERING_AXE.get(), "§0withering axe");
        enUsLang.put(ModItems.CORRUPTED_CROSSBOW.get(), "§2corrupted crossbow");
        enUsLang.put(ModItems.EMERALD_BLADE.get(), "§aemerald blade");
        enUsLang.put(ModItems.AIGLOS.get(), "§9aiglos");
        enUsLang.put(ModItems.EXPERT_SEAL.get(), "§dexpert seal");
    }
}
