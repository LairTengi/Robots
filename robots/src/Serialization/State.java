package Serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface State extends Serializable {
    void copy(Object obj);
    default void saveState(String outPath) {
        try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(outPath))) {
            outStream.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void loadState(String inPath) {
        if (new File(inPath).isFile()) {
            try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(inPath))) {
                Object object = inStream.readObject();
                copy(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}