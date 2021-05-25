package com.bakaful.project2021.domains;

import java.util.List;

public class FriendRequestWrapper {
    private List<FriendRequest> friendRequests;

    public FriendRequestWrapper () {
    }

    public void addRequest(FriendRequest request) {
        this.friendRequests.add(request);
    }
    public void addAllRequests(List<FriendRequest> requests) {
        this.friendRequests.addAll(requests);
    }

    public List<FriendRequest> getFriendRequests () {
        return friendRequests;
    }

    public void setFriendRequests (List<FriendRequest> friendRequestList) {
        this.friendRequests = friendRequestList;
    }
}
