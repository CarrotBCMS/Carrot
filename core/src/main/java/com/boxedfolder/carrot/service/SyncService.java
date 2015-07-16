package com.boxedfolder.carrot.service;

import java.util.Map;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface SyncService {
    Map<String, Object> sync(Long timestamp, String appKey);
}
