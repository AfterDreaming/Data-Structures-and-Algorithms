
/**
 * The grow options for the buffer
 * @author Robert
 *
 */
public enum BufferGrowMode {
    REGROW,  //causes a full buffer to regrow to twice its size when full
    OVERWRITE; //causes a full buffer to overwrite the oldest entry
}
