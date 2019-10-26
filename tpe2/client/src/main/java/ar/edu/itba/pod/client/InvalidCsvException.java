package ar.edu.itba.pod.client;

class InvalidCsvException extends Exception {
    protected String filename;
    protected int lineNumber;
    protected String line;

    public InvalidCsvException(String msg, String filename, int lineNumber, String line) {
        super(msg);
        this.filename = filename;
        this.lineNumber = lineNumber;
        this.line = line;
    }
}
