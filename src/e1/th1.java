package e1;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class th1 implements Runnable {
	public static boolean Run = true;
	
	String ans = "";
	Random ran = new Random();
	int sta = 0, flag = 0;
	boolean temp = false;
	HashMap<Integer, Integer> tpm;
	
	Graph g;
	
	Thread go, stop;
	
	th1(Graph g) {
		go = new Thread(this);
		stop = new Thread(this);
		this.g = g;
		this.sta = ran.nextInt(g.wordsid);
		this.tpm = g.vec.elementAt(sta);
		this.ans = this.ans + g.wordlist.get(this.sta);
	}
	 
	public void run() {
		//while(Run == true) {
		    //if(Run == true) {
			   // System.out.println("go");
			//System.out.println("startttt");
		    if(Thread.currentThread() == go) {
		    	flag += 1;
			    while(g.vec.elementAt(sta).isEmpty() != true && temp != true && Run == true) {
				    int index = ran.nextInt(g.vec.elementAt(sta).size());
				    int count = 0;
				    tpm = g.vec.elementAt(sta);
				
				    for (HashMap.Entry<Integer, Integer> entry : tpm.entrySet()) { 
					    if(count == index) {
						    ans = ans + g.wordlist.get(entry.getKey());
						    if(g.mark.elementAt(sta).get(entry.getKey()) == true) { //contain this edge
							    Run = false;
						    	System.out.println(Run + ans + "aaa");
						    } else {
							    g.mark.elementAt(sta).put(entry.getKey(), true);
							    sta = entry.getKey();
							    System.out.println(Run + ans);
						    }
						    break;
					    }
					    count += 1;
				    } 
				    try {
				    	System.out.print(g.vec.elementAt(sta).isEmpty());
				    	System.out.print(Run + "000");
				    	if(Run == false || g.vec.elementAt(sta).isEmpty() == true) {
				    		Run = false;
				    		System.out.print("ret:" + Run);
				    		return;
				    	}
				        Thread.sleep(1000);
			        } catch (Exception ex) {
            	
                    }
			    } 
		    
		    }
		    if(Thread.currentThread() == stop) {
		    	while(Run == true) {
			        try {
			        	if(flag != 0) {
			        	    System.out.print("stop" + flag);
	                        Scanner sc = new Scanner(System.in); 
	                        String ss = sc.nextLine();
	                        if(ss.equals("s")){
	      	                    Run = false;
	      	                    sc.close();
	      	                    return;
	                        }
	        
	                        sc.close();
			        	}
	                    Thread.sleep(1000);
	                } catch (Exception ex) {
	        	
	                }
			    }
			    System.out.print("stop:" + Run);
		    }
	}
}
