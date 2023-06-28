package com.springsmallads.service;

import com.springsmallads.entity.Ad;
import com.springsmallads.entity.Category;
import com.springsmallads.entity.Image;
import com.springsmallads.exception.EmptyFieldsException;
import com.springsmallads.exception.NotAdminException;
import com.springsmallads.exception.NotSignInException;
import com.springsmallads.repository.AdRepository;
import com.springsmallads.repository.CategoryRepository;
import com.springsmallads.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private LoginService loginService;

    public boolean createAd(String title, String description, double price, List<Integer> categoriesId, List<MultipartFile> images) throws NotAdminException, NotSignInException, IOException {
        if(loginService.isLogged()){
            if(loginService.isAdmin()){
                if(title == null || description == null || categoriesId == null || categoriesId.size() == 0 || price <= 0){
                    EmptyFieldsException.with("title", "description", "categories", "price");
                }
                Ad ad = Ad.builder().title(title).description(description).price(price).categories((List<Category>) categoryRepository.findAllById(categoriesId)).build();
                adRepository.save(ad);
                if(ad.getId() > 0) {
                    for(MultipartFile img: images) {
                        Image image = Image.builder().url(uploadService.store(img)).ad(ad).build();
                        imageRepository.save(image);
                    }
                    return true;
                }
                return false;
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }


    public List<Ad> getAds() throws NotSignInException {
        if(loginService.isLogged()) {
            return (List<Ad>) adRepository.findAll();
        }
        throw new NotSignInException();
    }

    public List<Ad> searchAds(String search) throws NotSignInException {
        if(loginService.isLogged()) {
            return (List<Ad>) adRepository.searchAdsByTitleLikeOrDescriptionLike(search+"%", "%"+search+"%");
        }
        throw new NotSignInException();
    }

}
