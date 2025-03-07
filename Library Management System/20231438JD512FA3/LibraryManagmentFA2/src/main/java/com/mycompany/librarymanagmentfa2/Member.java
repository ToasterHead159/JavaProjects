
package com.mycompany.librarymanagmentfa2;

import java.util.ArrayList;
import java.util.List; 
public class Member { //This creates the class of members that are currently in the library.
    private String Name; // this is the name attribute of the member that is part of the library.
    private String Email; // this is the email attribute of the member that is part of the library.
    private List<String> books; // this is the individual members list of checked out books attribute that is part of the library.
    private long fineTotal;//total fines that the member has.
    
    public Member(String Name,String Email,List<String> books, long fineTotal){ // this defines that object that is created when the class is called.
    this.Name = Name; // this assigns the values that are passed to the object are assigned to this instance of the object.
    this.Email = Email;
    this.books = books;
    this.fineTotal = fineTotal;
    }

    public String getName(){ //this is the method used to get the attribute for the specific object. 
    return Name;
    }
    
    public void setName(String Name){ //this is the method used to set the attribute for the specific object. 
        this.Name = Name;
    }
    public String getEmail(){
    return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public List<String> getbooks(){
        return books;
    }
    
    public void setbooks(List<String> books){
        this.books = books;
    }
    
    public long getfineTotal(){
        return fineTotal;
    }
    
    public void setfineTotal(long fineTotal){
        this.fineTotal = fineTotal;
    }
}

//VH/FH