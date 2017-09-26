package e1; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

class Graph { 
	
	int wordsid = 0; 
	Vector<HashMap<Integer, Integer>> vec = new Vector<HashMap<Integer, Integer>>();  //heads 
	HashMap<String, Integer> words = new HashMap<String, Integer>();  // trans words to int 
	Vector<String> wordlist = new Vector<String>(); 
	
	Vector<HashMap<Integer, Boolean>> mark = new Vector<HashMap<Integer, Boolean>>(); //func6 mark edge
	
	void addN(String x) { 
		if (words.containsKey(x) == false) { 
			words.put(x, wordsid); 
			wordlist.add(x); 
			HashMap<Integer, Integer> tpm = new HashMap<Integer, Integer>(); 
			vec.addElement(tpm); 
			HashMap<Integer, Boolean> temp = new HashMap<Integer, Boolean>();
			mark.addElement(temp);
			wordsid = wordsid + 1; 
		} 
	} 

	void addE(String u, String v) { 
		addN(u); 
		addN(v); 
		int uid = words.get(u); 
		int vid = words.get(v); 
		int Evalue = 1; 
		if (vec.elementAt(uid).containsKey(vid) == true) { 
			Evalue = (vec.elementAt(uid).get(vid))+ 1; 
		}  else {
			mark.elementAt(uid).put(vid, false);
		}
		vec.elementAt(uid).put(vid, Evalue); 
	} 

	void showEs(String x) { 
		if (words.containsKey(x) == false) { 
			System.out.println(x + "dose not exist!"); 
			return; 
		} 
		int xid = words.get(x); 
		HashMap<Integer, Integer> tpm = vec.elementAt(xid);	 
		for (HashMap.Entry<Integer, Integer> entry : tpm.entrySet()) { 
		    System.out.println("Key = " + entry.getKey() + wordlist.get(entry.getKey()) + ", Value = " + entry.getValue());  
		}  
	} 
/*
	void tst() { 
		addE("aa","bb"); 
		addE("bb", "cc"); 
		addE("aa", "cc"); 
		addE("aa", "cc"); 
		showEs("aa"); 
	} */
} 

public class e1 { 
	public static String line(String str) { 
		str = str.toLowerCase(); 
		String ss = ""; 
		boolean flag = false; 
		for(int i = 0; i < str.length(); i++) { 
			if((str.charAt(i) >= 'a' && str.charAt(i) <= 'z') || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')) { 
				ss = ss + str.charAt(i); 
				flag = true; 
			} else { 
				if(flag == true) {
					ss = ss + ' '; 
					flag = false; 
				} 
			} 
		} 
		return ss;
	} 

	public static Graph readTxtFile(String filePath){ 
		Graph g = new Graph();
        try { 
            String encoding="GBK"; 
            File file=new File(filePath); 
            if(file.isFile() && file.exists()){ //判断文件是否存在 
            	
                InputStreamReader read = new InputStreamReader( 
                new FileInputStream(file),encoding);//考虑到编码格式 
                BufferedReader bufferedReader = new BufferedReader(read); 
                
                String lineTxt = null; 
                String end = null;
                
                while((lineTxt = bufferedReader.readLine()) != null){ 
                	String str = line(lineTxt); 
                	if(str != ""){

                		String[] spl = str.split(" ");
                		if(end != null) {
                			g.addE(end, spl[0]);
                		}
                		for(int i = 0; i < spl.length - 1; i++) {
                            g.addE(spl[i], spl[i + 1]);
                		}
                		end = spl[spl.length - 1];
                	}
                } 
                read.close(); 
            } else { 
                System.out.println("找不到指定的文件"); 
            } 
        } catch (Exception e) { 
            System.out.println("读取文件内容出错"); 
            e.printStackTrace(); 
        } 
        return g;
    } 
	
	
	
