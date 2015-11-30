import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.*;
import java.util.ArrayList;

public class removeDuplicatefiles {
    static void findDuplicates(File folder) {
        File[] listOfFiles;
        ArrayList<WFile> wFiles=new ArrayList<WFile>();
        int fileNumber = 0, fileIndex;
        String fileName = null, fileFullPath=null;
        int recordsNumber = 0;

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg") || name.endsWith(".JPG");
            }
        };

        try {
            listOfFiles = folder.listFiles(filter);
            fileNumber = listOfFiles.length;
            if (fileNumber == 0) {
                System.out.println("No XML files found in the selected folder");
                System.exit(1);
            }

            System.out.println(" -----------------------------------------------------------");
            System.out.println("processing " + fileNumber + " dublincore file(s) ...");
            System.out.println(" ------------------------------------------------------------");

            for (fileIndex = 0; fileIndex < fileNumber; fileIndex++) {
                System.out.println(" ------------------------------------------------------------");
                System.out.println(" ------------------------------------------------------------");
                File fileFullPath1 = listOfFiles[fileIndex];
                //fileName= listOfFiles[fileIndex].getName();
                System.out.println("Reading file # " + (fileIndex+1));
                System.out.println("File name = " + fileFullPath1.getName());
                Path p = Paths.get(listOfFiles[fileIndex].getPath());

                BasicFileAttributes view
                        = Files.getFileAttributeView(p, BasicFileAttributeView.class)
                        .readAttributes();
                System.out.println("File date = " + view.lastModifiedTime());
                //System.out.println("File size = " + view.size());
                //System.out.println("File name = " + fileFullPath1.getName().substring(0,8));
                wFiles.add(new WFile(fileFullPath1.getName().substring(0,8),view.lastModifiedTime().toString().substring(0,10),view.size(),p.toString(),fileFullPath1.getAbsolutePath()));

            }
            recordsNumber = fileIndex;

            System.out.println(" --------------------Finsished! -----------------------------");
            System.out.println(" Total number of files=" + recordsNumber);
            System.out.println(" ------------------------------------------------------------");
        } catch (Exception NotFolder) {
            System.out.println("Could not read the files ");
        }
        for(int i=0;i<wFiles.size();i++){
            System.out.println("****************"+wFiles.get(i).fileFullPath+"**************");
            System.out.println(wFiles.get(i).fileName);
            System.out.println(wFiles.get(i).fileDate);
            System.out.println(wFiles.get(i).fileName);

            for(int j=i+1;j<wFiles.size();j++){
                if(wFiles.get(i).fileName.equals(wFiles.get(j).fileName) && wFiles.get(i).fileDate.equals(wFiles.get(j).fileDate) && wFiles.get(i).fileSize==wFiles.get(j).fileSize) {
                    try {
                        Path p = Paths.get(wFiles.get(i).filePath);
                        Files.delete(p);
                        System.out.println("****************[ File ="+wFiles.get(i).fileName+" was deleted successfully! **************");

                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }
    }
    public static void main(String args[]) {
        //File metadataFolder = new File("G:\\Mobile\\Iphone-M\\Camera201412");
        File metadataFolder = new File("G:\\Mobile\\Cameraa");
        findDuplicates(metadataFolder);

    }
}
public class WFile {
    String fileName;
    String fileDate;
    double fileSize;
    String filePath;
    String fileFullPath;
    WFile(String f, String d, double s,String p,String fp){
        fileName=f;
        fileDate=d;
        fileSize=s;
        filePath=p;
        fileFullPath=fp;
    }
}
