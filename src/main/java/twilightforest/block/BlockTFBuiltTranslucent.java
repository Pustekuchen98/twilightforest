package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BlockTFBuiltTranslucent extends BlockTFTowerTranslucent {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public BlockTFBuiltTranslucent() {
		super(Properties.create(Material.GLASS).hardnessAndResistance(50.0F, 2000.0F).sound(SoundType.METAL).noDrops());
		this.setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
	}

	//TODO: Remove?
//	@Override
//	@Deprecated
//	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
//		switch (state.getValue(VARIANT)) {
//			case REAPPEARING_INACTIVE:
//			case REAPPEARING_ACTIVE:
//				return BlockFaceShape.UNDEFINED;
//			default:
//				return super.getBlockFaceShape(worldIn, state, pos, face);
//		}
//	}

	@Override
	@Deprecated
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.isRemote) return;

		if (state.get(ACTIVE)) {
			world.removeBlock(pos, false);
			//world.notifyNeighborsRespectDebug(pos, this, false);
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.3F, 0.5F);
			//world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);

			// activate all adjacent inactive vanish blocks
			for (Direction e : Direction.values()) {
				BlockTFBuilder.checkAndActivateVanishBlock(world, pos.offset(e));
			}
		}
	}

	//TODO: Move to client
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
