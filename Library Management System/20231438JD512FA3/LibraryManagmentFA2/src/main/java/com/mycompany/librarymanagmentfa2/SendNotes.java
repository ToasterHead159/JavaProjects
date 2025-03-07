package com.mycompany.librarymanagmentfa2;

import static com.mycompany.librarymanagmentfa2.LibraryManagmentFA2.getBooklist;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class SendNotes implements Runnable {
    //this is the start of the thread. It runs every 20 seconds in the background
    @Override
    public void run() {
        while (true) {
            try {
                sendNotifications();
                break;

            } catch (IOException ex) {
                System.out.println("Send Notes Thread Ended");
                break;
            } catch (ParseException ex) {
                System.out.println("Parse Error Send Notes Thread");
            }

        }
    }
    /*This thread gets the specific data from the JSON. It then writes the data into lists. 
    each book is checked to see if it isAvaliable or not. If not then it checks the date of the book.
    If overdue then it lets the user know that the specific person will get an email for which specific book.*/
    public void sendNotifications() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        String FilePathin = "data/LibraryData.json";
        List<String> books = new ArrayList<String>();
        String title = " ";
        String name = " ";
        String email = " ";
        boolean isAvaliable = true;
        LocalDate today = LocalDate.now();
        LocalDate theDate = LocalDate.now();
        InputStream inputStream = new FileInputStream(FilePathin);
        try (InputStreamReader theReader = new InputStreamReader(inputStream)) {
            Object obj = parser.parse(theReader);
            JSONObject datafile = (JSONObject) obj;
            JSONArray bookListA = (JSONArray) datafile.get("books");
            JSONArray memListA = (JSONArray) datafile.get("contacts");

            for (Object book : bookListA) {
                JSONObject bookdata = (JSONObject) book;
                title = (String) bookdata.get("title");
                isAvaliable = (boolean) bookdata.get("isAvaliable");
                String theDates = (String) bookdata.get("LocalDate");
                theDate = LocalDate.parse(theDates);
            }

            for (Object member : memListA) {
                JSONObject specmember = (JSONObject) member;
                name = (String) specmember.get("name");
                email = (String) specmember.get("email");
                books = getBooklist(specmember.get("books"));
            }

            for (String book : books) {
                if (book == title) {
                    if (isAvaliable == false) {
                        if (today.isAfter(theDate)) {
                            System.out.println("Notifcation is sent to " + name.replaceAll("#", " ").toUpperCase() + " via " + email + " for the book " + title.replaceAll("#", " ").toUpperCase());
                            break;

                        }
                    }
                }
            }  
        } System.out.println("Notifications Sent!");
    } 
}
// VH/FH