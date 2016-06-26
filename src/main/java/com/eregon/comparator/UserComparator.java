package com.eregon.comparator;

import com.eregon.model.User;

import java.util.Comparator;

/**
 * Created by ruben on 26/06/16.
 */
public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
