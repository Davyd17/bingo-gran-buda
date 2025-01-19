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
    public User create(User newUser){

        verifyUsernameDuplication(newUser.username());

        return dao.insert(newUser)
                .orElseThrow(() -> new IllegalStateException(
                        "Oops something went wrong, User couldn't be created"
                ));
    }

    @Override
    public User update(Integer id, User newUser) {

        super.findById(id);

        verifyUsernameDuplication(newUser.username());

        return dao.update(id, newUser)
                .orElseThrow(() -> new IllegalStateException(
                        "Oops something went wrong, User couldn't be Updated"
                        ));
    }
}
