package com.anthonyhilyard.iceberg.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public final class CheckedBufferSource implements MultiBufferSource
{
	private boolean hasRendered = false;
	private final MultiBufferSource bufferSource;

	public CheckedBufferSource(MultiBufferSource bufferSource)
	{
		this.bufferSource = bufferSource;
	}

	@Override
	public VertexConsumer getBuffer(RenderType renderType)
	{
		final VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
		VertexConsumer vertexConsumerWrap = new VertexConsumer()
		{
			@Override
			public VertexConsumer vertex(double x, double y, double z)
			{
				hasRendered = true;
				return vertexConsumer.vertex(x, y, z);
			}

			@Override
			public VertexConsumer color(int r, int g, int b, int a) { return vertexConsumer.color(r, g, b, a); }

			@Override
			public VertexConsumer uv(float u, float v) { return vertexConsumer.uv(u, v); }

			@Override
			public VertexConsumer overlayCoords(int x, int y) { return vertexConsumer.overlayCoords(x, y); }

			@Override
			public VertexConsumer uv2(int u, int v) { return vertexConsumer.uv2(u, v); }

			@Override
			public VertexConsumer normal(float x, float y, float z) { return vertexConsumer.normal(x, y, z); }

			@Override
			public void endVertex() { vertexConsumer.endVertex(); }

			@Override
			public void defaultColor(int r, int g, int b, int a) { vertexConsumer.defaultColor(r, g, b, a); }

			@Override
			public void unsetDefaultColor() { vertexConsumer.unsetDefaultColor(); }
		};

		return vertexConsumerWrap;
	}
	
	public boolean hasRendered()
	{
		return hasRendered;
	}

	public void reset()
	{
		hasRendered = false;
	}
}
