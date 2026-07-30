package com.soulther.mod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BlueEndPortalFrameBlock extends Block {
    // Çerçevenin içine göz takılı olup olmadığını tutan özellik
    public static final BooleanProperty HAS_EYE = BooleanProperty.create("has_eye");

    public BlueEndPortalFrameBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_EYE, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        // Eğer çerçevede göz yoksa ve elindeki eşya Ender Gözü ise
        if (!state.getValue(HAS_EYE) && player.getItemInHand(hand).is(net.minecraft.world.item.Items.ENDER_EYE)) { 
            if (!level.isClientSide()) {
                // Yaratıcı modda değilse elindeki gözü 1 tane azalt
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                // Çerçeveyi gözlü duruma getir
                level.setBlock(pos, state.setValue(HAS_EYE, true), 3);
                
                // 12 çerçevenin tamamlanma kontrolünü tetikleyen metot
                checkAndCreatePortal(level, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    // Portalın tamamlanma kontrolünü yapan metot
    private void checkAndCreatePortal(Level level, BlockPos pos) {
        // İleride buraya 3x3 yapıyı tarayıp ortayı açacak algoritmayı ekleyeceğiz
    }
}
