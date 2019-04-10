/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Node
 *  jbotsim.Topology
 *  jbotsim.event.StartListener
 */
package davis.networks;

import davis.naming.IdScheme;
import davis.networks.NetworkGenerator;
import java.util.List;
import jbotsim.Node;
import jbotsim.Topology;
import jbotsim.event.StartListener;

public class DefaultGenerator<N extends Node>
extends NetworkGenerator<N> {
    @Override
    public void generate(Topology topology, IdScheme idScheme) {
        class ApplyIdScheme
        implements StartListener {
            Topology tp;
            IdScheme scheme;
            int networkSize;
            boolean already;

            public ApplyIdScheme(Topology topology, int n, IdScheme idScheme) {
                this.already = false;
                this.tp = topology;
                this.networkSize = n;
                this.scheme = idScheme;
            }

            public void onStart() {
                if (!this.already) {
                    int n = 0;
                    List<Integer> list = this.scheme.get(this.networkSize);
                    for (Node node : this.tp.getNodes()) {
                        node.setID(list.get(n).intValue());
                        ++n;
                    }
                    this.already = true;
                }
            }
        }
        topology.addStartListener((StartListener)new ApplyIdScheme(topology, this.networkSize, idScheme));
    }

    @Override
    public void generate(Topology topology, List<N> list) {
    }

}

