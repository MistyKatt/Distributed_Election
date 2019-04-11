/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Node
 */
package davis.nodes;

import davis.nodes.Place;
import davis.nodes.StatefulNode;
import java.awt.geom.Point2D;
import java.util.List;
import jbotsim.Node;

public abstract class Agent<B>
extends StatefulNode {
    public Place<B> place;

    public void go(Place<B> place) {
        this.log("GOTO " + place);
        this.place.agents.remove((Object)this);
        this.place.send(place, (Object)this);
        this.place = place;
        Point2D point2D = this.place.getLocation();
        Point2D point2D2 = place.getLocation();
        this.setLocation((point2D.getX() + point2D2.getX()) / 2.0, (point2D.getY() + point2D2.getY()) / 2.0);
        this.translate(10.0, 10.0);
    }

    public void stay() {
        this.place.send(this.place, (Object)this);
    }

    public List<Agent<B>> getLocalAgents() {
        return this.place.agents;
    }
}

