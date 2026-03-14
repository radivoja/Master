package com.project.parser;

import com.project.model.Model;
import com.project.model.Property;
import com.project.model.Stereotype;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public final class Printer {
    // Testing methhod
    public static void printInfo(Map<String, Model> models) throws IOException {
        String fileName = "C:\\Users\\Aleksandar\\Desktop\\testGenerated.txt";
        File file = new File(fileName);
        boolean exist = file.exists();
        if(!exist){
            file.createNewFile();
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
        for (Model model : models.values()) {
            printWriter.println(model.getName() + " " + model.getId());
        }
        printWriter.println();
        for (Model model : models.values()) {
            for(Property property : model.getProperties()){
                if(property.getStereotypes().size() > 0){
                    printWriter.print(model.getName() + " " + property.getName()+ "--->");
                    for(Stereotype stereotype : property.getStereotypes()) {
                        printWriter.print(stereotype.getName()+ ", ");
                    }
                    printWriter.println();
                    printWriter.println();
                }
            }

        }
        printWriter.close();
    }

}
