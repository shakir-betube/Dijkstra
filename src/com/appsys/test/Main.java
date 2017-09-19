package com.appsys.test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                Dijkstra main = new Dijkstra();
                main.loadFile(args[0]);

                int d = main.minCost();
                System.out.println("result: " + d);

            } catch (FileNotFoundException fnfe) {
                System.out.println(fnfe);
            } catch (IOException ioe) {
                System.out.println(ioe);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Please provide file");
        }
    }
}