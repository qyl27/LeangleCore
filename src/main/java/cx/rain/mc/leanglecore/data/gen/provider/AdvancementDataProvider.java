package cx.rain.mc.leanglecore.data.gen.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cx.rain.mc.leanglecore.data.gen.provider.base.IResourceLocationProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AdvancementDataProvider implements DataProvider, IResourceLocationProvider {
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
    public void run(HashCache cache) throws IOException {
        registerAdvancements();

        Path output = gen.getOutputFolder();
        advancements.forEach((key, advancement) -> {
            Path path = createPath(output, key);
            try {
                DataProvider.save(GSON, cache, advancement.serializeToJson(), path);
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

    protected DisplayInfo display(ItemLike icon, String title, String description) {
        return display(icon, title, description, null, FrameType.TASK);
    }

    protected DisplayInfo display(ItemLike icon, String title, String description, FrameType frame) {
        return display(icon, title, description, null, frame);
    }

    protected DisplayInfo display(ItemLike icon, String title, String description,
                                  ResourceLocation background) {
        return display(icon, title, description, background, FrameType.TASK, true);
    }


    protected DisplayInfo display(ItemLike icon, String title, String description,
                                  ResourceLocation background, FrameType frame) {
        return display(icon, title, description, background, frame, true);
    }

    protected DisplayInfo display(ItemLike icon, String title, String description,
                                  ResourceLocation background, FrameType frame,
                                  boolean showToast) {
        return display(icon, title, description, background, frame, true, true);
    }

    protected DisplayInfo display(ItemLike icon, String title, String description,
                                  ResourceLocation background, FrameType frame,
                                  boolean showToast, boolean announceToChat) {
        return display(icon, title, description, background, frame, true, true, false);
    }

    protected DisplayInfo display(ItemLike icon, String title, String description,
                                  ResourceLocation background, FrameType frame,
                                  boolean showToast, boolean announceToChat, boolean hidden) {
        return new DisplayInfo(new ItemStack(icon), new TranslatableComponent(title),
                new TranslatableComponent(description), background, frame, showToast, announceToChat, hidden);
    }

    protected Advancement.Builder root(DisplayInfo display) {
        return root(display, null);
    }

    protected Advancement.Builder root(DisplayInfo display,
                                       String criterionName,
                                       Criterion criterions) {
        Map<String, Criterion> map = new HashMap<>();
        map.put(criterionName, criterions);
        return root(display, map, RequirementsStrategy.AND);
    }

    protected Advancement.Builder root(DisplayInfo display,
                                       Map<String, Criterion> criterions) {
        return root(display, criterions, null);
    }

    protected Advancement.Builder root(DisplayInfo display,
                                       Map<String, Criterion> criterions,
                                       RequirementsStrategy requirements) {
        return root(display, criterions, requirements, null);
    }
    protected Advancement.Builder root(DisplayInfo display,
                                       Map<String, Criterion> criterions,
                                       RequirementsStrategy requirements,
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
                                        RequirementsStrategy requirements) {
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
                                        RequirementsStrategy requirements,
                                        AdvancementRewards.Builder rewards) {
        return advancement(display, parent, criterions, requirements, rewards);
    }

    protected Advancement.Builder advancement(DisplayInfo display, Advancement parent,
                                              String criterionName,
                                              Criterion criterions,
                                              AdvancementRewards.Builder rewards) {
        Map<String, Criterion> map = new HashMap<>();
        map.put(criterionName, criterions);

        return advancement(display, parent, map, RequirementsStrategy.AND, rewards);
    }

    protected Advancement.Builder advancement(DisplayInfo display, Advancement parent,
                                              Map<String, Criterion> criterions,
                                              RequirementsStrategy requirements,
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

    protected Criterion hasItem(ItemLike item) {
        return new Criterion(triggerWithItem(item));
    }

    protected Criterion hasItem(Tag<Item> item) {
        return new Criterion(triggerWithItem(item));
    }

    protected ItemPredicate predicate(ItemLike itemIn) {
        return ItemPredicate.Builder.item().of(itemIn).build();
    }

    protected ItemPredicate predicate(Tag<Item> ITagIn) {
        return ItemPredicate.Builder.item().of(ITagIn).build();
    }

    protected InventoryChangeTrigger.TriggerInstance triggerWithItem(ItemLike itemIn) {
        return triggerWithItems(ItemPredicate.Builder.item().of(itemIn).build());
    }

    protected InventoryChangeTrigger.TriggerInstance triggerWithItem(Tag<Item> ITagIn) {
        return triggerWithItems(ItemPredicate.Builder.item().of(ITagIn).build());
    }

    protected InventoryChangeTrigger.TriggerInstance triggerWithItems(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.TriggerInstance(
                EntityPredicate.Composite.ANY,
                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                MinMaxBounds.Ints.ANY, predicates);
    }

    protected AdvancementRewards.Builder rewardExp(int exp) {
        return new AdvancementRewards.Builder().addExperience(exp);
    }
}
