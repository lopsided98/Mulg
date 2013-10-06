/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ben Wolsieffer
 */
public class Channel {

    private boolean activated;

    private final ArrayList<ChannelActivationListener> activationListeners = new ArrayList<ChannelActivationListener>();

    public Channel() {
    }

    public boolean toggleActivated() {
        activated = !activated;
        ChannelActivationEvent cae = new ChannelActivationEvent();
        for (ChannelActivationListener cal : activationListeners) {
            if (activated) {
                cal.onActivated(cae);
            } else {
                cal.onDeactivated(cae);
            }
            if (cae.isActivationCancelled()) {
                activated = !activated;
                return false;
            }
        }
        return true;
    }
    
    public void addActivationListener(ChannelActivationListener cal) {
        activationListeners.add(cal);
    }

    public List<ChannelActivationListener> getActivationListeners() {
        return Collections.unmodifiableList(activationListeners);
    }
}
