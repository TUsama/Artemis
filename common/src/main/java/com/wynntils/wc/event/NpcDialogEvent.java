/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.wc.event;

import net.minecraftforge.eventbus.api.Event;

public class NpcDialogEvent extends Event {
    private final String codedDialog;

    public NpcDialogEvent(String codedDialog) {
        this.codedDialog = codedDialog;
    }

    public String getCodedDialog() {
        return codedDialog;
    }
}