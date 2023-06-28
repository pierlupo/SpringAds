package com.springsmallads.exception;


    public class CategoryNotExistsException extends Exception{

        public CategoryNotExistsException() {
            super("Category Does Not Exist");
        }
}
