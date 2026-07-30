package com.soulther.mod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BlueEndPortalFrameBlock extends Block {
    public static final BooleanProperty HAS_EYE = BooleanProperty.create("has_eye");

    public BlueEndPortalFrameBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_EYE, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        if (!state.getValue(HAS_EYE) && player.getItemInHand(hand).is(Items.ENDER_EYE)) { 
            if (!level.isClientSide()) {
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                level.setBlock(pos, state.setValue(HAS_EYE, true), 3);
                
                // Göz takıldıktan sonra portal yapısını kontrol et
                checkAndCreatePortal((ServerLevel) level, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void checkAndCreatePortal(ServerLevel level, BlockPos pos) {
        // Çevredeki 3x3'lük alanın köşelerini ve portalın içini tarayıp tamamlama mantığı
        // Minecraft'ın orijinal End Portal mantığına benzer şekilde çalışır.
        
        // Örnek olarak basitçe merkez alanı bulup portal bloklarını yerleştirme tetikleyicisi:
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        
        // Çerçevenin etrafındaki 3x3 boşluk alanını tarıyoruz
        boolean allFramesReady = true;
        
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                // Sadece çerçeve sınırlarını kontrol et
                if (Math.abs(x) == 2 || Math.abs(z) == 2) {
                    mutablePos.set(pos.getX() + x, pos.getY(), pos.getZ() + z);
                    BlockState checkState = level.getBlockState(mutablePos);
                    
                    if (checkState.getBlock() == this) {
                        if (!checkState.getValue(HAS_EYE)) {
                            allFramesReady = false;
                        }
                    }
                }
            }
        }

        // Eğer tüm çerçeveler gözle doldurulduysa ortadaki 3x3 alanı portal bloğuna dönüştür
        if (allFramesReady) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    mutablePos.set(pos.getX() + x, pos.getY(), pos.getZ() + z);
                    // Ortaya mavi portal bloğunu koyuyoruz (ModBlocks altındaki portal bloğu ile eşleşmeli)
                    // level.setBlock(mutablePos, ModBlocks.BLUE_END_PORTAL.get().defaultBlockState(), 3);
                }
            }
        }
    }
}
