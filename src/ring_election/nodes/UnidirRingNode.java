/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Message
 *  jbotsim.Node
 */
package davis.nodes;

import davis.nodes.RingNode;
import jbotsim.Message;
import jbotsim.Node;

public abstract class UnidirRingNode
extends RingNode {
    public void send(Object object) {
        this.send(this.right, new Message(object));
    }
}

