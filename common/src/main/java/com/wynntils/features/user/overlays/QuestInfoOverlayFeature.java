/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.features.user.overlays;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.core.config.ConfigHolder;
import com.wynntils.core.features.UserFeature;
import com.wynntils.core.features.overlays.Overlay;
import com.wynntils.core.features.overlays.OverlayPosition;
import com.wynntils.core.features.overlays.annotations.OverlayInfo;
import com.wynntils.core.features.overlays.sizes.GuiScaledOverlaySize;
import com.wynntils.core.features.properties.FeatureInfo;
import com.wynntils.mc.event.RenderEvent;
import com.wynntils.mc.render.FontRenderer;
import com.wynntils.mc.render.HorizontalAlignment;
import com.wynntils.mc.render.TextRenderSetting;
import com.wynntils.mc.render.TextRenderTask;
import com.wynntils.mc.render.VerticalAlignment;
import com.wynntils.utils.objects.CommonColors;
import com.wynntils.wc.utils.scoreboard.quests.QuestInfo;
import com.wynntils.wc.utils.scoreboard.quests.QuestManager;
import java.util.List;

@FeatureInfo(category = "Overlays")
public class QuestInfoOverlayFeature extends UserFeature {
    @OverlayInfo(renderType = RenderEvent.ElementType.GUI)
    private final Overlay questInfoOverlay = new QuestInfoOverlay();

    public static class QuestInfoOverlay extends Overlay {
        public QuestInfoOverlay() {
            super(
                    new OverlayPosition(
                            5,
                            -5,
                            VerticalAlignment.Top,
                            HorizontalAlignment.Right,
                            OverlayPosition.AnchorSection.TopRight),
                    new GuiScaledOverlaySize(300, 50),
                    HorizontalAlignment.Left,
                    VerticalAlignment.Middle);
        }

        List<TextRenderTask> toRender = List.of(
                new TextRenderTask(
                        "Tracked Quest Info:",
                        TextRenderSetting.getWithHorizontalAlignment(
                                this.getWidth(), CommonColors.GREEN, this.getRenderHorizontalAlignment())),
                new TextRenderTask(
                        "",
                        TextRenderSetting.getWithHorizontalAlignment(
                                this.getWidth(), CommonColors.ORANGE, this.getRenderHorizontalAlignment())),
                new TextRenderTask(
                        "",
                        TextRenderSetting.getWithHorizontalAlignment(
                                this.getWidth(), CommonColors.WHITE, this.getRenderHorizontalAlignment())));

        List<TextRenderTask> toRenderPreview = List.of(
                new TextRenderTask(
                        "Tracked Quest Info:",
                        TextRenderSetting.getWithHorizontalAlignment(
                                this.getWidth(), CommonColors.GREEN, this.getRenderHorizontalAlignment())),
                new TextRenderTask(
                        "Test quest:",
                        TextRenderSetting.getWithHorizontalAlignment(
                                this.getWidth(), CommonColors.ORANGE, this.getRenderHorizontalAlignment())),
                new TextRenderTask(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer tempus purus in lacus pulvinar dictum. Quisque suscipit erat pellentesque egestas volutpat.",
                        TextRenderSetting.getWithHorizontalAlignment(
                                this.getWidth(), CommonColors.WHITE, this.getRenderHorizontalAlignment())));

        private void recalculateRenderTasks() {
            toRender.get(0)
                    .setSetting(TextRenderSetting.getWithHorizontalAlignment(
                            this.getWidth(), CommonColors.GREEN, this.getRenderHorizontalAlignment()));

            toRender.get(1)
                    .setSetting(TextRenderSetting.getWithHorizontalAlignment(
                            this.getWidth(), CommonColors.ORANGE, this.getRenderHorizontalAlignment()));

            toRender.get(2)
                    .setSetting(TextRenderSetting.getWithHorizontalAlignment(
                            this.getWidth(), CommonColors.WHITE, this.getRenderHorizontalAlignment()));
        }

        private void recalculatePreviewRenderTasks() {
            toRenderPreview
                    .get(0)
                    .setSetting(TextRenderSetting.getWithHorizontalAlignment(
                            this.getWidth(), CommonColors.GREEN, this.getRenderHorizontalAlignment()));

            toRenderPreview
                    .get(1)
                    .setSetting(TextRenderSetting.getWithHorizontalAlignment(
                            this.getWidth(), CommonColors.ORANGE, this.getRenderHorizontalAlignment()));

            toRenderPreview
                    .get(2)
                    .setSetting(TextRenderSetting.getWithHorizontalAlignment(
                            this.getWidth(), CommonColors.WHITE, this.getRenderHorizontalAlignment()));
        }

        @Override
        protected void onConfigUpdate(ConfigHolder configHolder) {
            recalculateRenderTasks();
        }

        @Override
        public void render(PoseStack poseStack, float partialTicks, Window window) {
            QuestInfo currentQuest = QuestManager.getCurrentQuest();

            if (currentQuest == null) {
                return;
            }

            toRender.get(1).setText(currentQuest.quest());
            toRender.get(2).setText(currentQuest.description());

            FontRenderer.getInstance()
                    .renderTextsWithAlignment(
                            poseStack,
                            this.getRenderX(),
                            this.getRenderY(),
                            toRender,
                            this.getRenderedWidth(),
                            this.getRenderedHeight(),
                            this.getRenderHorizontalAlignment(),
                            this.getRenderVerticalAlignment());
        }

        @Override
        public void renderPreview(PoseStack poseStack, float partialTicks, Window window) {
            recalculatePreviewRenderTasks(); // we have to force update every time

            FontRenderer.getInstance()
                    .renderTextsWithAlignment(
                            poseStack,
                            this.getRenderX(),
                            this.getRenderY(),
                            toRenderPreview,
                            this.getRenderedWidth(),
                            this.getRenderedHeight(),
                            this.getRenderHorizontalAlignment(),
                            this.getRenderVerticalAlignment());
        }
    }
}