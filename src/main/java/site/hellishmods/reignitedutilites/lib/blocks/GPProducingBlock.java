package site.hellishmods.reignitedutilites.lib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import site.hellishmods.reignitedutilites.lib.tileentities.GPProducingTile;

public class GPProducingBlock<T extends GPProducingTile> extends Block {
    T tile;
    RegistryObject<TileEntityType<T>> tileType;

    public GPProducingBlock(Properties properties, RegistryObject<TileEntityType<T>> tileType) {
        super(properties);
        this.tileType = tileType;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, IBlockReader reader, java.util.List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("block.reignitedutilites.gp_producing.tooltip", tileType.get().create().getGPOutput()));
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity player, ItemStack stack) {
        if (world instanceof ServerWorld) tile.getTileData().putString("owner", player.getStringUUID());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {return true;}
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        tile = tileType.get().create();
        return tile;
    }
}
