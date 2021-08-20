package cx.rain.mc.leanglecore.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CustomCreativeTab extends CreativeTabs {
    private final Item item;

    public CustomCreativeTab(String label, Item itemIn) {
        super(label);

        item = itemIn;
    }

    public CustomCreativeTab(String label, Item itemIn, String background) {
        super(label);

        item = itemIn;
        setBackgroundImageName(background);
    }

    @Override
    public Item getTabIconItem() {
        return item;
    }
}
