package pcl.lc.module.stargate.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import pcl.lc.base.GenericBlockRenderer;
import pcl.lc.core.ResourceAccess;
import pcl.lc.module.ModuleStargates;
import cpw.mods.fml.client.FMLClientHandler;

public class BlockStargateDHDRenderer extends GenericBlockRenderer {

	private ResourceLocation texture;

	public BlockStargateDHDRenderer() {
		texture = ResourceAccess.getNamedResource(ResourceAccess
				.formatResourceName("textures/models/dhd_off_${TEX_QUALITY}.png"));
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int renderID, RenderBlocks rb) {
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks rb) {
		ModelStargateDHD model = ModuleStargates.Render.modelController;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(0.5f, 0.5f, 0.0f);
		GL11.glRotatef(45, 0, 1, 0);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		GL11.glTranslatef(-0.25f, -0.5f, 0.0f);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		model.renderAll();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
