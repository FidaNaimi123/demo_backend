package com.esprit.demo;

public class App {

    public int addition(int a, int b) {
        return a + b;
    }

    public String message() {
        return "Hello from Jenkins Pipeline!";
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.message());
        System.out.println("2 + 3 = " + app.addition(2, 3));
    }
}
