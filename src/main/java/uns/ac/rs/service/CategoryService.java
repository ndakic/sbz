package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.ArticleCategory;
import uns.ac.rs.model.UserCategory;
import uns.ac.rs.repository.CategoryRepository;
import uns.ac.rs.repository.UserCategoriesRepository;

import java.util.List;

/**
 * Created by Nikola Dakic on 9/7/17.
 */

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository articleCategoryRepository;

    @Autowired
    private UserCategoriesRepository userCategoriesRepository;

    public List<ArticleCategory> getArticleCategories(){
        return articleCategoryRepository.findAll();
    }

    public List<UserCategory> getUserCategories(){
        return userCategoriesRepository.findAll();
    }

    public void deleteArticleCategory(Long id){
        articleCategoryRepository.delete(id);
    }

    public ArticleCategory saveArticleCategory(ArticleCategory articleCategory){
        return articleCategoryRepository.save(articleCategory);
    }

    public void deleteUserCategory(Long id){
        userCategoriesRepository.delete(id);
    }

    public UserCategory updateUserCategory(UserCategory userCategory){
        return userCategoriesRepository.save(userCategory);
    }

    public ArticleCategory findByTitle(String title){
        return articleCategoryRepository.findOneByTitle(title);
    }

    public UserCategory getUserCatByTitle(String title) throws Exception{
        return userCategoriesRepository.findOneByTitle(title);

    }

}
