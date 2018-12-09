package init.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;

public class notepadRead {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	     
	     System.out.println("Count Rows : "+countLines("c:\\data.txt"));
	     int count = countLines("c:\\data.txt");
	     
	     for(int i=0;i<count;i++){
	    	 String[] words = readData(i);
	    	 System.out.println(""+words[0]);
		     System.out.println(""+words[1]);
		     System.out.println(""+words[2]);
		     System.out.println(""+words[3]);
		     System.out.println(""+words[4]);
		     System.out.println(""+words[5]);
		     System.out.println(""+words[6]);
		     System.out.println(""+words[7]);
		     System.out.println(""+words[8]);
		     System.out.println(""+words[9]);
		     
		     System.out.println("-------------------------------");
	     }
	}
	
	public static String[] readData(int i) throws IOException{
		String str; 
		ArrayList<String> itemList = new ArrayList<String>();
		
		FileReader file=new FileReader("c:\\data.txt");
	     BufferedReader br=new BufferedReader(file);
	     
		 ///read line from the file upto null   
	     while((str=br.readLine())!=null)
	     {
	         //System.out.println(str);
	         itemList.add(str);
	     }
	     br.close();
		
	     String myStringArray = itemList.get(i);
	     String[] words=myStringArray.split("\\s");
		return words;
	}
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : ++count;
	    } finally {
	        is.close();
	    }
	}

}
