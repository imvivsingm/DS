package DS;

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatingChar {
    public static Character firstNonRepeating(String s) {
        Map<Character,Integer> map=new HashMap<>();
        for(int i=0;i<s.length();i++){
            map.put(s.charAt(i),map.getOrDefault(s.charAt(i),0)+1);
        }
        for(Map.Entry<Character,Integer> entry:map.entrySet()) {
            if (entry.getValue() == 1) return entry.getKey();
        }
        return null;
    }
}
