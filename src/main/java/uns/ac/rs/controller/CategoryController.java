package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.ArticleCategory;
import uns.ac.rs.model.UserCategory;
import uns.ac.rs.service.CategoryService;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nikola Dakic on 7/20/17.
 */

@RestController
@RequestMapping("/api/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/articles")
    public List<ArticleCategory> getArticleCategories(){
        return categoryService.getArticleCategories();
    }

    @GetMapping("/users")
    public List<UserCategory> getUserCategories(){
        return categoryService.getUserCategories();
    }

    @PostMapping(value = "/article/add")
    public ResponseEntity<ArticleCategory> add(@RequestBody ArticleCategory articleCategory) throws Exception{

        ArticleCategory superCat = null;

        if(articleCategory.getArticleCategory().getTitle().equals("true")){
            superCat = categoryService.findByTitle("broad_consumption");
        }else{
            superCat = null;
        }

        articleCategory.setArticleCategory(superCat);
        ArticleCategory artCat = categoryService.saveArticleCategory(articleCategory);

        return Optional.ofNullable(artCat)
                .map(result -> new ResponseEntity<>(artCat, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/article/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws Exception{
        categoryService.deleteArticleCategory(id);

        ArticleCategory art = null;

        return new ResponseEntity<>(art, HttpStatus.OK);
    }

    @PostMapping(value = "/user/update")
    public ResponseEntity<UserCategory> update(@RequestBody UserCategory userCategory) throws Exception{

        UserCategory userCat = categoryService.updateUserCategory(userCategory);

        return Optional.ofNullable(userCat)
                .map(result -> new ResponseEntity<>(userCat, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
