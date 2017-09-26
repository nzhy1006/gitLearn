package e1;

import java.util.Scanner;

class th2 implements Runnable {

    public void run() { 
		    
		    while(th1.Run == true) {
		        try {
		        	System.out.print("stop");
                    Scanner sc = new Scanner(System.in); 
                    String ss = sc.nextLine();
                    if(ss.equals("s")){
      	                th1.Run = false;
                    }
        
                    sc.close();
                    Thread.sleep(1000);
                } catch (Exception ex) {
        	
                }
		    }
		    System.out.print("stop:" + th1.Run);

    }
}
