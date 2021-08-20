package cx.rain.mc.leanglecore.test.data.gen;

import cx.rain.mc.leanglecore.Leangle;
import cx.rain.mc.leanglecore.data.gen.provider.AdvancementDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AdvancementProvider extends AdvancementDataProvider {
    public AdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Leangle.MODID, existingFileHelper);
    }

    @Override
    protected void registerAdvancements() {
        add(modLoc("basic/root"), root(display(Items.APPLE, "advancements.basic.root.title",
                "advancements.basic.root.description", mcLoc("textures/block/cobblestone.png")),
                "got_apple", hasItem(Items.APPLE)));
    }
}
