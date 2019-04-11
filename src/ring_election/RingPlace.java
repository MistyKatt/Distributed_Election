/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Node
 */
package davis;

import davis.nodes.Place;
import davis.Ringable;
import jbotsim.Node;

public class RingPlace<B>
extends Place<B>
implements Ringable {
    Place<B> left = null;
    Place<B> right = null;

    @Override
    public Node getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(Node node) {
        this.left = (Place)node;
    }

    @Override
    public Node getRight() {
        return this.right;
    }

    @Override
    public void setRight(Node node) {
        this.right = (Place)node;
    }
}

