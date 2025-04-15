package com.dizzyhalo.dizzyhalo.client.renderer;

import com.dizzyhalo.dizzyhalo.Dizzyhalo;
import com.dizzyhalo.dizzyhalo.client.event.AiglosRenderEvent;
import com.dizzyhalo.dizzyhalo.common.register.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AiglosRenderer extends ThrownTridentRenderer {
    private final BakedModel bakedModel;

    public AiglosRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.bakedModel = pContext.getModelManager().getModel(AiglosRenderEvent.aiglosModel);
    }

    @Override
    public void render(
            @NotNull ThrownTrident pEntity,
            float pEntityYaw, float pPartialTicks,
            @NotNull PoseStack pPoseStack,
            @NotNull MultiBufferSource pBuffer,
            int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        Minecraft.getInstance().getItemRenderer().render(
                pEntity.getPickResult() == null ? new ItemStack(ModItems.AIGLOS::get) : pEntity.getPickResult(),
                ItemDisplayContext.NONE,
                false,
                pPoseStack,
                pBuffer,
                pPackedLight,
                OverlayTexture.NO_OVERLAY,
                bakedModel
        );
        pPoseStack.popPose();
    }
}
