package com.boxedfolder.carrot.domain.util;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class View {
    public interface Meta {}
    public interface MetaSync {}
    public interface General extends Meta {}
    public interface Client extends General {}
    public interface Sync extends General, MetaSync {}
}
