package com.twitter.tweet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twitter.tweet.model.Comment;
import com.twitter.tweet.model.Tweet;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	@Query( value="SELECT * "
			+ "FROM comment "
			+ "WHERE comment_tweet_id_tweetid IN"
			+ "("
			+ "SELECT tweetid FROM tweet WHERE tweetid = ?1"
			+ ") "
			+ "ORDER BY comment_created_at DESC",
			nativeQuery = true
	)
	List<Comment> findByTweetId(Tweet tweet);

}
