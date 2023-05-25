package com.sadshrimpy.referralxpert.utils.files.handlers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class JsonHandler<T> {
    private final Type type;
    private final Gson gson;
    private final File file;
    private final String fileName;
    private final String filePath = null;

    public JsonHandler(Type type, String fileName, String filePath) {
        this.fileName = fileName;
        this.type = type;
        this.gson = new Gson();
        this.file = new File(sadLibrary.generics().getPluginFolder(), fileName);
        //this.filePath = filePath;
    }

    public JsonHandler(Type type, String fileName) {
        this.fileName = fileName;
        this.type = type;
        this.gson = new Gson();
        this.file = new File(sadLibrary.generics().getPluginFolder(), fileName);
        //this.filePath = String.valueOf(sadLibrary.generics().getPluginFolder());
    }

    /*
    public void initialize() throws IOException {
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            if (file.createNewFile()) {
                sadLibrary.generics().getPlugin().saveResource(file.getName(), true);
                sadLibrary.messages().viaConsole(false, "&rThe file &f" + file.getName() + "&r: &rwas created &acorrectly &rusing defaults values.");
            } else
                sadLibrary.messages().viaConsole(false, "&eThe file &f" + file.getName() + "&r: &ccannot &rbe created.");
        }
    }
     */

    public boolean initialize() throws IOException {
        //File file = new File(filePath, fileName);
        if (!file.exists()) {
            if (file.createNewFile()) {
                sadLibrary.messages().viaConsole(false, "&rThe file &f" + file.getName() + "&r: &rwas created &acorrectly &rusing defaults values.");
                return true;
            } else {
                sadLibrary.messages().viaConsole(false, "&eThe file &f" + file.getName() + "&r: &ccannot &rbe created.");
                return false;
            }
        }
        return true;
    }

    public void append(T object) throws IOException {
        List<T> objects = read();
        objects.add(object);
        write(objects);
    }

    public void insert(T object) throws IOException {
        List<T> objects = new ArrayList<>();
        objects.add(object);
        write(objects);
    }

    public List<T> read() throws IOException {
        //File file = new File(filePath, fileName);
        FileReader fileReader = new FileReader(file);
        Type listType = TypeToken.getParameterized(ArrayList.class, type).getType();
        List<T> objects = gson.fromJson(fileReader, listType);
        fileReader.close();
        if (objects == null)
            objects = new ArrayList<>();
        return objects;
    }


    public void write(List<T> objects) throws IOException {
        //File file = new File(filePath, fileName);
        FileWriter fileWriter = new FileWriter(file);
        gson.toJson(objects, fileWriter);
        fileWriter.close();
    }

    public void delete() {
        //File file = new File(filePath);
        file.delete();
    }
}
    /*
    private File file;
    private Gson gson;

    public JsonHandler(String fileName) {
        file = new File(sadLibrary.getPluginFolder(), fileName);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public boolean initiateFile() throws Exception {
        if (!file.exists()) {
            if(file.createNewFile()) {
                sadLibrary.getPlugin().saveResource(file.getName(), true);
                sadLibrary.getMessages().consoleMessage("&rIl file &f" + file.getName() + "&r: creato &acorrettamente &rutilizzando i valori predefiniti.");
                return true;
            }
            else {
                sadLibrary.getMessages().consoleMessage("&rIl file &f" + file.getName() + "&r: &cnon puó &ressere creato.");
                return false;
            }
        }
        return false;
    }

    public <T> T read(Class<T> clazz) {
        T object = null;
        try {
            FileReader reader = new FileReader(file);
            object = gson.fromJson(reader, clazz);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public <T> void write(boolean append, T object) {
        try {
            FileWriter writer = new FileWriter(file, append);
            gson.toJson(object, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/