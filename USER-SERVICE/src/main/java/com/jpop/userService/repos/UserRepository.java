package com.jpop.userService.repos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.jpop.userService.model.User;

@Repository
public class UserRepository {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public User save(User user) {
		dynamoDBMapper.save(user);
		return user;
	}

	public List<User> getAllUsers() {
		PaginatedScanList<User> result = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
		List<User> list = new ArrayList<>();
		for (User u : result) {
			list.add(u);
		}
		return list;
	}

	public User getUserById(String userId) {
		return dynamoDBMapper.load(User.class, userId);
	}

	public String delete(String userId) {
		User user = dynamoDBMapper.load(User.class, userId);
		dynamoDBMapper.delete(user);
		return "User deleted";
	}

	public String update(String userId, User user) {
		dynamoDBMapper.save(user, new DynamoDBSaveExpression().withExpectedEntry("userId",
				new ExpectedAttributeValue(new AttributeValue().withS(userId))));
		return userId;
	}
}
