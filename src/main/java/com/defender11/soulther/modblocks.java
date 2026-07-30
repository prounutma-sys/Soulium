package com.seninpaketin.soulthermod; // Kendi paket adınla değiştirmeyi unutma!

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    // Bloklar için DeferredRegister
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SoultherMod.MOD_ID);

    // 1. Dark Ore (Cevher) - Dayanıklı, elmas kazma seviyesi, kırılınca tecrübe verir
    public static final RegistryObject<Block> DARK_ORE = registerBlock("dark-ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.of()
                            .strength(4.0f, 3.0f)
                            .requiresCorrectToolForDrops()
            ));

    // 2. Soul Rock (Ruh Taşı)
    public static final RegistryObject<Block> SOUL_ROCK = registerBlock("soul-rock",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .strength(3.0f, 3.0f)
                            .requiresCorrectToolForDrops()
            ));

    // 3. Blue End Portal Frame (Mavi Portal Çerçevesi) - Obsidyen sertliğinde
    public static final RegistryObject<Block> BLUE_END_PORTAL_FRAME = registerBlock("blue-end-portal-frame",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .strength(5.0f, 6.0f)
                            .requiresCorrectToolForDrops()
            ));

    // Blokların elmas/envanterde eşya olarak görünmesini sağlayan yardımcı metot
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        // Not: İtems kayıt sınıfında bu BlockItem'ları da kaydedeceğiz.
    }

    // Ana mod sınıfından (SoultherMod.java) çağrılacak kayıt metodu
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
