/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davis;

import davis.nodes.UnidirRingNode;

public class QElection extends UnidirRingNode {
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset

    // Initialization method (MANDATORY)
    public void init(){
	uid = getID(); // init id
	leader = uid;
	send(uid); // send own id
	become("active"); // initial state
    }

    public void active(Integer msg) {
	if (msg > uid) { // not leader
	    log("Je ne suis PAS ELU...");
	    become("follower");
	}
	if (msg == uid) { // own message
	    log("Je suis ELU!");
	    become("elected");
	    terminate();  // local termination
	} else { // sending to next node
	    send(msg); 
	}	    
    }
    
    public void follower(Integer msg) {
	if (msg == uid) { // own message
	    log("TOUR COMPLET");
	    terminate();  // local termination
	} else { // sending to next node
	    leader = Math.max(leader,msg); // update leader id
	    send(msg); 
	}	    
    }

    public void elected(Integer msg){
    }

}