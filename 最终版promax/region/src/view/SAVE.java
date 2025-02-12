package view;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class SaveProgramOutput {
    public static void main(String[] args) {
        String output = "这是要保存的程序输出内容。";

        // 创建保存文件夹
        File saveFolder = new File("SAVE");
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }

        // 创建保存文件
        File saveFile = new File("SAVE/output.txt");

        try {
            // 写入文件
            FileWriter writer = new FileWriter(saveFile);
            writer.write(output);
            writer.close();

            System.out.println("程序输出已保存到文件: " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("保存文件时发生错误: " + e.getMessage());
        }
    }
}