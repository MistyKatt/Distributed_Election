/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Link
 *  jbotsim.Link$Type
 *  jbotsim.Node
 *  jbotsim.Topology
 */
package davis.networks;

import davis.naming.IdScheme;
import davis.naming.IdScheme;
import davis.networks.NetworkGenerator;
import davis.Ringable;
import davis.Ringable;
import davis.nodes.StatefulNode;
import davis.nodes.StatefulNode;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jbotsim.Link;
import jbotsim.Node;
import jbotsim.Topology;

public class RingGenerator<N extends StatefulNode> extends NetworkGenerator<StatefulNode> {
    Link.Type type = Link.Type.UNDIRECTED;

    @Override
    public void generate(Topology topology, IdScheme idScheme) {
        if (this.networkSize == 0) {
            System.out.println("Empty ?");
            return;
        }
        List<Integer> list = idScheme.get(this.networkSize);
        System.out.println("Ids:" + list);
        ArrayList<StatefulNode> arrayList = new ArrayList<StatefulNode>();
        for (int i = 0; i < this.networkSize; ++i) {
            if (this.defaultModel == null) {
                this.defaultModel = "default";
            }
            StatefulNode statefulNode = (StatefulNode)topology.newInstanceOfModel(this.defaultModel);
            statefulNode.setID(list.get(i).intValue());
            statefulNode.setKnowledge("size", this.networkSize);
            statefulNode.setKnowledge("sizeBound2", this.networkSize + new Random().nextInt(this.networkSize + 1));
            statefulNode.disableWireless();
            arrayList.add(statefulNode);
        }
        StatefulNode.totalMessages = 0;
        this.generate(topology, arrayList);
    }

   
    public void generate(Topology topology, List<StatefulNode> list) {
        Dimension dimension = topology.getDimensions();
        double d = dimension.getWidth();
        double d2 = dimension.getHeight();
        double d3 = d / 2.0;
        double d4 = d2 / 2.0;
        double d5 = 0.45 * Math.min(d, d2);
        topology.pause();
        StatefulNode statefulNode = (StatefulNode)((Object)list.get(list.size() - 1));
        int n = 0;
        for (StatefulNode statefulNode2 : list) {
            statefulNode2.setLocation(d3 + d5 * Math.cos(6.283185307179586 * (double)n / (double)this.networkSize), d4 + d5 * Math.sin(6.283185307179586 * (double)n / (double)this.networkSize));
            topology.addNode((Node)statefulNode2);
            topology.addLink(new Link((Node)statefulNode, (Node)statefulNode2, this.type));
            ((Ringable)((Object)statefulNode)).setRight(statefulNode2);
            ((Ringable)((Object)statefulNode2)).setLeft(statefulNode);
            statefulNode = statefulNode2;
            ++n;
        }
        topology.resume();
    }

}

