/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagmentfa2;

import java.time.LocalDate;


/**
 *
 * @author jonva
 */
public class Book { //This makes the class for each book in the library.
    private String Title; //This is the attribute for the title of the book.
    private String Author; //This is the attribute for the authorof the book.
    private String ISBN; //This is the attribute for the ISBN of the book.
    private Boolean IsAvaliable; //This is the attribute of the books avaliablity.
    private LocalDate checkedOutDate;//Atrribute for dueDate
    
    public Book(String Title,String Author,String ISBN,Boolean IsAvaliable,LocalDate dueDate){ // this defines that object that is created when the class is called.
        this.Title = Title; // this assigns the values that are passed to the object are assigned to this instance of the object.
        this.Author = Author;
        this.ISBN = ISBN;
        this.IsAvaliable = IsAvaliable;
        this.checkedOutDate = dueDate;
    }
    public String getTitle(){ //this is the method used to get the title for the specific object. 
        return Title;
    }
    
    public void setTitle(String Title){ //this is the method used to set the title for the specific object. 
        this.Title = Title;
    }
    public String getAuthor(){
        return Author;
    }
    
    public void setAuthot(String Author){
        this.Author = Author;
    }
    
    public String getISBN(){
        return ISBN;
    }
    
    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }
    public Boolean getIsAvaliable(){
        return IsAvaliable;
    }
    
    public void setIsAvaliable(Boolean IsAvaliable){
        this.IsAvaliable = IsAvaliable;
    }
    
    public LocalDate getCheckedOutDate(){
        return checkedOutDate;
    }
    
    public void setCheckedOutDate(LocalDate dueDate){
        this.checkedOutDate = dueDate;
    }
    
    public static Boolean ChangeAvail(Boolean IsAvaliable){ // this is the method to change the avaliblity of the book.
        return !IsAvaliable;    
    }
    

}

//VH/FH