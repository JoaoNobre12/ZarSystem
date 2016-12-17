package zarsystem.model.database;

import zarsystem.view.blur.Blur;

import java.io.*;
import java.util.Formatter;

/**
 * Classe de criação e seleção de banco de dados
 * Created by joao on 07/11/16.
 */

public class CreateDatabase {

    public static boolean resetDbConfig() throws IOException{
            FileWriter fileWriter = new FileWriter("database.zar");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("localhost");
            printWriter.println("root");
            printWriter.println("215464");
            printWriter.println("3306");

            printWriter.close();
            return true;

    }

    public static void writeDbConfig(String host, String user, String pass, String port) throws IOException{

        //Formatter formatter = new Formatter("C:\\Teste\\test.txt");

        FileWriter fileWriter = new FileWriter("database.zar");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(host);
        printWriter.println(user);
        printWriter.println(pass);
        printWriter.println(port);

        printWriter.close();

    }

    public static String[] readDbConfig(){
        String[] configs = new String[4];

        try {
            FileReader fileReader = new FileReader("database.zar");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int x = 0;
            String str;
            while ((str = bufferedReader.readLine()) != null && x < 4){
                configs[x] = str;

                x++;
            }

            return configs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
