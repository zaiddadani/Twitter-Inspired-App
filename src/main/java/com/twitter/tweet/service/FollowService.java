package com.twitter.tweet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.twitter.tweet.dao.FollowerRepository;
import com.twitter.tweet.dao.UserRepository;
import com.twitter.tweet.model.Follower;
import com.twitter.tweet.model.User;

@Service
public class FollowService {

	@Autowired
	private UserRepository R_User;
	@Autowired
	FollowerRepository R_Follower;

	public void followUser(Authentication authentication, Long user_id) throws Exception
	{
		User LoggedInUser = R_User.findByUsername(authentication.getName());
		User FolloweeUser = R_User.findById(user_id).orElse(null);
		if(FolloweeUser == null)
		{
			throw new Exception("User not found!");
		}
		Follower f = new Follower();
		f.setFollower(LoggedInUser);
		f.setFollowee(FolloweeUser);
		R_Follower.save(f);
	}

	public void unfollowUser(Authentication authentication, Long user_id) throws Exception
	{
		User LoggedInUser = R_User.findByUsername(authentication.getName());
		User FolloweeUser = R_User.findById(user_id).orElse(null);
		if(FolloweeUser == null)
		{
			throw new Exception("User not found!");
		}

		Follower f = R_Follower.findByFolloweeAndFollower(FolloweeUser,LoggedInUser).orElse(null);
		if(f == null)
		{
			throw new Exception("User not following " + FolloweeUser.getUsername());
		}
		R_Follower.delete(f);

	}
}
