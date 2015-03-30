package uss.right.user;

import uss.model.database.User;
import uss.right.Right;

public class UpdateRight implements Right {
	
	boolean right;

	public UpdateRight(User loggedUser, User updateUser){
		right = loggedUser.getId().equals(updateUser.getId());
	}
	
	@Override
	public boolean hasRight() {
		return right;
	}
	
}
