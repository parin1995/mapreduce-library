package Service;

import Constants.MRConstant;
import DataType.KeyValuePair;
import DataType.StringComp;
import Model.Output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mapper implements MRService{

    private int id;
    private int ioPort;
    private String workerType;
    private List<String> inputFilePath;
    private String udfClass;
    private String OUTPUT_PATH = "../../tmp";

    //Tejas:
    private ArrayList<KeyValuePair> kvpairs;
    private int offset;
    private int startLine;


    public Mapper(int id, String workerType, int ioPort, List<String> inputFilePath, String udfClass) {
        this.id = id;
        this.ioPort = ioPort;
        this.workerType = workerType;
        this.inputFilePath = inputFilePath;
        this.udfClass = udfClass;
    }

    @Override
    public void execute() throws FileNotFoundException, NoSuchMethodException {
        /*
        1. load file using inputFilePath
        2. docId, String {entire document)
        3. Using java reflection call
         */
        List<StringComp> combined_data = new ArrayList<>();
        for(String filepath: inputFilePath)
        {
            File inputFile = new File(filepath);
            Scanner sc = new Scanner(inputFile);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                combined_data.add(new StringComp(data));
            }
            sc.close();
        }
        try {
            Class cls = Class.forName(udfClass);
            Class args[] = new Class[3];
            args[0] = StringComp.class;
            args[1] = StringComp.class;
            args[2] = Output.class;

            Method map_method = cls.getDeclaredMethod("map", args);
            map_method.invoke(cls,args);

            map_method.invoke(cls, new StringComp("1"), combined_data, new Output());

        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    public void write()
    {
        try {
            String filename = "intermediate-mapper-"+String.valueOf(startLine)+"-"+String.valueOf(offset)+".txt";
            FileWriter fileWriter = new FileWriter("intermediate/"+filename);

            for(KeyValuePair kp: kvpairs)
            {
                fileWriter.write(kp.toKeyValueString());
            }

            fileWriter.close();
        }catch (Exception e){
            System.out.println(MRConstant.FILE_WRITE_EXCEPTION);
        }
    }
}
