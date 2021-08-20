package cx.rain.mc.leanglecore.data.gen.provider;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public abstract class LanguageDataProvider extends LanguageProvider {
    public LanguageDataProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    // Helper methods below.
    public void addAdvancement(String category, String id, String title, String description) {
        add("advancement." + category + "." + id + ".title", title);
        add("advancement." + category + "." + id + ".description", description);
    }

    public void addItemGroup(ItemGroup tab, String name) {
        add(((TranslationTextComponent) tab.getDisplayName()).getKey(), name);
    }

    public void addTooltip(RegistryObject<? extends Item> item, String... tooltips) {
        String name = item.getId().getPath();
        for (int i = 1; i <= tooltips.length; i++) {
            add("tooltip." + name + "." + i, tooltips[i - 1]);
        }
    }
}
