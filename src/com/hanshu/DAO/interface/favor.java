package com.hanshu.DAO.ImageFavor;

import com.hanshu.bean.User;


public interface favor {

     boolean hasLikedTheImage(User user, int imageID);

     boolean likeImage(User user, int imageID);

     boolean unlikeImage(User user,int imageID);

     boolean deleteAllImageFavor(int imageID);


}
