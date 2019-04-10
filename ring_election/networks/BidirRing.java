/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davis.networks;

import davis.networks.Ring;
import jbotsim.Link;

public class BidirRing
extends Ring {
    public BidirRing() {
        this.type = Link.Type.UNDIRECTED;
    }
}
