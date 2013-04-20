package com.totsp.gwittir.compat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * C
 */
public class FileGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.totsp.gwittir.Emul";
    }

    public void testFile() throws IOException {
        GWT.log("TEST FILE");
        File f = new File("/file");
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        raf.writeInt(123);
        raf.seek(0);
        int i= raf.readInt();
        assertEquals(123, i);
    }
}
