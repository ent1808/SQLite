package org.o7planning.sqlite.Model;

public class Customer {

    private int id;
    private String name;
    private String number;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(String name, String number, String email, String address) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
    }

    public Customer(int id, String name, String number, String email, String address) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Name:" +getName() + " \n" +"Number: "+getNumber() +"\n" + "Email: "+getEmail() +"\n" + "Address:" +getAddress();
    }
}

