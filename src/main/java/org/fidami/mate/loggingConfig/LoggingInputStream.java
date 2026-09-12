package org.fidami.mate.loggingConfig;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoggingInputStream extends FilterInputStream {
    private final OutputStream log;

    public LoggingInputStream(InputStream in, OutputStream log) {
        super(in);
        this.log = log;
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        if (b != -1) {
            log.write(b);
            log.flush();
        }
        return b;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead > 0) {
            log.write(b, off, bytesRead);
            log.flush();
        }
        return bytesRead;
    }
}
