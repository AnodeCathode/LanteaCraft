package pcl.lc.base;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import pcl.lc.base.render.GlyphRenderer;
import pcl.lc.core.ResourceAccess;

public abstract class GenericScreen extends GuiScreen {

	final static int defaultTextColor = 0x404040;

	double uscale, vscale;
	float red = 1.0F, green = 1.0F, blue = 1.0F;
	protected int textColor = defaultTextColor;
	boolean textShadow = false;

	public GenericScreen() {
		super();
	}

	@Override
	public void drawScreen(int x, int y, float ft) {
		resetColor();
		textColor = defaultTextColor;
		textShadow = false;
		super.drawScreen(x, y, ft);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void close() {
		mc.thePlayer.closeScreen();
	}

	public void drawFramedSymbols(int x, int y, String address) {
		int scale = 2;
		bindTexture(ResourceAccess.getNamedResource("textures/gui/symbol_frame.png"), 512 / scale, 128 / scale);
		drawTexturedRect(x - (472 / scale) / 2, y, 472 / scale, 88 / scale, 0, 0);
		GlyphRenderer.drawAddress(mc, address, x - (472 / scale) / 2, y, 9, scale, zLevel);
	}

	public void drawAddressString(int x, int y, String address, int len, String padding, String caret) {
		StringBuilder result = new StringBuilder();
		result.append(address);
		if (len != result.length() && caret != null)
			result.append(caret);
		while (len > result.length())
			result.append(padding);
		drawCenteredString(fontRendererObj, result.toString(), x, y, 0xffffff);
	}

	protected void bindTexture(ResourceLocation rsrc) {
		bindTexture(rsrc, 1, 1);
	}

	public void bindTexture(ResourceLocation rsrc, int usize, int vsize) {
		mc.getTextureManager().bindTexture(rsrc);
		uscale = 1.0 / usize;
		vscale = 1.0 / vsize;
	}

	public void drawTexturedRect(double x, double y, double w, double h) {
		drawTexturedRectUV(x, y, w, h, 0, 0, 1, 1);
	}

	public void drawTexturedRect(double x, double y, double w, double h, double u, double v) {
		drawTexturedRect(x, y, w, h, u, v, w, h);
	}

	public void drawTexturedRect(double x, double y, double w, double h, double u, double v, double us, double vs) {
		drawTexturedRectUV(x, y, w, h, u * uscale, v * vscale, us * uscale, vs * vscale);
	}

	public void drawTexturedRectUV(double x, double y, double w, double h, double u, double v, double us, double vs) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_F(red, green, blue);
		tess.addVertexWithUV(x, y + h, zLevel, u, v + vs);
		tess.addVertexWithUV(x + w, y + h, zLevel, u + us, v + vs);
		tess.addVertexWithUV(x + w, y, zLevel, u + us, v);
		tess.addVertexWithUV(x, y, zLevel, u, v);
		tess.draw();
	}

	public void setColor(int hex) {
		setColor((hex >> 16) / 255.0, (hex >> 8 & 0xff) / 255.0, (hex & 0xff) / 255.0);
	}

	public void setColor(double r, double g, double b) {
		red = (float) r;
		green = (float) g;
		blue = (float) b;
	}

	public void resetColor() {
		setColor(1, 1, 1);
	}

	public void drawString(String s, int x, int y) {
		fontRendererObj.drawString(s, x, y, textColor, textShadow);
	}

	public void drawCenteredString(String s, int x, int y) {
		fontRendererObj.drawString(s, x - fontRendererObj.getStringWidth(s) / 2, y, textColor, textShadow);
	}

	public void drawInventoryName(IInventory inv, int x, int y) {
		drawString(inventoryName(inv), x, y);
	}

	public static String inventoryName(IInventory inv) {
		return StatCollector.translateToLocal(inv.getInventoryName());
	}

	public static String playerInventoryName() {
		return StatCollector.translateToLocal("container.inventory");
	}
}