/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package davis.networks;

import davis.naming.IdScheme;
import davis.nodes.StatefulNode;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jbotsim.Link;
import jbotsim.Node;
import jbotsim.Topology;

/**
 *
 * @author giuseppe
 */
public class MeshGenerator extends NetworkGenerator<StatefulNode>{
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

    @Override
    public void generate(Topology topology, List<StatefulNode> list) {
        Dimension dimension = topology.getDimensions();
        double d = dimension.getWidth();
        double d2 = dimension.getHeight();
        int x=(int) Math.sqrt(list.size());
        topology.pause();
        StatefulNode[][] sn=new StatefulNode[x][x];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                sn[i][j]=list.get(i+j*x);
                topology.addNode(sn[i][j]);
                sn[i][j].setLocation(sn[i][j].getSize()+i*(d/x), sn[i][j].getSize()+j*(d2/x));
            }
        }
        
        for(int i=0;i <x;i++){
            for(int j=0;j<x;j++){
            
            
            if(i-1>=0){
                topology.addLink(new Link(sn[i][j], sn[i-1][j],this.type ));
            }
            if(j-1 >=0){
                topology.addLink(new Link(sn[i][j], sn[i][j-1],this.type ));
            }
            
            if(i<x-1){
                topology.addLink(new Link(sn[i][j], sn[i+1][j],this.type ));
            }
            if(j<x-1){
                topology.addLink(new Link(sn[i][j], sn[i][j+1],this.type ));
            }
                
            }
        
        }
        topology.resume();
    }
    
}
