package mainPac;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import character.CharacterPanel;
import map.MapPanel;

public class XMLReader {

	public XMLReader(MapPanel mp, CharacterPanel cp){
		try {	
	         File inputFile = new File("src/levels/Level1.xml");
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         org.w3c.dom.Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         //
	         System.out.println("Root element :" 
	            + doc.getDocumentElement().getNodeName());
	         //
	         NodeList nList = doc.getElementsByTagName("units");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	        	    org.w3c.dom.Node nNode = nList.item(temp);
	        	    Scanner sc = new Scanner(nNode.getTextContent());
	        	    
	        		boolean makingPair=false;
	        		int xStartValue=-1, xEndValue=-1, yStartValue=-1, yEndValue=-1;
	        	    while(sc.hasNext()){
	        	    	String a = sc.next();
	        	    	if(a.charAt(0)=='X' && !makingPair){
	        	    		if(a.contains("-")){
	        	    	xStartValue = Integer.parseInt(a.substring(1, a.indexOf('-')));
	        	    	xEndValue = Integer.parseInt(a.substring(a.indexOf('-')+1, a.length()));
	        	    		System.out.println("x-: "+xStartValue+" "+xEndValue);
	        	    	
	        	    		}else{
	        	    			xStartValue = Integer.parseInt(a.substring(1));
	        	    			xEndValue = xStartValue;
	        	    			System.out.println("x: "+xEndValue);
	        	    		}
	        	    		makingPair=true;
	        	    	}else if(a.charAt(0)=='Y' && makingPair){
	        	    		if(a.contains("-")){
		        	 	yStartValue = Integer.parseInt(a.substring(1, a.indexOf('-')));
		        	    yEndValue = Integer.parseInt(a.substring(a.indexOf('-')+1, a.length()));
		        	    		System.out.println("y-: "+yStartValue+" "+yEndValue);
		        	    		}else{
		        	    			yStartValue = Integer.parseInt(a.substring(1));
		        	    			yEndValue = yStartValue;
		        	    			System.out.println("y: "+yEndValue);
		        	    		}
	        	    		
	        	    		makingPair=false;
	        	    	}else{
	        	    		System.out.println("check XML: can't read unit data!!");
	        	    	}
	        	    	
	        	    	if(makingPair==false){//this means a pair has been made
	        	    		//set the mapPanel units
	        	    		for(int x=xStartValue; x<=xEndValue; x++){
	        	    			for(int y=yStartValue; y<=yEndValue; y++){
	        	  mp.map[x][y]=Integer.parseInt(nNode.getAttributes().item(0).getNodeValue());
	        	    			}
	        	    		}
	        	    	}
	        	    }//end of scanning of this node
	        	    System.out.println("change node");
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}//end of constructor
	
	
	
}
