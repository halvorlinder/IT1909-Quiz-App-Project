module core {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    exports core;
    exports io;
    exports io.internal;
    opens core to com.fasterxml.jackson.databind;
}
