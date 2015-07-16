package com.boxedfolder.carrot.domain.util;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class View {
    public interface Meta {}
    public interface General extends Meta {}
    public interface Sync extends General {}
    public interface Client extends Sync {}
}
