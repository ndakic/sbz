package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.ArticleCategory;
import uns.ac.rs.model.UserCategory;
import uns.ac.rs.repository.CategoryRepository;
import uns.ac.rs.repository.UserCategoriesRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nikola Dakic on 7/20/17.
 */

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository articleCategoryRepository;

    @Autowired
    private UserCategoriesRepository userCategoriesRepository;


    @GetMapping("/articles")
    public List<ArticleCategory> getArticleCategories(){
        return articleCategoryRepository.findAll();
    }

    @GetMapping("/users")
    public List<UserCategory> getUserCategories(){
        return userCategoriesRepository.findAll();
    }

    @PostMapping(value = "/article/add")
    public ResponseEntity<ArticleCategory> add(@RequestBody ArticleCategory articleCategory) throws Exception{

        ArticleCategory superCat = null;

        if(articleCategory.getArticleCategory().getTitle().equals("true")){
            superCat = articleCategoryRepository.findOneByTitle("broad_consumption");
        }else{
            superCat = null;
        }

        articleCategory.setArticleCategory(superCat);
        ArticleCategory artCat = articleCategoryRepository.save(articleCategory);

        return Optional.ofNullable(artCat)
                .map(result -> new ResponseEntity<>(artCat, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping(value = "/article/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception{
        articleCategoryRepository.delete(id);

        ArticleCategory art = null;

        return new ResponseEntity<>(art, HttpStatus.OK);
    }

}
