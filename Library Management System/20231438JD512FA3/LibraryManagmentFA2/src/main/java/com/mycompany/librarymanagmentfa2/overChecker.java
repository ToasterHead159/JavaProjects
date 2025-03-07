package com.mycompany.librarymanagmentfa2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.List;
import java.util.ArrayList;

public class overChecker implements Runnable {

    //class for a note object
    public class Note {
        public String key;
        public String value;
    }

    String message;
    List<Note> NoteList = new ArrayList<Note>();
    //this is the start of the thread. 
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20000);//It runs every 20 seconds in the background. In real world I would only let it run once.
                overDueChecker();

            } catch (InterruptedException ex) {
                System.out.println("Good Bye");
                break;
            } catch (IOException ex) {
                System.err.println("Input Error.");
            } catch (ParseException ex) {
                System.err.println("Parsing Error.");
            }
        }
    }
    /* the thread reads from the file and checks which books are not avaliable (i.e. isAvaliable is false)
    then it compares the data of the checked out book with todays date. It then calculates the fines for the overdue books
    the thread then writes to the Notifications.json for record keeping purposes.*/
    public void overDueChecker() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        String FilePathin = "data/LibraryData.json";
        InputStream inputStream = new FileInputStream(FilePathin);
        try (InputStreamReader theReader = new InputStreamReader(inputStream)) {
            Object obj = parser.parse(theReader);
            JSONObject datafile = (JSONObject) obj;
            JSONArray bookListA = (JSONArray) datafile.get("books");
            for (Object book : bookListA) {
                JSONObject bookdata = (JSONObject) book;
                String title = (String) bookdata.get("title");
                boolean isAvaliable = (boolean) bookdata.get("isAvaliable");
                String theDates = (String) bookdata.get("LocalDate");
                LocalDate theDate = LocalDate.parse(theDates);

                long dayRate = 2;
                long fineDue;

                LocalDate todaysDate = LocalDate.now();

                if (isAvaliable == false) {
                    if (todaysDate.isAfter(theDate)) {
                        long daysBetween = ChronoUnit.DAYS.between(theDate, todaysDate);
                        fineDue = dayRate * daysBetween;// fine calculation
                        Note newNote = new Note();
                        newNote.key = "OverDue";
                        newNote.value = title + " is overdue by " + daysBetween + " days. A fine of R" + fineDue + " is due.";
                        NoteList.add(newNote);
                    } else if (todaysDate.equals(theDate)) {
                        Note newNote = new Note();
                        newNote.key = "Due";
                        newNote.value = title + "is due today.";
                    }
                }

            }
        } catch (Exception p) {
            p.printStackTrace();
        } finally {
            inputStream.close();
        }
        String FilePathout = "data/Notifications.json";
        //writing to the JSON
        try (OutputStream output = new FileOutputStream(FilePathout)) {
            OutputStreamWriter writer = new OutputStreamWriter(output);
            JSONObject datafile = new JSONObject();
            JSONArray noteList = new JSONArray();
            for (Note note : NoteList) {
                JSONObject specnote = new JSONObject();
                specnote.put("status", note.key);
                specnote.put("message", note.value);
                noteList.add(specnote);
            }

            datafile.put("notes", noteList);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(writer, datafile);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// VH/FH
