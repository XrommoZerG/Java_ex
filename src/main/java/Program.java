import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String[] person = new String[3];

        String birthday;
        String gender;
        Long phoneNumber;


        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать");
        System.out.println("Для начала ведите один любой символ");
        String line = scanner.nextLine();

        if (line.length() == 1) {
            System.out.println();
        }

        String[] splitedLine = line.split(" ");

        try {
            CheckLenData(splitedLine);

            phoneNumber = GetPhoneNumber(splitedLine);
            System.out.println(phoneNumber.toString());

            gender = GetGender(splitedLine);
            System.out.println(gender);

            birthday = GetBirthday(splitedLine);
            System.out.println(birthday);

            person = GetName(splitedLine);
            System.out.println(person[0] + " " + person[1] + " "+ person[2]);

            String[] data = new String[6];
            data[0] = person[0];
            data[1] = person[1];
            data[2] = person[2];
            data[3] = birthday;
            data[4] = phoneNumber.toString();
            data[5] = gender;

            if (write(data)){
                System.out.println("Данные записаны");
            }
            else {
                System.out.println("Неожиданная ошибка записи которая не бросила исключений.");
            }
        }
        catch (ExceptionFormatData e){
            System.out.println(e.getMessage());
        }


    }
    static String GetGender(String[] splitLine){
        for (String str: splitLine) {
            if (str.length() == 1 && str.matches("[fFmMжЖмМ]")){
                return str;
            }
        }
        throw new ExceptionFormatData("Указаный вами пол отсутствуе либо запрещён Законодательствл. (m/f, м/ж)");
    }
    static String GetBirthday (String[] splitedLine){
        for (String str: splitedLine) {
            if (str.matches("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}")){
                if (CheckBirthday(str)){
                    return str;
                }
            }
            else {
                throw new ExceptionFormatData("Вы что то напутали");
            }
        }
        throw new ExceptionFormatData("Велли не коректно дату. (dd.mm.yyyy)");
    }

    static boolean CheckBirthday(String str) {
        String[] splitedLine = str.split("\\.");
        Integer[] tmp = new Integer[splitedLine.length];

        int i = 0;
        for (String el: splitedLine) {
            tmp[i++] = Integer.parseInt(el);
        }

        if (tmp[0] < 1 || tmp[0] > 31){
            throw new ExceptionFormatData("Указан не верный День");
        }
        if (tmp[1] < 1 || tmp[1] > 12){
            throw new ExceptionFormatData("Указан не верный Месяц");
        }
        if (tmp[2] < 1900 || tmp[2] > 2024) {
            throw new ExceptionFormatData("Указан не верный Год");
        }
        return true;
    }
    static Long GetPhoneNumber(String[] splitedLine) {

        for (String str: splitedLine) {
            try {
                Long tel = Long.parseLong(str);

                if(str.length() != 10) {
                    throw new ExceptionFormatData("Не верное кол-во знаков в номере телефона.");
                }
                return tel;
            }
            catch (NumberFormatException e){
                e.getMessage();
            }
        }
        throw new ExceptionFormatData("Телефона в списке данных не нашлось. Вы точно вводили номер телефона?");
    }

    static String[] GetName(String[] splitedLine){
        int index = 0;
        String[] tmp = new String[3];
        for (String str: splitedLine) {
            if (str.matches("\\D+") && str.length() > 1){
                tmp[index++] = str;
            }
        }

        if (index == 3) {
            return tmp;
        }
        else {
            throw new ExceptionFormatData("Фио не смогли определить в массиве");
        }
    }

    static boolean CheckLenData (String[] line){
        if (line.length < 6){
            throw new ExceptionFormatData("Вы ввели меньше данных чем надо. " + line.length);
        }
        if (line.length > 6){
            throw new ExceptionFormatData("Вы ввели больше данных чем надо. " + line.length);
        }
        return true;
    }

    public static boolean write(String[] data) {
        String fileName =data[0] + ".txt";
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("File couldn't been created.");
        }

        try (FileWriter fileWriter = new FileWriter(fileName, true)){
            String line = data[0] + " " +
                    data[1] + " " +
                    data[2] + " " +
                    data[3] + " " +
                    data[4] + " " +
                    data[5] + "\n";

            fileWriter.write(line);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
