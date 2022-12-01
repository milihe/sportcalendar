package org.sradar;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> "hello");
    }
}