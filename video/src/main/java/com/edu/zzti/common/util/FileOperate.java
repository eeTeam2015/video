package com.edu.zzti.common.util;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.*;

/**
 * 文件读写操作
 */
public class FileOperate implements ServletContextAware {

    private static ServletContext servletContext;


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public static void writeFile(String filePath, String sets){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath);
            PrintWriter out = new PrintWriter(fw);
            out.write(CodeConvert.UTF_8ToGBK(sets));
            out.println();
            fw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String ReadJsonFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }


    public static boolean delFile(String path){
        if(path!=null&&!"".equals(path)){
            File file = new File(path);

            if (file.exists()){
                if(file.delete()){
                   return true;
                }
            }
        }
        return false;
    }

}
