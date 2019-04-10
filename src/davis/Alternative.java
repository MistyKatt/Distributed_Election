/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
package davis;

import davis.nodes.RingNode;


/**
 *
 * @author Lenovo
 */

package davis;

import davis.nodes.RingNode;
public class Alternative extends RingNode {
    long starttime,endtime;
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset
    Integer [] message={0,0};//uid dis maxdistance direction
    
    Integer [] termi={-1,0};
    Integer round=0;
    boolean terminated=false;
    @Override
    public void onClock()
    {
       if(!terminated){ 
           round++;
       }
     
       
    }
    // Initialization method (MANDATORY)
    public void init(){
        starttime=System.currentTimeMillis();
	uid = getID(); // init id
	leader = uid;
        message[0]=uid;
        message[1]=0;
	sendLeft(message);
	become("active"); // initial state
    }

    public void active(Integer [] msg) {
      
       if (msg[0] > uid) // not leader
           {
            log("Je ne suis PAS ELU...");
	    become("follower");
           }
         
	if (msg[0] == uid) { // own message
               become("elected") ;
              endtime=System.currentTimeMillis();
              System.out.println("time:"+Math.abs(starttime-endtime)+'\n');
               System.out.println("message number:"+super.getmessagenumber());
             }
        if(msg[0]<uid)
        {   Integer[] message1={0,0};
            if(msg[1]==0)
            {   message1[0]=uid;
                message1[1]=1;
                sendRight(message1);
               
            }
            else
            {   message1[0]=uid;
                message1[1]=0;
                sendLeft(message1);
            } 
        }
          //System.out.println(msg[0]);
         // System.out.println(msg[1]);
	      // local termination
	
		    
    }
    
    public void follower(Integer [] msg) {
	
       
       
             if(msg[1]==0)
               sendLeft(msg);  
             else
               sendRight(msg);  
             
         // System.out.println(msg[0]);
         // System.out.println(msg[1]);
            	    
    }

    public void elected(Integer[] msg){
          if (msg[0]==-1)
          {  terminate();
             terminated=true;
           //  System.out.println(round);
           
          }
    }

} 
