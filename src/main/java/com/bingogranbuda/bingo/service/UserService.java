package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.repository.Dao;
import com.bingogranbuda.bingo.repository.user.UserDao;
import com.bingogranbuda.bingo.util.service.UtilService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User>{

    public UserService(Dao<User> dao) {
        super(dao);
    }

    @Override
    protected String getEntityTypeSimpleName() {
        return User.class.getSimpleName();
    }

    private void verifyUsernameDuplication(String username){

        UtilService.verifyDuplication(() -> ((UserDao)dao).getByUsername(username),
                String.format("Username %s already exist", username));
    }

    @Override
    public void create(User newUser){

        verifyUsernameDuplication(newUser.username());

        UtilService.verifyRowAffected(
                dao.insert(newUser),
                "Oops something went wrong, user couldn't be created"
        );
    }

    @Override
    public void update(Integer id, User newUser) {

        super.findById(id);

        verifyUsernameDuplication(newUser.username());

        UtilService.verifyRowAffected(
                dao.update(id, newUser),
                "Oops something went wrong, user couldn't be updated"
        );
    }
}
