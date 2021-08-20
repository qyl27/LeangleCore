package cx.rain.mc.leanglecore.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class CustomCreativeTab extends CreativeTabs {
    private final Supplier<Item> item;

    public CustomCreativeTab(String label, Supplier<Item> itemIn) {
        super(label);

        item = itemIn;
    }

    public CustomCreativeTab(String label, Supplier<Item> itemIn, String background) {
        super(label);

        item = itemIn;
        setBackgroundImageName(background);
    }

    @Override
    public Item getTabIconItem() {
        return item.get();
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(item.get());
    }
}
