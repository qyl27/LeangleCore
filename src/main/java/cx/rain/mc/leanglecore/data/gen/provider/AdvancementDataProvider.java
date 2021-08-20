package cx.rain.mc.leanglecore.data.gen.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cx.rain.mc.leanglecore.data.gen.provider.base.IResourceLocationProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AdvancementDataProvider implements IDataProvider, IResourceLocationProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    protected static final Logger LOGGER = LogManager.getLogger();

    private final DataGenerator gen;
    private final ExistingFileHelper helper;

    protected final String modId;
    protected final Map<ResourceLocation, Advancement.Builder> advancements = new LinkedHashMap<>();

    public AdvancementDataProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        gen = generator;
        modId = modid;
        helper = existingFileHelper;
    }

    protected abstract void registerAdvancements();

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        registerAdvancements();

        Path output = gen.getOutputFolder();
        advancements.forEach((key, advancement) -> {
            Path path = createPath(output, key);
            try {
                IDataProvider.save(GSON, cache, advancement.serializeToJson(), path);
            } catch (IOException ex) {
                LOGGER.error("Could not write advancements {}", path, ex);
            }
        });
    }

    private static Path createPath(Path path, ResourceLocation key) {
        return path.resolve("data/" + key.getNamespace() + "/advancements/" + key.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Advancements";
    }

    // Helper methods below.
    protected Advancement add(ResourceLocation id, Advancement.Builder advancement) {
        advancements.put(id, advancement);
        return advancement.build(id);
    }

    protected DisplayInfo display(IItemProvider icon, String title, String description) {
        return display(icon, title, description, null, FrameType.TASK);
    }

    protected DisplayInfo display(IItemProvider icon, String title, String description, FrameType frame) {
        return display(icon, title, description, null, frame);
    }

    protected DisplayInfo display(IItemProvider icon, String title, String description,
                                  ResourceLocation background) {
        return display(icon, title, description, background, FrameType.TASK, true);
    }


    protected DisplayInfo display(IItemProvider icon, String title, String description,
                                  ResourceLocation background, FrameType frame) {
        return display(icon, title, description, background, frame, true);
    }

    protected DisplayInfo display(IItemProvider icon, String title, String description,
                                  ResourceLocation background, FrameType frame,
                                  boolean showToast) {
        return display(icon, title, description, background, frame, true, true);
    }

    protected DisplayInfo display(IItemProvider icon, String title, String description,
                                  ResourceLocation background, FrameType frame,
                                  boolean showToast, boolean announceToChat) {
        return display(icon, title, description, background, frame, true, true, false);
    }

    protected DisplayInfo display(IItemProvider icon, String title, String description,
                                  ResourceLocation background, FrameType frame,
                                  boolean showToast, boolean announceToChat, boolean hidden) {
        return new DisplayInfo(new ItemStack(icon), new TranslationTextComponent(title),
                new TranslationTextComponent(description), background, frame, showToast, announceToChat, hidden);
    }

    protected Advancement.Builder root(DisplayInfo display) {
        return root(display, null);
    }

    protected Advancement.Builder root(DisplayInfo display,
                                       String criterionName,
                                       Criterion criterions) {
        Map<String, Criterion> map = new HashMap<>();
        map.put(criterionName, criterions);
        return root(display, map, IRequirementsStrategy.AND);
    }

    protected Advancement.Builder root(DisplayInfo display,
                                       Map<String, Criterion> criterions) {
        return root(display, criterions, null);
    }

    protected Advancement.Builder root(DisplayInfo display,
                                       Map<String, Criterion> criterions,
                                       IRequirementsStrategy requirements) {
        return root(display, criterions, requirements, null);
    }
    protected Advancement.Builder root(DisplayInfo display,
                                       Map<String, Criterion> criterions,
                                       IRequirementsStrategy requirements,
                                       AdvancementRewards.Builder rewards) {
        return advancement(display, null, criterions, requirements, rewards);
    }

    protected Advancement.Builder child(DisplayInfo display,
                                        Advancement parent) {
        return child(display, parent, null);
    }

    protected Advancement.Builder child(DisplayInfo display,
                                        Advancement parent,
                                        Map<String, Criterion> criterions) {
        return child(display, parent, criterions, null);
    }

    protected Advancement.Builder child(DisplayInfo display,
                                        Advancement parent,
                                        String criterionName,
                                        Criterion criterions) {
        return advancement(display, parent, criterionName, criterions, null);
    }

    protected Advancement.Builder child(DisplayInfo display,
                                        Advancement parent,
                                        Map<String, Criterion> criterions,
                                        IRequirementsStrategy requirements) {
        return child(display, parent, criterions, requirements, null);
    }

    protected Advancement.Builder child(DisplayInfo display,
                                        Advancement parent,
                                        String criterionName,
                                        Criterion criterions,
                                        AdvancementRewards.Builder rewards) {
        return advancement(display, parent, criterionName, criterions, rewards);
    }

    protected Advancement.Builder child(DisplayInfo display,
                                        Advancement parent,
                                        Map<String, Criterion> criterions,
                                        IRequirementsStrategy requirements,
                                        AdvancementRewards.Builder rewards) {
        return advancement(display, parent, criterions, requirements, rewards);
    }

    protected Advancement.Builder advancement(DisplayInfo display, Advancement parent,
                                              String criterionName,
                                              Criterion criterions,
                                              AdvancementRewards.Builder rewards) {
        Map<String, Criterion> map = new HashMap<>();
        map.put(criterionName, criterions);

        return advancement(display, parent, map, IRequirementsStrategy.AND, rewards);
    }

    protected Advancement.Builder advancement(DisplayInfo display, Advancement parent,
                                              Map<String, Criterion> criterions,
                                              IRequirementsStrategy requirements,
                                              AdvancementRewards.Builder rewards) {
        Advancement.Builder builder = Advancement.Builder.advancement().display(display);

        if (parent != null) {
            builder.parent(parent);
        }

        if (criterions != null && !criterions.isEmpty()) {
            for (Map.Entry<String, Criterion> c : criterions.entrySet()) {
                builder.addCriterion(c.getKey(), c.getValue());
            }
        }

        if (requirements != null) {
            builder.requirements(requirements);
        }

        if (rewards != null) {
            builder.rewards(rewards);
        }
        return builder;
    }

    protected Criterion hasItem(IItemProvider item) {
        return new Criterion(triggerWithItem(item));
    }

    protected Criterion hasItem(ITag<Item> item) {
        return new Criterion(triggerWithItem(item));
    }

    protected ItemPredicate predicate(IItemProvider itemIn) {
        return ItemPredicate.Builder.item().of(itemIn).build();
    }

    protected ItemPredicate predicate(ITag<Item> ITagIn) {
        return ItemPredicate.Builder.item().of(ITagIn).build();
    }

    protected InventoryChangeTrigger.Instance triggerWithItem(IItemProvider itemIn) {
        return triggerWithItems(ItemPredicate.Builder.item().of(itemIn).build());
    }

    protected InventoryChangeTrigger.Instance triggerWithItem(ITag<Item> ITagIn) {
        return triggerWithItems(ItemPredicate.Builder.item().of(ITagIn).build());
    }

    protected InventoryChangeTrigger.Instance triggerWithItems(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.Instance(
                EntityPredicate.AndPredicate.ANY,
                MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY,
                MinMaxBounds.IntBound.ANY, predicates);
    }

    protected AdvancementRewards.Builder rewardExp(int exp) {
        return new AdvancementRewards.Builder().addExperience(exp);
    }
}
