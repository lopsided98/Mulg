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
public class ChannelActivationEvent {

    private boolean cancelled = false;

    public void cancelActivation() {
        cancelled = true;
    }

    public boolean isActivationCancelled() {
        return cancelled;
    }
    
}
