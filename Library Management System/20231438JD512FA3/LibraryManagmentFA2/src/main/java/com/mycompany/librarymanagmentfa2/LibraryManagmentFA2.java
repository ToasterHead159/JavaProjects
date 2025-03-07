package com.mycompany.librarymanagmentfa2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.*;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LibraryManagmentFA2 {

    static String UserNoSpaces(String theQuestion) { // this method is used to check if the user is just pushing enter on the input. this will prevent inaccurate data.
        String UserInput; //This initiates a variable for the userinput to be assinged to.
        boolean inPutGood = false; //this variable is used to break out of the loop for checking if the user input is correct or not.
        do { //this loops ensures that only correct inputs are returned to the rest of the program
            System.out.println(theQuestion); //this is for the question that needs to be printed everytime a user makes a mistake. This allows the user to not lose track of what they are doing.
            Scanner inPut = new Scanner(System.in);// makes a new instance of the scanner to get the users input.
            UserInput = inPut.nextLine();// gets the users input and assigns it to the variable
            if (UserInput.trim().isEmpty()) { //checks if the user input is competely empty by trimming the white space of the input. this ensures that inputs cannot be blank.
                System.out.println("You cannot have just a space!");//informs the user of the error that they have made. 

            } else { // if the users input is not blank then this runs.
                inPutGood = true; //changes the input checker is true which causes the loop to break
            }
        } while (!inPutGood);//condition to break the input checker loop.
        return UserInput;//returns the corrected or correct input to the rest of the code.
    }

    static int UserNum() { //This method is used to get a number from the user for the menu as well as the numbers for the calculator.
        int Number = 0; //This declares and initialises the number variable giving it a vaule of 0. 
        boolean InputGood = false; //This declares a boolean variable and is assigned the value of false.
        while (!InputGood) { //This while loop runs until the variable InputGood is true.
            String numQuestion = "Please provide a number:";// This prints the string asking the user to provide a number.
            String UserInput = UserNoSpaces(numQuestion);
            try { //This try statement is for handling input errors by the user.
                Number = Integer.parseInt(UserInput);//This assigns the variable number the input the user gives and converts it an integer value.
                InputGood = true;//This changes the value of the boolean variable to be able to break out of the loop.
            } catch (NumberFormatException e) { //This is to catch a number format exception error.
                System.out.println("Please use numbers only!");//This prints to inform the user to only use numbers and only appears if the error occurs
            }
        }

        return Number; //This returns the number for use in the main.
    }

    static String EmailValid() {//method used to validate the user email input. 
        String UserEmail = "";//this initiates the variable used for the user input to check against.
        boolean inputGood = false;// this is used as a means of breaking out the loop when the valid email is input.
        while (!inputGood) { //while the inputGood is false this code will run.
            System.out.println("Please enter the email address:");//asks the user to input their email address.
            Scanner inPut = new Scanner(System.in);//intiates an instance of the scanner class and assigns it to the variable.
            UserEmail = inPut.nextLine();//this takes the whole line from the scanner and assigns it to the variable.
            try {//this is used to check if the user email passes the validation check
                String RegularExpression = "^[A-Za-z0-9+_.-]+@(.+)$";//assigns the values to check for the validation to a variable.
                assert (Pattern.compile(RegularExpression).matcher(UserEmail).matches()) == true : "Email is not valid";//assertion to check if the email is valid or not.
                if (!(Pattern.compile(RegularExpression).matcher(UserEmail).matches())) { // this checks if the regualr expression does not  matche the useremail.
                    throw new Exception();//this runs if the email does not match
                }
                inputGood = true;//this breaks the loop once the error is not found
            } catch (Exception e) { //this exception will run when the email is not valid. 
                System.out.println("Your email is not correct. Please try again.");//this tells the user what they are doing wrong.
            }
        }

        return UserEmail; //this returns the correct email.
    }
    //method used for checking if books are overdue
    public static long overDueBookCheck(LocalDate dueDate) throws ParseException {
        long dayRate = 2;
        long fineDue;

        LocalDate todaysDate = LocalDate.now();

        if (todaysDate.isAfter(dueDate)) {
            long daysBetween = ChronoUnit.DAYS.between(dueDate, todaysDate);
            System.out.println("This book is " + daysBetween + " days late.");
            fineDue = dayRate * daysBetween;
            System.out.println("Fine due R" + fineDue);
            return fineDue;
        } else {
            System.out.println("This book is not overdue.");
            fineDue = 0;
            return fineDue;
        }

    }
    /*method for converting the members list of checked out books
    to a string list that can be passed to the variable of memberbooks*/
    public static List<String> getBooklist(Object books) {
        List<String> bookList = new ArrayList<>();
        if (books instanceof JSONArray) {
            JSONArray booksArray = (JSONArray) books;
            for (Object book : booksArray) {
                bookList.add((String) book);
            }
        }
        return bookList;
    }

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException, InterruptedException { //main entry point for the program.

        List<Book> BooksList = new ArrayList<>();//the array list for storing each book object after creation.
        List<Member> MembersList = new ArrayList<>();//the array list for storing each member object after creation.
        JSONParser parser = new JSONParser();
        String FilePath = "data/LibraryData.json"; //set the file path where the data is retrieved from
        InputStream inputStream = new FileInputStream(FilePath);// makes a new inputstream with the file path provided.

        //thie try statement is run with the resrouces. It creates JSON objects and JSON arrays.  
        try (InputStreamReader theReader = new InputStreamReader(inputStream)) {
            Object obj = parser.parse(theReader);
            JSONObject datafile = (JSONObject) obj;
            JSONArray bookListA = (JSONArray) datafile.get("books");
            JSONArray memListA = (JSONArray) datafile.get("contacts");
            /* this for loop addes the data from the JSON to the JSONArray. 
            Once all the data is passed to the variables then a new book is created 
            and added to the BookList for use inside the program.*/
            for (Object book : bookListA) {
                JSONObject bookdata = (JSONObject) book;
                String title = (String) bookdata.get("title");
                String author = (String) bookdata.get("author");
                String ISBN = (String) bookdata.get("isbn");
                boolean isAvaliable = (boolean) bookdata.get("isAvaliable");
                String theDates = (String) bookdata.get("LocalDate");
                LocalDate theDate = LocalDate.parse(theDates);
                Book anotherBook = new Book(title, author, ISBN, isAvaliable, theDate);
                BooksList.add(anotherBook);
            }
            // this for loop addes does the same as above but only for the members and their details.
            for (Object member : memListA) {
                JSONObject specmember = (JSONObject) member;
                String name = (String) specmember.get("name");
                String email = (String) specmember.get("email");
                List<String> books = getBooklist(specmember.get("books"));
                long fines = (long) specmember.get("fines");
                Member newestMember = new Member(name, email, books, fines);
                MembersList.add(newestMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputStream.close();// this closes the input stream to avoid problems with the backgroud thread.

        overChecker checker = new overChecker();//instantiates a new instance of the overCheceker class.

        Thread overDueThread = new Thread(checker);// Creates a new thread instance named overDueThread, initialized with the overChecker instance 'checker'.
        overDueThread.start();//this starts the thread.

        Calendar cal = Calendar.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        do {//ensures that the program loops until the user exits the program.
            System.out.println("\nWelcome to the Library!\nWhat would you like to do?\n1.Add a new book.\n2.Add a new member\n3.Find a book\n4.Check out a book.\n5.Return a book.\n6.Outstanding fines\n7.Notification Management\n0.Exit");//main menu for the program.
            int UserS = UserNum();//calls the check to check the user input.
            switch (UserS) { //condition for the switch case to compare to.
                case 1 -> { //Add a book.
                    System.out.println("Add a new book:");//introduces what the user is doing. Means of keeping user informed as to what they are doing.
                    String titleQuestion = "What is the title of the book?";//assigns the speicfic question to the variable.
                    //String Title = UserInput.nextLine().toLowerCase(); used as an assertion to check if the input checking method is working correctly.NOT USED.
                    String Title = UserNoSpaces(titleQuestion).toLowerCase();//passes the specific question to the input checker makes the return all small letters and assigns it to the varaible.
                    Title = Title.replaceAll("\\s+", "#");//this replaces all the spaces in the user input with #. This ensures overall data integrity so that searchs can be accurate.
                    String authQuestion = "Who is the Author?\nBe sure to include the first and last name.";//specific question for input checker. 
                    String Author = UserNoSpaces(authQuestion).toLowerCase();//passes the specific question to the input checker makes the return all small letters and assigns it to the varaible.
                    Author = Author.replaceAll("\\s+", "#");//this replaces all the spaces in the user input with #. This ensures overall data integrity so that searchs can be accurate.
                    int AuthorLast = Author.lastIndexOf("#");// first step to only keeping the last name of the author. gives the index position of the last #
                    String authorLName = Author.substring(AuthorLast + 1);//this creates a substring from the entered author name from the last # until the end of the string.
                    String isbnQuestion = "What is the ISBN?";//question for error handling
                    String ISBN = UserNoSpaces(isbnQuestion);//checks user input for blank inputs
                    Boolean IsAvaliable = true;//sets the book to avaliable in the library.
                    LocalDate checkedOutDate = LocalDate.now();//assigns the current date as the checked out date.
                    Book NewBook = new Book(Title, authorLName, ISBN, IsAvaliable, checkedOutDate);//makes a new book object and assigns the collected data to it.
                    BooksList.add(NewBook);//adds newly made book to the booklist
                    if (BooksList.isEmpty()) { //assertion for checking if the list is written to or not.
                        throw new AssertionError("The list is still empty");//displays if list is empty.
                    }
                    System.out.println("\nBook Added!\n");//informs the user that the book has been added to the collection.
                    for (int i = 0; i < BooksList.size(); i++) { //This is used to print all the titles in the current book list.
                        System.out.println(((Book) BooksList.get(i)).getCheckedOutDate());
                    } //This for looks is a means of assertion as to check if the book is added to the list. It is commented out as the user does not need to see it.
                    continue;//breaks out of the switch case statement but not the loop. 
                }
                case 2 -> {// add a member.
                    System.out.println("Add a new member:");//contextual text so the user knows what they are doing.
                    String nameQuestion = "What is the new members name?\nBe sure to include first and last name.";//question for the input checker
                    String Name = UserNoSpaces(nameQuestion).toLowerCase();//returns the userinput and makes it lower case
                    Name = Name.replaceAll("\\s+", "#");
                    String Email = EmailValid();//returns a valid email to the variable
                    List<String> MemBooks = new ArrayList<>();//initiates the list of the individual members checked out books
                    int fines = 0;
                    Member NewMember = new Member(Name, Email, MemBooks, fines);//makes a new memeber object and passes all the values to it
                    MembersList.add(NewMember);//adds the new member to the list of members
                    if (MembersList.isEmpty()) { //assertions to be sure the list is being added to.
                        throw new AssertionError("The list is still empty");//prints if the list stays empty.
                    }
                    System.out.println("\nMember Added!\n");//indicates to the user that the member has been added to the list.
                    //for (int i = 0; i < MembersList.size(); i++) { //This is used to print all the members names in the current book list.
                    //    System.out.println(((Member) MembersList.get(i)).getName());
                    //} //This for looks is a means of assertion as to check if the member is added to the list. It is commented out as the user does not need to see it.
                    continue;//breaks out of the switch case statment but not the loop.
                }
                case 3 -> { //search for a book
                    boolean CheckNoBooks = BooksList.isEmpty();//this checks if the book list is empty it assigns the value to the variable.
                    boolean bookfound = false;//the variable is used to break out of the for loop if the book is found.
                    if (CheckNoBooks == true) {
                        System.out.println("There are no books in the Library.\nPlease add a book before searching\n");//prints to inform the user that the booklist is empty and to populate the list before searching.
                        continue;//breaks out of the switch case
                    } else if (CheckNoBooks == false) {
                        System.out.println("Find a book.\nWhat would you like to seach by\n1. Title\n2. Author");//provides context for the user and a menu for specifying which to search by.
                        int TiOrAut = UserNum();//checks user input
                        if (TiOrAut == 1) {//search by title
                            String titleQuestion = "What is the title of the book?";//question for the user input check
                            String BookTitle = UserNoSpaces(titleQuestion).toLowerCase();//verification of user input and lower case
                            BookTitle = BookTitle.replaceAll("\\s+", "#");//replacing the spaces with #
                            for (Book book : BooksList) { //checks for each book in the book list.
                                if (book.getTitle().equalsIgnoreCase(BookTitle)) {//checks the title of the specific book in the list and compares it to the user search
                                    boolean Checkedin = book.getIsAvaliable();//assigns the value avalibility of the current book in the for loop and assigns it.
                                    if (Checkedin == true) {
                                        System.out.println("The book is in the collection and avaliable.\n");// tells the user if the book exists and is avaliable.
                                        bookfound = true;// changes the state of the variable to break out of the loop.
                                    } else {
                                        System.out.println("The book is in the collection but is NOT avaliable.\n");//tells user that the book exists but is not avaliable.
                                        bookfound = true;
                                    }
                                    break;
                                }
                            }
                            if (bookfound == false) {
                                System.out.println("The book is not in the collection.\n");//tells the user that the book does not exist.
                            }
                        } else if (TiOrAut == 2) { //search by author
                            Boolean authorInLib = false; // variable for loop breaking
                            String authQuestion = "Who is the Author?\nLast Name Only.";//question for input checker
                            String BookAuth = UserNoSpaces(authQuestion).toLowerCase();// changes input to lower case after checking
                            BookAuth = BookAuth.replaceAll("\\s+", "#");
                            for (Book book : BooksList) {//checks each book in the booklist
                                if (book.getAuthor().equalsIgnoreCase(BookAuth)) {//this compares the author to the user input.
                                    System.out.println("Book in collection by " + BookAuth.toUpperCase() + " Title: " + book.getTitle().toUpperCase().replaceAll("#", " ") + "\n");//prints the author and title if the author is found
                                    authorInLib = true;//changes the state of the condition to break the loop.
                                }
                            }
                            if (!authorInLib) {
                                System.out.println("No Books by " + BookAuth.toUpperCase() + " in collection. \n"); //prints if no books are avaliable by the author.
                            }
                        } else {
                            System.out.println("Invalid Selection! \nOnly selection 1 or 2\n");//error handling if the user uses a number that isn't one of the choices.
                        }
                    }
                    continue;//exits the switch not the loop
                }
                case 4 -> { //checking a book out
                    System.out.println("Checkout a book:");//contextual text for user to know whats happening.
                    String titleQuestion = "What is the title of the book?";//question for the user input
                    String BooktoCheckOut = null;//title of the book to check out
                    boolean bookAvalibility;// variable for changing the avaliablity of the book. 
                    String BookTitle = UserNoSpaces(titleQuestion).toLowerCase();// changes user input to lower case
                    BookTitle = BookTitle.replaceAll("\\s+", "#");
                    boolean bookfound = false;//variable to inform user that the book is not found.
                    boolean noMember = false;//variable to inform the user that the member is not found.
                    for (Book book : BooksList) {
                        if (book.getTitle().equalsIgnoreCase(BookTitle)) {//gets the title of each book and compares it to the user input
                            boolean Checkedin = book.getIsAvaliable();//checks if the specific book is avaliable 
                            if (Checkedin == true) {
                                System.out.println("The book is in the collection and avaliable.\n");//tells the user that the book is avaliable
                                bookfound = true;
                                BooktoCheckOut = book.getTitle();//gets the title of the found book.
                                String memQuestion = "Which member is taking the book?";//question for the input checker
                                String SearchMem = UserNoSpaces(memQuestion).toLowerCase();
                                SearchMem = SearchMem.replaceAll("\\s+", "#");
                                for (Member selmember : MembersList) {//for each loop for each member in the list
                                    if (selmember.getName().equalsIgnoreCase(SearchMem)) {//compares the name of the member with the user input
                                        selmember.getbooks().add(BooktoCheckOut);//once the member is found the title of the book is added to the members book list
                                        bookAvalibility = book.ChangeAvail(book.getIsAvaliable());//assigns the status of the found book
                                        book.setIsAvaliable(bookAvalibility);//changes the avaliablity of the book
                                        cal.add(Calendar.DAY_OF_MONTH, 1);
                                        LocalDate dueDate = LocalDate.now();
                                        book.setCheckedOutDate(dueDate);
                                        System.out.println("\nBook has been Checked out to:" + SearchMem.replaceAll("#", " ").toUpperCase() + "\n" + "The due date for the book is: " + dueDate);//confirms that the book has been checked out to the member.
                                        noMember = true;
                                        break;
                                    }
                                }
                            }
                            if (!noMember) {
                                System.out.println("Member not found.\n");//tells the user the book was not found.
                            }
                        }

                    }
                    if (bookfound == false) {
                        System.out.println("The book is not avaliable.\n");//tells the user the book was not found.

                    }
                    continue;
                }

                case 5 -> {
                    String bookTitle = "";//used to store the title of the speicific book. 
                    System.out.println("Return a book");//Contextual for user convinence.
                    String returnBookQuestion = "Which member is returning a book?";//question for error checking
                    String selMember = UserNoSpaces(returnBookQuestion).toLowerCase();//verification of user input and lower case
                    boolean memFound = false;
                    selMember = selMember.replaceAll("\\s+", "#");
                    for (Member memberSel : MembersList) {//for each loop for each member in the list
                        //System.out.println(memberSel.getName() + " " + selMember);
                        if (memberSel.getName().equalsIgnoreCase(selMember)) {
                            if (memberSel.getbooks().size() < 1) { //check if memeber has any books checked out.
                                System.out.println(memberSel.getName().replaceAll("#", " ").toUpperCase() + " has the no books checked out.\n");
                                memFound = true;
                                break;
                            } else {
                                System.out.println(memberSel.getName().replaceAll("#", " ").toUpperCase() + " has the following books checked out: ");//informs the user of the books checked out by member.
                                for (var book : memberSel.getbooks()) {
                                    System.out.println(book.replaceAll("#", " ").toUpperCase()); //prints out the list of books that the member has checked out.
                                }
                                String titleReturnQuestion = "\nPlease type out the title you would like to return:";
                                String TitleReturn = UserNoSpaces(titleReturnQuestion).toLowerCase();
                                TitleReturn = TitleReturn.replaceAll("\\s+", "#");
                                for (var bookTitles : memberSel.getbooks()) { //searches for the member the user is looking for
                                    if (bookTitles.equalsIgnoreCase(TitleReturn)) {//if the book is found the title of that book is assigned to the variable.
                                        bookTitle = bookTitles;
                                        System.out.println("Removing book from member...");//contextual message for the user so they know whats happening
                                        for (Book selbook : BooksList) {
                                            if (TitleReturn.equalsIgnoreCase(selbook.getTitle())) {//compares the user entered title with books title in the book list.
                                                long fineCal = overDueBookCheck(selbook.getCheckedOutDate());// checks if the book is overdue and assigns the fine value.
                                                //this adds the current fine to the members total fines
                                                if (fineCal > 0) { 
                                                    long totalfine = memberSel.getfineTotal();
                                                    totalfine += fineCal;
                                                    //a check to see if the user has paid the fine or not. if the user says yes then the fine is removed if No then it is added.
                                                    System.out.println("Has " + memberSel.getName().replaceAll("#", " ").toUpperCase() + "paid the fines?1.Yes or 2.No");
                                                    int UserN = UserNum();
                                                    while (true) {
                                                        if (UserN == 1) {
                                                            memberSel.setfineTotal(0);
                                                            break;
                                                        } else if (UserN == 2) {
                                                            memberSel.setfineTotal(totalfine);
                                                            break;
                                                        } else {
                                                            System.out.println("Please use only number 1 or 2");
                                                        }
                                                    }

                                                }
                                                boolean availChange = Book.ChangeAvail(selbook.getIsAvaliable());//returns the current value of the specific books avaliablity.
                                                selbook.setIsAvaliable(availChange);//changes the avaliblity of the book. It is done here so that if the search for the member fails
                                                memFound = true;
                                                System.out.println("\nThe book has been returned\n");//if forms user that the book has been returned. 
                                                break;
                                            }
                                        }

                                    } else {
                                        System.out.println("Title not found for " + memberSel.getName().replaceAll("#", " ").toUpperCase() + "\n");//informs the user that the member has not checked out any books
                                        break;
                                    }
                                }
                                memberSel.getbooks().remove(bookTitle);//removes the title of the book from the members book list. 
                                break;

                            }
                        }

                    }
                    if (memFound == false) {
                        System.out.println("No member found!\n");//this inform the user that the member is not found.

                    }
                    continue;
                }
                case 6 -> { //Oustanding fines
                    System.out.println("List of fines owed by members:");
                    boolean noOwes = false;
                    //this for loop looks at each members fines and returns a list of people that have fines outstanding.
                    for (Member specMem : MembersList) {

                        String name = specMem.getName();
                        long fines = specMem.getfineTotal();
                        if (fines > 0) {
                            System.out.println(name.replaceAll("#", " ").toUpperCase() + " has R" + fines + " in fines outstanding.\n");
                            noOwes = true;

                        } else {
                            continue;

                        }

                    }
                    if (noOwes == false) {
                        System.out.println("No fees owed by anyone!\n");
                    }
                    continue;
                }
                case 7 -> { //Notification Management
                    System.out.println("Notification Management\nWould you like to send over due notifications?\n1. Yes or 2. No");
                    int UserN = UserNum();
                    //if the user opts to send notifications the thread is run and then stopped and the user is returned to the main menu.
                    if (UserN == 1) {
                        SendNotes sender = new SendNotes();
                        Thread sendingNotes = new Thread(sender);
                        sendingNotes.start();
                        sendingNotes.join();
                        System.out.println("\n");
                        continue;
                    } else {
                        System.out.println("\n");
                        continue;
                    }
                }
                case 0 -> {
                    /*When the user exits the program the try with resrouces makes a new writer and JSON Arrays.
                    The current lists object's attributes are added to the JSON object arrays.*/
                    try (OutputStream output = new FileOutputStream(FilePath)) {
                        OutputStreamWriter writer = new OutputStreamWriter(output);
                        JSONObject datafile = new JSONObject();
                        JSONArray bookListModified = new JSONArray();
                        JSONArray memListModified = new JSONArray();

                        for (Book book : BooksList) {
                            JSONObject specBook = new JSONObject();
                            specBook.put("title", book.getTitle());
                            specBook.put("author", book.getAuthor());
                            specBook.put("isbn", book.getISBN());
                            specBook.put("isAvaliable", book.getIsAvaliable());
                            String localDate = formatter.format(book.getCheckedOutDate());
                            specBook.put("LocalDate", localDate);
                            bookListModified.add(specBook);
                        }
                        for (Member member : MembersList) {
                            JSONObject specMem = new JSONObject();
                            specMem.put("name", member.getName());
                            specMem.put("email", member.getEmail());
                            specMem.put("books", member.getbooks());
                            specMem.put("fines", member.getfineTotal());
                            memListModified.add(specMem);

                        }
                        /*these arrays are then added to the JSON and the data is serialized.
                        the Serialization ensures that the data is written to the file in correct format*/
                        datafile.put("books", bookListModified);
                        datafile.put("contacts", memListModified);
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.enable(SerializationFeature.INDENT_OUTPUT);
                        mapper.writeValue(writer, datafile);
                        writer.close();
                        System.out.println("Notifications are being sent...\nFines are being updated.");
                        Thread.sleep(2000);
                        overDueThread.interrupt();

                    } catch (IOException e) {
                        System.out.println("Input Error.");
                    }
                    break;//breaks out of the swtich case
                }
                default -> {
                    System.out.println("Invalid Selection!\nPlease chose only 1-7 or 0.\\n");//error handling if the user uses a number that is not one of the options.
                    continue;
                }
            }
            break;//breaks out the do while and closes the application

        } while (true);
    }
}

// VH/FH
