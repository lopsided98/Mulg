/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.game;

/**
 *
 * @author Ben Wolsieffer
 */
public abstract class ChannelActivationListener {

    private Channel channel;

    public boolean toggleActivated() {
        if (channel != null) {
            return channel.toggleActivated();
        }
        return false;
    }

    void setChannel(Channel channel) {
        this.channel = channel;
    }

    public abstract void onActivated(ChannelActivationEvent cae);

    public abstract void onDeactivated(ChannelActivationEvent cae);
}
