/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Node
 */
package davis.nodes;

import davis.Ringable;
import davis.Ringable;
import davis.nodes.StatefulNode;
import jbotsim.Node;

public abstract class RingNode
extends StatefulNode
implements Ringable {
    int size = 0;
    Node left = null;
    Node right = null;
    Object sentToRight = null;
    Object sentToLeft = null;
   
    public void setSize(int n) {
        this.size = n;
        
    }

    @Override
    public Node getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(Node node) {
        this.left = node;
        if (this.sentToLeft != null) {
            this.send(this.left, this.sentToLeft);
        }
    }

    @Override
    public Node getRight() {
        return this.right;
    }

    @Override
    public void setRight(Node node) {
        this.right = node;
        if (this.sentToRight != null) {
            this.send(this.right, this.sentToRight);
        }
    }

    public void sendRight(Object object) {
        if (this.right == null) {
            this.sentToRight = object;
        } else {
            this.send(this.right, object);
        }
    }

    public void sendLeft(Object object) {
        if (this.left == null) {
            this.sentToLeft = object;
        } else {
            this.send(this.left, object);
        }
    }
}

