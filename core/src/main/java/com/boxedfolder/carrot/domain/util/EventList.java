package com.boxedfolder.carrot.domain.util;

import com.boxedfolder.carrot.domain.Event;

import java.util.ArrayList;

/**
 * This stuff is to prevent type erasure for @JsonTypeInfo
 *
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */

public class EventList extends ArrayList<Event> {
}