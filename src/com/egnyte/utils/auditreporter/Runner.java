
package com.egnyte.utils.auditreporter;


import java.io.*;
import java.util.*;

public class Runner {

    private List<List<String>> users;
    private List<List<String>> files;



    public static void main(String[] args) throws IOException {
        Runner r = new Runner();
        //ifs are used for a possibility of running program with different arguments
        if (args.length > 2 && args[0].equals("-c")) {
            r.loadData(args[1], args[2]);
            r.makeWithOwners();
        } else if (args.length > 2 && args[0].equals("--top")) {
            int n = Integer.valueOf(args[1]);
            r.loadData(args[2], args[3]);
            r.makeTopN(n);
        } else {
            r.loadData(args[0], args[1]);
            r.run();
        }
    }

    ///////////////////////////////////////////////////////////////
    //function for finding top n elements
    private void makeTopN(int n) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("topNOutput.csv"), "utf-8"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        printHeaderForN(n);
        List<Record> recordList = new ArrayList<Record>();

        for (List<String> userRow : users) {
            short userId = Short.parseShort(userRow.get(0));

            try {
                writer.write("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            for (List<String> fileRow : files) {
                Record record = new Record(fileRow.get(0), fileRow.get(1), fileRow.get(2), userRow.get(0), userRow.get(1));//file id/size/file name/user id/ user name

                short ownerUserId = Short.parseShort(fileRow.get(3));
                if (ownerUserId == userId) {
                    recordList.add(record);
                }

            }

        }

        Collections.sort(recordList, new Comparator<Record>()                      //sorting a list by size value with Collections comparator
                {public int compare(Record s1, Record s2) {
                        Long fileSizeCompare = Long.parseLong(s1.fileSize);
                        Long fileSizeCompare2 = Long.parseLong(s2.fileSize);
                        return fileSizeCompare.compareTo(fileSizeCompare2);
                    }
                }
        );
        Collections.reverse(recordList);//reversing the list order from ascending do descending

        List<Record> subItems = new ArrayList<Record>(recordList.subList(0, n));// getting n elements from list
        for (Record r : subItems)
        {
            printNFiles(r.fileName, r.fileOwner, r.fileSize);
            try {
                writer.write(r.fileName);
                writer.write(",");
                writer.write(r.fileOwner);
                writer.write(",");
                writer.write(r.fileSize);
                writer.write(System.lineSeparator());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        try {
            writer.close();
        } catch (Exception ex) {/*ignore*/}
    }

    ///////////////////////////////////////////////////////////////
    //function generates the csv file with files matched to owners
    private void makeWithOwners() {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("taskoutput.csv"), "utf-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (List<String> userRow : users) {
            short userId = Short.parseShort(userRow.get(0));
            String userName = userRow.get(1);

            try {
                writer.write("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            for (List<String> fileRow : files) {
                long size = Long.parseLong(fileRow.get(1));
                String fileName = fileRow.get(2);
                short ownerUserId = Short.parseShort(fileRow.get(3));

                if (ownerUserId == userId) {
                    try {                               //writing files to the csv
                        writer.write(userName);
                        writer.write(",");
                        writer.write(fileName);
                        writer.write(",");
                        writer.write(Long.toString(size));
                        writer.write(System.lineSeparator());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        try {
            writer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    ///////////////////////////////////////////////////////////////
    private void run() {
        printHeader();

        for (List<String> userRow : users) {
            short userId = Short.parseShort(userRow.get(0));
            String userName = userRow.get(1);
            printUserHeader(userName);

            for (List<String> fileRow : files) {
                String fileId = fileRow.get(0);
                long size = Long.parseLong(fileRow.get(1));
                String fileName = fileRow.get(2);
                short ownerUserId = Short.parseShort(fileRow.get(3));
                if (ownerUserId == userId) {
                    printFile(fileName, size);
                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////
    private void loadData(String userFn, String filesFn) throws IOException {
        String line;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(userFn));
            users = new ArrayList<List<String>>();

            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                users.add(Arrays.asList(line.split(",")));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        reader = null;
        try {
            reader = new BufferedReader(new FileReader(filesFn));
            files = new ArrayList<List<String>>();

            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                files.add(Arrays.asList(line.split(",")));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    ///////////////////////////////////////////////////////////////
    private void printHeader() {
        System.out.println("Audit Report");
        System.out.println("============");
    }
    ///////////////////////////////////////////////////////////////
    private void printHeaderForN(int n) {
        System.out.println("Top #" + n + " Report");
        System.out.println("============");
    }
    ///////////////////////////////////////////////////////////////
    private void printUserHeader(String userName) {
        System.out.println("## User: " + userName);
    }
    ///////////////////////////////////////////////////////////////
    private void printFile(String fileName, long fileSize) {
        System.out.println("* " + fileName + " ==> " + fileSize + " bytes");
    }
    ///////////////////////////////////////////////////////////////
    private void printNFiles(String fileName, String userName, String fileSize) {
        System.out.println("* " + fileName + " ==> user " + userName + ", " + fileSize + " bytes");
    }


}
