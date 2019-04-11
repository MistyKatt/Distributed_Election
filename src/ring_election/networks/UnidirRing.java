/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Link
 *  jbotsim.Link$Type
 */
package davis.networks;

import davis.networks.Ring;
import jbotsim.Link;

public class UnidirRing
extends Ring {
    public UnidirRing() {
        this.type = Link.Type.DIRECTED;
    }
}

