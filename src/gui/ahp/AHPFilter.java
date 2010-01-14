package gui.ahp;

import java.io.FilenameFilter;
import java.io.File;

public class AHPFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
  	   if (name.endsWith(".ahp")) return true;
   	   return false;       
    }
}
class Main {
    public static void main (String[] args) {
        String dir = ".";
        if (args.length == 1)
            dir = args[0];
       
        File f1 = new File(dir);
        int i;
        String[] ls;
        FilenameFilter filter = new AHPFilter();
        System.out.println("AHP Files: " );
        for (ls = f1.list(filter), i = 0;
             ls != null && i < ls.length;
             System.out.println("\t" + ls[i++]));
    }
}

 
