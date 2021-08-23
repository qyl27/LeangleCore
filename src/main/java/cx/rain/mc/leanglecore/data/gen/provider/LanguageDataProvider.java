package cx.rain.mc.leanglecore.data.gen.provider;

import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fmllegacy.RegistryObject;

public abstract class LanguageDataProvider extends LanguageProvider {
    public LanguageDataProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    // Helper methods below.
    public void addAdvancement(String category, String id, String title, String description) {
        add("advancement." + category + "." + id + ".title", title);
        add("advancement." + category + "." + id + ".description", description);
    }

    public void addItemGroup(CreativeModeTab tab, String name) {
        add(((TranslatableComponent) tab.getDisplayName()).getKey(), name);
    }

    public void addTooltip(RegistryObject<? extends Item> item, String... tooltips) {
        String name = item.getId().getPath();
        for (int i = 1; i <= tooltips.length; i++) {
            add("tooltip." + name + "." + i, tooltips[i - 1]);
        }
    }
}
