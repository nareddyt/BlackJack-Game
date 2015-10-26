import java.io.CharArrayWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.LinkedList;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {

    // *****************************************************************************
    // INSTANCE PROPERTIES
    // *****************************************************************************

    static private byte[] LINE_SEP = System.getProperty("line.separator", "\n").getBytes();

    private int curLength; // length of current line

    private LinkedList<Integer> lineLengths; // length of lines within text area
    private int maxLines; // maximum lines allowed in text area
    private byte[] oneByte; // array for write(int val);

    // *****************************************************************************
    // INSTANCE CONSTRUCTORS/INIT/CLOSE/FINALIZE
    // *****************************************************************************

    private JTextArea textArea; // target text area

    public TextAreaOutputStream(JTextArea ta) {
        this(ta, 1000);
    }

    // *****************************************************************************
    // INSTANCE METHODS - ACCESSORS
    // *****************************************************************************

    public TextAreaOutputStream(JTextArea ta, int ml) {
        if (ml < 1) {
            throw new InputMismatchException("Maximum lines of " + ml + " in TextAreaOutputStream constructor is not permitted");
        }
        textArea = ta;
        maxLines = ml;
        lineLengths = new LinkedList<Integer>();
        curLength = 0;
        oneByte = new byte[1];
    }

    public synchronized void clear() {
        lineLengths = new LinkedList<Integer>();
        curLength = 0;
        textArea.setText("");
    }

    @Override
    public void close() {
        if (textArea != null) {
            textArea = null;
            lineLengths = null;
            oneByte = null;
        }
    }

    // *****************************************************************************
    // INSTANCE METHODS
    // *****************************************************************************

    @Override
    public void flush() {
    }

    /** Get the number of lines this TextArea will hold. */
    public synchronized int getMaximumLines() {
        return maxLines;
    }

    /** Set the number of lines this TextArea will hold. */
    public synchronized void setMaximumLines(int val) {
        maxLines = val;
    }

    @Override
    public void write(byte[] ba) {
        write(ba, 0, ba.length);
    }

    @Override
    public synchronized void write(byte[] ba, int str, int len) {
        try {
            curLength += len;
            if (bytesEndWith(ba, str, len)) {
                lineLengths.addLast(new Integer(curLength));
                curLength = 0;
                if (lineLengths.size() > maxLines) {
                    textArea.replaceRange(null, 0, lineLengths.removeFirst().intValue());
                }
            }
            for (int xa = 0; xa < 10; xa++) {
                try {
                    textArea.append(new String(ba, str, len));
                    break;
                } catch (Throwable thr) { // sometimes throws a java.lang.Error:
                                          // Interrupted attempt to aquire
                                          // write
                                          // lock
                    if (xa == 9) {
                        thr.printStackTrace();
                    } else {
                        Thread.sleep(200);
                    }
                }
            }
        } catch (Throwable thr) {
            CharArrayWriter caw = new CharArrayWriter();
            thr.printStackTrace(new PrintWriter(caw, true));
            textArea.append(System.getProperty("line.separator", "\n"));
            textArea.append(caw.toString());
        }
    }

    @Override
    public void write(int val) {
        oneByte[0] = (byte) val;
        write(oneByte, 0, 1);
    }

    // *****************************************************************************
    // STATIC PROPERTIES
    // *****************************************************************************

    private boolean bytesEndWith(byte[] ba, int str, int len) {
        if (len < LINE_SEP.length) {
            return false;
        }
        for (int xa = 0, xb = (str + len - LINE_SEP.length); xa < LINE_SEP.length; xa++, xb++) {
            if (LINE_SEP[xa] != ba[xb]) {
                return false;
            }
        }
        return true;
    }

} /* END PUBLIC CLASS */
