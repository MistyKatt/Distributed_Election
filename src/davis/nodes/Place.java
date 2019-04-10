/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Message
 *  jbotsim.Node
 */
package davis.nodes;

import davis.nodes.StatefulNode;
import java.awt.geom.Point2D;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import jbotsim.Message;
import jbotsim.Node;

public class Place<B>
extends StatefulNode {
    public B board;
    public List<Agent<B>> agents = new ArrayList<Agent<B>>();

    public void send(Node node, Object object) {
        this.send(node, new Message(object));
    }

    @Override
    public void onMessage(Message message) {
        try {
            assert (message != null);
            Agent agent = (Agent)((Object)message.getContent());
            if (message.getSender() != message.getDestination()) {
                System.out.println("MOVE  #" + ++totalMessages + "\t" + (Object)message);
                this.agents.add(agent);
                agent.setLocation(this.getLocation());
                agent.translate(10.0, 10.0);
                this.send(this, (Object)agent);
            } else {
                System.out.println("EXEC " + (Object)((Object)agent) + " in " + (Object)((Object)this));
                if (agent.state != "terminated") {
                    Collection collection = ((HashMap)Agent.actionsMap.get(agent.state)).values();
                    Method method = (Method)collection.iterator().next();
                    method.invoke((Object)agent, this.board);
                }
                this.setState((Object)this);
            }
        }
        catch (Exception var2_3) {
            var2_3.printStackTrace();
        }
    }

    public void addAgent(Agent<B> agent) {
        this.agents.add(agent);
        agent.setLocation(this.getLocation());
    }

    @Override
    public void init() {
        for (Agent<B> agent : this.agents) {
            this.log("Starting agent " + agent);
            this.send(this, agent);
        }
    }

    @Override
    public String toString() {
        return "Place " + this.getID();
    }
}

