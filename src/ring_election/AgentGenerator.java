/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Topology
 */
package davis;

import davis.nodes.Agent;
import davis.networks.NetworkGenerator;
import davis.nodes.Place;
import java.util.List;
import jbotsim.Topology;

public abstract class AgentGenerator<B>
extends NetworkGenerator<Place<B>> {
    public abstract void generate(Topology var1, List<? extends Place<B>> var2, List<? extends Agent<B>> var3);
}

