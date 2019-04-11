/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
package davis;

import davis.nodes.RingNode;
 */
package davis;

import davis.nodes.RingNode;


/**
 *
 * @author Lenovo
 */
public class Stage extends RingNode {
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset
    Integer isnextlevel=0;//0,1->wait for next level 2 get to next level
    Integer istermi=0;// check message from opposite sides
    Integer msgnumber=0;
    Integer [] stackmessage1;
    Integer [] stackmessage2;
    Integer [] message1={0,0,0};//uid dis maxdistance direction
    Integer [] message2={0,0,0};
    Integer [] termi={-1,0,0,};
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
	uid = getID(); // init id
	leader = uid;
        message1[0]=uid;
        message1[1]=0;
        message1[2]=0;
	sendLeft(message1);
        message2[0]=uid;
        message2[1]=1;
        message2[2]=0;
        sendRight(message2);// send right message
	become("active"); // initial state
    }

    public void active(Integer [] msg) {
       for(int i=0;i<3;i++)
            System.out.println(msg[i]);
       if (msg[0] > uid||msg[0]<uid) // not leader
            msgnumber++;
       if(msgnumber==1)
        stackmessage1=msg;
       if(msgnumber==2)
       {stackmessage2=msg;
           if(stackmessage1[0]>uid||stackmessage2[0]>uid)
           { if(stackmessage1[0]>uid)
                        if(stackmessage1[1]==0)
                             {
                                 stackmessage1[1]=1;
                                 stackmessage1[2]=1;
                                 sendRight( stackmessage1);

                             }
                         else
                             {
                                 stackmessage1[1]=0;
                                 stackmessage1[2]=1;
                                 sendLeft( stackmessage1);
                             }
               if(stackmessage2[0]>uid)
                    if(stackmessage2[1]==0)
                             {
                                 stackmessage2[1]=1;
                                 stackmessage2[2]=1;
                                 sendRight( stackmessage2);

                             }
                         else
                             {
                                 stackmessage2[1]=0;
                                 stackmessage2[2]=1;
                                 sendLeft( stackmessage2);
                             }
            log("Je ne suis PAS ELU...");
	    become("follower");
           }
           else
           {msgnumber=0;}
               
               
	}
       
        
	if (msg[0] == uid) { // own message
            
	    if(msg[2]==0)
            {istermi++;
            
                }
            else
            {   isnextlevel++;
            System.out.println(isnextlevel);
            
            }
            if(istermi==2)
            {  
               
               become("elected") ;
             }
            if(isnextlevel==2)
            {   isnextlevel=0;
                message1[0]=uid;
                message1[1]=0;
                message1[2]=0;
                sendLeft(message1);
                message2[0]=uid;
                message2[1]=1;
                message2[2]=0;
                sendRight(message2);
               
            }
	      // local termination
	
	}	    
    }
    
    public void follower(Integer [] msg) {
	for(int i=0;i<3;i++)
            System.out.println(msg[i]);    
        if(msg[0]==uid)
        {}
        else
           {
             if(msg[1]==0)
               sendLeft(msg);  
             else
               sendRight(msg);        
            }	    
    }

    public void elected(Integer[] msg){
          if (msg[0]==-1)
          {  terminate();
             terminated=true;
             System.out.println(round);
           
          }
    }

} 
