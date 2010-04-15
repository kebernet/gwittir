/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.google.gwt.core.client.GWT;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Window;
import com.totsp.gwittir.client.jsni.JavaScriptObjectDecorator;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;
import com.totsp.gwittir.client.util.html5db.Database;
import com.totsp.gwittir.client.util.html5db.Databases;
import com.totsp.gwittir.client.util.html5db.ResultSet;
import com.totsp.gwittir.client.util.html5db.ResultsCallback;
import com.totsp.gwittir.client.util.html5db.SQLError;
import com.totsp.gwittir.client.util.html5db.Transaction;
import com.totsp.gwittir.client.util.html5db.TransactionTask;

/**
 * This is a persistence mechanism for newer versions of WebKit based on the
 * HTML SQL storage:
 * 
 * http://webkit.org/misc/DatabaseExample.html
 * 
 * @author kebernet
 */
public class WindowContextPersisterHTML5 extends AbstractWindowContextPersister {
	private static final int QUOTA = 512000;
	private Database db;
	private HashMap<String, String> loaded = new HashMap<String, String>();

	@Override
	public int getByteLimit() {
		return 512000;
	}

	@Override
	public Map<String, String> getWindowContextData() {
		return this.loaded;
	}

	@Override
	public void storeWindowContextData(
			final Map<String, String> windowContextData) {
                db.run(
                        new TransactionTask() {

                            public void run(Transaction tx) {
                               tx.execute("DELETE FROM windowcontext ", new String[0], new ResultsCallback(){

                                public void onSuccess(Transaction tx, ResultSet rs) {

                                      for (Entry<String, String> entry : windowContextData.entrySet()) {
                                            tx
                                                            .execute(
                                                                            "INSERT INTO windowcontext (key, value) VALUES ( ?, ? )",
                                                                            new String[] { entry.getKey(),
                                                                                            entry.getValue() },
                                                                            new ResultsCallback() {

                                                                                    public void onFailure(Transaction tx,
                                                                                                    SQLError error) {
                                                                                            Window.alert("Insertion failure "
                                                                                                            + error.getMessage());
                                                                                    }

                                                                                    public void onSuccess(Transaction tx,
                                                                                                    ResultSet rs) {
                                                                                            Logger.getAnonymousLogger().log(
                                                                                                            Level.INFO,
                                                                                                            "Insertion made.", null);
                                                                                    }
                                                                            });
                                    }

                                }

                                public void onFailure(Transaction tx, SQLError error) {
                                    GWT.log("Failed to delete "+error.getMessage(), null);
                                }

                               });
                            }

                });



		
	}

	@Override
	public void init(final WindowContextCallback listener) {

		db = Databases.openDatabase("gwittir-windowcontext", "1.0",
				"This gwittir window context database", QUOTA);
		db.run(new TransactionTask() {
			public void run(Transaction tx) {
				tx.execute("SELECT COUNT(*) FROM windowcontext", null,
						new ResultsCallback() {
							public void onSuccess(Transaction tx, ResultSet rs) {
                                                                GWT.log("windowcontext found", null);
								tx.execute(
										"SELECT key, value FROM windowcontext",
										new Object[0], new ResultsCallback() {

											public void onSuccess(
													Transaction tx, ResultSet rs) {
                                                                                                try{
                                                                                                GWT.log("Loading "+rs.getRowCount(), null);
												for (JavaScriptObjectDecorator d : rs
														.getRows()) {
                                                                                                        loaded.put(d.getStringProperty("key"),
														   d.getStringProperty("value"));
													
												}
                                                                                                listener.onInitialized();
                                                                                                } catch(Exception e){
                                                                                                    GWT.log(null,e);
                                                                                                }
											}

											public void onFailure(
													Transaction tx,
													SQLError error) {
												Window.alert("Select failed");
											}

										});
							}

							public void onFailure(Transaction tx, SQLError error) {
								tx
										.execute(
												"CREATE TABLE IF NOT EXISTS windowcontext (key TEXT, value TEXT)",
												null, new ResultsCallback() {
													public void onSuccess(
															Transaction tx,
															ResultSet rs) {
														listener
																.onInitialized();
													}

													public void onFailure(
															Transaction tx,
															SQLError error) {
														Window
																.alert("Table Create failed");

													}
												});
							}
						});
			}
		});
	}

}
