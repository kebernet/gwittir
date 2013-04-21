/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.Window;

import com.totsp.gwittir.util.jsni.JavaScriptObjectDecorator;
import com.totsp.gwittir.mvc.util.html5db.Database;
import com.totsp.gwittir.mvc.util.html5db.Databases;
import com.totsp.gwittir.mvc.util.html5db.ResultSet;
import com.totsp.gwittir.mvc.util.html5db.ResultsCallback;
import com.totsp.gwittir.mvc.util.html5db.SQLError;
import com.totsp.gwittir.mvc.util.html5db.Transaction;
import com.totsp.gwittir.mvc.util.html5db.TransactionTask;


/**
 *
 * @author kebernet
 */
public class DBTest {
    public static void doInsert() {
        Database db = Databases.openDatabase("gwittir-windowcontext", "1.0",
                "This is the gwittir test db", 512000);
        db.run(new TransactionTask() {
                public void run(Transaction tx) {
                    tx.execute("SELECT COUNT(*) FROM Gwittir", null,
                        new ResultsCallback() {
                            public void onSuccess(Transaction tx, ResultSet rs) {
                                Window.alert("Worked!");
                                tx.execute("SELECT id, note FROM Gwittir", null,  new ResultsCallback(){

                            public void onSuccess(Transaction tx, ResultSet rs) {
                                for(JavaScriptObjectDecorator d: rs.getRows()){
                                    Window.alert( "Got:  "+d.getIntegerProperty("id")+" :: "+d.getStringProperty("note"));
                                }
                            }

                            public void onFailure(Transaction tx, SQLError error) {
                                Window.alert("Select failed");
                            }

                                });
                            }

                            public void onFailure(Transaction tx, SQLError error) {
                                tx.execute("CREATE TABLE Gwittir (id REAL UNIQUE, note TEXT)",
                                    null,
                                    new ResultsCallback() {
                                        public void onSuccess(Transaction tx,
                                            ResultSet rs) {
                                            Window.alert("Database created.");

                                            Object[] parameters = new Object[2];
                                            parameters[0] = 1;
                                            parameters[1] = "This is a test of the emergency broadcast system";
                                            tx.execute("INSERT INTO Gwittir (id, note) VALUES (?, ?)",
                                                parameters,
                                                new ResultsCallback() {
                                                    public void onSuccess(
                                                        Transaction tx,
                                                        ResultSet rs) {
                                                        Window.alert(
                                                            "Insert Succeeded.");
                                                    }

                                                    public void onFailure(
                                                        Transaction tx,
                                                        SQLError error) {
                                                        Window.alert(
                                                            "Insert failed.");
                                                    }
                                                });
                                        }

                                        public void onFailure(Transaction tx,
                                            SQLError error) {
                                            Window.alert(
                                                "Error creating database.");
                                        }
                                    });
                            }
                        });
                }
            });
    }
}
