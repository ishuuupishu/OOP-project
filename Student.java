//Defining a Student class.  
public class Student{  
    //defining fields  
    int id;//field or data member  
    String name; 
   } 
    //creating main method inside the Student class 
   class TestStudent1{ 
    public static void main(String args[]){  
     //Creating an object or instance  
     Student student1=new Student();//creating an object of Student  
     //Printing values of the object  
     System.out.println(student1.id);//accessing member through reference variable  
     System.out.println(student1.name);  
    }  
   }