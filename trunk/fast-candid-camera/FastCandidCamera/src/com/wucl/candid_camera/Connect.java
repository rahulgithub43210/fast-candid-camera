package com.wucl.candid_camera;

import java.util.List;

import weibo4andriod.DirectMessage;
import weibo4andriod.IDs;
import weibo4andriod.QueryResult;
import weibo4andriod.RateLimitStatus;
import weibo4andriod.Status;
import weibo4andriod.Trends;
import weibo4andriod.User;
import weibo4andriod.WeiboException;
import weibo4andriod.WeiboListener;

public class Connect implements WeiboListener {

	public Connect() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void blocked(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void created(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createdBlock(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createdFavorite(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createdFriendship(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletedDirectMessage(DirectMessage arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyed(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyedBlock(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyedDirectMessage(DirectMessage arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyedFavorite(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyedFriendship(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyedStatus(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabledNotification(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enabledNotification(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void followed(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotBlockingUsers(List<User> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotBlockingUsersIDs(IDs arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotCurrentTrends(Trends arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotDailyTrends(List<Trends> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotDirectMessages(List<DirectMessage> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotDowntimeSchedule(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotExists(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotExistsBlock(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotExistsFriendship(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFavorites(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFeatured(List<User> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFollowers(List<User> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFollowersIDs(IDs arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFriends(List<User> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFriendsIDs(IDs arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotFriendsTimeline(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotHomeTimeline(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotMentions(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotPublicTimeline(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotRateLimitStatus(RateLimitStatus arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotReplies(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotRetweetedByMe(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotRetweetedToMe(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotRetweetsOfMe(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotSentDirectMessages(List<DirectMessage> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotShow(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotShowStatus(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotTrends(Trends arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotUserDetail(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotUserTimeline(List<Status> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotWeeklyTrends(List<Trends> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void left(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(WeiboException arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retweetedStatus(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void searched(QueryResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sentDirectMessage(DirectMessage arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tested(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unblocked(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updated(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatedDeliverlyDevice(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatedLocation(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatedProfile(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatedProfileColors(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatedStatus(Status arg0) {
		// TODO Auto-generated method stub

	}

}
