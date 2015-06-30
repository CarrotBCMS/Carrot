package com.boxedfolder.carrot.domain.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class AnalyticsTransfer {
    public Long id;
    public String name;
    public int count = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }

        return getId().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AnalyticsTransfer)) {
            return false;
        }

        AnalyticsTransfer other = (AnalyticsTransfer)object;

        if (getId() == null || other.getId() == null) {
            return false;
        }

        return getId().longValue() == other.getId().longValue();
    }
}
