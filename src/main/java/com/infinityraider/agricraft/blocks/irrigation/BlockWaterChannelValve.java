package com.infinityraider.agricraft.blocks.irrigation;

import com.infinityraider.agricraft.items.blocks.ItemBlockCustomWood;
import com.infinityraider.agricraft.reference.Constants;
import com.infinityraider.agricraft.renderers.blocks.RenderChannelValve;
import com.infinityraider.agricraft.tiles.irrigation.TileEntityChannelValve;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import com.agricraft.agricore.core.AgriCore;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockWaterChannelValve extends AbstractBlockWaterChannel<TileEntityChannelValve> {

	public static final AxisAlignedBB BOX = new AxisAlignedBB(4 * Constants.UNIT, 0, 4 * Constants.UNIT, 12 * Constants.UNIT, 1, 12 * Constants.UNIT);

	public BlockWaterChannelValve() {
		super("valve");
	}

	@Override
	public void onNeighborChange(IBlockAccess iba, BlockPos pos1, BlockPos pos2) {
		super.onNeighborChange(iba, pos1, pos2);
		updatePowerStatus(iba, pos1);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		if (!world.isRemote) {
			updatePowerStatus(world, pos);
		}
	}

	private void updatePowerStatus(IBlockAccess iba, BlockPos pos) {
		TileEntity te = iba.getTileEntity(pos);
		if (te != null && te instanceof TileEntityChannelValve) {
			TileEntityChannelValve valve = (TileEntityChannelValve) te;
			valve.updatePowerStatus();
		}
	}

	//allows levers to be attached to the block
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side != EnumFacing.UP;
	}

	@Override
	public TileEntityChannelValve createNewTileEntity(World world, int meta) {
		return new TileEntityChannelValve();
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public RenderChannelValve getRenderer() {
		return new RenderChannelValve(this);
	}

	@Override
	public Class<? extends ItemBlockCustomWood> getItemBlockClass() {
		return ItemBlockValve.class;
	}

	public static class ItemBlockValve extends ItemBlockCustomWood {

		public ItemBlockValve(Block block) {
			super(block);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
			super.addInformation(stack, player, list, flag);
			list.add(AgriCore.getTranslator().translate("agricraft_tooltip.valve"));
		}
	}

}