	public static String bridge(Graph g, String s1, String s2) {
		if(g.words.containsKey(s1) == false || g.words.containsKey(s2) == false) {
			return "No word1 or word2 in the graph";
		}
		
		int xid = g.words.get(s1);
		int yid = g.words.get(s2);
		HashMap<Integer, Integer> tpm = g.vec.elementAt(xid);
		
		String str = "";
		
		for (HashMap.Entry<Integer, Integer> entry : tpm.entrySet()) { 
			if(g.vec.elementAt(entry.getKey()).containsKey(yid)) {
				str += g.wordlist.elementAt(entry.getKey());
			}
		} 
		
		if(str == "") {
			return "no bridge";
		}else{
			return "bridge" + str;
		}
	}
	
	public static String newtext(Graph g, String str) {
		String[] div = str.split(" ");
		String ans = "";
		for(int i = 0; i < div.length - 1; i++) {
			ans = ans + div[i];
			ans = ans + " ";
			if(bridge(g, div[i], div[i + 1]) != "no bridge" &&
			   bridge(g, div[i], div[i + 1]) != "No word1 or word2 in the graph") {
				ans = ans + bridge(g, div[i], div[i + 1]);
				ans = ans + " ";
			}
		}
		ans = ans + div[div.length - 1];
		return ans;
	}
	
	public static String shortestpath(Graph g, String start, String end) {
		
		int INF = 99999;
		String ans = "";
		int P[] = new int[g.wordsid];
		int D[] = new int[g.wordsid];
		boolean mark[] = new boolean[g.wordsid];
		
		int sid = g.words.get(start);
		int eid = g.words.get(end);
		
		for(int i = 0; i < g.wordsid; i++) {
			mark[i] = false;
			P[i] = 0;
			if(g.vec.elementAt(sid).containsKey(i) == false) {
				D[i] = INF;
			} else {
				D[i] = g.vec.elementAt(sid).get(i);
			}
			//System.out.println(g.wordlist.get(i) + D[i]);
		}

		D[sid] = 0;
		mark[sid] = true;
		int min;
		int nextid = sid;

		for(int i = 0; i < g.wordsid; i++) {
			min = 99999;
			for(int j = 0; j < g.wordsid; j++) {
				if(mark[j] == false && D[j] < min) {
					nextid = j;
					min = D[j];
				}
			}
			mark[nextid] = true;

			for(int j = 0; j < g.wordsid; j++) {
				int t;
			    if(g.vec.elementAt(nextid).containsKey(j) == false) {
		     		t = INF;
				} else {
					t = g.vec.elementAt(nextid).get(j);
				}
				if(mark[j] == false && (min + t < D[j])) {
					D[j] = min + t;
					P[j] = nextid;
				}
			}
		}

		int temp = eid, jj = 1;
		int[] PP = new int[g.wordsid];
		
		
		while(P[temp] != 0) {
			PP[g.wordsid - jj] = P[temp];
			temp = P[temp];
			jj += 1;
		}
		System.out.println(start);
		for(int i = 0; i < g.wordsid; i++) {
			if(PP[i] != 0) {
				System.out.println(g.wordlist.get(PP[i]));
			}
		}
		System.out.println(end);

		return ans;
	}
	
	
	public static String random(Graph g) {
		th1 t = new th1(g);
		t.go.start();
		t.stop.start();

		try {  
            t.stop.join();  
            t.go.join();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 
		System.out.println("ssssssssssssssssss");
		return t.ans;
		
	}
	

	public static void main(String argv[]){ 
 
	    String name = "D:/1.txt";

	    Graph g = readTxtFile(name); 
	    //g.showEs("to");
	    //String s = bridge(g,"a","j");
	    //System.out.println(g.vec.elementAt(1).isEmpty());  
	    //String ss = "seek to explore new and exciting synergies";
	   // String ans = newtext(g,ss);
	    //System.out.println(ans);
	    
	   // shortestpath(g, "a", "j");


	    String ss = random(g);
	    System.out.println("ans:" + ss);
	} 
}