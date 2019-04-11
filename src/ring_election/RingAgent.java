/*
 * Decompiled with CFR 0_118.
 */
package davis;

import davis.nodes.Agent;
import davis.nodes.Place;
import davis.RingPlace;

public abstract class RingAgent<B>
extends Agent<B> {
    public void goLeft() {
        this.go(((RingPlace)this.place).left);
    }

    public void goRight() {
        this.go(((RingPlace)this.place).right);
    }
}

