/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Node
 *  jbotsim.Topology
 */
package davis.networks;

import davis.naming.IdScheme;
import davis.naming.IdScheme;
import java.util.List;
import jbotsim.Node;
import jbotsim.Topology;

public abstract class NetworkGenerator<N extends Node> {
    public int networkSize;
    public int running;
    public String defaultModel;

    public abstract void generate(Topology var1, IdScheme var2);

    public abstract void generate(Topology var1, List<N> var2);

    void setNetworkSize(int n) {
        this.networkSize = n;
    }

    int getNetworkSize() {
        return this.networkSize;
    }
}

