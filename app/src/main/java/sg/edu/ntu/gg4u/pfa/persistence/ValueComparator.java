package sg.edu.ntu.gg4u.pfa.persistence;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class ValueComparator implements Comparator<String> {
    Map<String, Double> base;

    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }
    
    public int firstWord(String i)
    {
    	String arr[] = i.split(" ", 2);
    	String temp = arr[0].replaceAll("[^\\d]", ""); 
    	System.out.println(temp);
    	int result = Integer.parseInt(temp);
    	return result;
    }
    


    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
    	int tempA = 0;
    	int tempB = 0;
    	    	
        for (Entry<String, Double> entry : this.base.entrySet()) {
        	String temp = entry.getKey();
        	if(temp == a)
        		tempA = firstWord(a);
        	else if(temp == b)
        		tempB = firstWord(b);
        }
        if (tempA >= tempB) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }
}